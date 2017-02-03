package id.base.app.util;

import id.base.app.annotation.DownloadField;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationFunction {
	private static final Logger logger = LoggerFactory.getLogger(AnnotationFunction.class);

	public static List<String> getDownloadFields(Class domainClass) {
		//List<String> downloadFields = new ArrayList<String>();
    	Field[] fields = domainClass.getDeclaredFields();
    	int size = 0;
    	int annotationSize = 0;
    	for(Field field : fields){
    		Annotation[] annotations = field.getAnnotations();
    		for (Annotation annotation : annotations) {
				if(annotation instanceof DownloadField){
					size++;
				}
			}
    	}
    	String[] downloadFields = new String[size];
    	for(Field field : fields){
    		Annotation[] annotations = field.getAnnotations();
    		for (Annotation annotation : annotations) {
				if(annotation instanceof DownloadField){
					DownloadField df = (DownloadField) annotation;
					if(df.isForDownload()){
						downloadFields[df.position()] = df.headerName();
					}
				}
    		}
    	}
    	
    	List<String> dlFields = Arrays.asList(downloadFields);
    	return dlFields;
    }
	
	public static int getPropertyLength(Class domainClass, String propertyName){
		int length = 0;
		Method method;
		try {
			method = domainClass.getDeclaredMethod("get" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1), null);
			Column col = method.getAnnotation(Column.class);
			length = col.length();
		} catch (SecurityException e) {
			logger.error(e.getCause().toString(),e);
		} catch (NoSuchMethodException e) {
			logger.error(e.getCause().toString(),e);
		}
		return length;
	}
	
	public static Map<String, String> getMapDownloadFields(Class domainClass) {
		//List<String> downloadFields = new ArrayList<String>();
		Map<String, String> maps = new HashMap<String, String>();
		
    	Field[] fields = domainClass.getDeclaredFields();
    	int size = 0;
    	int annotationSize = 0;
    	for(Field field : fields){
    		Annotation[] annotations = field.getAnnotations();
    		for (Annotation annotation : annotations) {
				if(annotation instanceof DownloadField){
					size++;
				}
			}
    	}
    	String[] downloadFields = new String[size];
    	for(Field field : fields){
    		Annotation[] annotations = field.getAnnotations();
    		for (Annotation annotation : annotations) {
				if(annotation instanceof DownloadField){
					DownloadField df = (DownloadField) annotation;
					if(df.isForDownload()){
						maps.put(df.headerName(), field.getName());
					}
				}
    		}
    	}
    	
    	return maps;
    }
}
