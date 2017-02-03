package id.base.app.service.forecast.call.api;

import id.base.app.SystemParameter;
import id.base.app.exception.SystemException;
import id.base.app.rest.PathInterfaceRestCaller;
import id.base.app.util.DateTimeFunction;
import id.base.app.util.StringFunction;
import id.base.app.util.json.JSONArray;
import id.base.app.util.json.JSONObject;
import id.base.app.util.json.JSONUtil;
import id.base.app.valueobject.forecast.ForecastCallDaily;
import id.base.app.valueobject.forecast.ForecastCallHourly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

@Service
@Qualifier("ForecastWeatherService")
public class ForecastWeatherService implements ForecastWeatherAPI {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ForecastWeatherService.class);
	
	private ObjectMapper mapper = new ObjectMapper();
	
	protected RestTemplate rt = new RestTemplate();
	
	@Override
	public Map<String, Object> callForecastWeather(final String coordinate) throws SystemException {
		Map<String, Object> returnMap = new HashMap<>();
		try {
			ResponseEntity response = firstCall(coordinate);
			returnMap.put("hourly", convertToHourly(response));
			returnMap.put("daily", convertToDaily(response));
			response = secondCall(coordinate, DateTimeFunction.truncateDate(new Date()).getTime() / 1000);
			returnMap.put("hourlyTimeMachine", convertToHourly(response));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return returnMap;
	}
	
	private ResponseEntity firstCall(final String coordinate) {
		ResponseEntity response = execute(new PathInterfaceRestCaller() {
			@Override
			public String getPath() {
				return null;
			}
			
			@Override
			public Map<String, Object> getParameters() {
				Map<String, Object> map = new HashMap<>();
					map.put("apiKey", SystemParameter.FORECAST_API_KEY);
					map.put("coordinate", coordinate);
				return map;
			}
		});
		return response;
	}
	
	private ResponseEntity secondCall(final String coordinate, final Long timeMachineParameter) {
		final String parameter = coordinate + "," + timeMachineParameter;
		ResponseEntity response = execute(new PathInterfaceRestCaller() {
			@Override
			public String getPath() {
				return null;
			}
			
			@Override
			public Map<String, Object> getParameters() {
				Map<String, Object> map = new HashMap<>();
					map.put("apiKey", SystemParameter.FORECAST_API_KEY);
					map.put("coordinate", parameter);
				return map;
			}
		});
		return response;
	}
	
	public ResponseEntity execute(PathInterfaceRestCaller interfaceRestCaller) throws SystemException {
		String url = SystemParameter.FORECAST_API_URL;
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		ResponseEntity response = rt.exchange(url,HttpMethod.GET, entity, Object.class, interfaceRestCaller.getParameters());
		return response;
	}
	
	public List<ForecastCallHourly> convertToHourly(ResponseEntity response) throws Exception {
		List<ForecastCallHourly> resultList = new ArrayList<>();
		String hourlyResult = JSONUtil.getString("hourly", mapper.writeValueAsString(response.getBody()));
		if(StringFunction.isNotEmpty(hourlyResult)) {
			JSONObject json = new JSONObject(hourlyResult);
			JSONArray array = json.getJSONArray("data");
			TypeFactory t = TypeFactory.defaultInstance();
			resultList = mapper.readValue(array.toString(), t.constructCollectionType(ArrayList.class, ForecastCallHourly.class));
		}
		return resultList;
	}
	
	public List<ForecastCallDaily> convertToDaily(ResponseEntity response) throws Exception {
		List<ForecastCallDaily> resultList = new ArrayList<>();
		String dailyResult = JSONUtil.getString("daily", mapper.writeValueAsString(response.getBody()));
		if (StringFunction.isNotEmpty(dailyResult)) {
			JSONObject json = new JSONObject(dailyResult);
			JSONArray array = json.getJSONArray("data");
			TypeFactory t = TypeFactory.defaultInstance();
			resultList = mapper.readValue(array.toString(), t.constructCollectionType(ArrayList.class, ForecastCallDaily.class));
		}
		return resultList;
	}
	
}
