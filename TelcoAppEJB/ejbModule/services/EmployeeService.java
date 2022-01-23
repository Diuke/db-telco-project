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
public class EmployeeService {
	@PersistenceContext(unitName = "TelcoAppEJB")
	private EntityManager em;
	
	public EmployeeService() {
	}
	
	public boolean insertPackageProduct(Integer productId, Integer packageId) {
		try {
			em.createNativeQuery("INSERT INTO telco_app_db.ProductPackage (product_id, package_id) VALUES (?,?)")
			.setParameter(1, productId)
			.setParameter(2, packageId)
			.executeUpdate();
			return true;
			
		} catch (Exception e) {
			return false; 
		}
	}
	
	public boolean insertServicePackage(Integer serviceId, Integer packageId) {
		try {
			em.createNativeQuery("INSERT INTO telco_app_db.ProductPackage (service_id, package_id) VALUES (?,?)")
			.setParameter(1, serviceId)
			.setParameter(2, packageId)
			.executeUpdate();
			return true;
			
		} catch (Exception e) {
			return false; 
		}
	}
	
	public boolean insertPackagePeriod(Integer periodId, Integer packageId, float value) {
		try {
			em.createNativeQuery("INSERT INTO telco_app_db.ProductPackage (period_id, package_id, value) VALUES (?,?,?)")
			.setParameter(1, periodId)
			.setParameter(2, packageId)
			.setParameter(3, value)
			.executeUpdate();
			return true;
			
		} catch (Exception e) {
			return false; 
		}
	}
	
	public boolean createOptionalProduct(String name, float value) {
		try {
			Product newProduct = new Product();
			newProduct.setName(name);
			newProduct.setValue(value);
			em.persist(newProduct);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean createServicePackage(String packageName, String periodsAndValues, String services, String products) {	
		try {
			//return em.createNamedQuery("TelcoPackage.findAll", TelcoPackage.class).getResultList();
			String[] periodsAndValuesList = periodsAndValues.split(",");
			String[] servicesList = services.split(",");
			String[] productsList = products.split(",");
			
			TelcoPackage packageToCreate = new TelcoPackage();
			packageToCreate.setName(packageName);
			
			Integer packageId = packageToCreate.getId();
			
			for(String productId: productsList) {
				Integer pId = Integer.parseInt(productId);
				Product p = em.find(Product.class, pId);
				packageToCreate.getProducts().add(p);
			}
			
			for(String serviceId: servicesList) {
				Integer sId = Integer.parseInt(serviceId);
				Service s = em.find(Service.class, sId);
				packageToCreate.getServices().add(s);
			}
			
			for(String pv: periodsAndValuesList) {
				System.out.println(pv);
				String[] periodValue = pv.split("!");
				Integer peId = Integer.parseInt(periodValue[0]);
				float value = Float.parseFloat(periodValue[1]);
				Period pe = em.find(Period.class, peId);
				PackagePeriod packPeriod = new PackagePeriod();
				packPeriod.setValue(value);
				packPeriod.setPeriod(pe);
				packPeriod.setTelcoPackage(packageToCreate);
				packageToCreate.getPackagePeriods().add(packPeriod);
			}
			em.persist(packageToCreate);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
}