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
	
	public boolean insertOrderProduct(Integer orderId, Integer productId) {
		try {
			em.createNativeQuery("INSERT INTO telco_app_db.OrderProduct (order_id, product_id) VALUES (?,?)")
			.setParameter(1, orderId)
			.setParameter(2, productId)
			.executeUpdate();
			return true;
			
		} catch (Exception e) {
			return false; 
		}
		
	}
	
	public boolean setOrderStatusPaid(Integer orderId) {
		try {
			Order order = em.find(Order.class, orderId);
			if(order.getStatus() == Order.PAID) {
				return false;
			}
			System.out.println(order);
			
			order.setStatus(Order.PAID);
			em.persist(order);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Order> getListOfOrdersByUser(User user) { 
		return em.createNamedQuery("Order.getByUserId", Order.class).setParameter(1, user.getUserId()).getResultList();
	}
	
	public Integer createOrder(Integer userId, Integer packageId, Integer periodId, Float total, String startingDate, String productIds){
		User orderUser = em.find(User.class, userId);
		TelcoPackage orderPackage = em.find(TelcoPackage.class, packageId); 
		Period orderPeriod = em.find(Period.class, periodId);
		Date today = new Date();
		String[] dateSplit = startingDate.split("-"); // [yyyy, mm, dd]
		int day = Integer.parseInt(dateSplit[2]);
		int month = Integer.parseInt(dateSplit[1]);
		int year = Integer.parseInt(dateSplit[0]);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date startDate = cal.getTime();
		
		String[] productIdsSplit = productIds.split(","); //comma-separated ids of products
		
		System.out.println(total);
		
		try {				
			Order newOrder = new Order();
			newOrder.setUser(orderUser);
			newOrder.setTelcoPackage(orderPackage);
			newOrder.setPeriod(orderPeriod);
			newOrder.setPurchaseDate(today);
			newOrder.setTotal(total);
			newOrder.setSuscriptionStartDate(startDate);
			newOrder.setStatus(Order.PENDING);
			
			em.persist(newOrder);
			em.flush();
			
			Integer addedOrderId = newOrder.getId();
			//Order addedOrder = em.getReference(Order.class, addedOrderId);
			
			
			for(String productId: productIdsSplit) {
				Integer pId = Integer.parseInt(productId);
				boolean added = this.insertOrderProduct(addedOrderId, pId);
				if(!added) {
					throw new Exception("Error adding a product");
				}
			}
			
			return addedOrderId;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
	}
}