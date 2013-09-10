package tmp.net.socket;

import java.io.IOException;

import com.yuan.common.file.FileUtil;
import com.yuan.common.util.HttpUtil;

public class HttpDemo {

	public static void main(String[] args)throws Exception {
//		System.out.println(HttpUtil.doGet("http://www.baidu.com", null, "GBK"));
		uplaodImage();
	}
	
	public static void uplaodImage(){
		try {
			byte[] data = FileUtil.readBinaryFile("d:/1.JPG");
			String urlPath = "http://192.168.5.73:8080/ameba_service/uploadservice?uploadType=player_photo&photoType=3&passportId=10001";
			HttpUtil.doPost(urlPath, "application/octet-stream", data, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
