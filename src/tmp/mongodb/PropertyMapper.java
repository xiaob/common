package tmp.mongodb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

public class PropertyMapper {

	public static final PropertyMapper DEFAULT_MAPPER = new PropertyMapper();
	static{
		DEFAULT_MAPPER.addMapping("id", "_id");
	}
	
	private Map<String, String> mapping = new HashMap<String, String>();
	private List<String> exclusion = new ArrayList<String>();
	
	public PropertyMapper(){
		addExclude("class");
	}
	
	public void addExclude(String exclude){
		exclusion.add(exclude);
	}
	
	public void addMapping(String srcKey, String destKey){
		mapping.put(srcKey, destKey);
	}
	
	public void mapping(Map<String, Object> map){
		for(Map.Entry<String, String> entry : mapping.entrySet()){
			replaceProperty(map, entry.getKey(), entry.getValue());
		}
	}
	
	public void reserveMapping(Map<String, Object> map){
		for(Map.Entry<String, String> entry : mapping.entrySet()){
			replaceProperty(map, entry.getValue(), entry.getKey());
		}
	}
	
	private void replaceProperty(Map<String, Object> map, String srcKey, String destKey){
		if(map.containsKey(srcKey)){
			Object value = map.get(srcKey);
			map.remove(srcKey);
			if(value != null){
				if(value instanceof ObjectId){
					map.put(destKey, value.toString());
				}else if(destKey.equals("_id")){
					if(ObjectId.isValid(value.toString())){
						map.put(destKey, new ObjectId(value.toString()));
					}else{
						map.put(destKey, value.toString());
					}
				}else{
					map.put(destKey, value);
				}
			}
		}
	}
	
}
