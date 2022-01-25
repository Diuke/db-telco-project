package model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Order database table.
 * 
 */
@Entity
@Table(name = "Order", schema = "telco_app_db")
@NamedQuery(name="Order.findAll", query="SELECT o FROM Order o")
@NamedQuery(name="Order.getByUserId", query="SELECT o FROM Order o WHERE o.user.userId = ?1 ORDER BY o.id DESC")
@NamedQuery(name="Order.getSuspendedOrders", query="SELECT o FROM Order o WHERE o.status = 2 OR o.status = 0")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int REJECTED = 2;
	public static final int PAID = 1;
	public static final int PENDING = 0;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="purchase_date")
	private Date purchaseDate;

	private int status;

	@Temporal(TemporalType.DATE)
	@Column(name="suscription_start_date") 
	private Date suscriptionStartDate;

	private float total;

	//bi-directional many-to-one association to Period
	@ManyToOne(fetch = FetchType.LAZY)
	private Period period;

	//bi-directional many-to-one association to TelcoPackage
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="package_id")
	private TelcoPackage telcoPackage;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;

	//bi-directional many-to-many association to Order
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name="OrderProduct"
		, joinColumns={
			@JoinColumn(name="order_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="product_id")
			}
		)
	private List<Product> products;

	@OneToMany(mappedBy="order", fetch = FetchType.LAZY)
	private List<Schedule> schedules;

	public Order() {
		this.products = new ArrayList<Product>();
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getPurchaseDate() {
		return this.purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getSuscriptionStartDate() {
		return this.suscriptionStartDate;
	}

	public void setSuscriptionStartDate(Date suscriptionStartDate) {
		this.suscriptionStartDate = suscriptionStartDate;
	}

	public float getTotal() {
		return this.total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public Period getPeriod() {
		return this.period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public TelcoPackage getTelcoPackage() {
		return this.telcoPackage;
	}

	public void setTelcoPackage(TelcoPackage telcoPackage) {
		this.telcoPackage = telcoPackage;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Schedule> getSchedules() {
		return this.schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}

	public Schedule addSchedule(Schedule schedule) {
		getSchedules().add(schedule);
		schedule.setOrder(this);

		return schedule;
	}

	public Schedule removeSchedule(Schedule schedule) {
		getSchedules().remove(schedule);
		schedule.setOrder(null);

		return schedule;
	}


}