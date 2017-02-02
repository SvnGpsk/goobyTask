package de.bs14.Webservice.resources;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bs14.Webservice.resources.db.DBConnection;

public class ModifyTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBConnection dbClient = new DBConnection();
	private Logger logger = LoggerFactory.getLogger(CreateGroupServlet.class);

	/**
	 * Verarbeitet POST-Requests auf /modifytask, indem ein Datenbankaufruf
	 * angestossen wird zur Aenderung des Aufgabenstatus und schickt ein GET an das Task-Board ab.
	 */
	@POST
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.debug("ModifyTaskServlet doPost ausgeführt.");

		String taskid = request.getParameter("taskidModified");
		String taskstate = request.getParameter("taskstateModified");
		
		try {
			dbClient.modifyTask(taskid, taskstate);
		} catch (SQLException e) {
			logger.error("Fehler beim bearbeiten der Aufgabe.");
		}
		
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher("/taskboard");
		dispatcher.forward(request, response);
	}
}
