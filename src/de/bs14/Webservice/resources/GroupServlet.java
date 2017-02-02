package de.bs14.Webservice.resources;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import de.bs14.Webservice.model.Group;
import de.bs14.Webservice.model.User;
import de.bs14.Webservice.resources.db.DBConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GroupServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private DBConnection dbClient = new DBConnection();
	private ArrayList<Group> groupList;
	private Logger logger = LoggerFactory.getLogger(GroupServlet.class);

	/**
	 * Verarbeitet POST-Requests auf /groups, indem an doGET weitergeleitet wird.
	 */
	@POST
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
		logger.debug("GroupServlet doPost augeführt.");
		doGet(request, response);
	}
	/**
	 * Verarbeitet GET-Requests auf /groups, indem ein Datenbankaufruf
	 * angestossen wird und speichert die Gruppen des Users in der Session.
	 */
	@GET
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug("GroupServlet doGet augeführt.");
		User user = (User) request.getSession().getAttribute("user");
		try {
			groupList = dbClient.getAllGroups(user.getUsername());
			logger.info("GroupList erhalten.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.getSession().setAttribute("groupList", groupList);
		request.getRequestDispatcher("/WEB-INF/groups.jsp").forward(request, response);
	}
	
}
