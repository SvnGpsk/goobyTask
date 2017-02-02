package de.bs14.Webservice.resources;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bs14.Webservice.resources.db.DBConnection;

public class DeleteTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBConnection dbClient = new DBConnection();
	private Logger logger = LoggerFactory.getLogger(DeleteTaskServlet.class);

	/**
	 * Verarbeitet POST-Requests auf /deletetask, indem ein Datenbankaufruf
	 * angestossen wird und leitet an das Taskboard-Servlet weiter.
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String taskid = request.getParameter("taskidDelete");
		try {
			dbClient.deleteTask(taskid);
		} catch (SQLException e) {
			logger.error("Fehler beim Löschen der Aufgabe.");
		}
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher("/taskboard");
		dispatcher.forward(request, response);
	}

}
