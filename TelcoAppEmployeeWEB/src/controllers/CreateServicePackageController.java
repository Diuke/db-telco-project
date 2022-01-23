package controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jose4j.json.internal.json_simple.JSONObject;
import org.thymeleaf.TemplateEngine;

import model.*;
import services.ApiService;
import services.EmployeeService;
import services.OrderService;

import java.util.List;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * Servlet implementation class GoToLoginPage
 */
@WebServlet("/create")
public class CreateServicePackageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "services/ApiService")
	private ApiService apiService;
	
	@EJB(name = "services/EmployeeService")
	private EmployeeService employeeService;

	public CreateServicePackageController() {
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
		User loggedInUser = null;
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) { 
			loggedInUser = (User)session.getAttribute("user"); 
		} 
		if(loggedInUser == null) {
			response.sendRedirect("/TelcoAppEmployeeWEB/Login");
			return;
		}
		
		List<Period> periods = apiService.getListOfValidityPeriods();  
		List<Service> services = apiService.getListOfServices();  
		List<Product> products = apiService.getListOfProducts();

		String path = "/WEB-INF/create.html";
		ServletContext servletContext = getServletContext(); 
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("periods", periods); 
		ctx.setVariable("services", services); 
		ctx.setVariable("products", products); 
		ctx.setVariable("loggedInUser", loggedInUser);   
		templateEngine.process(path, ctx, response.getWriter());

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String PACKAGE_STRING = "package";
		String PRODUCT_STRING = "product";
		
		String productIds = null;
		String servicesIds = null;
		String periodsValuesIds = null;
		String packageName = null;
		String create = null;
		if (request.getParameterMap().containsKey("do") && request.getParameter("do") != "" && !request.getParameter("do").isEmpty()) {
			create = request.getParameter("do").toString(); 
		} 
		if (request.getParameterMap().containsKey("name") && request.getParameter("name") != "" && !request.getParameter("name").isEmpty()) {
			packageName = request.getParameter("name").toString();
		} 
		if (request.getParameterMap().containsKey("periods") && request.getParameter("periods") != "" && !request.getParameter("periods").isEmpty()) {
			periodsValuesIds = request.getParameter("periods").toString();  
		} 
		if (request.getParameterMap().containsKey("products") && request.getParameter("products") != "" && !request.getParameter("products").isEmpty()) {
			productIds = request.getParameter("products").toString();
		} 
		if (request.getParameterMap().containsKey("services") && request.getParameter("services") != "" && !request.getParameter("services").isEmpty()) {
			servicesIds = request.getParameter("services").toString();  
		} 
		
		String productName = null;
		float productValue = 0f; 
		if (request.getParameterMap().containsKey("productName") && request.getParameter("productName") != "" && !request.getParameter("productName").isEmpty()) {
			productName = request.getParameter("productName").toString();
		} 
		if (request.getParameterMap().containsKey("productValue") && request.getParameter("productValue") != "" && !request.getParameter("productValue").isEmpty()) {
			productValue = Float.parseFloat(request.getParameter("productValue").toString());
		} 
		
		boolean created = false;
		if(create.equals("package")) {    
			created = employeeService.createServicePackage(packageName, periodsValuesIds, servicesIds, productIds);
		} else if(create.equals("product")) { 
			created = employeeService.createOptionalProduct(productName, productValue);
		} else {
			created = false;
		}
		
		JSONObject resp = new JSONObject();
		resp.put("result", created);
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(resp);
		out.flush();
		return;
	}

}
