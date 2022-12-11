package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AreaData {
	Connection connection;
	Statement statement; 
	public AreaData() throws ClassNotFoundException, SQLException {
		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TicketBooking","postgres","1344");
		statement = connection.createStatement();
	}
	public ResultSet getAllArea() {
		try {
			ResultSet result = statement.executeQuery("select * from trainstops");
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public ResultSet getPlacesByID(String id) {
		System.out.println(id);
		try {
			ResultSet result = statement.executeQuery("select * from trainstops where trainid="+id);
			
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public void closeConnection() throws SQLException {
		connection.close();
	}
}
