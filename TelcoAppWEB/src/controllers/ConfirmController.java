package controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;

import model.*;
import services.ApiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * Servlet implementation class HomepageController
 */
@WebServlet("/confirm")
public class ConfirmController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "services/ApiService")
	private ApiService apiService;

	public ConfirmController() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException { 
		
		Integer packageId = null;
		Integer periodId = null; 
		ArrayList<Integer> productList = null;
		String startingDate = null; //YYYY-MM-DD -> As it comes from HTML
		
		//let queryParams = "?periodId=" + this.selectedPeriodId + "&products=" + productsQueryParam + "&package=" + this.packageId;
		if (request.getParameterMap().containsKey("periodId") && request.getParameter("periodId") != "" && !request.getParameter("periodId").isEmpty()) {
			periodId = Integer.parseInt(request.getParameter("periodId"));
		}
		
		if (request.getParameterMap().containsKey("startingDate") && request.getParameter("startingDate") != "" && !request.getParameter("startingDate").isEmpty()) {
			startingDate = request.getParameter("startingDate").toString();
		}
		
		if (request.getParameterMap().containsKey("package") && request.getParameter("package") != "" && !request.getParameter("package").isEmpty()) {
			packageId = Integer.parseInt(request.getParameter("package"));
		}
		
		String productString = "";
		if (request.getParameterMap().containsKey("products") && request.getParameter("products") != "" && !request.getParameter("products").isEmpty()) {
			productString = request.getParameter("products").toString();
			String[] productSplit = productString.split(",");
			productList = new ArrayList<Integer>();
			for(String s: productSplit) {
				productList.add(Integer.parseInt(s));
			}
		}
		
		User loggedInUser = null;
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("user") == null) {
			session.setAttribute("goto", "confirm");
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("periodId", periodId.toString());
			params.put("package", packageId.toString());
			params.put("products", productString);
			params.put("startingDate", startingDate);
			
			
			session.setAttribute("gotoParams", params);
			response.sendRedirect("/TelcoAppWEB/Login");
			return;
		}
		
		loggedInUser = (User)session.getAttribute("user");

		List<Product> selectedProducts = null;
		PackagePeriod selectedPackagePeriod = null;
		
		if(productList != null) {
			selectedProducts = apiService.getProductsById(productList); 
		} 
		
		Float pricePerMonthForProducts = 0f;
		for(int i = 0; i < selectedProducts.size(); i++) {
			pricePerMonthForProducts += selectedProducts.get(i).getValue();
		}
		
		if(periodId != null && packageId != null) {
			System.out.println(packageId + ", " + periodId); 
			selectedPackagePeriod = apiService.getPackagePeriodByPackageIdPeriodId(packageId, periodId);
		} 
		
		
		Float totalPrice = selectedPackagePeriod.getPeriod().getMonths() * (selectedPackagePeriod.getValue() + pricePerMonthForProducts);
		Float pricePerMonth = selectedPackagePeriod.getValue(); 

		String path = "/WEB-INF/confirm.html";   
		ServletContext servletContext = getServletContext(); 
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("selectedPackagePeriod", selectedPackagePeriod); 
		ctx.setVariable("selectedProducts", selectedProducts); 
		ctx.setVariable("selectedProductsIds", productString);   
		ctx.setVariable("startingDate", startingDate); 
		ctx.setVariable("totalPrice", totalPrice);   
		ctx.setVariable("pricePerMonth", pricePerMonth);
		ctx.setVariable("pricePerMonthForProducts", pricePerMonthForProducts);
		ctx.setVariable("loggedInUser", loggedInUser);
		templateEngine.process(path, ctx, response.getWriter());

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException { 
		doGet(request, response);
	}

}
