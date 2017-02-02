package de.bs14.Webservice.resources.db;

/**
 * 
 * @author it4-gapssv
 *	Konstanten für die Verbindung zu Datenbank.
 */
public class Constants {
  public static String dbClass = "com.mysql.jdbc.Driver";
  private static String dbName= "users";
  public static String dbUrl = "jdbc:mysql://localhost:3306/"+dbName;
  public static String dbUser = "root";
  public static String dbPwd = "";
}