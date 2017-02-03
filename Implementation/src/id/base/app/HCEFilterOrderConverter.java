package id.base.app;

import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.MatchMode;

import softtech.hong.hce.model.Expression;
import softtech.hong.hce.model.Order;

public class HCEFilterOrderConverter {

	private static Expression buildExpression(SearchFilter searchFilter) {

		switch (searchFilter.getOperand()) {
		case EQUALS:
			return Expression.eq(searchFilter.getFieldName(),
					searchFilter.getValue());
		case EQUALS_OR_GREATER_THAN:
			return Expression.ge(searchFilter.getFieldName(),
					searchFilter.getValue());
		case EQUALS_OR_LESS_THAN:
			return Expression.le(searchFilter.getFieldName(),
					searchFilter.getValue());
		case GREATER_THAN:
			return Expression.gt(searchFilter.getFieldName(),
					searchFilter.getValue());
		case AND:
			return Expression.gt(searchFilter.getFieldName(),
					searchFilter.getValue());
		case IN:
			return Expression.in(searchFilter.getFieldName(),
					(Collection) searchFilter.getValue());
		case IS_NOT_NULL:
			return Expression.isNotNull(searchFilter.getFieldName());
		case IS_NULL:
			return Expression.isNull(searchFilter.getFieldName());
		case LESS_THAN:
			return Expression.lt(searchFilter.getFieldName(),
					searchFilter.getValue());
		case LIKE:
			return Expression.ilike(searchFilter.getFieldName(),
					"%".concat(searchFilter.getValue().toString()).concat("%"),
					MatchMode.ANYWHERE);
		case NOT_EQUAL:
			return Expression.ne(searchFilter.getFieldName(),
					searchFilter.getValue());
		case IN_ARRAY:
			return Expression.in(searchFilter.getFieldName(),
					(Object[]) searchFilter.getValue());
		case NOT_IN_ARRAY:
			return Expression.not(Expression.in(searchFilter.getFieldName(),(Object[]) searchFilter.getValue()));
		default:
			return null;
		}
	}

	public static Expression getExpression(SearchFilter filter) {
		if(filter!=null){
			if (filter.isOr()) {
				return Expression.or(buildExpression(filter.getLeftFilter()),
						buildExpression(filter.getRightFilter()));
			} else if (filter.isAnd()) {
				return Expression.and(buildExpression(filter.getLeftFilter()),
						buildExpression(filter.getRightFilter()));
			} else {
				return buildExpression(filter);
			}
		}else{
			return null;
		}
	}
	
	public static Expression convertFilters(List<SearchFilter> filters){
		if(filters!=null){
			Expression expressions = new Expression();
			for (SearchFilter filter : filters) {
				expressions.add(getExpression(filter));
			}
			return expressions;
		}else{
			return null;
		}
	}

	public static Order getOrder(SearchOrder order){
		if(order!=null){
			if(order.getSort()==SearchOrder.Sort.ASC)
		    	   return Order.asc(order.getFieldName());
		    	return Order.desc(order.getFieldName());
		}else{
			return null;
		}
	}
	
	public static Order[] convertOrders(List<SearchOrder> orders){
		if(orders!=null){
			List<Order> hceOrders = new ArrayList<Order>();
			for (SearchOrder order : orders) {
				hceOrders.add(getOrder(order));
			}
			return hceOrders.toArray(new Order[hceOrders.size()]);
		}else{
			return null;
		}
	}
}
