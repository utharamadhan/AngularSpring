package id.base.app.service.lookup;

import id.base.app.exception.SystemException;
import id.base.app.service.MaintenanceService;
import id.base.app.valueobject.LookupAddress;

import java.util.List;

public interface ILookupAddressService extends MaintenanceService<LookupAddress>{

	public List<LookupAddress> findByLookupAddressGroup(String lookupAddressGroup)
				throws SystemException;
		
	public List<LookupAddress> findByGroupOrderBy(String lookupAddressGroup,
			String orderBy, boolean desc) throws SystemException;
}