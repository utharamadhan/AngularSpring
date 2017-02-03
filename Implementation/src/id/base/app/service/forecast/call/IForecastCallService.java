package id.base.app.service.forecast.call;

import id.base.app.exception.SystemException;
import id.base.app.service.MaintenanceService;
import id.base.app.valueobject.forecast.ForecastCallDaily;
import id.base.app.valueobject.forecast.ForecastCallHourly;

public interface IForecastCallService extends MaintenanceService<ForecastCallHourly> {
	
	public ForecastCallHourly getForecastCallHourly(Long pkLookupAddress) throws SystemException;
	
	public ForecastCallDaily getForecastDaily(Long pkLookupAddress, Integer plusParam) throws SystemException;
	
	public void purgingForecastData() throws SystemException;
	
}
