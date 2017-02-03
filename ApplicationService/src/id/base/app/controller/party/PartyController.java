package id.base.app.controller.party;

import id.base.app.SystemConstant;
import id.base.app.controller.SuperController;
import id.base.app.exception.SystemException;
import id.base.app.rest.RestConstant;
import id.base.app.service.MaintenanceService;
import id.base.app.service.party.IPartyService;
import id.base.app.valueobject.party.Party;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value=RestConstant.RM_PARTY)
public class PartyController extends SuperController<Party> {
	
	public PartyController() {
		super();
	}
	
	@Autowired
	private IPartyService partyService;

	@Override
	public MaintenanceService<Party> getMaintenanceService() {
		return partyService;
	}

	@Override
	public Party validate(Party anObject) throws SystemException {
		return null;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findAllPartyByRole/{pkCompany}/{roleCode}")
	@ResponseBody
	public List<Party> findAllPartyByRole(@PathVariable(value="pkCompany") Long pkCompany,  @PathVariable(value="roleCode") String roleCode) throws SystemException {
		return partyService.findAllPartyByRole(pkCompany, roleCode, SystemConstant.EMPTY_KEYWORD);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findAllPartyByRoleAndKeyword/{pkCompany}/{roleCode}/{keyword}")
	@ResponseBody
	public List<Party> findAllPartyByRole(@PathVariable(value="pkCompany") Long pkCompany,  @PathVariable(value="roleCode") String roleCode, @PathVariable(value="keyword") String keyword) throws SystemException {
		return partyService.findAllPartyByRole(pkCompany, roleCode, keyword);
	}
	
}