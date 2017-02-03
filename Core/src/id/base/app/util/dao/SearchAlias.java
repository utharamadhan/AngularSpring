package id.base.app.util.dao;

import org.hibernate.sql.JoinType;

public class SearchAlias {
	
	private String aliasName;
	private String aliasProperty;
	private JoinType joinType;
	
	public SearchAlias(String aliasProperty, String aliasName) {
		this.aliasProperty = aliasProperty;
		this.aliasName = aliasName;
	}
	
	public SearchAlias(String aliasProperty, String aliasName, JoinType joinType) {
		this.aliasProperty = aliasProperty;
		this.aliasName = aliasName;
		this.joinType = joinType;
	}
	
	public String getAliasProperty() {
		return aliasProperty;
	}
	public void setAliasProperty(String aliasProperty) {
		this.aliasProperty = aliasProperty;
	}
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	
	/**
	 * @return the joinType
	 */
	public JoinType getJoinType() {
		return joinType;
	}

	/**
	 * @param joinType the joinType to set
	 */
	public void setJoinType(JoinType joinType) {
		this.joinType = joinType;
	}

}
