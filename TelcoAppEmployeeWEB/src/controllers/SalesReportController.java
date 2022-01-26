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
import services.SalesReportService;

import java.util.List;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * Servlet implementation class GoToLoginPage
 */
@WebServlet("/salesReport")
public class SalesReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "services/SalesReportService")
	private SalesReportService salesReportService;

	public SalesReportController() {
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
		 
		List<MvSales> salesReport = salesReportService.getSalesReport();  
		List<MvProduct> productReport = salesReportService.getProductSalesReport(); 
		List<MvSalesPeriod> salesPeriodReport = salesReportService.getSalesPeriodReport();
		List<User> insolventUsers = salesReportService.getInsolventUsers();
		List<AudTable> alerts = salesReportService.getAlerts();
		List<Order> suspendedOrders = salesReportService.getSuspendedOrders();

		String path = "/WEB-INF/salesReport.html";
		ServletContext servletContext = getServletContext(); 
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		System.out.println(productReport);
		
		ctx.setVariable("salesReport", salesReport); 
		ctx.setVariable("productReport", productReport);
		ctx.setVariable("salesPeriodReport", salesPeriodReport); 
		ctx.setVariable("insolventUsers", insolventUsers); 
		ctx.setVariable("alerts", alerts);
		ctx.setVariable("suspendedOrders", suspendedOrders); 
		ctx.setVariable("loggedInUser", loggedInUser);    
		templateEngine.process(path, ctx, response.getWriter());

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
