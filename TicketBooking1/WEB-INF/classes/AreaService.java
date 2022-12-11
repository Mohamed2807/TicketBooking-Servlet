package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.DeserializationException;
import org.json.simple.JSONArray;
import org.json.simple.JsonArray;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;

import database.AreaData;
import database.TrainsData;

public class AreaService {
	public String getdata(String userName) {
		JsonObject obj =null;
		try {
			JsonArray stopsArray =new JsonArray();
			JsonObject stopsData= null;
			ResultSet resultSet = new AreaData().getAllArea();
			while(resultSet.next()) {
				stopsData= new JsonObject();
				stopsData.put("stop", resultSet.getString(2));
				stopsData.put("trainId",resultSet.getString(4));
				stopsArray.add(stopsData);
			}
			obj= new JsonObject();
			obj.put("stopsData", stopsArray);
			obj.put("user",userName);
			return obj.toJson();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void checkTime(String fromTime,String toTime) {
		
	}
	public boolean searchTrains(String from,String to) {
		System.out.println("from=>"+from+"to=>"+to);
		String fromTrainId="";
		String toTrainId="";
		int fromRow=0;
		int toRow=0;
		
	
		try {
			ResultSet resultSet = new TrainsData().checkFilter();
			while(resultSet.next()) {
				if(resultSet.getString(2).toLowerCase().equals(from.toLowerCase())) {
					System.out.println("from"+resultSet.getRow());
					fromTrainId=resultSet.getString(4);
					fromRow=resultSet.getRow();
				
						System.out.println("top =>"+fromRow+"=>"+toRow);
						if(fromTrainId.equals(toTrainId)) {
							if(fromRow<toRow) {
								System.out.println(fromTrainId +"=>"+toTrainId+"=>"+fromRow+"=>"+toRow);
								return true;
							}else {
								System.out.println("the condotion false"+fromTrainId +"=>"+toTrainId+"=>"+fromRow+"=>"+toRow);
								
							}
						}
					
					System.out.println(resultSet.getString(3)+"=>"+resultSet.getString(4));
				}
				if(resultSet.getString(2).toLowerCase().equals(to.toLowerCase())) {
					System.out.println("to"+resultSet.getRow());
					toTrainId=resultSet.getString(4);
					
					toRow=resultSet.getRow();
					
						System.out.println("top =>"+fromRow+"=>"+toRow);
						if(fromTrainId.equals(toTrainId)) {
							if(fromRow<toRow) {
								System.out.println(fromTrainId +"=>"+toTrainId+"=>"+fromRow+"=>"+toRow);
								return true;
								
							}else {
								System.out.println("the condition false"+fromTrainId +"=>"+toTrainId+"=>"+fromRow+"=>"+toRow);
								
							}
						}
					
					System.out.println(resultSet.getString(3)+"=>"+resultSet.getString(4));
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public String getPlacePointByID(String id) throws ClassNotFoundException, SQLException {
		JsonObject obj = null;
		JsonArray array = new JsonArray();
		ResultSet result = new AreaData().getPlacesByID(id);
		while(result.next()) {
			obj= new JsonObject();
			obj.put("stops",result.getString(2));
			obj.put("time", result.getString(3));
			array.add(obj);
		}
		return array.toJson();
		
	}
	public JsonArray getHomeSeatListData(List<String> trainList) throws ClassNotFoundException, SQLException, DeserializationException {
		JsonArray array = new JsonArray();
		PassengerDataService seatList = new PassengerDataService();
		
		
		JsonObject trainSeatsList= null;
		for(int i=0;i<trainList.size();i++) {
			trainSeatsList = new JsonObject();
			trainSeatsList.put(trainList.get(i), (JsonArray) Jsoner.deserialize(seatList.filterWithtrainID(trainList.get(i))));
			array.add(trainSeatsList);
		}
		seatList.closeSeatListDataBase();
		// System.out.println(array);
		return array;
	}
	
	public  JsonArray getHomeTrainData(List<String> trainList,String date) throws ClassNotFoundException, SQLException {
		TrainsData trainData= new TrainsData();
		JsonObject obj = new JsonObject();
		JsonArray array = new JsonArray();
		System.out.println("the home train data is called ");
		System.out.println("the trains modified");
		int j=1;
		for(int i=0;i<trainList.size();i++) {
			ResultSet resultSet = trainData.getFilteredTrainData(trainList.get(i), date);
			if(resultSet.next()){
			System.out.println("the homeTrain data is true");
			System.out.println(resultSet.getString(3));
			System.out.println(resultSet.getString(4));
			System.out.println(resultSet.getString(5));

			System.out.println(resultSet.getString(6));
			System.out.println(resultSet.getString(7));
			System.out.println(resultSet.getString(8));
			System.out.println(resultSet.getString(9));
			System.out.println(resultSet.getString(10));
			System.out.println(resultSet.getString(11));
			System.out.println(resultSet.getString(12));
			System.out.println(resultSet.getString(13));
			System.out.println(resultSet.getString(14));
			System.out.println(resultSet.getString(15));
			System.out.println(resultSet.getString(16));
			System.out.println(resultSet.getString(17));
			System.out.println(resultSet.getString(18));
			System.out.println(resultSet.getString(19));
			System.out.println(resultSet.getString(20));
			System.out.println(resultSet.getString(21));
			obj= new JsonObject();
			obj.put("trainID", resultSet.getInt(2));
			obj.put("trainName", resultSet.getString(8));
			obj.put("container", resultSet.getString(3));
			obj.put("bookedCoach", resultSet.getString(4));
			obj.put("unbookedCoach", resultSet.getString(5));
			obj.put("bookedSeats", resultSet.getString(6));
			obj.put("availableSeats", resultSet.getString(7));
			obj.put("initialPlace", resultSet.getString(10));
			obj.put("destination", resultSet.getString(11));
			obj.put("departureTime", resultSet.getString(12));
			obj.put("arraivalTime", resultSet.getString(13));
			obj.put("seater", resultSet.getString(15));
			obj.put("sleeper", resultSet.getString(16));
			obj.put("AcSeater", resultSet.getString(19));
			obj.put("seaterFare", resultSet.getString(17));
			obj.put("sleeperFare", resultSet.getString(18));
			obj.put("AcSleeper", resultSet.getString(20));
			obj.put("AcSeaterFare", resultSet.getString(21));
			obj.put("AcSleeperFare", resultSet.getString(22));
			array.add(obj);
			}else{
				 resultSet = trainData.getFilteredTrainData1(trainList.get(i));
				if(resultSet.next()){
					System.out.println("the homeTrain data is false and changed");
					
					obj= new JsonObject();
					obj.put("trainID", resultSet.getInt(1));
					obj.put("trainName", resultSet.getString(2));
					obj.put("container", resultSet.getString(3));
					obj.put("bookedCoach","0");
					obj.put("unbookedCoach", resultSet.getString(3));
					obj.put("bookedSeats", "0");
					obj.put("availableSeats", (resultSet.getInt(3)*15));
					obj.put("initialPlace", resultSet.getString(5));
					obj.put("destination", resultSet.getString(6));
					obj.put("departureTime", resultSet.getString(7));
					obj.put("arraivalTime", resultSet.getString(8));
					obj.put("seater", resultSet.getString(10));
					obj.put("sleeper", resultSet.getString(11));
					
					obj.put("seaterFare", resultSet.getString(12));
					obj.put("sleeperFare", resultSet.getString(13));
					obj.put("AcSeater", resultSet.getString(14));
					obj.put("AcSleeper", resultSet.getString(15));
					obj.put("AcSeaterFare", resultSet.getString(16));
					obj.put("AcSleeperFare", resultSet.getString(17));
					array.add(obj);
			}
			
		}
	}
	trainData.closeDataBaseserver();
		// System.out.println("the array is "+array);
		return array;
}
	public JsonObject getFilterTrains(String from,String to,String UserName,String date) throws ClassNotFoundException, SQLException, DeserializationException {
		List<String> listOfId= new ArrayList<>();
		System.out.println("from=>"+from+"to=>"+to);
		String fromTrainId="";
		String toTrainId="";
		int fromRow=0;
		int toRow=0;
	
		try {
			ResultSet resultSet = new TrainsData().checkFilter();
			while(resultSet.next()) {
				if(resultSet.getString(2).toLowerCase().equals(from.toLowerCase())) {
					System.out.println("from");
					fromTrainId=resultSet.getString(4);
					fromRow=resultSet.getInt(5);
					
						if(fromTrainId.equals(toTrainId)) {
							if(fromRow<toRow) {
								System.out.println(fromTrainId +"=>"+toTrainId+"=>"+fromRow+"=>"+toRow);
								listOfId.add(fromTrainId);
							}else {
								System.out.println("the condotion false"+fromTrainId +"=>"+toTrainId+"=>"+fromRow+"=>"+toRow);
							
							}
						}
					
					System.out.println(resultSet.getString(3)+"=>"+resultSet.getString(4));
				}
				if(resultSet.getString(2).toLowerCase().equals(to.toLowerCase())) {
					System.out.println("to");
					toTrainId=resultSet.getString(4);
				
					toRow=resultSet.getInt(5);
					
						if(fromTrainId.equals(toTrainId)) {
							if(fromRow<toRow) {
								listOfId.add(fromTrainId);
							}else {
								System.out.println("the condition false"+fromTrainId +"=>"+toTrainId+"=>"+fromRow+"=>"+toRow);
								
							}
						}
					
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JsonObject obj = new JsonObject();
		try {
			obj.put("trainData", getHomeTrainData(listOfId,date));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("exception from train Data");
		}
		try {
			obj.put("seatListData", getHomeSeatListData(listOfId));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("exception from seat");
			e.printStackTrace();
		}
		System.out.println("data sent");
		obj.put("boarding", getBoardingData(listOfId));
		obj.put("user",UserName);
		return obj ;
	}
	public List getFiltertrains1(String from,String to) throws ClassNotFoundException, SQLException {
		List<String> listOfId= new ArrayList<>();
		System.out.println("from=>"+from+"to=>"+to);
		String fromTrainId="";
		String toTrainId="";
		int fromRow=0;
		int toRow=0;
	
		try {
			ResultSet resultSet = new TrainsData().checkFilter();
			while(resultSet.next()) {
				if(resultSet.getString(2).toLowerCase().equals(from.toLowerCase())) {
					System.out.println("from");
					fromTrainId=resultSet.getString(4);
					fromRow=resultSet.getInt(5);
					
						if(fromTrainId.equals(toTrainId)) {
							if(fromRow<toRow) {
								System.out.println(fromTrainId +"=>"+toTrainId+"=>"+fromRow+"=>"+toRow);
								listOfId.add(fromTrainId);
							}else {
								System.out.println("the condotion false"+fromTrainId +"=>"+toTrainId+"=>"+fromRow+"=>"+toRow);
							
							}
						}
					
					System.out.println(resultSet.getString(3)+"=>"+resultSet.getString(4));
				}
				if(resultSet.getString(2).toLowerCase().equals(to.toLowerCase())) {
					System.out.println("to");
					toTrainId=resultSet.getString(4);
				
					toRow=resultSet.getInt(5);
					
						if(fromTrainId.equals(toTrainId)) {
							if(fromRow<toRow) {
								listOfId.add(fromTrainId);
							}else {
								System.out.println("the condition false"+fromTrainId +"=>"+toTrainId+"=>"+fromRow+"=>"+toRow);
								
							}
						}
					
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listOfId;
	}
	public JsonArray getBoardingData(List<String> trainIDs) throws ClassNotFoundException, SQLException {
		JsonArray array1 = new JsonArray();
		JsonArray array2 = new JsonArray();
		JsonObject obj = null;
		JsonObject obj1=new JsonObject();
		ResultSet result;
		AreaData areaData = new AreaData();
		for(int i=0;i<trainIDs.size();i++) {
			result = areaData.getPlacesByID(trainIDs.get(i));
		
			array1 = new JsonArray();
			while(result.next()) {
				obj= new JsonObject();
				obj.put("stop",result.getString(2));
				obj.put("time", result.getString(3));
				array1.add(obj);
			}
			obj1.put(trainIDs.get(i), array1);
			array2.add(obj1);
		}
		areaData.closeConnection();
		return array2;
	}
}
