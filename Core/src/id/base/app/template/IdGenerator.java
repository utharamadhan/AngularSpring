package id.base.app.template;

import id.base.app.util.StringFunction;

public class IdGenerator {
	public static final String SEQUENCE_FORMAT = "00000";
	
	
	public static String generateProductId(String productName, long merchantPk, long productSequence) {
		String result;
		
		String[] words = productName.trim().split(" ");
		if (words.length == 0) {
			return null;
		}
		
		// 1st word
		if (words[0].length() > 1) {
			result = words[0].substring(0,2);
		} else {
			result = words[0] + words[0];
		}
		
		// 2nd word
		if (words.length > 1) {
			// if there's a 2nd word, get the 1st character
			result += words[1].charAt(0);
		} else {
			// no 2nd word, get the last char of the 1st word
			result += words[0].charAt(words[0].length()-1);
		}
		
		// add merchant pk
		result += "-" + StringFunction.formatNumber(merchantPk, SEQUENCE_FORMAT);
		
		// add the product sequence
		result += "-" + StringFunction.formatNumber(productSequence, SEQUENCE_FORMAT);
		
		return result.toUpperCase();
	}
	
	public static String generateProductId(String productId, long skuSequence) {
		String result = productId;
		
		// add the product sequence
		result += "-" + StringFunction.formatNumber(skuSequence, SEQUENCE_FORMAT);
		
		return result;
	}
}
