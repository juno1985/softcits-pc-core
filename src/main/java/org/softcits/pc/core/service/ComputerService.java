package org.softcits.pc.core.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;
import org.softcits.pc.mgt.common.SoftcitsJsonUtil;
import org.softcits.pc.core.exception.PC404Exception;
import org.softcits.pc.core.exception.PC4XXException;
import org.softcits.pc.core.mapper.MbgComputerMapper;
import org.softcits.pc.core.model.MbgComputer;
import org.softcits.pc.core.model.MbgComputerExample;
import org.softcits.pc.core.model.MbgComputerForm;
import org.softcits.pc.core.model.MbgComputerUpdateForm;
import org.softcits.pc.core.model.PCPager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.transaction.annotation.Isolation;
//需要加入事务管理
@Transactional(value = "transactionManager", rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, timeout = 300)
@Service
public class ComputerService {
	
	@Autowired
	private MbgComputerMapper mbgComputerMapper;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Value("${PC_ID_REDIS}")
	private String PC_ID_REDIS;
	
	//返回所有的Computer数组
	public PCPager<MbgComputer> getAllComputers(String pageSize, String pageNum){
		PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
		List<MbgComputer> mbgComputerList = mbgComputerMapper.selectByExample(null);
		
		PCPager<MbgComputer> pcPager = new PCPager<>();
		pcPager.setData(mbgComputerList);
		pcPager.setPageNum(Integer.parseInt(pageNum));
		//查询数据库中所有的条数
		Long totalRows = mbgComputerMapper.countByExample(null);
		pcPager.setTotalRows(totalRows);
		
		return pcPager;
	}

	//根据pc主键删除pc
	public String deleteById(String cid) {
		mbgComputerMapper.deleteByPrimaryKey(Integer.parseInt(cid));
		return "Success";
	}

	//添加商品
	public void add(@Valid MbgComputerForm mbgComputerForm) {
		MbgComputer mbgComputer = new MbgComputer();
	/*	mbgComputer.setTrademark(mbgComputerForm.getTrademark());
		mbgComputer.setPrice(mbgComputerForm.getPrice());
		mbgComputer.setPic(mbgComputerForm.getPic());*/
		
		String tradeMark = mbgComputerForm.getTrademark();
		
		MbgComputerExample mbgComputerExa = new MbgComputerExample();
		MbgComputerExample.Criteria mbgComputerCri = mbgComputerExa.createCriteria();
		mbgComputerCri.andTrademarkEqualTo(tradeMark);
		List<MbgComputer> mbgComputerList = mbgComputerMapper.selectByExample(mbgComputerExa);
		//如果已经存在该商品名称则不允许添加
		if(mbgComputerList.size() > 0 ) {
			throw new PC4XXException("PC Already Exist");
		}
		
		
		BeanUtils.copyProperties(mbgComputerForm, mbgComputer);
		mbgComputerMapper.insert(mbgComputer);
	}

	public MbgComputer queryComputerById(String cid) {
		
		String key = PC_ID_REDIS + ":" + cid;
		
		//判断是否在redis中
		if(stringRedisTemplate.hasKey(key)) {
			String pcJson = stringRedisTemplate.opsForValue().get(key);
			
			//重置过期时间
			stringRedisTemplate.expire(key, 60, TimeUnit.MINUTES);
			
			//需要将JSON字符串转化为MbgComputer对象
			return SoftcitsJsonUtil.jsonToPojo(pcJson, MbgComputer.class);
		}
		
		//Redis中没有查到的话则查询数据库
		MbgComputerExample mbgComputerExa = new MbgComputerExample();
		MbgComputerExample.Criteria mbgComputerCri = mbgComputerExa.createCriteria();
		mbgComputerCri.andIdEqualTo(Integer.parseInt(cid));
		List<MbgComputer> mbgComList = mbgComputerMapper.selectByExample(mbgComputerExa);
		if(mbgComList.size() < 0) {
			throw new PC404Exception("PC Not Found");
		}
		//将MbgComputer转化为JSON字符串然后存进Redis
		MbgComputer mbgComputer = mbgComList.get(0);
		String pcJson = SoftcitsJsonUtil.objectToJson(mbgComputer);
		
		//然后将数据写入Redis
		stringRedisTemplate.opsForValue().set(key, pcJson, 60, TimeUnit.MINUTES);
		
		return mbgComList.get(0);
	}

	public void update(@Valid MbgComputerUpdateForm mbgComputerUpdateForm) {
		
		//更新前需要先判断商品是否存在
		Integer cid = mbgComputerUpdateForm.getId();
		@SuppressWarnings("unchecked")
		MbgComputer mbgComputer = mbgComputerMapper.selectByPrimaryKey(cid);
		
		if(mbgComputer == null) {
			throw new PC404Exception("PC Not Found");
		}
		mbgComputerUpdateForm.setPic(mbgComputer.getPic());
		//如果存在则进行商品更新
		BeanUtils.copyProperties(mbgComputerUpdateForm, mbgComputer);
		mbgComputerMapper.updateByPrimaryKey(mbgComputer);
	}

}
