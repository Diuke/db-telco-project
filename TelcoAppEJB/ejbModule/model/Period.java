package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Period database table.
 * 
 */
@Entity
@Table(name = "Period", schema = "telco_app_db")
@NamedQuery(name="Period.findAll", query="SELECT p FROM Period p")
public class Period implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private int months;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="period", fetch = FetchType.LAZY)
	private List<Order> orders;

	//bi-directional many-to-one association to PackagePeriod
	@OneToMany(mappedBy="period", fetch = FetchType.LAZY)
	private List<PackagePeriod> packagePeriods;

	public Period() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMonths() {
		return this.months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setPeriod(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setPeriod(null);

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
		packagePeriod.setPeriod(this);

		return packagePeriod;
	}

	public PackagePeriod removePackagePeriod(PackagePeriod packagePeriod) {
		getPackagePeriods().remove(packagePeriod);
		packagePeriod.setPeriod(null);

		return packagePeriod;
	}

}