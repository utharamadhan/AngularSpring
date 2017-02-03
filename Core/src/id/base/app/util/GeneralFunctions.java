package id.base.app.util;

import java.util.Random;
import java.util.UUID;

public class GeneralFunctions {
	
	
	public static String getDefaultPassword(int min, int max){
		UUID uuid = UUID.randomUUID();
		String myRandom = uuid.toString();
		Random random = new Random(System.currentTimeMillis());
		int result = min + random.nextInt(max - min + 1);
		return myRandom.substring(0,result);
	}
	
	
	public static void main(String[] args) {
		System.out.println(safeEqual(null, "test"));
	}
	
	public static boolean safeEqual(Object obj1, Object obj2) {
		if (obj1 == null && obj2 == null) {
			return true;
		}
		if((obj1 instanceof String) || (obj2 instanceof String)){
			if(obj1==null) obj1="";
			if(obj2==null) obj2="";
		}else if (obj1 == null || obj2 == null) {
			return false;
		}
		return obj1.equals(obj2);
	}
	
}
