package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.simple.DeserializationException;
import org.json.simple.JsonArray;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;

import database.TrainsData;

public class TrainUpdateService  {
	public String initialSeparator(String data) {
		String temp="";
		boolean check=false;
		for(int i=0;i<data.length();i++) {
			if(data.charAt(i)=='/') {
				check=true;
				i++;
			}
			if(check) {
				temp+=data.charAt(i);
			}
			
		}
		
		return temp.trim();
		
	}
	public String finalSeparator(String data) {
		String temp="";
		for(int i=0;i<data.length();i++) {
			if(data.charAt(i)=='/') {
				System.out.println("its breaked");
				System.out.println(temp);
				break;
			}
			temp+=data.charAt(i);
		}
		
		return temp.trim();
		
	}

	public void getAllDataInSameTrain(String trainData) throws DeserializationException, ClassNotFoundException, SQLException {
		JsonObject obj = (JsonObject) Jsoner.deserialize(trainData);
		
		int trainId=Integer.parseInt(obj.get("trainID").toString());
		String trainName=obj.get("trainName").toString();
		String initialPlace=obj.get("initialPlace").toString();
		String destination=obj.get("destination").toString();
		String departureTime=obj.get("departureTime").toString();
		String arraivalTime=obj.get("arraivalTime").toString();
		int seater=Integer.parseInt(initialSeparator( obj.get("AC/seater").toString()));
		int acSeater=Integer.parseInt(finalSeparator(obj.get("AC/seater").toString()));
		int sleeper=Integer.parseInt(initialSeparator(obj.get("AC/sleeper").toString()));
		int acSleeper=Integer.parseInt(finalSeparator(obj.get("AC/sleeper").toString()));
		int seaterfare=Integer.parseInt(initialSeparator( obj.get("AC/Seater fare").toString()));
		int acseaterFare=Integer.parseInt(finalSeparator(obj.get("AC/Seater fare").toString()));
		int sleeperFare= Integer.parseInt(initialSeparator(obj.get("AC/sleeperFare").toString()));
		int acSleeperFare=Integer.parseInt(finalSeparator(obj.get("AC/sleeperFare").toString()));
		// int bookedSeats=Integer.parseInt(obj.get("bookedSeats").toString());
		// System.out.println("1=====================");
		// int availableSeats=Integer.parseInt(obj.get("availableSeats").toString());
		int bookedCoach=Integer.parseInt(obj.get("bookedCoach").toString());
		int container=seater+acSeater+acSleeper+sleeper;
		System.out.println(trainData);
		
		System.out.println("departure Time"+departureTime);
		System.out.println("arraivalTime"+arraivalTime);
		System.out.println("ac seater"+acSeater);
		System.out.println("seater="+seater);
		System.out.println("acSleeper="+acSleeper);
		System.out.println("sleeper="+sleeper);
		System.out.println("acSeater fare"+acseaterFare);
		System.out.println("acSleeperfare"+acSleeperFare);
		System.out.println("sleperFare"+sleeperFare);
		
		System.out.println("bookedCoach"+bookedCoach);
		System.out.println("container "+container);
		System.out.println(obj.get("stops"));
		
		
		JsonArray array =(JsonArray) obj.get("stops");
		Set set;
		List<String> stopList=null;
		TrainsData trainsData = new TrainsData();
		trainsData.updateTrains(trainId, trainName, container, 0, container, 0, (container*15));
		trainsData.updateTrainContainer(trainId, seater, sleeper, seaterfare, sleeperFare, acSeater, acSleeper, acseaterFare, acSleeperFare);
		if(array.size()>0) {
			obj = new JsonObject();
			obj=(JsonObject) array.get(0);
			set=obj.keySet();
			stopList=new ArrayList(set);
			 initialPlace=stopList.get(0);
			 departureTime= obj.get(stopList.get(0)).toString();
			obj = new JsonObject();
			obj=(JsonObject) array.get((array.size()-1));
			set=obj.keySet();
			stopList=new ArrayList(set);
			 destination=stopList.get(0);
			 arraivalTime=obj.get(stopList.get(0)).toString();
			 trainsData.deleteStops(trainId);
			}
		
		for(int i=0;i<array.size();i++) {
			obj = new JsonObject();
			obj=(JsonObject) array.get(i);
			set=obj.keySet();
			stopList=new ArrayList(set);
			System.out.println(stopList);
			System.out.println(obj.get(stopList.get(0)).toString());
			System.out.println(obj.get("order").toString());
			trainsData.insertTrainStops(stopList.get(0), obj.get(stopList.get(0)).toString(),obj.get("order").toString(),trainId);
		}
	
		
		trainsData.updateTrainSchedule(trainId, initialPlace, destination,String.valueOf(departureTime) , String.valueOf(arraivalTime));
		trainsData.closeDataBaseserver();
	}
	public void getAllDataInDiffTrain(String trainData,String OldId) {
		System.out.println(trainData+OldId);
	}
}
