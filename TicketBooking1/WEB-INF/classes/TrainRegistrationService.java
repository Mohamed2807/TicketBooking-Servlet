package service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.DeserializationException;
import org.json.simple.JSONObject;
import org.json.simple.JsonArray;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;
import database.TrainsData;

public class TrainRegistrationService {
	JsonObject jsonObject=null;
	JsonArray jsonArray=null;
	public void registerTrain(String json) throws ClassNotFoundException, SQLException  {
		try {
			jsonObject=(JsonObject) Jsoner.deserialize(json);
		}catch( DeserializationException e) {
			System.out.println("json error");
		}
		TrainsData traindata = new TrainsData();
//		seater coach
		JsonObject seaterData=(JsonObject) jsonObject.get("seater");
		JsonObject seaterAcCoach=(JsonObject) seaterData.get("ac");
		JsonObject seaterNormalCoach=(JsonObject) seaterData.get("normal");
//		sleeper coach
		JsonObject sleeperData=(JsonObject) jsonObject.get("sleeper");
		JsonObject sleeperAcCoach=(JsonObject) sleeperData.get("ac");
		JsonObject sleeperNormalCoach=(JsonObject) sleeperData.get("normal");
		
		
		int trainId=Integer.parseInt(jsonObject.get("trainId").toString());
		String trainName=jsonObject.get("trainName").toString();
		int coach=Integer.parseInt(seaterAcCoach.get("compartment").toString())+Integer.parseInt(seaterNormalCoach.get("compartment").toString())+Integer.parseInt(sleeperNormalCoach.get("compartment").toString())+Integer.parseInt(sleeperAcCoach.get("compartment").toString());
		int seaterAcFare=Integer.parseInt(seaterAcCoach.get("fare").toString());
		 int seaterNormalFare=Integer.parseInt(seaterNormalCoach.get("fare").toString());
		int sleeperAcFare=Integer.parseInt(sleeperAcCoach.get("fare").toString());
		int sleeperNormalFare=Integer.parseInt(sleeperNormalCoach.get("fare").toString());
		int seaterAc=Integer.parseInt(seaterAcCoach.get("compartment").toString());
		int seaterNormal=Integer.parseInt(seaterNormalCoach.get("compartment").toString());
		int sleeperNormal=Integer.parseInt(sleeperNormalCoach.get("compartment").toString());
		int sleeperAc=Integer.parseInt(sleeperAcCoach.get("compartment").toString());
		JsonArray stops= (JsonArray) jsonObject.get("stops");
		JsonObject startingPoint=(JsonObject) stops.get(0);
		JsonObject endingPoint=(JsonObject) stops.get(stops.size()-1);
		try {
			System.out.println(stops);
		} catch (NullPointerException  e) {
			// TODO: handle exception
			System.out.println("nullPointer");
		}
		
		System.out.println(startingPoint.get("name").toString());
		System.out.println(endingPoint.get("name").toString());
		System.out.println(startingPoint.get("time").toString());
		System.out.println( endingPoint.get("time").toString());
		
		JsonObject stopPlaces;
		System.out.println("printing stop places");
		
		if(traindata.insertTrain(trainId, trainName,coach , 0, coach, 0, (coach*15))) {
			traindata.insertTrainContainer(trainId,seaterNormal , sleeperNormal, seaterNormalFare, sleeperNormalFare, 	seaterAc, sleeperAc, seaterAcFare, sleeperAcFare);
			traindata.insertTrainschedule(trainId, startingPoint.get("name").toString(), endingPoint.get("name").toString(),startingPoint.get("time").toString(), endingPoint.get("time").toString());
			for(int i=0;i<stops.size();i++) {
				stopPlaces=(JsonObject) stops.get(i);
				System.out.println("i=>"+i+stopPlaces.get("name").toString()+ stopPlaces.get("time").toString());
				traindata.insertTrainStops(stopPlaces.get("name").toString(), stopPlaces.get("time").toString(),stopPlaces.get("order").toString(), trainId);
			}
		}
		traindata.closeDataBaseserver();
	}
	public void updateSameTrain(String trainData) throws ClassNotFoundException, SQLException {
//		TrainsData trainDatabase = new TrainsData();
//		trainDatabase.updateTrains(id, name, container, bookedContainer, unbookedConatainer, bookedseats, availableseats);
//		trainDatabase.updateTrainContainer(id, fare1, fare2, fare3, sleepFare, sleeper, first, second, third);
//		trainDatabase.updateTrainSchedule(id, initalPlace, destination, startsTime, endstime);
	}
	public void updateTrainWithID(String trainData,String oldID) {
		
	}
	public void bookSeats() {
		
	}
	public void cancelSeats() {
		
	}
	public String getDataForClient(String from ,String to,String date) throws ClassNotFoundException, SQLException {
		JsonObject obj = new JsonObject();
		JsonArray array = new JsonArray();
		TrainsData trainData = new TrainsData();
	
		try {
			ResultSet resultSet =trainData.filterWithNavigation(from, to);
			//ResultSet resultSet = new TrainsData().trainAllData();
			while(resultSet.next()) {
				obj= new JsonObject();
				obj.put("trainID", resultSet.getInt(1));
				obj.put("trainName", resultSet.getString(2));
				obj.put("container", resultSet.getInt(3));
				obj.put("bookedCoach", resultSet.getInt(4));
				obj.put("unbookedCoach", resultSet.getInt(5));
				obj.put("bookedSeats", resultSet.getInt(6));
				obj.put("availableSeats", resultSet.getInt(7));
				obj.put("initialPlace", resultSet.getString(9));
				obj.put("destination", resultSet.getString(10));
				obj.put("departureTime", resultSet.getString(11));
				obj.put("arraivalTime", resultSet.getString(12));
				obj.put("seater", resultSet.getInt(14));
				obj.put("sleeper", resultSet.getInt(15));
				obj.put("seaterFare", resultSet.getInt(16));
				obj.put("sleeperFare", resultSet.getInt(17));
				obj.put("acSeater", resultSet.getInt(18));
				obj.put("acSleeper", resultSet.getInt(19));
				obj.put("acSeaterFare", resultSet.getInt(20));
				obj.put("acSleeperFare", resultSet.getInt(21));
				array.add(obj);
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NullPointerException e) {
			obj = new JsonObject();
			obj.put("trainData","null");
			System.out.println(obj.toJson());
			return obj.toJson();
		}
		obj = new JsonObject();
		obj.put("trainData", array);
		obj.put("from", from);
		obj.put("to", to);
		obj.put("onwardDate", date);
		System.out.println(obj.toJson());
		trainData.closeDataBaseserver();
		return obj.toJson();
	}
	public String getData() throws ClassNotFoundException, SQLException {
		TrainsData trainData = new TrainsData();
		JsonObject obj ;
		JsonArray array = new JsonArray();
		ResultSet resultSet;
		try {
			resultSet = trainData.trainAllData();
			while(resultSet.next()) {
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		obj = new JsonObject();
		obj.put("trainData", array);
		trainData.closeDataBaseserver();
		return obj.toJson();
	}
	public String getDataWithTrainDate(String trainID) throws ClassNotFoundException, SQLException{
		TrainsData trainData = new TrainsData();
		JsonObject obj ;
		JsonArray array = new JsonArray();
		ResultSet resultSet;
		try {
			resultSet = trainData.trainAllDatawithDate(trainID);
			while(resultSet.next()) {
				obj= new JsonObject();
			obj.put("date",resultSet.getString(1));
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
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		obj = new JsonObject();
		trainData.closeDataBaseserver();
		obj.put("trainDataWithDate", array);
		
		return obj.toJson();
	}

}
