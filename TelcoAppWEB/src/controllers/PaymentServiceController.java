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

import org.apache.commons.lang.StringEscapeUtils;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.thymeleaf.TemplateEngine;

import model.*;
import services.ApiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * Servlet implementation class HomepageController
 */
@WebServlet("/pay")
public class PaymentServiceController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "services/ApiService")
	private ApiService apiService;

	public PaymentServiceController() { 
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
	
	//If random is true, outcome does not matter.
	private boolean paymentStatus(boolean random, boolean outcome) {
		if(random) { 
			Random rand = new Random();
			boolean paymentResponse = rand.nextBoolean(); 
			return paymentResponse;
		} else {
			return outcome;
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {  
		boolean isRandom = false, outcome = false;
		Integer orderId = null;
		if (request.getParameterMap().containsKey("isRandom") && request.getParameter("isRandom") != "" && !request.getParameter("isRandom").isEmpty()) {
			isRandom = Integer.parseInt(request.getParameter("isRandom").toString()) == 1;
		}
		
		if (request.getParameterMap().containsKey("outcome") && request.getParameter("outcome") != "" && !request.getParameter("outcome").isEmpty()) {
			outcome = Integer.parseInt(request.getParameter("outcome").toString()) == 1 ? true : false;
		}
		
		if (request.getParameterMap().containsKey("orderId") && request.getParameter("orderId") != "" && !request.getParameter("orderId").isEmpty()) {
			orderId= Integer.parseInt(request.getParameter("orderId").toString()); 
		}
		
		boolean payStatus;
		boolean operationSuccess; 
		System.out.println(orderId);
		if(orderId == null) {  
			payStatus = false;
		} else {
			payStatus = this.paymentStatus(isRandom, outcome);
			if(payStatus) {  
				System.out.println("in");
				operationSuccess = apiService.setOrderStatusPaid(orderId);
				System.out.println(operationSuccess);
				if(!operationSuccess) payStatus = false; 
			}
		}
		
		
		JSONObject resp = new JSONObject();
		resp.put("result", payStatus);
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(resp);
		out.flush();
		
		return;

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException { 
		doGet(request, response);
	}

}
