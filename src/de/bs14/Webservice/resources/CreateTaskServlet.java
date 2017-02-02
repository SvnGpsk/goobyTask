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

public class CreateTaskServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private DBConnection dbClient = new DBConnection();
	private Logger logger = LoggerFactory.getLogger(CreateTaskServlet.class);

	/**
	 * Verarbeitet POST-Requests auf /createtask, indem ein Datenbankaufruf
	 * angestossen wird und schickt ein GET-Request an das Task-Board ab.
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String taskname = request.getParameter("taskname");
		String taskworker = request.getParameter("taskworker");
		String taskstate = request.getParameter("taskstate");
		Group group = (Group) request.getSession().getAttribute("groupobject");
		try {
			dbClient.createTask(taskname, taskworker, taskstate, group);
		} catch (Exception e) {
			logger.error("Fehler beim Erstellen der Aufgabe.");
		}
		doGet(request, response);
	}

	/**
	 * Verarbeitet GET-Requests auf /createtask, indem an das Task-Board
	 * weitergeleitet wird.
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Group group = (Group) request.getSession().getAttribute("groupobject");
		ArrayList<Task> allTasks = null;
		ArrayList<Task> todoTasks = new ArrayList<Task>();
		ArrayList<Task> inprogressTasks = new ArrayList<Task>();
		ArrayList<Task> checkTasks = new ArrayList<Task>();
		ArrayList<Task> doneTasks = new ArrayList<Task>();

		try {
			allTasks = dbClient.getTasksFromGroup(group);
		} catch (SQLException e) {
			logger.error("Fehler beim Lesen der Aufgaben der Gruppe.");
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

		request.getRequestDispatcher("/WEB-INF/taskboard.jsp").forward(request,
				response);
	}

}
