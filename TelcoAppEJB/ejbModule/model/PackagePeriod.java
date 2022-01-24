package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the PackagePeriod database table.
 * 
 */
@Entity
@Table(name = "PackagePeriod", schema = "telco_app_db")
@NamedQuery(name="PackagePeriod.findAll", query="SELECT p FROM PackagePeriod p")
@NamedQuery(name="PackagePeriod.findByPackageIdPeriodId", query="SELECT p FROM PackagePeriod p WHERE p.telcoPackage.id = ?1 AND p.period.id = ?2")
public class PackagePeriod implements Serializable { 
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PackagePeriodPK id;

	private float value;

	//bi-directional many-to-one association to Period
	@ManyToOne
	private Period period;

	//bi-directional many-to-one association to TelcoPackage
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name="package_id")
	private TelcoPackage telcoPackage;

	public PackagePeriod() {
	}

	public PackagePeriodPK getId() {
		return this.id;
	}

	public void setId(PackagePeriodPK id) {
		this.id = id;
	}

	public float getValue() {
		return this.value;
	}

	public void setValue(float value) {
		this.value = value;
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

}