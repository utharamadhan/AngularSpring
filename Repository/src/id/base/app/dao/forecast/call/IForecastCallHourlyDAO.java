package id.base.app.dao.forecast.call;

import id.base.app.IBaseDAO;
import id.base.app.exception.SystemException;
import id.base.app.valueobject.forecast.ForecastCallHourly;

import java.util.List;

public interface IForecastCallHourlyDAO extends IBaseDAO<ForecastCallHourly> {
	
	public Boolean isAvailableForToday(Long pkLookupAddress) throws SystemException;
	
	public List<ForecastCallHourly> getAllForecastForToday(Long pkLookupAddress) throws SystemException;
	
	public ForecastCallHourly getCurrentForecastHourly(Long pkLookupAddress, Integer hour) throws SystemException;
	
	public void purgingForecastHourly(Integer purgingLimit) throws SystemException;
	
}
