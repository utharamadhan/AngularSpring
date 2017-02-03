package id.base.app.controller;

import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;
import id.base.app.rest.RestConstant;
import id.base.app.service.function.IAppFunctionService;
import id.base.app.valueobject.AppFunction;
import id.base.app.valueobject.AppRole;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(RestConstant.RM_APP_FUNCTION)
public class AppFunctionController {
	@Autowired
	IAppFunctionService appFunctionService;
	
	@RequestMapping(value="/findAppFunctionByPermissionList")
	@ResponseBody
	public List<AppFunction> findAppFunctionByPermissionList(@RequestParam(value="roles") String roles) throws SystemException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<AppRole> appRoles = new ArrayList<AppRole>();
			if(StringUtils.isNotEmpty(roles)){
				appRoles = mapper.readValue(roles, new TypeReference<List<AppRole>>(){});
			}
			return appFunctionService.findAppFunctionByPermissionList(appRoles);
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException(new ErrorHolder("error finding your data"));
		}
	}

	@RequestMapping(value="/findAppFunctionMenuByUserRoles")
	@ResponseBody
	public List<AppFunction> findAppFunctionMenuByUserRoles(@RequestParam(value="roles") String roles) throws SystemException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<AppRole> appRoles = new ArrayList<AppRole>();
			if(StringUtils.isNotEmpty(roles)){
				appRoles = mapper.readValue(roles, new TypeReference<List<AppRole>>(){});
			}
			return appFunctionService.findAppFunctionMenuByUserRoles(appRoles);
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException(new ErrorHolder("error finding your data"));
		}
	}
	
	@RequestMapping(value="/findAppFunctionByAppRole")
	@ResponseBody
	public List<AppFunction> findAppFunctionByAppRole(@RequestParam(value="pkAppRole") Long pkAppRole) throws Exception {
		return appFunctionService.findAppFunctionByAppRole(pkAppRole);
	}
}
