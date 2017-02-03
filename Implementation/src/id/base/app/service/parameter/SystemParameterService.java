package id.base.app.service.parameter;

import id.base.app.dao.parameter.IAppParameterDAO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SystemParameterService extends ParameterService {
	
	public SystemParameterService(){
		super();
	}
	
	public SystemParameterService(IAppParameterDAO parameterDAO){
    	super(parameterDAO);
    }
	
}
