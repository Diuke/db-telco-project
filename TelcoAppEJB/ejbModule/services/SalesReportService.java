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
	
	public List<User> getInsolventUsers(){		
		return em.createNamedQuery("User.getInsolvent", User.class).getResultList();
	}
	
	public List<AudTable> getAlerts(){		
		return em.createNamedQuery("AudTable.findAll", AudTable.class).getResultList();
	}

	public List<Order> getSuspendedOrders(){		
		return em.createNamedQuery("Order.getSuspendedOrders", Order.class).getResultList();
	}
	
	
}