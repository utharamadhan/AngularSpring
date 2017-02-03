package id.base.app.valueobject.post;

import id.base.app.valueobject.BaseEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "POST_CATEGORY")
public class PostCategory extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -3694426625701710600L;
	
	@Id
	@Column(name = "PK_POST_CATEGORY", unique = true ,nullable = false)
	private Long pkPostCategory;
	
	@Column(name = "CATEGORY_CODE")
	private String categoryCode;
	
	@Column(name = "CATEGORY_DESCR")
	private String categoryDescr;

	public Long getPkPostCategory() {
		return pkPostCategory;
	}

	public void setPkPostCategory(Long pkPostCategory) {
		this.pkPostCategory = pkPostCategory;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryDescr() {
		return categoryDescr;
	}

	public void setCategoryDescr(String categoryDescr) {
		this.categoryDescr = categoryDescr;
	}
}