package model;

import java.io.Serializable;
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

	//bi-directional many-to-one association to Order
	@ManyToOne
	private Order order;

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

}