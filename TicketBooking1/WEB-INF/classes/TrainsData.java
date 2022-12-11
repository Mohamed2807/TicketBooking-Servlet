package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TrainsData {
	Connection connection;
	Statement statement; 
	public TrainsData() throws ClassNotFoundException, SQLException {
		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TicketBooking","postgres","1344");
		statement = connection.createStatement();
	}
	public boolean insertTrain(int id,String name,int container,int bookedContainer,int unbookedConatainer,int bookedseats,int availableseats) {
		try {
			System.out.println("the train is registered");
			statement.execute("insert into trains(id,name,container) values("+id+",'"+name+"',"+container+");");
		}catch(SQLException e) {
			System.out.println("the train register failed");
			return false;
		}
		return true;
	}
	public boolean updateTrainsbyClient(int id,String name,int container,int bookedContainer,int unbookedConatainer,int bookedseats,int availableseats) {
		try {
			 statement.execute("update  trains set container="+container+",bookedcontainer="+bookedContainer+",unbookedcontainer="+unbookedConatainer+",bookedseats="+bookedseats+",available_seats="+availableseats+"where id ="+id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;	
	}
	public boolean updateTrainByDate(String date,String name,int id,int container,int bookedContainer,int unbookedConatainer,int bookedseats,int availableseats) {
		try {
			statement.execute("update  trainWithAllDates set container="+container+",bookedcontainer="+bookedContainer+",unbookedcontainer="+unbookedConatainer+",bookedseats="+bookedseats+",available_seats="+availableseats+" where trainid ="+id+"and date='"+date+"'");
	   } catch (SQLException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		   return false;
	   }
	   return true;
	}
	public boolean insertTrainBydate(String date,String name,int id,int container,int bookedContainer,int unbookedConatainer,int bookedseats,int availableseats){
		try {
			statement.execute("insert into trainWithAllDates values('"+date+"',"+id+","+container+","+bookedContainer+","+unbookedConatainer+","+bookedseats+","+availableseats+");");
		}catch(SQLException e) {
			System.out.println("the train register failed");
			return false;
		}
		return true;
	}
	public boolean checkDates(String trainId,String date){
		try {

			System.out.println("the train id is"+trainId+"-------------------------------------------------------------------chceking train date table------------------------------------------------");
			System.out.println("the extrenal  id is "+date+"======================>"+trainId);
			ResultSet result = statement.executeQuery("select * from trainWithAllDates  where trainid="+trainId+"and date='"+date+"'");
			while(result.next()){
				System.out.println("the train id is "+result.getString(1)+"==============A========>"+result.getString(2));
			
				if(result.getString(1).equals(date)&&result.getString(2).equals(trainId)){
					System.out.println("its founded true");
					return true;
				}
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean updateTrains(int id,String name,int container,int bookedContainer,int unbookedConatainer,int bookedseats,int availableseats) {
		try {
			 statement.execute("update  trains set name='"+name+"',container="+container+"where id ="+id);
			 statement.execute("delete from seats where trainid="+id);
			 statement.execute("delete from trainWithAllDates where trainid="+id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;	
	}
	public boolean insertTrainschedule(int id,String initalPlace,String destination,String startsTime,String endstime) {
		try {
			statement.execute("insert into trainschedule values("+id+",'"+initalPlace+"','"+destination+"','"+startsTime+"','"+endstime+"');");
		}catch(SQLException e) {
			System.out.println("the trainschedule register failed");
			return false;
		}
		return true;
	}
	public boolean updateTrainSchedule(int id,String initalPlace,String destination,String startsTime,String endstime) {
		try {
			 statement.execute("update  trainschedule set initial='"+initalPlace+"',destination='"+destination+"',startstime='"+startsTime+"',endstime='"+endstime+"' where id ="+id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean insertTrainContainer(int id,int seater,int sleeper,int seaterfare,int sleepfare,int acseater,int acsleeper,int acseaterfare,int acsleeperfare) {
		try {
			statement.execute("insert into traincontainer values("+id+","+seater+","+sleeper+","+seaterfare+","+sleepfare+","+acseater+","+acsleeper+","+acseaterfare+","+acsleeperfare+");");
		}catch(SQLException e) {
			System.out.println("the traincontainer register failed");
			return false;
		}
		return true;
	}
	public boolean updateTrainContainer(int id,int seater,int sleeper,int seaterfare,int sleepFare,int acseater,int acsleeper,int acseaterfare,int acsleeperfare) {
		 try {
			statement.execute("update  traincontainer set seater="+seater+",sleeper="+sleeper+",seaterfare="+seaterfare+",sleeperfare="+sleepFare+",acseater="+acseater+",acsleeper="+acsleeper+",acseaterfare="+acseaterfare+",acsleeperfare="+acsleeperfare+"where id ="+id);
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			return false;
		}
		 return true;
	}
	public boolean insertTrainStops(String stops,String time,String order,int trainId) {
		System.out.println(stops+time+trainId);
		try {
			statement.execute("insert into trainstops(stops,time,trainid,orderby) values('"+stops+"','"+time+"',"+trainId+","+order+");");
		}catch(SQLException e) {
			System.out.println("the trainStops register failed");
			return false;
		}
		return true;
	}
	

	public boolean deleteStops(int id) {
		try {
			statement.execute("delete from trainstops where trainid ="+id);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
			
		}
	}
	public boolean deleteTrain(int id) throws SQLException {
		try {
			statement.execute("delete from seats where trainid="+id);
			statement.execute("delete from trainstops where trainid ="+id);
			statement.execute("delete from trainschedule where id ="+id);
			statement.execute("delete from traincontainer where id ="+id);
			statement.execute("delete from trainwithalldates where trainid="+id);
			statement.execute("delete from trains where id = "+id);
		} catch (SQLException e) {
			e.printStackTrace();
			connection.close();
			return false;	
		}
		connection.close();
		return true;
	}
	
	public ResultSet trainAllDatawithDate(String trainId) throws SQLException {
		try {
			System.out.println("the train id is"+trainId);
			ResultSet result = statement.executeQuery("select trainwithalldates.*,trains.name,trainschedule.*,traincontainer.* from trainwithalldates inner join trains on trainwithalldates.trainid=trains.id inner join trainschedule on trainwithalldates.trainid=trainschedule.id inner join traincontainer on trainwithalldates.trainid=traincontainer.id where trainWithAllDates.trainid="+trainId+";");
		
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}
	}
	public ResultSet getFilteredTrainData(String trainId,String date) throws SQLException {
		try {
			System.out.println("the train id is"+trainId);
			ResultSet result = statement.executeQuery(" select trainwithalldates.*,trains.name,trainschedule.*,traincontainer.* from trainwithalldates inner join trains on trainwithalldates.trainid=trains.id inner join trainschedule on trainwithalldates.trainid=trainschedule.id inner join traincontainer on trainwithalldates.trainid=traincontainer.id where trainWithAllDates.trainid="+trainId+"and date='"+date+"';");
		
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}
	}
	public ResultSet getFilteredTrainData1(String trainId) throws SQLException {
		try {
			System.out.println("the train id is"+trainId);
			ResultSet result = statement.executeQuery("select trains.id,trains.name,trains.container,trainschedule.*,traincontainer.* from trains   inner join trainschedule on trains.id=trainschedule.id inner join traincontainer on trains.id=traincontainer.id where trains.id="+trainId);
		
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}
	}
	public ResultSet trainAllData() {
		try {
			ResultSet result = statement.executeQuery("select trains.id,trains.name,trains.container,trainschedule.*,traincontainer.* from trains   inner join trainschedule on trains.id=trainschedule.id inner join traincontainer on trains.id=traincontainer.id");
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public ResultSet filterWithNavigation(String from,String to) {
		try {
			ResultSet result = statement.executeQuery("  select trains.id,trains.name,trains.container,trainschedule.*,traincontainer.* from trains   inner join trainschedule on trains.id=trainschedule.id inner join traincontainer on trains.id=traincontainer.id where initial='"+from+"'AND destination='"+to+"'");
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public ResultSet checkFilter() throws SQLException {
			ResultSet result = statement.executeQuery("select * from trainstops");
			return result;
	}
	public void closeDataBaseserver() throws SQLException {
		connection.close();
	}
}
