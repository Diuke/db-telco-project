package controllers;

import java.io.IOException;
import java.util.HashMap;

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
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import model.User;
import exceptions.CredentialsException;
import services.UserService;

@WebServlet("/CheckLogin")
public class CheckLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "services/UserService")
	private UserService usrService;

	public CheckLogin() {
		super();
	}

	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		// obtain and escape params
		String username = null;
		String password = null;
		try {
			username = StringEscapeUtils.escapeJava(request.getParameter("username"));
			password = StringEscapeUtils.escapeJava(request.getParameter("password"));
			if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
				throw new Exception("Missing or empty credential value");
			}

		} catch (Exception e) {
			// for debugging only e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
			return;
		}
		User user = null;
		try {
			// query db to authenticate for user
			user = usrService.checkCredentials(username, password);
		} catch (CredentialsException | NonUniqueResultException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials");
			return;
		}

		// If the user exists, add info to the session and go to home page, otherwise
		// show login page with error message

		String path;
		if (user == null) {
			ServletContext servletContext = getServletContext();
			final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
			ctx.setVariable("errorMsg", "Incorrect username or password");
			path = "/login.html";
			templateEngine.process(path, ctx, response.getWriter());
		} else {
			//QueryService qService = null;
			try {
				// Get the Initial Context for the JNDI lookup for a local EJB
				InitialContext ic = new InitialContext();
				// Retrieve the EJB using JNDI lookup
				//qService = (QueryService) ic.lookup("java:/openejb/local/QueryServiceLocalBean");
			} catch (Exception e) {
				e.printStackTrace();
			}
			session.setAttribute("user", user);
			
			System.out.println(session.getAttribute("user"));
			
			String goTo = (String)session.getAttribute("goto");
			HashMap<String, String> params = (HashMap<String, String>)session.getAttribute("gotoParams");
			
			if(goTo == null) {
				response.sendRedirect("/TelcoAppWEB/home"); 
				return;
			} else {
				String redirect = "/TelcoAppWEB/" + goTo;
				if(params != null) {
					redirect += "?";
					for(String p: params.keySet()) {
						redirect += p.toString() + "=" + params.get(p) + "&";
					}
				}
				session.removeAttribute("goto");
				session.removeAttribute("gotoParams");
				response.sendRedirect(redirect);
				return;
			}
			
		}

	}

	public void destroy() {
	}
}