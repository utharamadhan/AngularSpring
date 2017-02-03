package id.base.app.core;

import id.base.app.service.parameter.IParameterService;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;

public class SchedulerContextConfig {
	public static Logger LOGGER = LoggerFactory.getLogger(SchedulerContextConfig.class);
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	IParameterService parameterService;
	
	@Autowired
    private PlatformTransactionManager transactionManager;
	
	@Autowired
	ApplicationContext applicationContext;
	
}
