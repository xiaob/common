package com.yuan.common.collection;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ListEnumeration<E> implements Enumeration<E> {
	
	private List<E> list = new ArrayList<E>();
	private int index = -1;
	
	public ListEnumeration(){
		
	}
	
	public void add(E e){
		list.add(e);
	}

	@Override
	public boolean hasMoreElements() {
		if(list.isEmpty()){
			return false;
		}
		
		return index < (list.size() - 1);
	}

	@Override
	public E nextElement() {
		index ++ ;
		return list.get(index);
	}
	
}
