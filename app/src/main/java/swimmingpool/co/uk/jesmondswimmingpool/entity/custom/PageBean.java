package swimmingpool.co.uk.jesmondswimmingpool.entity.custom;

import java.util.List;

public class PageBean<T> {

	private Integer currentPage;
	private Integer totalPage;
	/**
	 * this.currentPage*this.pageSize;
	 */
	private Integer startCount;
	/**
	 * 页面的起始数字和结束数字
	 */
	private Integer beginNumber;
	public Integer getBeginNumber() {
		return beginNumber;
	}
	public void setBeginNumber(Integer beginNumber) {
		this.beginNumber = beginNumber;
	}
	public Integer getEndNumber() {
		return endNumber;
	}
	public void setEndNumber(Integer endNumber) {
		this.endNumber = endNumber;
	}
	private Integer endNumber;
	
	public Integer getStartCount() {
		return startCount;
	}
	public void setStartCount(Integer startCount) {
		this.startCount = startCount;
	}
	private Integer totalCount;
	private List<T> beans;
	private Integer pageSize;
	
	
	
	public PageBean(Integer currentPage, Integer totalCount, Integer pageSize) {
		
		this.currentPage = currentPage;
		
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		if(pageSize==null || pageSize<=0) 
		{
			this.pageSize=10;
			
		}
		this.totalPage = (totalCount+ this.pageSize-1)/this.pageSize;
		
		if(currentPage==null)
			this.currentPage=0;
	/*	System.out.println(this.currentPage  );
		System.out.println(this.totalPage  );
		System.out.println(this.totalCount   );*/
		
		if(	this.currentPage>=totalPage)
			this.currentPage=(this.totalPage-1);
		
		if(	this.currentPage<0)
			this.currentPage=0;
		
	   	startCount=	this.currentPage*this.pageSize;
	   	
	   	
	   	if(this.currentPage<=5 || totalPage <=10) {
	   		beginNumber=0;
	   		endNumber=this.totalPage;
	   	}else {
	   							
	   		endNumber=  this.currentPage+4;
	   		if(endNumber>totalPage)
	   			endNumber=totalPage;
	   		
	   		beginNumber=endNumber-9;
	   		if(beginNumber<0)
	   			beginNumber=0;
	   		
	   	}
	   	
	   	
		
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public List<T> getBeans() {
		return beans;
	}
	public void setBeans(List<T> beans) {
		this.beans = beans;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
