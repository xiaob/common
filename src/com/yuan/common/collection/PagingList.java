package com.yuan.common.collection;

import java.util.List;

public class PagingList<T> {
	
	private List<T> list;
	private Integer pageNo = 0;
	private Integer pageSize = -1;
	
	public PagingList(List<T> list, Integer pageSize){
		this.list = list;
		this.pageSize = pageSize;
	}
	
	public List<T> goToPage(Integer pageNo){
		if(!isLegalPageNo(pageNo)){
			return null;
		}
		this.pageNo = pageNo;
		return moreList();
	}
	
	private List<T> moreList(){
		int fromIndex = pageNo*pageSize;
		int toIndex = fromIndex + pageSize;
		if(pageSize < 0){
			fromIndex = pageNo*list.size();
			toIndex = list.size();
		}
		if(toIndex > list.size()){
			toIndex = list.size();
		}
		
		return list.subList(fromIndex, toIndex);
	}
	
	public List<T> moreList(Integer beginIndex){
		int fromIndex = beginIndex;
		int toIndex = fromIndex + pageSize;
		if(pageSize < 0){
			toIndex = list.size();
		}
		if(toIndex > list.size()){
			toIndex = list.size();
		}
		
		return list.subList(fromIndex, toIndex);
	}
	
	public List<T> getCurrentPage(){
		return moreList();
	}
	
	public List<T> nextPage(){
		if(!isLegalPageNo(pageNo + 1)){
			return null;
		}
		
		pageNo++;
		return moreList();
	}
	
	public List<T> previousPage(){
		if(!isLegalPageNo(pageNo - 1)){
			return null;
		}
		
		pageNo--;
		return moreList();
	}
	
	private boolean isLegalPageNo(Integer pageNo){
		return (pageNo >= 0) && (pageNo < getPageCount());
	}
	
	public Integer getPageCount(){
		if(pageSize < 0 || list.size() < pageSize){
			return 1;
		}
		
		Integer pageCount = list.size()/pageSize;
		if(list.size()%pageSize != 0){
			pageCount ++;
		}
		return pageCount;
	}
	
	public static <T extends Object> List<T> moreList(List<T> list, Integer beginIndex, Integer pageSize){
		int fromIndex = beginIndex;
		int toIndex = fromIndex + pageSize;
		if(pageSize < 0){
			toIndex = list.size();
		}
		if(toIndex > list.size()){
			toIndex = list.size();
		}
		return list.subList(fromIndex, toIndex);
	}

}
