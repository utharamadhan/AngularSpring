package id.base.app.dao.lookup;

import id.base.app.IBaseDAO;
import id.base.app.exception.SystemException;
import id.base.app.valueobject.LookupAddress;
import id.base.app.valueobject.MasterAddress;

import java.util.List;

public interface IMasterAddressDAO extends IBaseDAO<MasterAddress>{
	
	public List<LookupAddress> findAddressByParent(String addressGroupSource, Long fkLookupAddressParent) throws SystemException;
	
	public List<Integer> searchPostalCode(String keyword) throws SystemException;

}
