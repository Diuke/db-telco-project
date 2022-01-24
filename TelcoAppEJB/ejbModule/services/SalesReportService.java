package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.*;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class SalesReportService {
	@PersistenceContext(unitName = "TelcoAppEJB")
	private EntityManager em;
	
	public SalesReportService() {
	}
	
	public List<MvSales> getSalesReport(){		
		return em.createNamedQuery("mvSales.findAll", MvSales.class).getResultList();
	}
	
	public List<MvProduct> getProductSalesReport(){		
		return em.createNamedQuery("mvProduct.findAll", MvProduct.class).getResultList();
	}
	
	public List<MvSalesPeriod> getSalesPeriodReport(){		
		return em.createNamedQuery("mvSalesPeriod.findAll", MvSalesPeriod.class).getResultList();
	}
	
	
}