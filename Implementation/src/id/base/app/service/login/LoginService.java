package id.base.app.service.login;

import id.base.app.dao.login.ILoginDAO;
import id.base.app.dao.parameter.IAppParameterDAO;
import id.base.app.dao.user.IUserDAO;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.service.MaintenanceService;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.AppUser;
import id.base.app.valueobject.RuntimeUserLogin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginService implements LoginDirectoryService ,
     MaintenanceService<RuntimeUserLogin> {
	
	@Autowired
	protected  ILoginDAO loginDAO;
	@Autowired
	private IUserDAO userDAO;
	@Autowired
	private IAppParameterDAO appParameterDAO;
	
	public LoginService(){
		super();
	}

	public LoginService(ILoginDAO loginDAO,IUserDAO userDAO) {
		this.loginDAO = loginDAO;
		this.userDAO=userDAO;
	}
	
	public void delete(Long[] objectPKs) throws SystemException {
		loginDAO.delete(objectPKs);
	}

	public RuntimeUserLogin findById(Long id) throws SystemException {
		return loginDAO.findRuntimeUserLoginById(id);
	}

	public void saveOrUpdate(RuntimeUserLogin anObject) throws SystemException {
		loginDAO.saveOrUpdate(anObject);
	}

	

	public void register(RuntimeUserLogin runtimeUser) throws SystemException {
		loginDAO.saveOrUpdate(runtimeUser);
		AppUser appUser = userDAO.findAppUserById(runtimeUser.getUserId());
		appUser.setLastLoginDate(runtimeUser.getLoginTime());
		appUser.setLastLoginAccess(runtimeUser.getRemoteAddress());
		userDAO.saveOrUpdate(appUser);
	}

	public void unregister(Long runtimeUserId) throws SystemException {
		loginDAO.delete(new Long[]{runtimeUserId});
		AppUser appUser = userDAO.findAppUserById(runtimeUserId);
		/*appUser.setLastLogOutDate(userDAO.getCurrentSystemDate());*/
		userDAO.saveOrUpdate(appUser);
	}

	public RuntimeUserLogin findByReferencesId(Long referencesPK)
			throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}

	public PagingWrapper<RuntimeUserLogin> findAllByFilter(int startNo,
			int offset, List<SearchFilter> filter, List<SearchOrder> order)
			throws SystemException {
		return loginDAO.findAllRuntimeUserLoginByFilter(startNo, offset, filter, order);
	}

	@Override
	public List<RuntimeUserLogin> findObjects(Long[] objectPKs)
			throws SystemException {
			List<RuntimeUserLogin> runtimeUserlist=new ArrayList<RuntimeUserLogin>();
			AppUser user=null;
			for(Long s:objectPKs){
				user=userDAO.findAppUserById(s);
				RuntimeUserLogin userLoginsLogin =new RuntimeUserLogin();
				userLoginsLogin.setUserName(user.getUserName());
				runtimeUserlist.add(userLoginsLogin);
			}
		return runtimeUserlist;
	}

	@Override
	public void unregisterAll() throws SystemException {
		loginDAO.deleteAll();
	}
	
	@Override
	public void unreqisterExpiredLoginSession() throws SystemException {
		loginDAO.deleteExpiredUsers();
	}

	@Override
	public List<RuntimeUserLogin> findAll(List<SearchFilter> filter,
			List<SearchOrder> order) throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RuntimeUserLogin findByAccessInfoId(String accessInfoId)
			throws SystemException {
		RuntimeUserLogin rul = loginDAO.findByAccessInfoId(accessInfoId);
		//TODO find the blocked variable here
		
		return rul;
	}

	@Override
	public RuntimeUserLogin findByUserName(String userName)
			throws SystemException {
		RuntimeUserLogin rul = loginDAO.findByUserName(userName);
		//TODO find the blocked variable here
		if(null != rul){	
		}
		
		return rul;
	}
}
