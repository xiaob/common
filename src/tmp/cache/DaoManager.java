package tmp.cache;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class DaoManager {

	private Map<String, Object> daoCache = new HashMap<String, Object>();
	
 	public <T> T getDao(Class<T> clazz){
 		if(daoCache.containsKey(clazz.getName())){
 			Object value = daoCache.get(clazz.getName());
 			if(value == null){
 				return null;
 			}
 			return clazz.cast(value);
 		}
 		
 		Object obj = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{clazz}, new MethodHandler());
 		if(obj == null){
 			return null;
 		}
 		return clazz.cast(obj);
	}
	
}
