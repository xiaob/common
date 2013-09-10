package com.yuan.common.om.binary;

import java.io.InputStream;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

public class JsonTest2 {

	public static void main(String[] args)throws Exception {
		test3();
	}
	
	public static void test1()throws Exception{
		JsonFactory jsonF = new JsonFactory();

        // let's write to a file, using UTF-8 encoding (only sensible one)
        StringWriter strw = new StringWriter();
        JsonGenerator jg = jsonF.createJsonGenerator(strw);
        jg.useDefaultPrettyPrinter(); // enable indentation just to make debug/testing easier

        jg.writeStartObject();
        // can either do "jg.writeFieldName(...) + jg.writeNumber()", or this:
        jg.writeNumberField("id", 1);
        jg.writeStringField("text", "test");
        jg.writeNumberField("fromUserId", 2);
        jg.writeNumberField("toUserId", 3);
        jg.writeStringField("langugeCode", "zh");
        jg.writeNullField("t");
//        jg.writeRaw("\"raw\" : [{},{}]");
        byte[] data = new byte[2];
        data[0] = 10;
        data[1] = 20;
        jg.writeBinaryField("b", data);
        jg.writeEndObject();
        jg.close();

        System.out.println(strw.toString());
	}
	
	public static void test2()throws Exception{
		ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
//		mapper.registerSubtypes(Student.class);
		SerializationConfig sc = mapper.getSerializationConfig();
        sc.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.setSerializationConfig(sc);
        
        String serialValue = mapper.writeValueAsString(new Student("wang", 20));
		System.out.println(serialValue);
		
		serialValue = mapper.writeValueAsString(new Grade());
		System.out.println(serialValue);
	}
	
	public static void test3()throws Exception{
		ObjectMapper m = new ObjectMapper();
		InputStream is = JsonTest2.class.getResourceAsStream("/user.json");
		JsonNode rootNode = m.readValue(is, JsonNode.class);
		System.out.println(rootNode.has("name"));
		System.out.println(rootNode.path("name").path("last").getTextValue());
		System.out.println(rootNode.path("verified").getBooleanValue());
		System.out.println(rootNode.path("userImage").getBinaryValue());
	}

}
