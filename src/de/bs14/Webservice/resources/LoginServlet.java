package de.bs14.Webservice.resources;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bs14.Webservice.model.User;
import de.bs14.Webservice.resources.db.DBConnection;

public class LoginServlet extends HttpServlet {
	public HashMap<String, User> loggedInUsers = new HashMap<String, User>();
	private static final long serialVersionUID = 1L;
	private DBConnection dbclient = new DBConnection();
	private Logger logger = LoggerFactory.getLogger(LoginServlet.class);

	/**
	 * Verarbeitet POST-Requests auf /login, indem ein Datenbankaufruf
	 * angestossen wird und die username/password kombination ueberprueft wird.
	 * Letztendlich wird man an /groups weitergeleitet.
	 */
	@POST
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		String sessionId = request.getSession().getId();
		System.out.println(sessionId);
		String username = request.getParameter("login-username");
		String password = request.getParameter("login-password");
		String groupsJSP = "/groups";
		User user = null;

		if (loggedInUsers.get(username) != null) {
			user = loggedInUsers.get(username);

			if (!user.getSessionId().equals("0")) {
				if (checkCredentials(username, password)) {
					logger.info("User " + username + " hat sich eingeloggt.");
					request.getSession().setAttribute("user", user);
					ServletContext context = getServletContext();
					RequestDispatcher dispatcher = context
							.getRequestDispatcher(groupsJSP);
					dispatcher.forward(request, response);
				} else {
					response.sendRedirect("../de.bs14");
				}
			} else {
				if (checkCredentials(username, password)) {
					logger.info("User " + username + " hat sich eingeloggt.");
					user.setSessionId(sessionId);
					loggedInUsers.put(user.getUsername(), user);
					request.getSession().setAttribute("user", user);
					ServletContext context = getServletContext();
					RequestDispatcher dispatcher = context
							.getRequestDispatcher(groupsJSP);
					dispatcher.forward(request, response);
				} else {
					response.sendRedirect("../de.bs14");
				}
			}
		} else {
			if (checkCredentials(username, password)) {
				logger.info("User " + username + " hat sich eingeloggt.");
				try {
					user = dbclient.getUserFromDbByUsername(username);
				} catch (Exception e) {
					logger.error("Fehler beim Abfragen des USers aus der DB.");
				}
				user.setSessionId(sessionId);
				loggedInUsers.put(user.getUsername(), user);
				request.getSession().setAttribute("user", user);
				ServletContext context = getServletContext();
				RequestDispatcher dispatcher = context
						.getRequestDispatcher(groupsJSP);
				dispatcher.forward(request, response);

			} else {
				response.sendRedirect("../de.bs14");
			}
		}
	}

	/**
	 * Methode die ueberprueft, ob das eingegebene Passwort zu dem User gehört.
	 * Wenn ja, dann Login erfolgreich. 
	 * 
	 * @param uname
	 * @param pwd
	 * @return
	 */
	private boolean checkCredentials(String uname, String pwd) {
		System.out.println("Inside checkCredentials");
		boolean result = false;
		if (Utility.isNotNull(uname) && Utility.isNotNull(pwd)) {
			try {
				result = DBConnection.checkLogin(uname, pwd);
			} catch (Exception e) {
				System.out.println("Inside checkCredentials catch");
				result = false;
			}
		} else {
			result = false;
		}

		return result;
	}

}
