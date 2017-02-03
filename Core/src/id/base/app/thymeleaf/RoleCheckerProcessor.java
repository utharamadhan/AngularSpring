package id.base.app.thymeleaf;

import id.base.app.LoginSession;

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

public class RoleCheckerProcessor extends AbstractConditionalVisibilityAttrProcessor {
	
	public static final int ATTR_PRECEDENCE = 0;
	public static final String ATTR_NAME = "roleCheck";
	public static final String REVERSE = "!";

	protected RoleCheckerProcessor() {
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

		final boolean reverse = attributeValue.startsWith(REVERSE);
		final String permissionsExpression = reverse ? attributeValue
				.substring(1, attributeValue.length()) : attributeValue;

		final IStandardExpressionParser expressionParser = StandardExpressions
				.getExpressionParser(configuration);

		final IStandardExpression permissionsExpr = getExpressionDefaultToLiteral(
				expressionParser, configuration, arguments,
				permissionsExpression);

		final Integer permissionsObject = (Integer) permissionsExpr.execute(
				configuration, arguments);

		if (reverse) {
			return !loginSession.hasPermission(permissionsObject);
		} else {
			return loginSession
					.hasPermission(permissionsObject);
		}
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
