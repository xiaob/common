package tmp.others;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProbTest {

	public static void main(String[] args) {
		int count = 0;
		int total = 200000;
		for(int i=0; i<total; i++){
			List<Integer> list = roll();
			System.out.println(list);
			if(containsHouse(list)){
				count ++;
			}
		}
		
		System.out.println(count);
		System.out.println(count/(double)total);
	}
	
	public static List<Integer> roll(){
		List<Integer> list = new ArrayList<Integer>();
		
		WeightRoll weightRoll = new WeightRoll(0.11f);
		for(int i=0; i<6; i++){
			list.add(weightRoll.roll());
		}
		
		return list;
	}
	
	public static boolean containsHouse(List<Integer> list){
		return list.contains(1);
	}

}

class WeightRoll{
	
	private static Random r = new Random();
	
	private List<Float> list = new ArrayList<Float>();
	
	public WeightRoll(float prob){
		float p = (1 - prob)/5;
		float sum = prob;
		list.add(sum);
		for(int i=0; i<5; i++){
			sum += p;
			list.add(sum);
		}
	}
	
	public int roll(){
		float p = r.nextFloat();
		for(int i=0; i<list.size(); i++){
			float f = list.get(i);
			if(p <= f){
				return i + 1;
			}
		}
		
		return list.size();
	}
}
