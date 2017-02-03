package id.base.app.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;

import javax.persistence.Column;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionFunction {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionFunction.class);
	
    public static String getColumnName(Class domainClass, String fieldName) {
    	try {
			Method method = domainClass.getMethod("get"+Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1), null);
			Column column = method.getAnnotation(Column.class);
			return column.name();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    
    public static void setProperties(Class domainClass, String prop, String value) {
    	try {
			Field field = domainClass.getField(prop);
			if (field.getType().isAssignableFrom(Integer.class) ||
					field.getType().isAssignableFrom(int.class)) {
				field.setInt(null, Integer.valueOf(value));
			} else if (field.getType().isAssignableFrom(Long.class) ||
					field.getType().isAssignableFrom(long.class)) {
				field.setLong(null, Integer.valueOf(value));
			} else if (field.getType().isAssignableFrom(Boolean.class) ||
					field.getType().isAssignableFrom(boolean.class)) {
				field.setBoolean(null, Boolean.parseBoolean(value));
			} else if (field.getType().isAssignableFrom(Double.class) ||
					field.getType().isAssignableFrom(double.class)) {
				field.setDouble(null, Double.parseDouble(value));
			} else {
				// string value
				field.set(null, value);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
			LOGGER.error("Failed setting domain property due to security. Please change checkMemberAccess(this, Member.PUBLIC) or checkPackageAccess() to allow the access.");
		} catch (NoSuchFieldException e) {
			LOGGER.error("Field {} has not been declared yet in {}.", new Object[] {prop, domainClass.getName()});
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			LOGGER.error("Illegal value is given for field {}. The value given is {}", new Object[] {prop, value});
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			LOGGER.error("Failed setting domain property due to field {} is inaccessible.", prop);
		}
    }
    
    public static Object getPropertyValue(Class domainClass, String prop){
    	Object value = null;
    	try {
			Field field = domainClass.getField(prop);
			if (field.getType().isAssignableFrom(Integer.class) ||
					field.getType().isAssignableFrom(int.class)) {
				value = field.getInt(null);
			} else if (field.getType().isAssignableFrom(Long.class) ||
					field.getType().isAssignableFrom(long.class)) {
				value = field.getLong(null);
			} else if (field.getType().isAssignableFrom(Boolean.class) ||
					field.getType().isAssignableFrom(boolean.class)) {
				value = field.getBoolean(null);
			} else if (field.getType().isAssignableFrom(Double.class) ||
					field.getType().isAssignableFrom(double.class)) {
				value = field.getDouble(null);
			} else {
				// string value
				value = field.get(null);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
			LOGGER.error("Failed setting domain property due to security. Please change checkMemberAccess(this, Member.PUBLIC) or checkPackageAccess() to allow the access.");
		} catch (NoSuchFieldException e) {
			try {
				LOGGER.error("Field {} has not been declared yet in {}.", new Object[] {URLEncoder.encode(StringUtils.trimToEmpty(prop), "UTF-8"), domainClass.getName()});
			} catch (UnsupportedEncodingException e1) {
				
				e1.printStackTrace();
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			try {
				if (LOGGER.isErrorEnabled()) LOGGER.error("Failed setting domain property due to field {} is inaccessible.", URLEncoder.encode(StringUtils.trimToEmpty(prop), "UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				
				e1.printStackTrace();
			}
		}
		return value;
    }
    
    public static Field getField(Class cc, String name) throws NoSuchFieldException{
		Field res = null;
		try{
			res = cc.getDeclaredField(name);
			return res;
		}catch(NoSuchFieldException e){
	        Class c = cc.getSuperclass();
	        if (c != null) {
	        	try{
	        		res = getField(c, name);
	        		return res;
	        	}catch(NoSuchFieldException ee){
	        		throw new NoSuchFieldException(name);
	        	}
	        }else{
	        	throw new NoSuchFieldException(name);
	        }
		}
	}
}
