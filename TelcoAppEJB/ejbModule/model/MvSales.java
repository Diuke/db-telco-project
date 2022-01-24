package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Product database table.
 * 
 */
@Entity
@Table(name = "MvSales", schema = "telco_app_db")
@NamedQuery(name="mvSales.findAll", query="SELECT p FROM MvSales p")

public class MvSales implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="package_id")
	private int packageId;
	
	@Column(name="sales_with")
	private float salesWith;
	
	@Column(name="sales_without")
	private float salesWithout;
	
	@Column(name="total_puchases")
	private int totalPurchases;
	
	@Column(name="avg_optional")
	private float averageOptional;

	public MvSales() {
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

	public float getSalesWith() {
		return salesWith;
	}

	public void setSalesWith(float salesWith) {
		this.salesWith = salesWith;
	}

	public float getSalesWithout() {
		return salesWithout;
	}

	public void setSalesWithout(float salesWithout) {
		this.salesWithout = salesWithout;
	}

	public int getTotalPurchases() {
		return totalPurchases;
	}

	public void setTotalPurchases(int totalPurchases) {
		this.totalPurchases = totalPurchases;
	}

	public float getAverageOptional() {
		return averageOptional;
	}

	public void setAverageOptional(float averageOptional) {
		this.averageOptional = averageOptional;
	}

	

	

}