package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the Schedule database table.
 * 
 */
@Entity
@Table(name = "Schedule", schema = "telco_app_db")
@NamedQuery(name="Schedule.findAll", query="SELECT s FROM Schedule s")
public class Schedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@OneToOne(fetch = FetchType.EAGER)
	private Order order;
	
	@Temporal(TemporalType.DATE)
	@Column(name="activation") 
	private Date activation;

	@Temporal(TemporalType.DATE)
	@Column(name="deactivation") 
	private Date deactivation;
	
	public Schedule() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	public Date getActivation() {
		return this.activation;
	}

	public void setActivation(Date activation) {
		this.activation = activation;
	}
	
	public Date getDeactivation() {
		return this.deactivation;
	}

	public void setDeactivation(Date deactivation) {
		this.deactivation = deactivation;
	}

}