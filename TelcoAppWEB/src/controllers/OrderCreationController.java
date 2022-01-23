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
import services.OrderService;

import java.util.List;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * Servlet implementation class GoToLoginPage
 */
@WebServlet("/CreateOrder")
public class OrderCreationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "services/ApiService")
	private ApiService apiService;
	
	@EJB(name = "services/OrderService")
	private OrderService orderService;

	public OrderCreationController() {
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
		
		Integer userId = null;
		Integer packageId = null;
		Integer periodId = null;
		Float total = null;
		String startingDate = null;
		String productIds = null;
		if (request.getParameterMap().containsKey("userId") && request.getParameter("userId") != "" && !request.getParameter("userId").isEmpty()) {
			userId = Integer.parseInt(request.getParameter("userId"));
		}
		if (request.getParameterMap().containsKey("packageId") && request.getParameter("packageId") != "" && !request.getParameter("packageId").isEmpty()) {
			packageId = Integer.parseInt(request.getParameter("packageId"));
		}
		if (request.getParameterMap().containsKey("periodId") && request.getParameter("periodId") != "" && !request.getParameter("periodId").isEmpty()) {
			periodId = Integer.parseInt(request.getParameter("periodId"));
		}
		if (request.getParameterMap().containsKey("total") && request.getParameter("total") != "" && !request.getParameter("total").isEmpty()) {
			total = Float.parseFloat(request.getParameter("total"));
			System.out.println(total);
			
		}
		if (request.getParameterMap().containsKey("startingDate") && request.getParameter("startingDate") != "" && !request.getParameter("startingDate").isEmpty()) {
			startingDate = request.getParameter("startingDate").toString();
		}
		if (request.getParameterMap().containsKey("productIds") && request.getParameter("productIds") != "" && !request.getParameter("productIds").isEmpty()) {
			productIds = request.getParameter("productIds").toString();
		} 
		
		int orderCreated = orderService.createOrder(userId, packageId, periodId, total, startingDate, productIds);
		
		String message = "";
		if(orderCreated != -1) {
			message = "ORDER CREATED";
		} else {
			message = "THERE WAS AN ERROR! ORDER NOT CREATED!";
		}

		String path = "/WEB-INF/order.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("message", message); 
		ctx.setVariable("orderId", orderCreated);
		ctx.setVariable("loggedInUser", loggedInUser);   
		templateEngine.process(path, ctx, response.getWriter());

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
