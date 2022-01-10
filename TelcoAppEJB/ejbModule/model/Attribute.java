package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Attribute database table.
 * 
 */
@Entity
@Table(name = "Attribute", schema = "telco_app_db")
@NamedQuery(name="Attribute.findAll", query="SELECT a FROM Attribute a")
public class Attribute implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	
	private String name;

	@Column(name="extra_value")
	private float extraValue;

	private float quantity;

	//bi-directional many-to-one association to Service
	@ManyToOne
	private Service service;

	public Attribute() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getExtraValue() {
		return this.extraValue;
	}

	public void setExtraValue(float extraValue) {
		this.extraValue = extraValue;
	}

	public float getQuantity() {
		return this.quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public Service getService() {
		return this.service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}