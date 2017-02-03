package id.base.app.test.person;

import id.base.app.SystemParameter;
import id.base.app.service.MaintenanceService;
import id.base.app.service.parameter.IParameterService;
import id.base.app.valueobject.AppUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:serviceContext.xml"})
@WebAppConfiguration
//@TestExecutionListeners({})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional
public class UserTest extends TestCase{
	@Autowired
	@Qualifier(value="userMaintenanceService")
	MaintenanceService<AppUser> userService;
	
	@Autowired
	@Qualifier("systemParameterService")
	private IParameterService service;
	
	@Test
	public void testFindUser() {
		AppUser user = userService.findById(102L);
		assertNotNull(user);
	}
	
	@Test
	public void testParam() {
		List<String> names = new ArrayList<String>();
		names.add(SystemParameter.NAME_SESSION_EXPIRED_INTERVAL);
		Map<String,Object> params = service.findAppParametersByNames(names);
		assertTrue(params.size()>0);
		for (Map.Entry<String, Object> string : params.entrySet()) {
			System.out.println(string.getValue());
		}
	}
}
