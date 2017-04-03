package de.bs14.Webservice.resources;

import java.io.IOException;
import java.sql.SQLException;
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

import de.bs14.Webservice.model.Group;
import de.bs14.Webservice.model.Task;
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
