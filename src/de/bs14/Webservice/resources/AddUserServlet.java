package de.bs14.Webservice.resources;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.bs14.Webservice.model.Group;
import de.bs14.Webservice.model.User;
import de.bs14.Webservice.resources.db.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private DBConnection dbclient = new DBConnection();
	private Logger logger = LoggerFactory.getLogger(AddUserServlet.class);

	/**
	 * Lädt die taskboard.jsp.
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.debug("AddUserServlet doGet ausgeführt.");
		request.getRequestDispatcher("/WEB-INF/taskboard.jsp").forward(request,
				response);
	}

	/**
	 * Verarbeitet POST-Requests auf /addUser, indem ein Datenbankaufruf
	 * angestossen wird und schickt ein get an das Task-Board ab.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String username = req.getParameter("userToAdd");
		Group group = (Group) req.getSession().getAttribute("groupobject");
		User user = null;
		try {
			user = dbclient.getUserFromDbByUsername(username);
			dbclient.addUserToGroup(user, group);
			logger.info("User erhalten und der Gruppe [" + group.getGroupname()
					+ "] hinzugefügt");
		} catch (Exception e) {
			logger.error("Fehler beim hinzufügen des Users zur Gruppe.");
		}
		doGet(req, resp);
	}
}
