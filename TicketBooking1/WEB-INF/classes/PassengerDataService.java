package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.json.simple.JsonArray;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;
import org.json.simple.DeserializationException;
import java.util.ArrayList;
import java.util.List;
import database.SeatsListData;
import database.UserData;
import database.TrainsData;
import java.util.Set;
import java.sql.ResultSet;

public class PassengerDataService {
	SeatsListData seatListDataBase;
	public PassengerDataService()throws ClassNotFoundException, SQLException{
		 seatListDataBase=	new SeatsListData();
	}
	public void closeSeatListDataBase() throws ClassNotFoundException, SQLException{
		seatListDataBase.closeDataBaseServer();
	}
	public String getDataWithDateAndTrain(String date,String trainId) {
		JsonArray array=  new JsonArray();
		try {
			ResultSet result =seatListDataBase.getFilterDateAndID(date, trainId);
			JsonObject obj = null;
			while(result.next()) {
				obj=new JsonObject();
				obj.put("trainId", result.getString(2));
				obj.put("seatName",result.getString(3));
				obj.put("coach", result.getString(4));
				obj.put("passengerName", result.getString(5));
				obj.put("pnrID", result.getString(6));
				obj.put("fare", result.getString(7));
				obj.put("date",result.getString(8));
				obj.put("bookedBy", result.getString(9));
				obj.put("from", result.getString(10));
				obj.put("to", result.getString(11));
				
				array.add(obj);
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array.toJson();
		
	}
	public String filterWithtrainID(String id) throws ClassNotFoundException, SQLException {
		ResultSet result = seatListDataBase.getFilterByID(id);
		JsonArray array = new JsonArray();
		JsonObject obj = null;
		try {
		while(result.next()) {
			obj=new JsonObject();
			obj.put("trainId", result.getString(2));
			obj.put("seatName",result.getString(3));
			obj.put("coach", result.getString(4));
			obj.put("passengerName", result.getString(5));
			obj.put("pnrID", result.getString(6));
			obj.put("fare", result.getString(7));
			obj.put("date",result.getString(8));
			obj.put("bookedBy", result.getString(9));
			obj.put("from", result.getString(10));
			obj.put("to", result.getString(11));
			array.add(obj);
		}
	
		return array.toJson();
		}catch(NullPointerException e) {
			System.out.println("the null pointer catched");
			return new JsonArray().toJson();
		}
		
	}
	
	public String filterWithDate(String date) throws ClassNotFoundException, SQLException {
		ResultSet result = seatListDataBase.getFilterbyDate(date);
		JsonArray array = new JsonArray();
		JsonObject obj = null;
		try {
		while(result.next()) {
			obj=new JsonObject();
			obj.put("trainId", result.getString(2));
			obj.put("seatName",result.getString(3));
			obj.put("coach", result.getString(4));
			obj.put("passengerName", result.getString(5));
			obj.put("pnrID", result.getString(6));
			obj.put("fare", result.getString(7));
			obj.put("date",result.getString(8));
			obj.put("bookedBy", result.getString(9));
			obj.put("from", result.getString(10));
			obj.put("to", result.getString(11));
			array.add(obj);
		}
		
		return array.toJson();
		}catch(NullPointerException e) {
			return new JsonArray().toJson();
		}
		
	}
	public String getAllPassengerData() throws ClassNotFoundException, SQLException {
		ResultSet result = seatListDataBase.getAllData();
		JsonArray array = new JsonArray();
		JsonObject obj = null;
		
		try {
		while(result.next()) {
			obj=new JsonObject();
			obj.put("trainId", result.getString(2));
			obj.put("seatName",result.getString(3));
			obj.put("coach", result.getString(4));
			obj.put("passengerName", result.getString(5));
			obj.put("pnrID", result.getString(6));
			obj.put("fare", result.getString(7));
			obj.put("date",result.getString(8));
			obj.put("bookedBy", result.getString(9));
			obj.put("from", result.getString(10));
			obj.put("to", result.getString(11));
			array.add(obj);
		}
		
		return array.toJson();
	}catch(NullPointerException e) {
		return new JsonArray().toJson();
	}
	}
	public String getPassengerByUser(String user)throws ClassNotFoundException{
		
		
		try {
			ResultSet result = seatListDataBase.getDataByUser(user);
			JsonArray array = new JsonArray();
			JsonObject obj = null;
		while(result.next()) {
			obj=new JsonObject();
			obj.put("trainId", result.getString(2));
			obj.put("seatName",result.getString(3));
			obj.put("coach", result.getString(4));
			obj.put("passengerName", result.getString(5));
			obj.put("pnrID", result.getString(6));
			obj.put("fare", result.getString(7));
			obj.put("date",result.getString(8));
			obj.put("bookedBy", result.getString(9));
			obj.put("from", result.getString(10));
			obj.put("to", result.getString(11));
			array.add(obj);
		}
		
		return array.toJson();
	}catch( SQLException |NullPointerException e ) {
		return new JsonArray().toJson();
	}

	}
	public boolean registerSeats(String seatsList,String bookedBy) throws  DeserializationException {
		//System.out.println("the list is "+seatsList);
		JsonObject data = (JsonObject)Jsoner.deserialize(seatsList);
		JsonArray seatListArray= (JsonArray)data.get("booked");
		JsonObject obj = new JsonObject();


		LocalDate date1 =java.time.LocalDate.now();
		LocalDate date2 =LocalDate.parse(data.get("date").toString());
		
		int todayYear=date1.getYear();
		int todayMonth=date1.getMonthValue();
	    int today=date1.getDayOfMonth();
	    int inputYear=date2.getYear();
		int inputMonth=date2.getMonthValue();
	    int inputDay=date2.getDayOfMonth();
	    
		if(todayYear<inputYear){

		}else if(todayYear==inputYear){
			if(todayMonth<inputMonth){

			}else if(todayMonth==inputMonth){
				if(today>inputDay){
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
	    // if(year>=year1) {
	    // 	System.out.println("year1 is bigger");
	    // 	  if(month>month1) {
	    // 		  System.out.println("year1 is bigger");
	    	  	
		// 	  }else if(month==month1){
		// 		if(day<day1){
		// 			System.out.println(day+"=>"+day1);
	    // 	  		return false;
		// 		}
		// 	  }else{
		// 		return false;
		// 	  }
	    // }else {
	    // 	return false;
	    // }

		try{
		
		for(int i=0;i<seatListArray.size();i++){
			obj = new JsonObject();
			obj = (JsonObject)seatListArray.get(i);
			System.out.println("the date is "+ data.get("date").toString());
			seatListDataBase.registerSeats(data.get("trainId").toString(), obj.get("seat").toString(), obj.get("coach").toString(), obj.get("name").toString(), obj.get("fare").toString(), data.get("date").toString(), bookedBy, data.get("from").toString(), data.get("to").toString());
		}
		System.out.println("before-----------------------------------------------------------");
		System.out.println(Integer.parseInt(data.get("bookedCoach").toString()));
		System.out.println(Integer.parseInt(data.get("unBookedCoach").toString()));
		System.out.println(Integer.parseInt(data.get("trainId").toString()));
		System.out.println( data.get("name").toString());
		System.out.println(Integer.parseInt(data.get("bookedCoach").toString()));
		System.out.println( Integer.parseInt(data.get("unBookedCoach").toString()));
		System.out.println(Integer.parseInt(data.get("bookedSeat").toString()));
		System.out.println(Integer.parseInt(data.get("unBookedSeats").toString()));
		
		int container=Integer.parseInt(data.get("bookedCoach").toString())+Integer.parseInt(data.get("unBookedCoach").toString());
		System.out.println(Integer.parseInt(data.get("trainId").toString())+ data.get("name").toString()+ container+ Integer.parseInt(data.get("bookedCoach").toString())+ Integer.parseInt(data.get("unBookedCoach").toString())+ Integer.parseInt(data.get("bookedSeat").toString())+ Integer.parseInt(data.get("unBookedSeats").toString()));
		TrainsData trainData=new TrainsData();
		if(trainData.checkDates(data.get("trainId").toString(),data.get("date").toString())){
			System.out.println("true from passenger data service");
			trainData.updateTrainByDate(data.get("date").toString(),data.get("name").toString(),Integer.parseInt(data.get("trainId").toString()), container, Integer.parseInt(data.get("bookedCoach").toString()), Integer.parseInt(data.get("unBookedCoach").toString()),  Integer.parseInt(data.get("bookedSeat").toString()), Integer.parseInt(data.get("unBookedSeats").toString()));
		}else{
			System.out.println("false from passenger data service");
			trainData.insertTrainBydate(data.get("date").toString(),data.get("name").toString(),Integer.parseInt(data.get("trainId").toString()), container, Integer.parseInt(data.get("bookedCoach").toString()), Integer.parseInt(data.get("unBookedCoach").toString()),  Integer.parseInt(data.get("bookedSeat").toString()), Integer.parseInt(data.get("unBookedSeats").toString()));
		}
		
		return true;
	}catch(SQLException | ClassNotFoundException e){
		System.out.println("from registerSeats class");
		
		return false;
	}
	
	}
	public String checkSeats(String SeatsData){
		System.out.println("***********************************************************************************************************************************");
		System.out.println("the recived data is "+SeatsData);
		JsonArray coachList = new JsonArray();
		
		JsonObject seatsList;
		JsonObject originData=null;
		JsonObject getSeatList=null;
		try{
		 originData=(JsonObject)Jsoner.deserialize(SeatsData);
		 getSeatList = (JsonObject)originData.get("array");

		}catch(DeserializationException  e){
			
		}
		try{
		System.out.println("the seats checking ------------------------------------------------------------------------------------------------------------------------------------------------");
		Set set=getSeatList.keySet();
		
		 ResultSet result =seatListDataBase.getFilterDateAndID(originData.get("date").toString(),originData.get("id").toString());
		List<String> list = new ArrayList<>(set);
			
		while(result.next()){
			if(result.getString(2).equals(originData.get("id").toString())){
				for(int i=0;i<list.size();i++){
					coachList=(JsonArray) getSeatList.get(list.get(i));
					if(result.getString(4).equals(list.get(i))){
					for(int j=0;j<coachList.size();j++){
						System.out.println(list.get(i)+"<><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
							seatsList=(JsonObject)coachList.get(j);
							if(seatsList.get("coach").equals(result.getString(3))){
							System.out.println("=>"+seatsList.get("coach")+"=>"+list.get(i));
							System.out.println(result.getString(2)+"=>"+result.getString(3)+"=>"+result.getString(4));
								return "true";
							}
						}
					}
				}
			}
			
		 }
		 seatListDataBase.closeDataBaseServer();
			}catch(SQLException e ){
				System.out.println("the sql exception");
			}
			System.out.println("after the condition");
			
		return "false";
	}
		
}
