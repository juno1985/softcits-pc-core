package org.softcits.pc.core.model;

import java.util.List;

public class PCPager<T> {
	//当前页码
	private Integer pageNum;
	//总共条数
	private Long totalRows;
	//业务数据
	private List<T> data;
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Long getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(Long totalRows) {
		this.totalRows = totalRows;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	
	

}
