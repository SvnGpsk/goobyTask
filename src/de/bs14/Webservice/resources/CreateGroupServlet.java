package de.bs14.Webservice.resources;

import java.io.IOException;
import java.util.ArrayList;

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

public class CreateGroupServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private DBConnection dbClient = new DBConnection();
	private ArrayList<String> groupList = new ArrayList<String>();
	private Logger logger = LoggerFactory.getLogger(CreateGroupServlet.class);

	/**
	 * Verarbeitet POST-Requests auf /creategroup, indem ein Datenbankaufruf
	 * angestossen wird und leitet and das groups-Servlet weiter (/groups).
	 */
	@POST
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.debug("CreateGroupServlet doPost ausgeführt.");
		response.setContentType("text/html");
		String groupname = request.getParameter("gruppenname");
		User user = (User) request.getSession().getAttribute("user");

		try {
			dbClient.insertGroup(groupname, user.getUsername());
			groupList.add(groupname);
		} catch (Exception e) {
			logger.error("Fehler beim Erstellen der Gruppe in der DB.");
		}
		String groupsJSP = "/groups";
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(groupsJSP);
		dispatcher.forward(request, response);
	}

}
