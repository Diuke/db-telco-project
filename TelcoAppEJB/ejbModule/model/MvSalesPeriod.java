package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Product database table.
 * 
 */
@Entity
@Table(name = "MvSalesPeriod", schema = "telco_app_db")
@NamedQuery(name="mvSalesPeriod.findAll", query="SELECT p FROM MvSalesPeriod p")

public class MvSalesPeriod implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private int id;
	
	@Column(name="package_id")
	private int packageId;
	
	@Column(name="period_id")
	private int periodId;
	
	private int months;
	
	@Column(name="total_purchases")
	private int totalPurchases;
	
	private String name;

	public MvSalesPeriod() {
	}

	public int getId() {  
		return id; 
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPackageId() {
		return packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}

	public int getPeriodId() {
		return periodId;
	}

	public void setPeriodId(int periodId) {
		this.periodId = periodId;
	}

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	public int getTotalPurchases() {
		return totalPurchases;
	}

	public void setTotalPurchases(int totalPurchases) {
		this.totalPurchases = totalPurchases;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}