package de.bs14.Webservice.resources;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bs14.Webservice.resources.db.DBConnection;

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(RegisterServlet.class);

	/**
	 * Verarbeitet POST-Requests auf /register, indem ein Datenbankaufruf mit
	 * den Werten des Formulars angestossen wird und registriert somit einen neuen User.
	 */
	@POST
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		String sessionId = request.getSession().getId();
		logger.debug("sessionId: " + sessionId);
		String username = request.getParameter("register-username");
		String password = request.getParameter("register-password");
		String email = request.getParameter("register-email");

		logger.debug("Inside doRegister " + username + "  " + password);
		int retCode = registerUser(username, password, email);
		if (retCode == 0) {
			response.sendRedirect("../de.bs14");
		} else if (retCode == 1) {
			String loginErrorJSP = "/registerError.jsp";
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(loginErrorJSP);
			logger.debug("User schon registriert!");
			dispatcher.forward(request, response);

			// response = Utility.constructJSON("register",false,
			// "You are already registered");
		} else if (retCode == 2) {
			String loginErrorJSP = "/registerError.jsp";
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(loginErrorJSP);
			logger.debug("Sonderzeichen sind nicht erlaubt!");
			dispatcher.forward(request, response);

			// response = Utility.constructJSON("register",false,
			// "Special Characters are not allowed in Username and Password");
		} else if (retCode == 3) {
			String loginErrorJSP = "/registerError.jsp";
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(loginErrorJSP);
			logger.debug("Fehler bei der User Registrierung.");
			dispatcher.forward(request, response);
		}
	}
	/**
	 * Anstoﬂen der Datenbankoperation zur registrierung eines neuen Users.
	 * @param username
	 * @param pwd
	 * @param email
	 * @return
	 */
	private int registerUser(String username, String pwd, String email) {
		System.out.println("Inside checkCredentials");
		int result = 3;
		if (Utility.isNotNull(username) && Utility.isNotNull(pwd)) {
			try {
				if (DBConnection.insertUser(username, pwd, email)) {
					logger.debug("In RegisterUser if");
					result = 0;
				}
			} catch (SQLException sqle) {
				logger.error("RegisterUser catch SQLException(Sonderzeichen)");
				// When Primary key violation occurs that means user is already
				// registered
				if (sqle.getErrorCode() == 1062) {
					result = 1;
				}
				// When special characters are used in name, username or
				// password
				else if (sqle.getErrorCode() == 1064) {
					logger.error("Error CODE:" + sqle.getErrorCode());
					result = 2;
				}
			} catch (Exception e) {
				logger.error("Inside checkCredentials catch e. Fehler beim Registrieren des Users.");
				result = 3;
			}
		} else {
			System.out.println("Inside checkCredentials else");
			result = 3;
		}

		return result;
	}

}