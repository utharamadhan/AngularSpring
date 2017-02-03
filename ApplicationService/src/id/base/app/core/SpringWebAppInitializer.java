package id.base.app.core;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class SpringWebAppInitializer implements WebApplicationInitializer{
	private static Logger logger = LoggerFactory.getLogger(SpringWebAppInitializer.class);
	
	@Override
	public void onStartup(ServletContext container) throws ServletException {

		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
		appContext.register(ApplicationContextConfig.class);
		appContext.register(SchedulerContextConfig.class);
		
		container.addListener(new ContextLoaderListener(appContext));
		
		container.addListener(new ParameterLoader(appContext));
        
		ServletRegistration.Dynamic dispatcher = container.addServlet("SpringDispatcher", new DispatcherServlet(appContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/*");
		dispatcher.setAsyncSupported(true);
		
		printenv();
	}
	
	private void printenv() {
		Properties properties = System.getProperties();
		Set<Entry<Object, Object>> entrySet = properties.entrySet();
		
		logger.info("Environment variables");
		Iterator<Entry<Object, Object>> iterator = entrySet.iterator();
		while (iterator.hasNext()) {
			Entry<Object, Object> entry = iterator.next();
			logger.info("" + entry.getKey() + "=" + entry.getValue());
		}
	}
}
