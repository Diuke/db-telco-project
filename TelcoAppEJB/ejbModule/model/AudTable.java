package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the AudTable database table.
 * 
 */
@Entity
@Table(name = "AudTable", schema = "telco_app_db")
@NamedQuery(name="AudTable.findAll", query="SELECT a FROM AudTable a")
public class AudTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private float ammount;

	private String email;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_rejection")
	private Date lastRejection;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public AudTable() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getAmmount() {
		return this.ammount;
	}

	public void setAmmount(float ammount) {
		this.ammount = ammount;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getLastRejection() {
		return this.lastRejection;
	}

	public void setLastRejection(Date lastRejection) {
		this.lastRejection = lastRejection;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}