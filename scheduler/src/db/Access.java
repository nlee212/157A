package db;

import java.sql.*;
import java.util.ArrayList;

public class Access {

	/**
	 * populate database
	 * 
	 */

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/sjsu2";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "";
	private Connection conn = null;

	public ArrayList<ArrayList<Course>> formatInput(String[] courses) {
		ArrayList<ArrayList<Course>> list = new ArrayList<ArrayList<Course>>();
		String current = "";
		Statement stmt = null;
		for (String c : courses) {
			String query = "SELECT * from courses WHERE course=\"" + c + "\"";
			try {
				stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery(query);
				while (result.next()) {

					String course = result.getString("course");
					int section = result.getInt("section");
					int classNumber = result.getInt("class_nbr");
					String days = result.getString("days");
					String times = result.getString("times");
					String location = result.getString("location");
					String instructor = result.getString("instructor");
					if (!times.equalsIgnoreCase("TBA")) {

						String[] t = times.split("-");
						Course nc = new Course(course, "" + classNumber, ""
								+ section, days, Integer.parseInt(t[0]),
								Integer.parseInt(t[1]),location, instructor);
						if (!current.equals(course)) {
							current = course;
							list.add(new ArrayList<Course>());
						}
						list.get(list.size() - 1).add(nc);
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				return null;
			}
		}

		return list;
	}

	public void close() {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}

	}

	public void connect() {
		try {
			java.lang.Class.forName(JDBC_DRIVER); // Register JDBC Driver
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}