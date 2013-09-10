package tmp.mongodb;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.yuan.common.util.ReflectUtil;

public class MongoDbUtil {

	public static DBObject toDBObject(Object bean, PropertyMapper propertyMapper) throws Exception{
		if(bean == null){
			return null;
		}
		
		Map<String, Object> map = ReflectUtil.describe(bean);
		if(propertyMapper != null){
			propertyMapper.mapping(map);
		}
		Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
		while(it.hasNext()){
			if(it.next().getValue() == null){
				it.remove();
			}
		}
		
		DBObject dbObject = new BasicDBObject(map);
		
		return dbObject;
	}
	
	public static <T> T toBean(DBObject dbObject, Class<T> clazz, PropertyMapper propertyMapper)throws Exception{
		if(dbObject == null){
			return null;
		}
		
		Map<String, Object> map = convertMap(dbObject.toMap());
		if(propertyMapper != null){
			propertyMapper.reserveMapping(map);
		}
		return ReflectUtil.fillPOJO(clazz, map);
	}
	private static Map<String, Object> convertMap(Map<?, ?> map){
		Map<String, Object> newMap = new HashMap<String, Object>();
		for(Map.Entry<?, ?> entry : map.entrySet()){
			newMap.put(String.class.cast(entry.getKey()), Object.class.cast(entry.getValue()));
		}
		return newMap;
	}
	
	public static <T> List<T> list(DBCursor cursor, Class<T> clazz, PropertyMapper propertyMapper)throws Exception{
		List<T> list = new ArrayList<T>();
		
		while(cursor.hasNext()){
			DBObject dbObject = cursor.next();
			list.add(toBean(dbObject, clazz, propertyMapper));
		}
		
		return list;
	}
}
