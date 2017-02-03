package id.base.app.util;

public class IntegerFunction {
	public static boolean isEmpty (Integer i) {
        boolean empty = false;
        if (i == null) {
            empty = true;
        }
        return empty;
    }
	
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
		} catch (Exception e) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	public static Integer concateInteger(Integer front, Integer back){
		String s = "";
		if(null != front){
			s = s.concat(String.valueOf(front));
		}
		if(null != back){
			s = s.concat(String.valueOf(back));
		}
		return Integer.valueOf(s);
	} 
	
	public static Boolean compareSave(Object obj1, Object obj2){
		int o1 = 0;
		int o2 = 0;
		if(obj1 instanceof Integer){
			if(!isEmpty((Integer) obj1)){
				o1 = ((Integer) obj1).intValue();
			}
		}else {
			o1 = (int) obj1;
		}
		if(obj2 instanceof Integer){
			if(!isEmpty((Integer) obj2)){
				o2 = ((Integer) obj2).intValue();
			}
		}else {
			o2 = (int) obj2;
		}
		
		return o1 == o2;
	}

}