package org.softcits.pc.core.service;

import java.util.List;

import javax.validation.Valid;

import org.softcits.pc.core.exception.PC404Exception;
import org.softcits.pc.core.exception.PC4XXException;
import org.softcits.pc.core.mapper.MbgComputerMapper;
import org.softcits.pc.core.model.MbgComputer;
import org.softcits.pc.core.model.MbgComputerExample;
import org.softcits.pc.core.model.MbgComputerForm;
import org.softcits.pc.core.model.PCPager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
		MbgComputerExample mbgComputerExa = new MbgComputerExample();
		MbgComputerExample.Criteria mbgComputerCri = mbgComputerExa.createCriteria();
		mbgComputerCri.andIdEqualTo(Integer.parseInt(cid));
		List<MbgComputer> mbgComList = mbgComputerMapper.selectByExample(mbgComputerExa);
		if(mbgComList.size() < 0) {
			throw new PC404Exception("PC Not Found");
		}
		return mbgComList.get(0);
	}

}
