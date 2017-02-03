package id.base.app.service.lookup;

import id.base.app.exception.SystemException;
import id.base.app.valueobject.LookupGroup;

public interface ILookupGroupService {
	
	public LookupGroup findByName(String name) throws SystemException;

	public abstract boolean checkUpdatableByLookupPK(Long pk) throws SystemException;

	public abstract boolean checkUpdatableByGroupName(String name) throws SystemException;
	
}
