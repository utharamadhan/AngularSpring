package id.base.app.service.forecast.call;

import id.base.app.SystemParameter;
import id.base.app.dao.forecast.call.IForecastCallDailyDAO;
import id.base.app.dao.forecast.call.IForecastCallHourlyDAO;
import id.base.app.dao.lookup.ILookupAddressDAO;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.service.forecast.call.api.ForecastWeatherAPI;
import id.base.app.util.DateTimeFunction;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.LookupAddress;
import id.base.app.valueobject.forecast.ForecastCallDaily;
import id.base.app.valueobject.forecast.ForecastCallHourly;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ForecastCallService implements IForecastCallService {

	@Autowired
	private IForecastCallHourlyDAO hourlyDAO;
	@Autowired
	private IForecastCallDailyDAO dailyDAO;
	@Autowired
	@Qualifier("ForecastWeatherService")
	private ForecastWeatherAPI forecastService;
	@Autowired
	private ILookupAddressDAO lookupAddressDAO;
	
	@Override
	public ForecastCallHourly findById(Long id) throws SystemException {
		return hourlyDAO.findById(id);
	}

	@Override
	public void saveOrUpdate(ForecastCallHourly anObject) throws SystemException {
		hourlyDAO.saveOrUpdate(anObject);
	}

	@Override
	public void delete(Long[] objectPKs) throws SystemException {
		hourlyDAO.delete(objectPKs);
	}

	@Override
	public List<ForecastCallHourly> findObjects(Long[] objectPKs) throws SystemException {
		return null;
	}

	@Override
	public PagingWrapper<ForecastCallHourly> findAllByFilter(int startNo, int offset, List<SearchFilter> filter, List<SearchOrder> order) throws SystemException {
		return hourlyDAO.findAllByFilter(startNo, offset, filter, order);
	}

	@Override
	public List<ForecastCallHourly> findAll(List<SearchFilter> filter, List<SearchOrder> order) throws SystemException {
		return null;
	}

	@Override
	public ForecastCallHourly getForecastCallHourly(Long pkLookupAddress) throws SystemException {
		if(!hourlyDAO.isAvailableForToday(pkLookupAddress)) {
			//do call forecast api
			LookupAddress la = lookupAddressDAO.findById(pkLookupAddress);
			Map<String, Object> resultMap = forecastService.callForecastWeather(la.getCoordinate());
			if(resultMap != null) {
				//store it to db
				saveResultAsForecastHourlyAndDaily(pkLookupAddress, resultMap);
			}
		}
		
		//get all forecast hourly for current date
		ForecastCallHourly currentForecast = hourlyDAO.getCurrentForecastHourly(pkLookupAddress, DateTimeFunction.getCalendar(new Date()).get(Calendar.HOUR_OF_DAY));
		
		return currentForecast;
	}
	
	private void saveResultAsForecastHourlyAndDaily(Long pkLookupAddress, Map<String, Object> resultMap) {
		//save hourly
		List<ForecastCallHourly> firstCallHourly = (List<ForecastCallHourly>) resultMap.get("hourly");
		ForecastCallHourly earliestTime = null;
		if(firstCallHourly != null && firstCallHourly.size() > 0) {
			Calendar calDayAfterTomorrow = DateTimeFunction.getCalendar(new Date());
				calDayAfterTomorrow.add(Calendar.DAY_OF_YEAR, 2);
			Long millisDayAfterTomorrow = DateTimeFunction.truncateDate(calDayAfterTomorrow.getTime()).getTime();
			for(ForecastCallHourly hourly : firstCallHourly) {
				hourly.setLookupAddress(LookupAddress.getInstance(pkLookupAddress));
				hourly.setForecastDate(convertToTruncatedDate(hourly.getTime()));
				hourly.setForecastTime(convertToTruncatedHour(hourly.getTime()));
				if ((hourly.getTime() * 1000) < millisDayAfterTomorrow) {					
					hourlyDAO.saveOrUpdate(hourly);
					if(earliestTime != null){
						if(hourly.getTime() < earliestTime.getTime()){						
							earliestTime = hourly;
						}
					}else{
						earliestTime = hourly;
					}
				}
			}
		}
		List<ForecastCallHourly> timeMachineHourly = (List<ForecastCallHourly>) resultMap.get("hourlyTimeMachine");
		if (earliestTime != null && timeMachineHourly != null && timeMachineHourly.size() > 0) {
			Long millisDate = earliestTime.getForecastDate().getTime();
			Calendar offsetCal = DateTimeFunction.getCalendar(earliestTime.getForecastDate());
				offsetCal.set(Calendar.HOUR_OF_DAY, earliestTime.getForecastTime());
			Long millisOffset = offsetCal.getTimeInMillis();
			for(ForecastCallHourly hourly : timeMachineHourly) {
				hourly.setLookupAddress(LookupAddress.getInstance(pkLookupAddress));
				hourly.setForecastDate(convertToTruncatedDate(hourly.getTime()));
				hourly.setForecastTime(convertToTruncatedHour(hourly.getTime()));
				if((hourly.getTime() * 1000) >= millisDate && (hourly.getTime() * 1000) < millisOffset) {
					hourlyDAO.saveOrUpdate(hourly);
				}
			}
		}
		
		//save daily
		List<ForecastCallDaily> firstCallDaily = (List<ForecastCallDaily>) resultMap.get("daily");
		if (firstCallDaily != null && firstCallDaily.size() > 0) {
			for(ForecastCallDaily daily : firstCallDaily) {
				daily.setLookupAddress(LookupAddress.getInstance(pkLookupAddress));
				daily.setForecastDate(convertToTruncatedDate(daily.getTime()));
				if(!dailyDAO.isForecastDailyAlreadyExist(daily.getLookupAddress().getPkLookupAddress(), daily.getForecastDate())) {
					dailyDAO.saveOrUpdate(daily);
				}
			}
		}
	}
	
	private Date convertToTruncatedDate(Long time) {
		try{
			return DateTimeFunction.truncateDate(new Date(time * 1000));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Integer convertToTruncatedHour(Long time) {
		try{
			Calendar cal = DateTimeFunction.getCalendar(time * 1000);
			return cal.get(Calendar.HOUR_OF_DAY);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(DateTimeFunction.truncateDate(new Date()).getTime());
	}

	@Override
	public ForecastCallDaily getForecastDaily(Long pkLookupAddress, Integer plusParam) throws SystemException {
		Calendar cal = DateTimeFunction.getCalendar(DateTimeFunction.truncateDate(new Date()));
			cal.add(Calendar.DAY_OF_YEAR, plusParam);
		return dailyDAO.getForecastDaily(pkLookupAddress, cal.getTime());
	}

	@Override
	public void purgingForecastData() throws SystemException {
		hourlyDAO.purgingForecastHourly(SystemParameter.FORECAST_PURGING_LIMIT);
		dailyDAO.purgingForecastDaily(SystemParameter.FORECAST_PURGING_LIMIT);
	}

}
