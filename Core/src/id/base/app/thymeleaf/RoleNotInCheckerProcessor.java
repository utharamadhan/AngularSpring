package id.base.app.thymeleaf;

import id.base.app.LoginSession;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.dom.Element;
import org.thymeleaf.exceptions.ConfigurationException;
import org.thymeleaf.processor.attr.AbstractConditionalVisibilityAttrProcessor;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.standard.expression.TextLiteralExpression;

public class RoleNotInCheckerProcessor extends AbstractConditionalVisibilityAttrProcessor {
	public static final int ATTR_PRECEDENCE = 0;
	public static final String ATTR_NAME = "roleNotIn";
	public static final String COMMA = ",";

	protected RoleNotInCheckerProcessor() {
		super(ATTR_NAME);
	}

	@Override
	public int getPrecedence() {
		return 0;
	}

	@Override
	protected boolean isVisible(final Arguments arguments,
			final Element element, final String attributeName) {

		String attributeValue = element.getAttributeValue(attributeName);

		if (attributeValue == null || attributeValue.trim().equals("")) {
			return false;
		}
		attributeValue = attributeValue.trim();
		List<String> trueAttributeValues = new ArrayList<String>();
		if(attributeValue.indexOf(COMMA)>0){
			String[] attributeValues = attributeValue.split(COMMA);
			for (String attr : attributeValues) {
				String a = attr.trim();
				if(a.length()>0){
					trueAttributeValues.add(a);
				}
			}
		}else{
			trueAttributeValues.add(attributeValue);
		}

		final IContext context = arguments.getContext();
		if (!(context instanceof IWebContext)) {
			throw new ConfigurationException(
					"Thymeleaf execution context is not a web context (implementation of "
							+ IWebContext.class.getName()
							+ ". Spring Security integration can only be used in "
							+ "web environements.");
		}
		final IWebContext webContext = (IWebContext) context;

		HttpServletRequest request = webContext.getHttpServletRequest();

		HttpSession session = request.getSession();
		if (null == session) {
			return false;
		}

		LoginSession loginSession = (LoginSession) session.getAttribute("user");
		if (null == loginSession) {
			return false;
		}

		final Configuration configuration = arguments.getConfiguration();
		final IStandardExpressionParser expressionParser = StandardExpressions
				.getExpressionParser(configuration);
		boolean permit = false;
		for (String attr : trueAttributeValues) {
			String permissionsExpression = attr;

			IStandardExpression permissionsExpr = getExpressionDefaultToLiteral(
					expressionParser, configuration, arguments,
					permissionsExpression);

			Integer permissionsObject = (Integer) permissionsExpr.execute(
					configuration, arguments);

			permit = loginSession
					.hasPermission(permissionsObject);
			if(permit == false){
				break;
			}
		}
		return permit;
	}

	protected static IStandardExpression getExpressionDefaultToLiteral(
			final IStandardExpressionParser expressionParser,
			final Configuration configuration,
			final IProcessingContext processingContext, final String input) {

		final IStandardExpression expression = expressionParser
				.parseExpression(configuration, processingContext, input);
		if (expression == null) {
			return new TextLiteralExpression(input);
		}
		return expression;

	}
}
