package org.softcits.pc.core.service;

import java.util.List;

import javax.validation.Valid;

import org.softcits.pc.core.mapper.MbgComputerMapper;
import org.softcits.pc.core.model.MbgComputer;
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
		
		BeanUtils.copyProperties(mbgComputerForm, mbgComputer);
		mbgComputerMapper.insert(mbgComputer);
	}

}
