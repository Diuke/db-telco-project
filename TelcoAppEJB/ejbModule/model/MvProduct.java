package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Product database table.
 * 
 */
@Entity
@Table(name = "MvProduct", schema = "telco_app_db")
@NamedQuery(name="mvProduct.findAll", query="SELECT p FROM MvProduct p")

public class MvProduct implements Serializable { 
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private int id;
	
	@Column(name="product_name")
	private String productName;
	
	@Column(name="product_id")
	private int productId;
	
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) { 
		this.productId = productId;
	}

	@Column(name="total_sales")
	private float totalSales;

	public MvProduct() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public float getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(float totalSales) {
		this.totalSales = totalSales;
	}

	

}