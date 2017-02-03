package id.base.app.core;

import id.base.app.exception.ErrorHolder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ConstraintViolationExceptionHandler extends ResponseEntityExceptionHandler{
	private static Logger LOGGER = LoggerFactory
			.getLogger(ConstraintViolationExceptionHandler.class);
	
	private static final Map<String,String> CONSTRAINT_MAPPING = new HashMap<String,String>();
	static{
		CONSTRAINT_MAPPING.put("I_UK_4XKN51W67833GSUJHAUW00CFQ", "error.lookup.code.exists");
		CONSTRAINT_MAPPING.put("DOCUMENT_CODE", "error.message.document.code.duplicate");
		CONSTRAINT_MAPPING.put("FK_LOOKUP_BANK_TYPE_CONS", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_LOOKUP_BANK_CONS", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_LOOKUP_BANK_CCY_CONS", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_DOCUMENT_1", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_CONTRACT_9", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_CONTRACT_8", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_CONTRACT_6", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_CONTRACT_7", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_CONTRACT_BENEFIT_1", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_CONTRACT_LIFE_ASSURED_3", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_CONTRACT_STRUCTURE_2", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("GL_JOURNAL_R02", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("GL_JOURNAL_ELEMENT_R04", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("GL_JOURNAL_ELEMENT_R03", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("GL_JOURNAL_ELEMENT_R02", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("GL_MAPPING_ELEMENT_R02", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("GL_MAPPING_ELEMENT_R01", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_CONTRACT_FIELD_DEF_6", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_PARTNER_PRODUCT_FIELD_DEF_0", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_PARTNER_PRODUCT_FIELD_DEF_1", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_PAMLCBVFPTTFVCMPFF74JFNWW", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("SYS_C0010787", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_PRODUCT_ELIGIBILITY_0", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_PRODUCT_ELIGIBILITY_1", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_PRODUCT_ELIGIBILITY_2", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_PRODUCT_ELIGIBILITY_3", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_PRODUCT_ELIGIBILITY_4", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_PRODUCT_ELIGIBILITY_5", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_PRODUCT_FOLLOW_UP_2", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_PRODUCT_FOLLOW_UP_3", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_PRODUCT_FOLLOW_UP_4", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_PRODUCT_AFTER_SALES_1", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_PRODUCT_AFTER_SALES_2", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_PRODUCT_AFTER_SALES_3", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_PRODUCT_AFTER_SALES_4", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_PRODUCT_AFTER_SALES_5", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_AGE_DEFINITION", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_CONTRACT_TERM_LOOKUP", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_INSURANCE_CODE", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LIFE_AH_CODE_LOOKUP", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_ATTR_DATA_TYPE", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_BENEFIT_RULE", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_COV_PERIOD", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_COMPONENT_TYPE", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_COVERAGE_PERIOD", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_COMPONENT_STATUS", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_BENEFIT_RULE", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_BILLING_FREQ", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_STATUTORY_CODE", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_SECTION_CODE", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_SUB_SECTION", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_CLASS_COMPONENT", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_TYPE", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_COVER_PERIOD", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_BENEF_RULE", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_STATU_CODE", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_SEC_CODE", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_SUB_SEC", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_CLASS_COMP", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_GENDER", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_CONTACT_TYPE", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_ID_TYPE", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_FATCA", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_DIST_CHANNEL", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_PARTY_FATCA", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_PARTY_DISTRIBUTION", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_GL_MODULE_GROUP", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_MEASUREMENT", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_MEASUREMENT_QUALI", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_VALIDATION", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_REFERENCE_GROUP", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_DATA_TYPE", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_REFERENCE_TYPE2", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_RELATIONSHIP", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_TARGET_COMMISSION", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_FIELD_DEF", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_PRODUCT_FIELD_DEFINITION_0", "error.message.delete.definition.already.inused");
		CONSTRAINT_MAPPING.put("FK_DG0RW4VIFU0R1102XKO0APKNB", "error.message.delete.benefit.already.inused");
		CONSTRAINT_MAPPING.put("UC_DOCUMENT_CODE", "error.message.field.document.unique.code");
		CONSTRAINT_MAPPING.put("I_DOCUMENT_CODE", "error.message.field.document.unique.code");
		CONSTRAINT_MAPPING.put("FK_CONTRACT_AFTER_SALES_1", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_CONTRACT_AFTER_SALES_2", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_CONTRACT_AFTER_SALES_3", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_CONTRACT_AFTER_SALES_4", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_CONTRACT_AFTER_SALES_5", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("FK_CONTRACT_AFTER_SALES_6", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_OCCUPATION", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_SMOKING_INDICATOR", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_MEDICAL_EVDNCE", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("I_PARTY_ID_UNIQUE", "error.client.id.duplicate");
		CONSTRAINT_MAPPING.put("I_PARTY_IDTYP_UNIQUE", "error.client.id.type.duplicate");
		CONSTRAINT_MAPPING.put("C_CONTRACT_REF_NO_CONTRACT", "error.contract.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_CONTRACT_REF_NO_LOOKUP", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_LOOKUP_RELATION_INTERN", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_FK_LOOKUP_PIDTYPE", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_FK_LOOKUP_PATYP", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_FK_LOOKUP_PACOUN", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("C_FK_LOOKUP_PCCTYPE", "error.lookup.delete.already.inused");
		CONSTRAINT_MAPPING.put("COA_BAL_MTD_R02", "error.message.coa.delete.already.inused");
	}
	
	@Autowired
	private ResourceBundleMessageSource messageSource;

	@ExceptionHandler({ ConstraintViolationException.class })
	protected ResponseEntity<Object> handleInvalidRequest(RuntimeException e,
			WebRequest request) throws JsonProcessingException {
		ConstraintViolationException cve = (ConstraintViolationException) e;
		List<ErrorHolder> errorHolders = new LinkedList<ErrorHolder>();
		LOGGER.error("RuntimeError {}", e);
		errorHolders.add(new ErrorHolder(messageSource.getMessage(getConstraintErrorMessage(cve.getConstraintName()),null,Locale.ENGLISH)));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(e, errorHolders, headers,
				HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	private String getConstraintErrorMessage(String constraintName){
		String cleanConstraintName = cleanConstraintName(constraintName);
		if(CONSTRAINT_MAPPING.containsKey(cleanConstraintName)){
			return CONSTRAINT_MAPPING.get(cleanConstraintName);
		}else{
			return "error.constraint.message.default";
		}
	}
	
	private String cleanConstraintName(String constraintName){
		if(constraintName.contains(".")){
			return constraintName.substring(constraintName.lastIndexOf(".")+1,constraintName.length());
		}else{
			return constraintName;
		}
	}
	
}
