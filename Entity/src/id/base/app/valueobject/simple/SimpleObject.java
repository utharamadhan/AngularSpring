package id.base.app.valueobject.simple;

import id.base.app.valueobject.Lookup;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SIMPLE_OBJECT")
public class SimpleObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6750157036347273474L;
	
	public static final SimpleObject getInstance() {
		return new SimpleObject();
	}

	@Id
	@SequenceGenerator(name="SIMPLE_OBJECT_PK_SIMPLE_OBJECT_SEQ", sequenceName="SIMPLE_OBJECT_PK_SIMPLE_OBJECT_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SIMPLE_OBJECT_PK_SIMPLE_OBJECT_SEQ")
	@Column(name = "PK_SIMPLE_OBJECT", unique = true ,nullable = false)
	private Long pkSimpleObject;

	@Column(name = "NAME")
	private String name;
	
	@ManyToOne
	@JoinColumn(name="FK_LOOKUP_TYPE")
	private Lookup typeLookup;
	
	@Column(name="STATUS")
    private Integer status;

	public Long getPkSimpleObject() {
		return pkSimpleObject;
	}
	public void setPkSimpleObject(Long pkSimpleObject) {
		this.pkSimpleObject = pkSimpleObject;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Lookup getTypeLookup() {
		return typeLookup;
	}
	public void setTypeLookup(Lookup typeLookup) {
		this.typeLookup = typeLookup;
	}

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
