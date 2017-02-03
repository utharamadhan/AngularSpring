package id.base.app.template;

import id.base.app.util.StringFunction;

public class MerchantIdGenerator {
	
	public static final String SEQUENCE_FORMAT = "00000";
	
	public static String generateMerchantId(String merchantName, long merchantSequence) {
		String result="";
		merchantName=StringFunction.replaceSpecialChars(merchantName, "");
		String[] words = merchantName.trim().split(" ");
		if (words.length == 0) {
			return null;
		}
		if(words.length>1){
			if(words[0].length()>1){
				result = words[0].substring(0,2)+words[1].substring(0,1);
			}else{
				result = words[0].substring(0,1)+words[0].substring(0,1)+words[1].substring(0,1);
			}
		}else{
			if(words[0].length()>1){
				result=result+words[0].substring(0,2);
			}else{
				result=result+words[0].substring(0,1)+words[0].substring(0,1);
			}
			result=result+words[0].substring(words[0].length()-1);
		}
		
		
		result += "-" + StringFunction.formatNumber(merchantSequence, SEQUENCE_FORMAT);
		
		return result.toUpperCase();
	}
}
