package id.base.app.util.dao;

import id.base.app.SystemConstant;
import id.base.app.util.DateTimeFunction;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


public class SearchFilter implements Serializable{

	private static final long serialVersionUID = 413203047065623921L;
	
	private SearchFilter leftFilter;
	private SearchFilter rightFilter;
	private boolean or = false;
	private Operator 	operand ;
	private String 		fieldName;
	private Object 		value;
	private Class valueClass;
	public boolean isOr() {
		return or;
	}
	private boolean and = false;
	public boolean isAnd() {
		return and;
	}
	
	public SearchFilter(){
		super();
	}

	public SearchFilter(SearchFilter leftFilter, SearchFilter rightFilter) {
		this.leftFilter = leftFilter;
		this.rightFilter = rightFilter;
		or = true ;
	}
	
	public SearchFilter(SearchFilter leftFilter, SearchFilter rightFilter, boolean and) {
		this.leftFilter = leftFilter;
		this.rightFilter = rightFilter;
		this.and = and ;
	}

	
	public SearchFilter getLeftFilter() {
		return leftFilter;
	}
	public SearchFilter getRightFilter() {
		return rightFilter;
	}
	

	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public Object getValue() {
		if(valueClass==null){
			return value;
		}else{
			if(valueClass == Long.class){
				return Long.valueOf(String.valueOf(value));
			}else if(valueClass == Date.class){
				return DateTimeFunction.string2Date((String.valueOf(value)), SystemConstant.SYSTEM_TIME_MASK);
			}else{
				return value;
			}
		}
	}
	public void setValue(Object value) {
		this.value = value;
	}

	public SearchFilter(String fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.operand = operator;
		this.value = value;
	}
	
	public SearchFilter(String fieldName, Operator operator, Object value, Class valueClass) {
		this.fieldName = fieldName;
		this.operand = operator;
		this.value = value;
		this.valueClass = valueClass;
	}

	public Class getValueClass() {
		return valueClass;
	}

	public void setValueClass(Class valueClass) {
		this.valueClass = valueClass;
	}

	public Operator getOperand() {
		return operand;
	}
	public void setOperand(Operator operand) {
		this.operand = operand;
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	@Override
	public int hashCode() {
		 return HashCodeBuilder.reflectionHashCode(this);
	}
	
	
	@Override
	public String toString() {
		return  " field name : " + getFieldName() + ", operand : " + getOperand() + " , value : " + getValue();
	}


	

	
	
}
