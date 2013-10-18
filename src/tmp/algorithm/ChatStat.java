package tmp.algorithm;

public class ChatStat {

	public static void main(String[] args) {
		int c = 0;
		for(int i =1; i<=100; i++){//灯
			if((count(i) % 2) != 0){//被按了奇数次
				c++;
				System.out.println(i + "号灯亮着！");
			}
		}
		System.out.println("共有"+c+"盏灯亮着");
	}
	
	//被整除次数,被按了几次
	public static int count(int i){//人
		int c = 0;
		for(int j=1; j<=100; j++){
			if(j< i) continue;
			if(j%i == 0) c++;
		}
		return c;
	}

}
