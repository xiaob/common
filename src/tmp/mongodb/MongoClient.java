package tmp.mongodb;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.util.JSON;
import com.yuan.common.util.StringUtil;

public class MongoClient {

	private Mongo mongo;
	private DB db;
	
	public MongoClient(String ip, int port) throws UnknownHostException, MongoException{
		mongo = new Mongo(ip, port);
	}
	
	public MongoClient(List<ServerAddress> seeds) throws MongoException {
		mongo = new Mongo(seeds);
	}
	
	public void use(String dbName){
		db = mongo.getDB(dbName);
	}
	
	public MongoOptions getOptions(){
		return mongo.getMongoOptions();
	}
	
	public Object insert(String collection, Object model, PropertyMapper propertyMapper)
			throws Exception {
		DBObject obj = MongoDbUtil.toDBObject(model, propertyMapper);
		db.getCollection(collection).insert(obj);

		return obj.get("_id");
	}

	public Object insert(String collection, Map<String, Object> model) throws Exception {
		DBObject obj = new BasicDBObject(model);
		db.getCollection(collection).insert(obj);

		return obj.get("_id");
	}
	
	public Object insert(String collection, DBObject obj) {
		db.getCollection(collection).insert(obj);

		return obj.get("_id");
	}
	
	public <T> List<T> find(String collection, String query, String sort, Integer limit, Class<T> clazz, PropertyMapper propertyMapper) throws Exception{
		DBObject queryDbObject = null;
		if(StringUtil.hasText(query)){
			queryDbObject = (DBObject)JSON.parse(query);
		}
		DBObject sortDbObject = null;
		if(StringUtil.hasText(sort)){
			sortDbObject = (DBObject)JSON.parse(sort);
		}
		
		return find(collection, queryDbObject, sortDbObject, limit, clazz, propertyMapper);
	}
	
	public DBObject findOne(String collection, DBObject query)throws Exception{
		DBCollection dbCollection = db.getCollection(collection);
		return dbCollection.findOne(query);
	}
	
	public <T> T findOne(String collection, DBObject query, Class<T> clazz, PropertyMapper propertyMapper) throws Exception {
		DBCollection dbCollection = db.getCollection(collection);
		return MongoDbUtil.toBean(dbCollection.findOne(query), clazz, propertyMapper);
	}
	
	public <T> List<T> find(String collection, DBObject query, DBObject sort, Integer limit, Class<T> clazz, PropertyMapper propertyMapper) throws Exception{
		DBCollection dbCollection = db.getCollection(collection);
		DBCursor cursor = null;
		if(query != null){
			cursor = dbCollection.find(query);
		}else{
			cursor = dbCollection.find();
		}
		
		if(sort != null){
			cursor = cursor.sort(sort);
		}
		if(limit != null){
			cursor = cursor.limit(limit);
		}
		
		return MongoDbUtil.list(cursor, clazz, propertyMapper);
	}
	
	public Integer count(String collection, DBObject query){
		DBCollection dbCollection = db.getCollection(collection);
		DBCursor cursor = null;
		if(query != null){
			cursor = dbCollection.find(query);
		}else{
			cursor = dbCollection.find();
		}
		
		return cursor.count();
	}
	
	public DBObject group(String collection, DBObject key, DBObject initial, DBObject condition, String reduce, String finalize){
		DBCollection dbCollection = db.getCollection(collection);
		
		return dbCollection.group(key, condition, initial, reduce, finalize);
	}
	
	public void update(String collection, DBObject q, DBObject o){
		DBCollection dbCollection = db.getCollection(collection);
		dbCollection.update(q, o, false ,true);
		
	}
	
	public void upsert(String collection, DBObject q, DBObject o){
		DBCollection dbCollection = db.getCollection(collection);
		dbCollection.update(q, o, true, true);
	}
	
	public void upload(String fileName) throws IOException{
		GridFS gridFS = new GridFS(db);
		gridFS.createFile(new File(fileName)).save();
	}
	
	public void download(String fileName, String path) throws IOException{
		GridFS gridFS = new GridFS(db);
		GridFSDBFile gridFSDBFile = gridFS.findOne(fileName);
		gridFSDBFile.writeTo(path);
	}
	
	public void remove(String collection, String id) {
		DBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(id));
		db.getCollection(collection).remove(query);
	}
	
	public DBCollection getDBCollection(String collection){
		return db.getCollection(collection);
	}
	
	public DB getDB(){
		return  db;
	}
	
	public void close(){
		if(mongo != null){
			mongo.close();
		}
	}
	
	public static void main(String[] args){
		System.out.println(JSON.parse("{\"age\":20}").getClass().getName());
	}
	
}
