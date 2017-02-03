package id.base.app.controller;

import id.base.app.exception.SystemException;
import id.base.app.rest.RestConstant;
import id.base.app.service.role.IAppRoleFunctionService;
import id.base.app.util.StringFunction;
import id.base.app.valueobject.UserGroupAccessNode;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(RestConstant.RM_ROLE_FUNCTION)
public class AppRoleFunctionController {
	@Autowired
	IAppRoleFunctionService appRoleFunctionService;
	
	@RequestMapping(method=RequestMethod.GET, value="/accessTree/{pkAppRole}/{userType}")
	@ResponseBody
	public List<UserGroupAccessNode> getAccessTree(@PathVariable(value="pkAppRole") Long pkAppRole,
			@PathVariable(value="userType") Integer userType) throws SystemException {
		return appRoleFunctionService.getAccessTree(pkAppRole, userType);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/updateAccessibilities")
	@ResponseStatus( HttpStatus.OK )
	public void updateAccessibilities(@RequestBody String requestBody)
			throws SystemException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Map<String,Object> map = mapper.readValue(requestBody, new TypeReference<Map<String,Object>>(){});
			String accessibilities = String.valueOf(map.get("accessibilities"));
			Long pkAppRole = Long.valueOf(String.valueOf(map.get("pkAppRole")));
			final int[] pkAppFunction = StringFunction.splitIntoInt(accessibilities, ",");
			appRoleFunctionService.updateAccessibilities(pkAppRole, pkAppFunction);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
