package id.base.app.service.lookup;

import id.base.app.exception.SystemException;
import id.base.app.service.MaintenanceService;
import id.base.app.valueobject.Lookup;

import java.util.List;

public interface ILookupService extends MaintenanceService<Lookup> {

	List<Lookup> findByLookupGroup(String lookupGroup) throws SystemException;

	Lookup findByCode(String code, String lookupGroup) throws SystemException;
	
	Lookup findById(Long id) throws SystemException;
	
	public abstract List<Lookup> findByLookupGroupOrderBy(String lookupGroup, String orderBy,boolean desc) throws SystemException;
	
	List<Lookup> findByLookupGroupAndUsage(String lookupGroup, String usage)
			throws SystemException;

}