package tmp.html;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupTest {

	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.parse(new URL("http://www.baidu.com:80"), 1000*60*3);

		System.out.println("title : " + doc.title());
		
		Elements scripts = doc.getElementsByTag("script");
		for(Element script : scripts){
			System.out.println(script.toString());
		}
		
		Elements links = doc.getElementsByTag("a");
		for (Element link : links) {
			System.out.println(link.toString());
		}

	}

}
