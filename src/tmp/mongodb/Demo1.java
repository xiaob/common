package tmp.mongodb;

import java.net.UnknownHostException;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class Demo1 {

	public static void main(String[] args) throws UnknownHostException, MongoException {
		Mongo mongo = new Mongo("127.0.0.1", 27017);
		DB db = mongo.getDB("test");
		DBCollection users = db.getCollection("users");
		
//		WriteConcern.SAFE
		
		add(users);
//		remove(users);
//		update(users);
		find(users);
		mongo.close();
	}
	
	public static void add(DBCollection users){
		DBObject user = new BasicDBObject();
		user.put("name", "zhang");
		user.put("age", 20);
		
		users.insert(user);
	}
	
	public static void remove(DBCollection users){
		users.remove(new BasicDBObject("age", new BasicDBObject("$gte", 24)));
	}
	public static void update(DBCollection users){
		DBObject user = findById(users , "4f5ef26b1fad3178996838ce");
		user.put("age", 99);
		users.update(new BasicDBObject("_id", new ObjectId("4f5ef26b1fad3178996838ce")), user);
	}
	public static void find(DBCollection users){
		DBCursor cursor = users.find();
		while(cursor.hasNext()){
			DBObject user = cursor.next();
			System.out.println(user.get("name") + " : " + user.get("age"));
			System.out.println(user.toMap());
		}
	}
	public static DBObject findById(DBCollection users, String id){
		
		return users.findOne(new BasicDBObject("_id", new ObjectId(id)));
	}
	
	public static void test1(){
		DBObject query = new BasicDBObject();
		DBObject d = new BasicDBObject();
		d.put("imgPath", null);
		d.put("imgPath", "");
		query.put("$or", d);
	}

}
