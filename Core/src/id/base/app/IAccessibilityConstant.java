package id.base.app;

import id.base.app.util.ReflectionFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dito
 * 
 */
public class IAccessibilityConstant {

	public static int INT_INTERNAL_FUNCTION  = 1;
	
	public static int INT_DASHBOARD = 100;
	
	public static int INT_ADMINISTRATOR 		= 200;
		public static int INT_APP_ROLE			= 210;
		public static int INT_APP_USER			= 220;
		public static int INT_SYSTEM_PARAMETER 	= 230;
		public static int INT_REFERENCE 		= 240;
	
	public static List<Long> MENU_LIST = new ArrayList<>();
	static {
		MENU_LIST.add(Long.valueOf(IAccessibilityConstant.INT_INTERNAL_FUNCTION));
		MENU_LIST.add(Long.valueOf(IAccessibilityConstant.INT_DASHBOARD));
		MENU_LIST.add(Long.valueOf(IAccessibilityConstant.INT_ADMINISTRATOR));
	}
		
	public static void synchronizeFunction(String functionName, int functionNumber){
		if(functionName!=null){
			ReflectionFunction.setProperties(IAccessibilityConstant.class, functionName, String.valueOf(functionNumber));
		}
	}
}
