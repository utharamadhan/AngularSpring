package id.base.app.core;

import id.base.app.IAccessibilityConstant;
import id.base.app.SystemParameter;
import id.base.app.config.BeanUtilsConfigurer;
import id.base.app.service.IServiceName;
import id.base.app.service.function.IAppFunctionService;
import id.base.app.service.parameter.IParameterService;
import id.base.app.valueobject.AppFunction;
import id.base.app.valueobject.AppParameter;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.jasypt.digest.config.SimpleDigesterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ParameterLoader extends ContextLoader implements ServletContextListener  {

	private Logger logger=LoggerFactory.getLogger(ParameterLoader.class);
	
	public ParameterLoader() {
	}

	public ParameterLoader(WebApplicationContext context) {
		super(context);
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext servletContext=servletContextEvent.getServletContext();

		WebApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
		
//		initializeAccessibility(ctx);
		
		initializeAppParameter(ctx); //TODO we are using a lot of SystemParameter., for temporary solution we must sync it
		
		initializeDigester(ctx);
		
		initializeLdap(ctx);
		
		initializeBeanUtils();
		
		initializeMailSender(ctx);
		
		initializeCalculationEngine(ctx);
	}
	
	private void initializeAppParameter(WebApplicationContext ctx) {
		IParameterService appParameterService=(IParameterService)ctx.getBean("systemParameterService");
		List<AppParameter> appParameters = appParameterService.findAllAppParameter();
		for (AppParameter appParam : appParameters) {
			if(appParam.getName()!=null && appParam.getValue()!=null){
				logger.debug("set parameter for "+appParam.getName()+" with "+ appParam.getValue());
				SystemParameter.updateSystemEnvironment(appParam.getName(),appParam.getValue().toString());
			}
		}
	}

	private void initializeCalculationEngine(WebApplicationContext ctx) {
		/*CalculationEngine.registerMeasurement(-1l, new InsuredAmountMeasurement());
		CalculationEngine.registerMeasurement(-2l, new PolicyTermInYearMeasurement());
		CalculationEngine.registerMeasurement(11l, new BillingFrequencyMeasurement());
		CalculationEngine.registerMeasurement(12l, new FixedAmountMeasurement());
		CalculationEngine.registerMeasurement(15l, new GrossPremiumMeasurement());
		CalculationEngine.registerMeasurement(-45l, new PremiumBaseAmountMeasurement());*/
	}

	private void initializeMailSender(WebApplicationContext ctx) {
		logger.info("Start initializing mail sender... ");
		JavaMailSenderImpl mailSender = (JavaMailSenderImpl) ctx.getBean("mailSender");
		mailSender.setHost(SystemParameter.MAIL_HOST);
		mailSender.setPort(SystemParameter.MAIL_PORT);
		mailSender.setUsername(SystemParameter.MAIL_USERNAME);
		mailSender.setPassword(SystemParameter.MAIL_PASSWORD);
		logger.info("Finish initializing mail sender... ");
	}

	private void initializeBeanUtils() {
		logger.info("Start initializing beanutils... ");
		BeanUtilsConfigurer.configure();
		logger.info("Finish initializing beanutils... ");
	}

	private void initializeLdap(WebApplicationContext ctx) {
		/*logger.info("Start initializing ldap context... ");
		LdapTemplate ldapTemplate = (LdapTemplate) ctx.getBean(IServiceName.LDAP_TEMPLATE);
		ContextSource contextSource = ldapTemplate.getContextSource();
		LdapContextSource ldapContext = (LdapContextSource) ctx.getBean(IServiceName.LDAP_CONTEXT);
		ldapContext.setUrl(SystemParameter.LDAP_URL);
		ldapContext.setUserDn(SystemParameter.LDAP_USER_DN);
		ldapContext.setBase(SystemParameter.LDAP_BASE);
		
		StringEncryptor encryptor = (StringEncryptor) ctx.getBean(IServiceName.ENCRYPTOR);
		ldapContext.setPassword(encryptor.decrypt(SystemParameter.LDAP_PASSWORD));
		
		logger.info("Finish initializing ldap context... ");*/
	}

	private void initializeDigester(WebApplicationContext ctx) {
		logger.info("Start initializing digester... ");
//		StandardStringDigester digester = (StandardStringDigester) ctx.getBean(IServiceName.DIGESTER);
		SimpleDigesterConfig config = (SimpleDigesterConfig) ctx.getBean(IServiceName.DIGESTER_CONFIG);
		config.setAlgorithm(SystemParameter.DIGESTER_ALGORITHM);
//		digester.setConfig(config);
		//digester.initialize();
		logger.info("Finish initializing digester... ");
	}

	private void initializeAccessibility(WebApplicationContext ctx) {
		logger.info("Start loading Accesibility Constants .. ");
		IAppFunctionService appFunctionService = (IAppFunctionService) ctx
				.getBean(IAppFunctionService.class);

		List<AppFunction> appFunctions = appFunctionService.findAllAppFunction();
		
		for (AppFunction appFunction : appFunctions) {
			if(appFunction!=null){
			logger.debug("Setting appfunction [{}] with functionNumber [{}] ",
						appFunction.getDescr(), appFunction.getPkAppFunction());
				IAccessibilityConstant.synchronizeFunction(appFunction.getDescr(),
						appFunction.getPkAppFunction().intValue());
			}
		}
		
		logger.info("Finish loading Accesibility Constants .. ");
	}

}
