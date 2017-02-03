package id.base.app.service.user;

import id.base.app.exception.SystemException;
import id.base.app.valueobject.AppUser;

import java.util.List;

public interface IUserNotificationService {
	public List<AppUser> findNotifiyingPasswordUser()throws SystemException;
}
