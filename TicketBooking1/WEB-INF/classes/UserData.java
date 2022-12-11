package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.DeserializationException;
import org.json.simple.JsonArray;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;
import org.postgresql.util.PSQLException;

public class UserData {
	Connection connection;
	Statement statement; 
	public UserData() throws ClassNotFoundException, SQLException {
		
		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TicketBooking","postgres","1344");
		statement = connection.createStatement();
	}
	public boolean verifyAdminmail(String mail) throws SQLException {
		ResultSet resultSet = statement.executeQuery("select * from admin");
		while(resultSet.next()) {
			if(resultSet.getString(3).equals(mail)) {
				if(resultSet.getString(5).equals("1")){
					connection.close();
					return true;	
				}
			}
		}
		return false;
	}
	public boolean verifyAdmin(String mail,String pass) throws SQLException {
		ResultSet resultSet = statement.executeQuery("select * from users");
		while(resultSet.next()) {
			if(resultSet.getString(3).equals(mail)) {
				if(resultSet.getString(4).equals(pass)) {
					if(resultSet.getString(5).equals("1")){
					connection.close();
					return true;
					}
				}
			}
		}
		return false;
	}
	public boolean addAdmin(String name,String pass) throws SQLException {
		return statement.execute("update users set mail='"+name+"',password='"+pass+"' where id =1");
	}
	public boolean verifyEmail(String mail) throws SQLException {
		System.out.println("verify email method is called");
		try {
			ResultSet resultSet = statement.executeQuery("select mail from users");
			while(resultSet.next()) {
				
				if(resultSet.getString(1).equals(mail)) {
					connection.close();
					return true;
				}
			}
		} catch (SQLException e) {
			System.out.println("the mail verification failed from verifyemail method "+e);
		}
		return false;
	}
	public boolean addUsers(String name,String pass,String mail) throws SQLException {
		try { 
			statement.execute("insert into users(name,mail,password,interface)values('"+name+"','"+mail+"','"+pass+"',0);");
			connection.close();
			return true;
		} catch (SQLException e) {
			
			System.out.println("the user registraion is failed from addUsers method");
		}
		connection.close();
		return false;
	}
	public boolean checkUser(String mail,String pass) throws SQLException {
		try {
			ResultSet resultSet = statement.executeQuery("select * from users");
			while(resultSet.next()) {
				if(resultSet.getString(3).equals(mail)) {
					if(resultSet.getString(4).equals(pass)) {
						if(resultSet.getString(5).equals("0")){
						connection.close();
						return true;
						}
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("the user  verification is  failed from Checkuser method"+e);
		} 
		connection.close();
		return false;
	}

	

}
