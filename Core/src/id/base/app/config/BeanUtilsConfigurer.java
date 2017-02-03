package id.base.app.config;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.ShortConverter;

/**
 * Provides default converter for BeanUtils as needed.
 * There are two types: primitive and object converter.
 * Primitive are for converting to primitive values (e.g. int, double, etc)
 * Object converter are for converting to object values (e.g. Integer, Double, etc)
 * @author sanz
 *
 */
public class BeanUtilsConfigurer {
	static IntegerConverter integerConverter = new IntegerConverter(null);
	static IntegerConverter integerConverterPrimitive = new IntegerConverter(0);
	static DoubleConverter doubleConverter = new DoubleConverter(null);
	static DoubleConverter doubleConverterPrimitive = new DoubleConverter(0);
	static LongConverter longConverter = new LongConverter(null);
	static LongConverter longConverterPrimitive = new LongConverter(0);
	static FloatConverter floatConverter = new FloatConverter(null);
	static FloatConverter floatConverterPrimitive = new FloatConverter(0);
	static BooleanConverter booleanConverter =new BooleanConverter(null);
	static BooleanConverter booleanConverterPrimitive =new BooleanConverter(false);
	static ShortConverter shortConverter = new ShortConverter(null);
	static ShortConverter shortConverterPrimitive = new ShortConverter(0);
	
	public static void configure() {
        ConvertUtils.register(integerConverterPrimitive, Integer.TYPE);
		ConvertUtils.register(integerConverter, Integer.class);
		ConvertUtils.register(longConverterPrimitive, Long.TYPE);
		ConvertUtils.register(longConverter, Long.class);
		ConvertUtils.register(doubleConverterPrimitive, Double.TYPE);
		ConvertUtils.register(doubleConverter, Double.class);
		ConvertUtils.register(floatConverterPrimitive, Float.TYPE);
		ConvertUtils.register(floatConverter, Float.class);
		ConvertUtils.register(booleanConverterPrimitive, Boolean.TYPE);
		ConvertUtils.register(booleanConverter, Boolean.class);
		ConvertUtils.register(shortConverterPrimitive, Short.TYPE);
        ConvertUtils.register(shortConverter, Short.class);
        
//        ConvertUtils.register(dateConverter, Date.class);
	}
}
