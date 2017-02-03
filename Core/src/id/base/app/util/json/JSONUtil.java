package id.base.app.util.json;

import id.base.app.util.StringFunction;

public class JSONUtil {
	
	/**
	 * @param propertyName
	 * @param jsonBody
	 * @return jsonValue of the property, and return null if not found
	 */
	public static String getString(String propertyName, String jsonBody) {
		try{					
			JSONObject json = new JSONObject(jsonBody);
			return StringFunction.isEmpty(json.getString(propertyName)) ? null : json.getString(propertyName);
		}catch(Exception e){
			return null;
		}
	}
	
}
