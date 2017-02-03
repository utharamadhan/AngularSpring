package id.base.app.dao.lookup;

import id.base.app.IBaseDAO;
import id.base.app.exception.SystemException;
import id.base.app.valueobject.LookupAddress;

import java.util.List;

public interface ILookupAddressDAO extends IBaseDAO<LookupAddress>{
	
	public List<LookupAddress> findByLookupAddressGroup(String lookupAddressGroup) throws SystemException;
	public List<LookupAddress> findByGroupOrderBy(String lookupAddressGroup, String orderBy, boolean desc) throws SystemException;
	
}
