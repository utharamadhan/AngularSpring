
package id.base.app.rest;

import id.base.app.LoginSession;
import id.base.app.SystemConstant;
import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class LoginSessionUtil {
   
	private final static Logger logger = LoggerFactory.getLogger(LoginSessionUtil.class);

    
    public static LoginSession getLogin() {
    	ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    	HttpServletRequest request = attr.getRequest();
        HttpSession session = request.getSession();
        if (null == session) {
            logger.info("Empty HTTP Session");
            throw new SystemException(new ErrorHolder("error.session.invalidated"));
        }

        LoginSession loginSession = (LoginSession) session.getAttribute(SystemConstant.USER_OBJECT_KEY);
        if (null == loginSession) {
            logger.info("Empty Login Session");
            throw new SystemException(new ErrorHolder("error.session.expired"));
        }
        return loginSession;
    }
}
