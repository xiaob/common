package tmp.thrift;

public class T1 {

	public static void main(String[] args) {
		t1(new SyncValue<String>());
	}
	
	@SuppressWarnings("unchecked")
	public static void t1(Object obj){
		SyncValue.class.cast(obj).put("11");
		
	}
	
	private static void t2(SyncValue<String> syncValue){
		
	}

}
