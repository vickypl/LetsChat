/**
 * 
 */
package com.chat.db;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
	author: Vicky pl
	email: vicky542011@gmail.com
	mobile: 7828789845
 **/
/**
 * @author Anonymox
 *
 */
public class DatabaseConnector {

	public void writer(Exception e, String servletname) throws Exception {
		
		//method in DatabaseConnector for getting path of properties file
		String path = getPathOfProperties();
		
		//accessing properties file to read the logfiles path
		FileReader reader = new FileReader(path);
		Properties p = new Properties();
		p.load(reader);
		String realPath = p.getProperty("errorLogFilePath");
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(realPath, true)));
		pw.write("Name of Servlet: -> "+servletname+"\n");
		pw.write("Date/Time of Error: -> "+new java.util.Date()+"\n");
		e.printStackTrace(pw);
		pw.write("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
		pw.close();
	}
	
	
	public void  logToFile(Exception e, String servletname) {
		try { writer(e, servletname); } catch (Exception exception) { e.printStackTrace(); }
	}
	
	//this method returns the path of the properties file
	public String getPathOfProperties() {
		String path = DatabaseConnector.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String myFile="system.properties";
		//String myFile="com\\chat\\db\\system.properties";
		path = path.replace("DatabaseConnector.class", myFile);
/*		path = path+myFile;
		path = path.replace("/", "\\");
		path = path.substring(1, path.length());*/
		return path;
	}
	
	public String getPropertiesPath(String key) throws IOException {
		String path = getPathOfProperties();
		FileReader reader  = new FileReader(path);
			Properties p = new Properties();
			p.load(reader);
		String realPath = p.getProperty(key);
		return realPath;
	}
	
	public Connection connectTo() throws Exception {
		
		String path=getPathOfProperties();
		
		FileReader reader = new FileReader(path);
		Properties p = new Properties();
		p.load(reader);
		String dbUser = p.getProperty("dbUser");
		String dbPass = p.getProperty("dbPass");

		Connection connection = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			connection=DriverManager.getConnection(url, dbUser, dbPass);
		} catch(ClassNotFoundException e) {
			writer(e, "dbConnector");
		} catch(SQLException e) {
			writer(e, "dbConnector");
		} catch(Exception e) {
			writer(e, "dbConnector");
		}
		return connection;
	}
	
	public void closeConnection(Connection connection) throws Exception{
		if(connection!=null) {
			try {
				connection.close();
			} catch(SQLException e) {
				writer(e, "dbConnector");
			}
		}
	}
}
