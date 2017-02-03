package id.base.app.valueobject.post;

import id.base.app.valueobject.BaseEntity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "POST_CONTENT")
public class PostContent extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 806029044551504365L;
	
	@Id
	@Column(name = "PK_POST_CONTENT", unique = true ,nullable = false)
	private Long pkPostContent;
	
	@ManyToOne(cascade={CascadeType.DETACH})
	@JoinColumn(name="FK_POST_CATEGORY")
	private PostCategory postCategory;
	
	@Column(name = "POST_TITLE")
	private String postTitle;
	
	@Column(name = "STOCK_CONTENT")
	private String stockContent;

	public Long getPkPostContent() {
		return pkPostContent;
	}

	public void setPkPostContent(Long pkPostContent) {
		this.pkPostContent = pkPostContent;
	}

	public PostCategory getPostCategory() {
		return postCategory;
	}

	public void setPostCategory(PostCategory postCategory) {
		this.postCategory = postCategory;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getStockContent() {
		return stockContent;
	}

	public void setStockContent(String stockContent) {
		this.stockContent = stockContent;
	}
}