package model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the TelcoPackage database table.
 * 
 */
@Entity
@Table(name = "TelcoPackage", schema = "telco_app_db")
@NamedQuery(name="TelcoPackage.findAll", query="SELECT t FROM TelcoPackage t")
public class TelcoPackage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="telcoPackage", fetch = FetchType.LAZY)
	private List<Order> orders;

	//bi-directional many-to-one association to PackagePeriod
	@OneToMany(mappedBy="telcoPackage", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER) 
	private List<PackagePeriod> packagePeriods;

	//bi-directional many-to-many association to Product
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name="ProductPackage"
		, joinColumns={
			@JoinColumn(name="package_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="product_id")
			}
		)
	private List<Product> products;

	//bi-directional many-to-many association to Service
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name="ServicePackage"
		, joinColumns={
			@JoinColumn(name="package_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="service_id")
			}
		)
	private List<Service> services;

	public TelcoPackage() {
		this.products = new ArrayList<Product>();
		this.services = new ArrayList<Service>();
		this.packagePeriods = new ArrayList<PackagePeriod>();
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

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setTelcoPackage(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setTelcoPackage(null);

		return order;
	}

	public List<PackagePeriod> getPackagePeriods() {
		return this.packagePeriods;
	}

	public void setPackagePeriods(List<PackagePeriod> packagePeriods) {
		this.packagePeriods = packagePeriods;
	}

	public PackagePeriod addPackagePeriod(PackagePeriod packagePeriod) {
		getPackagePeriods().add(packagePeriod);
		packagePeriod.setTelcoPackage(this);

		return packagePeriod;
	}

	public PackagePeriod removePackagePeriod(PackagePeriod packagePeriod) {
		getPackagePeriods().remove(packagePeriod);
		packagePeriod.setTelcoPackage(null);

		return packagePeriod;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Service> getServices() {
		return this.services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

}