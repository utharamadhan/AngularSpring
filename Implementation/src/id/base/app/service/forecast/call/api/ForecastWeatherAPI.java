package id.base.app.service.forecast.call.api;

import id.base.app.exception.SystemException;

import java.util.Map;

public interface ForecastWeatherAPI {
	
	public Map<String, Object> callForecastWeather(String coordinate) throws SystemException;

}
