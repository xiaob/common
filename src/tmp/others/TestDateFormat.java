package tmp.others;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class TestDateFormat {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public static void main(String[] args) {
		for(int i=0; i<10; i++){
			new Thread1(sdf).start();
			new Thread2(sdf).start();
		}
        
    } 
	
}


class Thread1 extends Thread{
    private SimpleDateFormat sdf;
    public Thread1(SimpleDateFormat sdf){
        this.sdf = sdf;
    }
    public void run() {
        for(;;){
            try {
            	System.out.println(sdf.parse("2012-02-14"));
				TimeUnit.SECONDS.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    }
}
class Thread2 extends Thread{
    private SimpleDateFormat sdf;
    public Thread2(SimpleDateFormat sdf){
        this.sdf = sdf;
    }
    public void run() {
        for(;;){
           try {
			System.out.println(sdf.parse("2012-02-14"));
			TimeUnit.SECONDS.sleep(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
        }
    }
} 