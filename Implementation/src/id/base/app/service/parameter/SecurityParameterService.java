package id.base.app.service.parameter;

import id.base.app.dao.parameter.IAppParameterDAO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SecurityParameterService extends ParameterService {
	
	public SecurityParameterService(){
		super();
	}
	
	public SecurityParameterService(IAppParameterDAO parameterDAO){
    	super(parameterDAO);
    }
	
	
	
}
