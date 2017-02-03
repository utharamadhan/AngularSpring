package id.base.app.dao.forecast.call;

import id.base.app.IBaseDAO;
import id.base.app.exception.SystemException;
import id.base.app.valueobject.forecast.ForecastCallDaily;

import java.util.Date;

public interface IForecastCallDailyDAO extends IBaseDAO<ForecastCallDaily> {
	
	public Boolean isForecastDailyAlreadyExist(Long pkLookupAddress, Date date) throws SystemException;
	
	public ForecastCallDaily getForecastDaily(Long pkLookupAddress, Date dateParam) throws SystemException;
	
	public void purgingForecastDaily(Integer purgingLimit) throws SystemException;

}
