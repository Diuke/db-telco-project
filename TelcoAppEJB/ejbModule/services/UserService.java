package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.NonUniqueResultException;
import model.User;
import exceptions.*;
import java.util.List;

@Stateless
public class UserService {
	@PersistenceContext(unitName = "TelcoAppEJB")
	private EntityManager em;

	public UserService() {
	}

	public User checkCredentials(String username, String password) throws CredentialsException, NonUniqueResultException {
		List<User> uList = null;
		try {
			System.out.println("im here!");
			uList = em.createNamedQuery("User.checkCredentials", User.class).setParameter(1, username).setParameter(2, password).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CredentialsException("Could not verify credentals");
		}
		if (uList.isEmpty())
			return null;
		else if (uList.size() == 1) 
			return uList.get(0);
		throw new NonUniqueResultException("More than one user registered with same credentials");

	}
	
	public User checkCredentialsEmployee(String username, String password) throws CredentialsException, NonUniqueResultException {
		List<User> uList = null;
		try {
			uList = em.createNamedQuery("User.checkCredentials", User.class).setParameter(1, username).setParameter(2, password).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CredentialsException("Could not verify credentals");
		}
		if (uList.isEmpty()) {
			return null;
		}
		else if (uList.size() == 1) {
			User u = uList.get(0);
			if(u.getRole() != User.ROLE_EMPLOYEE) {
				throw new CredentialsException("Could not verify credentals");
			}
			return u;
		}
		throw new NonUniqueResultException("More than one user registered with same credentials");

	}
	
	public boolean registerUser(String username, String password, String email) { 
		try {
			User newUser = new User();
			newUser.setUsername(username);
			newUser.setPassword(password);
			newUser.setEmail(email);
			newUser.setRole(1); //new normal user
			em.persist(newUser);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void addOneFailedPaymentToUser(int userId) {
		try {
			User userToAdd = em.find(User.class, userId);
			userToAdd.setfailedPayment(userToAdd.getfailedPayment() + 1);
			em.persist(userToAdd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}