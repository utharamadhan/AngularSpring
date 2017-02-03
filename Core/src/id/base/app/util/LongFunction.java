/**
 * 
 */
package id.base.app.util;


/**
 * @author Hendri Yauw
 *
 */
public class LongFunction {

	/**
	 * @param argument (long[])
	 * @return Long[]
	 */
	public static Long[] splitIntoLongObject(long[] argument) {
		long [] largument = (long[])argument;
		Long Largument [] = new Long[largument.length];
		int i=0;
		 
		for(long temp:largument){
		    Largument[i++] = temp;
		}
		return Largument;
    }
	
	public static boolean isEmpty (Long l) {
        boolean empty = false;
        if (l == null) {
            empty = true;
        }
        return empty;
    }
	
	/**
	 * calculate long value from two object
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static Long longCalValue(Long obj1, Long obj2){
		Long result = 0l;
		if(null != obj1){
			result = result+obj1;
		}
		if(null != obj2){
			result = result+obj2;
		}
		return result;
	}
	
	/** save compare between two long object
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static Boolean longCompare(Long obj1, Long obj2){
		Boolean result = false;
        if (obj1 == null) {
            if (obj2 == null) {
                result = true;
            } else {
                result = false;
            }
        } else {
            result = obj1.equals(obj2);
        }
		return result;
	}

}
