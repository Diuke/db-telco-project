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

import java.util.HashMap;
import java.util.List;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
   
/**
 * Servlet implementation class HomepageController 
 */
@WebServlet("/show")
public class ShowController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "services/ApiService")
	private ApiService apiService;

	public ShowController() {
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
		
		Integer packageId = null;
		if (request.getParameterMap().containsKey("packageId") && request.getParameter("packageId") != "" && !request.getParameter("packageId").isEmpty()) {
			packageId = Integer.parseInt(request.getParameter("packageId"));
		}
		
		TelcoPackage packageDetails = null;
		if(packageId != null) {
			packageDetails = apiService.getPackageById(packageId);  
		} 

		String path = "/WEB-INF/show.html";     
		ServletContext servletContext = getServletContext(); 
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("packageDetails", packageDetails); 
		ctx.setVariable("loggedInUser", loggedInUser);
		templateEngine.process(path, ctx, response.getWriter());

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
