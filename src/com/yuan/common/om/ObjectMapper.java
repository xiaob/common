package com.yuan.common.om;

import java.util.List;
import java.util.Map;

public interface ObjectMapper<E> {

	public <T extends Object> T readObject(E source, Class<T> clazz);
	public <T extends Object> List<T> readObjectList(E source, Class<T> clazz);
	public <K extends Object, V extends Object> Map<K, V> readObjectMap(E source, Class<K> kClass, Class<V> vClass);
	
	public E writeObject(Object obj);
	public E writeObject(Object[] objArr);
	public E writeObject(List<? extends Object> objList);
	public E writeObject(Map<? extends Object, ? extends Object> objMap);
	
}
