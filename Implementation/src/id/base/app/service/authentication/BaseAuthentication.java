package id.base.app.service.authentication;

import id.base.app.LoginSession;
import id.base.app.service.AuthenticationService;
import id.base.app.valueobject.AppUser;

public abstract class BaseAuthentication implements AuthenticationService<LoginSession> {

	protected LoginSession buildLoginSession(AppUser appUser) {
		LoginSession loginSession = new LoginSession();
		loginSession.setPkAppUser(appUser.getPkAppUser());
		loginSession.setUserName(appUser.getUserName());
		loginSession.setEmail(appUser.getEmail());
		loginSession.setName(appUser.getParty().getName());
		loginSession.setUserType(appUser.getUserType());
		loginSession.setFlagSuperUser(Boolean.TRUE);
		return loginSession;
	}
}