package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SeatsListData {
	Connection connection;
	Statement statement; 
	public SeatsListData() throws ClassNotFoundException, SQLException {
		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TicketBooking","postgres","1344");
		statement = connection.createStatement();
	}
	
	public ResultSet getFilterDateAndID(String date,String trainId ) throws SQLException {
		try {
			ResultSet result = statement.executeQuery("select * from seats where date='"+date+"' AND trainid="+trainId);
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public ResultSet getFilterByID(String id) throws SQLException {
		try {
			ResultSet result = statement.executeQuery("select * from seats where  trainid="+id);
			
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public ResultSet getFilterbyDate(String date)throws SQLException{
		try {
			ResultSet result = statement.executeQuery("select * from seats where  date='"+date+"'");
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public ResultSet getAllData() {
		try {
			ResultSet result = statement.executeQuery("select * from seats ");
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public ResultSet getDataByUser(String user){
		try {
			ResultSet result = statement.executeQuery("select * from seats where bookedby ='"+user+"'");
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public boolean registerSeats(String trainId,String seatName,String containerName,String passName,String amount,String date,String bookedBy,String from,String to){
		try{
			System.out.println(trainId+"=>"+seatName+"=>"+containerName+"=>"+passName+"=>"+amount+"=>"+date+"=>"+bookedBy+"=>"+from+to);
			System.out.println("the seats inset method is calling");
			statement.execute("insert into seats (trainid,seatname,container,passengername,fare,date,bookedby,initialplace,finalplace)values("+trainId+",'"+seatName+"','"+containerName+"','"+passName+"',"+amount+",'"+date+"','"+bookedBy+"','"+from+"','"+to+"')");
		}catch(SQLException e){
			System.out.println("the sql catched ");
		}
		return false;
	}
	public void closeDataBaseServer() throws SQLException {
		connection.close();
	}
	public boolean deleteSeats(String id,String date,String seat,String coaches){
		try{
			System.out.println(id+"=>"+date+"=>"+seat+"=>"+coaches);
		statement.execute("delete from seats where trainid="+id+" and date='"+date+"'and seatname='"+seat+"'and container='"+coaches+"'");
		}catch(SQLException e){
			System.out.println("the sql exception occured");
			return false;
		}
		return true;
	}
}
