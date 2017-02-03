package id.base.app.util.dao;

import java.io.Serializable;

public class SearchOrder implements Serializable{
	
	private static final long serialVersionUID = 4592184237640447103L;

	public enum Sort { ASC , DESC}
	
	private String fieldName;
	private Sort sort;
	

	public SearchOrder(String fieldName, Sort sort) {
		super();
		this.fieldName = fieldName;
		this.sort = sort;
	}

	public SearchOrder() {
		super();
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}
	
	

}
