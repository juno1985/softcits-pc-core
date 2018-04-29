package org.softcits.pc.core.service;

import java.util.List;

import org.softcits.pc.core.mapper.MbgComputerMapper;
import org.softcits.pc.core.model.MbgComputer;
import org.softcits.pc.core.model.PCPager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
