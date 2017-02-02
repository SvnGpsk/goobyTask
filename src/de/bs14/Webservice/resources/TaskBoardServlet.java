package de.bs14.Webservice.resources;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bs14.Webservice.model.Group;
import de.bs14.Webservice.model.Task;
import de.bs14.Webservice.resources.db.DBConnection;

public class TaskBoardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private DBConnection dbClient = new DBConnection();
	private Logger logger = LoggerFactory.getLogger(TaskBoardServlet.class);

	/**
	 * Verarbeitet POST-Requests auf /taskboard, indem doGet aufgerufen wird .
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.debug("TaskBoardServlet doPost ausgeführt.");
		doGet(request, response);
	}

	/**
	 * Verarbeitet GET-Requests auf /taskboard, indem ein Datenbankaufruf
	 * angestossen wird, der die Aufgaben der Gruppe lädt und liefert die
	 * taskboard.jsp mit diesen Daten aus.
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String groupid = request.getParameter("groupid");
		Group group = null;
		group = (Group) request.getSession().getAttribute("groupobject");
		if (groupid != null) {
			try {
				group = dbClient.getGroupById(groupid);
			} catch (SQLException e) {
				logger.error("Fehler beim auslesen der Gruppe aus der DB.");
			}
		}
		ArrayList<Task> allTasks = null;
		ArrayList<Task> todoTasks = new ArrayList<Task>();
		ArrayList<Task> inprogressTasks = new ArrayList<Task>();
		ArrayList<Task> checkTasks = new ArrayList<Task>();
		ArrayList<Task> doneTasks = new ArrayList<Task>();

		try {
			allTasks = dbClient.getTasksFromGroup(group);
		} catch (SQLException e) {
			logger.error("Fehler beim auslesen der Aufgaben der Gruppe aus der DB.");
		}
		for (Task task : allTasks) {
			if (task.getTaskstate().equals("todo")) {
				todoTasks.add(task);
			}
			if (task.getTaskstate().equals("inprogress")) {
				inprogressTasks.add(task);
			}
			if (task.getTaskstate().equals("check")) {
				checkTasks.add(task);
			}
			if (task.getTaskstate().equals("done")) {
				doneTasks.add(task);
			}
		}

		request.getSession().setAttribute("todoTaskList", todoTasks);
		request.getSession()
				.setAttribute("inprogressTaskList", inprogressTasks);
		request.getSession().setAttribute("checkTaskList", checkTasks);
		request.getSession().setAttribute("doneTaskList", doneTasks);

		request.getSession().setAttribute("groupobject", group);
		request.getRequestDispatcher("/WEB-INF/taskboard.jsp").forward(request,
				response);

	}
}
