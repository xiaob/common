package tmp.net.hc;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Async;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.concurrent.FutureCallback;

public class Demo1 {

	public static void main(String[] args) {
		test2();
	}
	
	public static void test1(){
		try {
			Response response = Request.Get("http://www.baidu.com").execute();
			System.out.println(response.returnContent().asString());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void test2(){
		Async async = Async.newInstance();
		async.use(Executors.newSingleThreadExecutor());
		async.execute(Request.Get("http://www.baidu.com"), new FutureCallback<Content>() {
			@Override
			public void failed(Exception ex) {
				ex.printStackTrace();
			}
			
			@Override
			public void completed(Content content) {
				System.out.println("========================= " + Thread.currentThread().getName());
				System.out.println(content.asString());
			}
			
			@Override
			public void cancelled() {
				
			}
		});
		System.out.println("111111111111111111111111111 " + Thread.currentThread().getName());
		
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void test3(){
		Form form = Form.form();
		form.add("1", "1");
		form.add("2", "2");
		List<NameValuePair> list = form.build();
		
		System.out.println(list);
	}

}
