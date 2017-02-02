package de.bs14.Webservice.resources.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bs14.Webservice.model.Group;
import de.bs14.Webservice.model.Task;
import de.bs14.Webservice.model.User;

public class DBConnection {
	/**
	 * Erzeugen der Datenbank
	 * 
	 * @return
	 * @throws Exception
	 */
	private static Logger logger = LoggerFactory.getLogger(DBConnection.class);
	
	@SuppressWarnings("finally")
	public static Connection createConnection() throws Exception {
		Connection con = null;
		try {
			Class.forName(Constants.dbClass);
			con = DriverManager.getConnection(Constants.dbUrl,
					Constants.dbUser, Constants.dbPwd);
		} catch (Exception e) {
			throw e;
		} finally {
			return con;
		}
	}

	/**
	 * Überprüfung, ob Username und Passwort richtig sind
	 * 
	 * @param uname
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	public static boolean checkLogin(String uname, String pwd) throws Exception {
		boolean isUserAvailable = false;
		Connection dbConn = null;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				logger.error("Fehler in der Datenbankverbindung.");
			}
			Statement stmt = dbConn.createStatement();
			String query = "SELECT * FROM user WHERE username = '" + uname
					+ "' AND password=" + "'" + pwd + "'";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				isUserAvailable = true;
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return isUserAvailable;
	}

	/**
	 * Einfügen der Userdaten in die Datenbank bei Registrierung
	 * 
	 * @param name
	 * @param uname
	 * @param pwd
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public static boolean insertUser(String username, String pwd, String email)
			throws SQLException, Exception {
		boolean insertStatus = false;
		Connection dbConn = null;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				logger.error("Fehler in der Datenbankverbindung.");
			}
			Statement stmt = dbConn.createStatement();
			String query = "INSERT into user(username, password, email) values('"
					+ username + "'," + "'" + pwd + "','" + email + "')";
			int records = stmt.executeUpdate(query);
			if (records > 0) {
				insertStatus = true;
			}
		} catch (Exception e) {
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return insertStatus;
	}

	/**
	 * Einfügen von Gruppen in die Datenbank und der Zuordnung zum User
	 * 
	 * @param groupName
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public boolean insertGroup(String groupName, String username)
			throws Exception {
		Connection dbConn = null;
		boolean insertStatus = false;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				logger.error("Fehler in der Datenbankverbindung.");
			}
			Statement stmt = dbConn.createStatement();
			String query = "INSERT into groups(groupname) values('" + groupName
					+ "')";
			int records = stmt.executeUpdate(query);
			query = "INSERT into usergrouprelation(userid,groupid) values('"
					+ username + "','" + getGroupIdByName(groupName) + "')";
			records = stmt.executeUpdate(query);
			if (records > 0) {
				insertStatus = true;
			}
		} catch (Exception e) {
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return insertStatus;
	}

	/**
	 * Auslesen der Gruppen-ID durch den Namen
	 * 
	 * @param groupName
	 * @return
	 * @throws SQLException
	 */
	private String getGroupIdByName(String groupName) throws SQLException {
		String groupId = null;
		Connection dbConn = null;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				logger.error("Fehler in der Datenbankverbindung.");
			}
			Statement stmt = dbConn.createStatement();
			String query = "SELECT * FROM groups WHERE groupname = '"
					+ groupName + "'";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				groupId = rs.getString("groupid");
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return groupId;
	}

	/**
	 * Auslesen der Userdaten durch den Usernamen
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public User getUserFromDbByUsername(String username) throws Exception {
		Connection dbConn = null;
		User user = null;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				logger.error("Fehler in der Datenbankverbindung.");
			}
			Statement stmt = dbConn.createStatement();
			String query = "Select * FROM user WHERE username = '" + username
					+ "'";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String uname = rs.getString("username");
				String password = rs.getString("password");
				String email = rs.getString("email");
				user = new User(uname, password, email, "0");
			}
		} catch (SQLException sqle) {

			throw sqle;
		} catch (Exception e) {
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}

		return user;
	}

	/**
	 * Auslesen aller User aus der Datenbank
	 * 
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Group> getAllUsers() throws Exception {
		Connection dbConn = null;
		Group group = null;
		ArrayList<Group> groupList = new ArrayList<Group>();
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				logger.error("Fehler in der Datenbankverbindung.");
			}
			Statement stmt = dbConn.createStatement();
			String query = "Select * FROM user;";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String groupid = rs.getString("username");
				String groupname = rs.getString("password");
				group = new Group(groupid, groupname);
				groupList.add(group);
			}
		} catch (SQLException sqle) {

			throw sqle;
		} catch (Exception e) {
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}

		return groupList;
	}

	/**
	 * Auslesen aller Gruppen für einen bestimmten User
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Group> getAllGroups(String username) throws Exception {
		Connection dbConn = null;
		Group group = null;
		ArrayList<Group> groupList = new ArrayList<Group>();
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				logger.error("Fehler in der Datenbankverbindung.");
			}
			Statement stmt = dbConn.createStatement();
			String groupid = "";
			String query = "SELECT * FROM usergrouprelation WHERE userid ='"
					+ username + "'";
			ResultSet rs = stmt.executeQuery(query);
			ResultSet rsName = null;
			ArrayList<String> groupIdList = new ArrayList<String>();
			while (rs.next()) {
				groupid = rs.getString("groupid");
				groupIdList.add(groupid);
			}
			for (String id : groupIdList) {
				String queryName = "SELECT * FROM groups WHERE groupid ='" + id
						+ "'";
				rsName = stmt.executeQuery(queryName);
				while (rsName.next()) {
					String groupname = rsName.getString("groupname");
					group = new Group(id, groupname);
					groupList.add(group);
				}
			}
		} catch (SQLException sqle) {

			throw sqle;
		} catch (Exception e) {
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}

		return groupList;
	}

	/**
	 * Auslesen des Gruppennamens durch die Gruppen-ID
	 * 
	 * @param groupid
	 * @return
	 * @throws SQLException
	 */
	public Group getGroupById(String groupid) throws SQLException {
		Connection dbConn = null;
		Group group = null;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				logger.error("Fehler in der Datenbankverbindung.");
			}
			Statement stmt = dbConn.createStatement();
			String query = "Select * FROM groups WHERE groupid = '" + groupid
					+ "'";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String groupname = rs.getString("groupname");
				group = new Group(groupid, groupname);
			}
		} catch (SQLException sqle) {

			throw sqle;
		} catch (Exception e) {
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}

		return group;
	}

	/**
	 * Einfügen eines Users in die usergrouprelation-Tabelle,
	 * damit dieser Mitglied einer bestimmten Gruppe wird
	 * 
	 * @param user
	 * @param group
	 * @return
	 * @throws SQLException
	 */
	public boolean addUserToGroup(User user, Group group) throws SQLException {
		Connection dbConn = null;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				logger.error("Fehler in der Datenbankverbindung.");
			}
			Statement stmt = dbConn.createStatement();
			String query = "INSERT into usergrouprelation(userid,groupid) values('"
					+ user.getUsername() + "','" + group.getGroupid() + "')";
			stmt.executeUpdate(query);
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			if (dbConn != null) {
				dbConn.close();
				return false;
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return true;
	}

	/**
	 * Einfügen einer neuen Aufgabe in die "tasks"-Tabelle 
	 * und Zuordnung der Aufgabe zur einer Gruppe 
	 * 
	 * @param taskname
	 * @param taskworker
	 * @param taskstate
	 * @param group
	 * @throws Exception
	 */
	public void createTask(String taskname, String taskworker,
			String taskstate, Group group) throws Exception {
		Connection dbConn = null;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				logger.error("Fehler in der Datenbankverbindung.");
			}
			Statement stmt = dbConn.createStatement();
			String query = "INSERT into tasks(taskname, taskworker, taskstate) values('"
					+ taskname
					+ "',"
					+ "'"
					+ taskworker
					+ "','"
					+ taskstate
					+ "')";
			stmt.executeUpdate(query);
			
			query = "INSERT into taskgrouprelation(taskname, groupid) values('"
					+ taskname + "'," + "'" + group.getGroupid()
					+ "')";
			stmt.executeUpdate(query);
		}  catch (Exception e) {
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
	}

	/**
	 * Auslesen aller Aufgaben, die einer Gruppe zugehören
	 * 
	 * @param group
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Task> getTasksFromGroup(Group group) throws SQLException {
		ArrayList<Task> allTasks = new ArrayList<Task>();
		Connection dbConn = null;
		Task task = null;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				logger.error("Fehler in der Datenbankverbindung.");
			}
			Statement stmt = dbConn.createStatement();
			String query = "SELECT * FROM taskgrouprelation WHERE groupid ='"
					+ group.getGroupid() + "'";
			ResultSet rs = stmt.executeQuery(query);
			ResultSet rsTasks = null;
			String taskname= null;
			ArrayList<String> tasknameList = new ArrayList<String>();
			while (rs.next()) {
				taskname = rs.getString("taskname");
				tasknameList.add(taskname);
			}
			for (String tasknameLoop : tasknameList) {
				String queryName = "SELECT * FROM tasks WHERE taskname ='" + tasknameLoop
						+ "'";
				rsTasks = stmt.executeQuery(queryName);
				while (rsTasks.next()) {
					String tasknameTemp = rsTasks.getString("taskname");
					String taskworkerTemp = rsTasks.getString("taskworker");
					String taskstateTemp = rsTasks.getString("taskstate");
					task = new Task(tasknameTemp,taskworkerTemp,taskstateTemp);
					allTasks.add(task);
				}
			}
		} catch (SQLException sqle) {

			throw sqle;
		} catch (Exception e) {
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}

		return allTasks;
	}

	/**
	 * Veränderung des Aufgabenstatus in der Datenbank
	 * 
	 * @param taskid
	 * @param taskstate
	 * @throws SQLException
	 */
	public void modifyTask(String taskid, String taskstate) throws SQLException {
		Connection dbConn = null;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				logger.error("Fehler in der Datenbankverbindung.");
			}
			Statement stmt = dbConn.createStatement();
			String query = "UPDATE tasks SET taskstate='"+taskstate+"' WHERE taskname='"+taskid+"'";
			stmt.executeUpdate(query);	
		}  catch (Exception e) {
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		
	}

	/**
	 * Löschen einer Aufgabe aus der Datenbank
	 * 
	 * @param taskid
	 * @throws SQLException
	 */
	public void deleteTask(String taskid) throws SQLException {
		Connection dbConn = null;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				logger.error("Fehler in der Datenbankverbindung.");
			}
			Statement stmt = dbConn.createStatement();
			String query = "DELETE FROM tasks WHERE taskname='"+taskid+"'";
			stmt.executeUpdate(query);	
			query = "DELETE FROM taskgrouprelation WHERE taskname='"+taskid+"'";
			stmt.executeUpdate(query);
		}  catch (Exception e) {
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		
	}

}
