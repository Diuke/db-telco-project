package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Stateless
public class ApiService {
	@PersistenceContext(unitName = "TelcoAppEJB")
	private EntityManager em;
	
	public ApiService() {
	}
	
	public List<TelcoPackage> getListOfServicePackages(){		
		return em.createNamedQuery("TelcoPackage.findAll", TelcoPackage.class).getResultList();
	}
	
	public List<Period> getListOfValidityPeriods(){		 
		return em.createNamedQuery("Period.findAll", Period.class).getResultList();
	}
	
	public List<Service> getListOfServices(){		
		return em.createNamedQuery("Service.findAll", Service.class).getResultList();
	}
	
	public List<Product> getListOfProducts(){		
		return em.createNamedQuery("Product.findAll", Product.class).getResultList();
	}
	
	public TelcoPackage getPackageById(Integer id) {
		return em.find(TelcoPackage.class, id);
	}
	
	public Period getPeriodById(Integer id) {
		return em.find(Period.class, id);
	} 
	
	public List<Product> getProductsById(ArrayList<Integer> ids) { 
		return em.createNamedQuery("Product.getProductsByIds", Product.class).setParameter(1, ids).getResultList();
	}
	
	public PackagePeriod getPackagePeriodByPackageIdPeriodId(Integer packageId, Integer periodId) {
		return em.createNamedQuery("PackagePeriod.findByPackageIdPeriodId", PackagePeriod.class).setParameter(1, packageId).setParameter(2, periodId).getSingleResult();
	}
}