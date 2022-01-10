package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Product database table.
 * 
 */
@Entity
@Table(name = "Product", schema = "telco_app_db")
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
@NamedQuery(name="Product.getProductsByIds", query="SELECT p FROM Product p WHERE p.id in ?1")

public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String name;

	private float value;

	//bi-directional many-to-many association to Order
	@ManyToMany
	@JoinTable(
		name="OrderProduct"
		, joinColumns={
			@JoinColumn(name="product_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="order_id")
			}
		)
	private List<Order> orders;

	//bi-directional many-to-many association to TelcoPackage
	@ManyToMany(mappedBy="products")
	private List<TelcoPackage> telcoPackages;

	public Product() {
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

	public float getValue() {
		return this.value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<TelcoPackage> getTelcoPackages() {
		return this.telcoPackages;
	}

	public void setTelcoPackages(List<TelcoPackage> telcoPackages) {
		this.telcoPackages = telcoPackages;
	}

}