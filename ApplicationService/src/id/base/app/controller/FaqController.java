package id.base.app.controller;

import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;
import id.base.app.rest.RestConstant;
import id.base.app.service.MaintenanceService;
import id.base.app.service.faq.IFaqService;
import id.base.app.valueobject.Faq;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestConstant.RM_FAQ)
public class FaqController extends SuperController<Faq>{
	
	@Autowired
	private IFaqService faqService;
	
	@Override
	public MaintenanceService<Faq> getMaintenanceService() {
		return faqService;
	}

	@Override
	public Faq validate(Faq anObject) throws SystemException {
		List<ErrorHolder> errors = new ArrayList<>();
		if(anObject.getQuestion() == null){
			errors.add(new ErrorHolder("Question is Mandatory"));
		}
		if(anObject.getAnswer() == null){
			errors.add(new ErrorHolder("Answer is Mandatory"));
		}
		if(errors != null && errors.size() > 0){
			throw new SystemException(errors);
		}
		return anObject;
	}
	
}
