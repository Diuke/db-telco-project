package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Service database table.
 * 
 */
@Entity
@Table(name = "Service", schema = "telco_app_db")
@NamedQuery(name="Service.findAll", query="SELECT s FROM Service s")
public class Service implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String name;

	//bi-directional many-to-one association to Attribute
	@OneToMany(mappedBy="service", fetch = FetchType.EAGER)
	private List<Attribute> attributes;

	//bi-directional many-to-many association to TelcoPackage
	@ManyToMany(mappedBy="services", fetch = FetchType.LAZY)
	private List<TelcoPackage> telcoPackages;

	public Service() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Attribute> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public Attribute addAttribute(Attribute attribute) {
		getAttributes().add(attribute);
		attribute.setService(this);

		return attribute;
	}

	public Attribute removeAttribute(Attribute attribute) {
		getAttributes().remove(attribute);
		attribute.setService(null);

		return attribute;
	}

	public List<TelcoPackage> getTelcoPackages() {
		return this.telcoPackages;
	}

	public void setTelcoPackages(List<TelcoPackage> telcoPackages) {
		this.telcoPackages = telcoPackages;
	}

}