package tmp.others;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatTest {

	public static void main(String[] args) {
		String s = "can not find npc:{{Npc_{0}_name}} at scene {{Scene_{1}_name}} controler {{SceneControler_{2}_name}}";
		
		System.out.println(format(s, 1, 2, 3));
	}
	
	public static String format(String text, Object... args){
		String s1 = text.replaceAll("\\{\\{", "'{'");
		String s2 = s1.replaceAll("\\}\\}", "'}'");
		String s3 = MessageFormat.format(s2, args);
		
		Pattern pattern = Pattern.compile("\\{([a-zA-Z]+)_(\\d+)_([a-zA-Z]+)\\}");
		Matcher matcher = pattern.matcher(s3);
		StringBuffer sb = new StringBuffer();
		while(matcher.find()){
			String replacement = process(matcher.group(1), matcher.group(2), matcher.group(3));
			matcher.appendReplacement(sb, replacement);
		}
		matcher.appendTail(sb);
		
		return sb.toString();
	}
	
	private static String process(String objName, String id, String propertyName){
		System.out.println(objName + ", " + id + ", " + propertyName);
		return "===";
	}

}
