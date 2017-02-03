package id.base.app.util;

import id.base.app.SystemParameter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class NumericFunction {
	static NumberFormat decimalFormat;
	
	public static String formatCurrency(Double num) {
		if (num == null) {
			return "";
		}
		decimalFormat = NumberFormat.getInstance(new Locale(SystemParameter.LOCALE));
		return decimalFormat.format(num);
	}
	
	public static String formatDouble(Double num, String pattern) {
		if (num == null) {
			return "";
		}
		NumberFormat decimalFormat = new DecimalFormat(pattern);
		return decimalFormat.format(num);
	}
	
	/**
	 * @return valueOne * valueTwo, prevent floating point precision issue
	 */
	public static double multiply(double valueOne, double valueTwo){
		try{
			return BigDecimal.valueOf(valueOne).multiply(BigDecimal.valueOf(valueTwo)).doubleValue();
		}catch(Exception e){
			return 0d;
		}
	}
	
	public static double divide(double valueOne, double valueTwo){
		try{
			return BigDecimal.valueOf(valueOne).divide(BigDecimal.valueOf(valueTwo), 6, RoundingMode.HALF_UP).doubleValue();
		}catch(Exception e){
			return 0d;
		}
	}
	
	public static double add(double valueOne, double valueTwo){
		try{
			return BigDecimal.valueOf(valueOne).add(BigDecimal.valueOf(valueTwo)).doubleValue();
		}catch(Exception e){
			return 0d;
		}
	}
	
	public static double substract(double valueOne, double valueTwo){
		try{
			return BigDecimal.valueOf(valueOne).subtract(BigDecimal.valueOf(valueTwo)).doubleValue();
		}catch(Exception e){
			return 0d;
		}
	}
	
	public static BigDecimal toBigDecimal(String doubleValue) {
		try {
			return BigDecimal.valueOf(Double.valueOf(doubleValue));
		} catch (Exception e) {}
		return null;
	}
}
