package tmp.algorithm;

public class TestString {

	
	public static void main(String[] args) {
		compareString();

	}

	public static void compareString(){
		String s1 = "wabcadfsf";
		String s2 = "acadfbfws";
		
		System.out.println(equalsString(s1, s2));
	}
	private static boolean equalsString(String s1, String s2){
		if(s1.length() != s2.length()){
			return false;
		}
		
		int result = 0;
		for(int i=0; i<s1.length(); i++){
			result ^= s1.charAt(i)^s2.charAt(i);
		}
		
		return result == 0;
	}
}
