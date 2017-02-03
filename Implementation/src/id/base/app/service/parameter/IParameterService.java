package id.base.app.service.parameter;

import id.base.app.exception.SystemException;
import id.base.app.valueobject.AppParameter;

import java.util.List;
import java.util.Map;

public interface IParameterService {

	public abstract List<AppParameter> findAllAppParameter() throws SystemException;
	public AppParameter findParameterByName(String name) throws SystemException;
	public abstract Map<String,String> findParameterPairValue(String likeName)
			throws SystemException;

	public abstract Map<String,Object> findAppParametersByNames(List<String> names) throws SystemException;
}