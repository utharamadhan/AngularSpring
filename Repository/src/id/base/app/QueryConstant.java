package id.base.app;

public class QueryConstant {
	public static final String OBJECT_NAME = "#OBJECT#";
	
	public static final String PROPERTY_NAME = "#PROPERTY#";
	
	public static final String PROPERTY_VALUE = "#PROPERTYVALUE#";
	
	public static final String IDENTIFIER = "#IDENTIFIER#";
	
	public static final String CONDITION = "#CONDITION#"; 
	
	public static final String UPDATE_QUERY = "update #OBJECT# set #PROPERTYVALUE# where #CONDITION#";
	
	public static final String DELETE_QUERY = "delete from #OBJECT# where ";
	
	public static final String DELETE_BY_PROPERTY_QUERY = "delete from #OBJECT# where #PROPERTY# = :expression";
	
	public static final String HARD_DELETE_QUERY = "delete from #OBJECT# where #IDENTIFIER# in (:ids)";
	
	public static final String SOFT_DELETE_QUERY = "update #OBJECT# set #PROPERTY# = :expression where #IDENTIFIER# in (:ids)";
	
	public static final String SOFT_DELETE_QUERY_MULTIPLE_UPDATE = "update #OBJECT# set #PROPERTY# where #IDENTIFIER# in (:ids)";
	
	public static final String ALIAS = "main";
	
	public static final String AND = " AND ";
	
	public static final String IN = " IN ";
	
	public static final String LIKE = " LIKE ";
	
	public static final String[] byPass = {"active", "number", "viewable", "editable", "removed", "serialVersionUID"};
	
	public static final String[] eqFields = {"double","int","long", "float", "class java.lang.Integer", 
												"class java.lang.Float", "class java.lang.Double", "class java.lang.Long", 
												"class java.util.Date", "class java.sql.Time", "boolean", "class java.lang.Boolean"};
}
