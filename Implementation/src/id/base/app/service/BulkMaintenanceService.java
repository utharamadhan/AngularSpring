package id.base.app.service;

import id.base.app.exception.SystemException;

import java.util.List;

public interface BulkMaintenanceService<T> extends MaintenanceService<T> {

	public void saveOrUpdate(List<T> maintenanceObjects) throws SystemException ;
	
}
