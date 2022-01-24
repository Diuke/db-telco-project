package controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.persistence.NonUniqueResultException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;

import model.*;
import services.UserService;

import java.util.HashMap;
import java.util.List;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import exceptions.CredentialsException;

/**
 * Servlet implementation class GoToLoginPage
 */
@WebServlet("/Register")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "services/UserService")
	private UserService usrService; 

	public RegisterController() {
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
		
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			response.sendRedirect("/TelcoAppWEB/home"); 
			return;
		} 

		String path = "/WEB-INF/register.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale()); 
		ctx.setVariable("hasLogin", false);
		templateEngine.process(path, ctx, response.getWriter());
 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		// obtain and escape params
		String username = null;
		String password = null;
		String email = null;
		try {
			username = StringEscapeUtils.escapeJava(request.getParameter("username"));
			password = StringEscapeUtils.escapeJava(request.getParameter("password"));
			email = StringEscapeUtils.escapeJava(request.getParameter("email"));
			if (username == null || password == null || username.isEmpty() || password.isEmpty() || email == null || email.isEmpty()) {
				throw new Exception("Missing or empty credential value");
			}

		} catch (Exception e) {
			// for debugging only e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
			return;
		} 
		
		boolean userCreated = usrService.registerUser(username, password, email);
		String path;
		if(!userCreated) {
			ServletContext servletContext = getServletContext(); 
			final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
			ctx.setVariable("errorMsg", "Could not create user, try again.");
			ctx.setVariable("hasLogin", false);
			path = "/WEB-INF/register.html";
			templateEngine.process(path, ctx, response.getWriter());
		} else {
			ServletContext servletContext = getServletContext();
			final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
			ctx.setVariable("successMsg", "User registered successfuly!");
			ctx.setVariable("hasLogin", true);
			path = "/WEB-INF/register.html";
			templateEngine.process(path, ctx, response.getWriter());
		}
		
		
	}

}
