package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the User database table.
 * 
 */
@Entity
@Table(name = "User", schema = "telco_app_db")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
@NamedQuery(name = "User.checkCredentials", query = "SELECT r FROM User r WHERE r.username = ?1 and r.password = ?2")

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final long ROLE_USER = 1;
	public static final long ROLE_EMPLOYEE = 2;

	@Id
	@Column(name="user_id")
	private int userId;

	private byte insolvent;

	private String password;

	private int role;
	
	private int failed_payment;

	private String username;

	//bi-directional many-to-one association to AudTable
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY)
	private List<AudTable> audTables;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY)
	private List<Order> orders;

	public User() {
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public byte getInsolvent() {
		return this.insolvent;
	}

	public void setInsolvent(byte insolvent) {
		this.insolvent = insolvent;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getfailedPayment() {
		return this.failed_payment;
	}

	public void setfailedPayment(int failedPayment) {
		this.failed_payment = failedPayment;
	}
	
	public int getRole() {
		return this.role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<AudTable> getAudTables() {
		return this.audTables;
	}

	public void setAudTables(List<AudTable> audTables) {
		this.audTables = audTables;
	}

	public AudTable addAudTable(AudTable audTable) {
		getAudTables().add(audTable);
		audTable.setUser(this);

		return audTable;
	}

	public AudTable removeAudTable(AudTable audTable) {
		getAudTables().remove(audTable);
		audTable.setUser(null);

		return audTable;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setUser(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setUser(null);

		return order;
	}

}