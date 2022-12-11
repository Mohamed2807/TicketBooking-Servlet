var currentBoardingData='';
var currentDroppingData='';
var defaultBoarding='';
var defaultDropping='';
var currentDate=[];
let trainList = {}
// function defaultCall(){
// var req = new XMLHttpRequest();
// req.onreadystatechange = function () {
//   var day = "";
//   if (this.readyState == 4 && this.status == 200) {
   
//     trainList = JSON.parse(this.response);
//     console.log("the json is ");
//     console.log(trainList);
//     console.log(trainList.boarding);
//     console.log("the user is "+trainList['user']);
//     if(trainList['user']=='null'){
//       $("#register").show();
//       $("#logout").hide();
//       $("#wishList").hide();
//     }else{
//       $("#register").hide();
//       $("#logout").show();
//       $("#wishList").show();
//     }
   
//     if (trainList.trainData != "null") {
     
//     } else {
//       document.getElementById("goback").click();
//     }
//     currentBoardingData=trainList.from;
//     currentDroppingData=trainList.to;
//     defaultBoarding=trainList.from;
//     defaultDropping=trainList.to;
//     $("#from").text(trainList.from);
//     $("#to").text(trainList.to);
//     $("#date").text(trainList.onward);
//     currentDate=trainList.onward;
//     $("#total-Trains").text(trainList.trainData.length + " Trains found");
//     generateTrain(trainList.trainData);
//     if(trainList['user']!='null'){
//       getRecords();
//     }
   
  
    
//   }
// };
// req.open("POST", "http://localhost:8080/TicketBooking1/filterTrain", true);
// req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
// req.send();
//}
// defaultCall();


function inputDate(date){
  return new Promise((resolve)=>{
    console.log("the input method is called")
  var req = new XMLHttpRequest();
  req.onreadystatechange = function () {
    var day = "";
    if (this.readyState == 4 && this.status == 200) {
     
      trainList = JSON.parse(this.response);
      console.log("the json is ");
      console.log(trainList);
      console.log(trainList.boarding);
      console.log("the user is "+trainList['user']);
      if(trainList['user']=='null'){
        $("#register").show();
        $("#logout").hide();
      }else{
        $("#register").hide();
        $("#logout").show();
      }
      if (trainList.trainData != "null") {
       
      } else {
        document.getElementById("goback").click();
      }
      currentBoardingData=trainList.from;
      currentDroppingData=trainList.to;
      defaultBoarding=trainList.from;
      defaultDropping=trainList.to;
      $("#from").text(trainList.from);
      $("#to").text(trainList.to);
      $("#date").text(trainList.onward);
      currentDate=trainList.onward;
      $("#total-Trains").text(trainList.trainData.length + " Trains found");
      generateTrain(trainList.trainData);
      resolve();
    }
  };
  req.open("POST", "http://localhost:8080/TicketBooking1/givenDateFilter", true);
  req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  req.send("onward="+date);
  })
  
}
let isBefore6AmFilterApplied = false;
let isFrom6Amto12PmFilterApplied = false;
let isFrom12Pmto6PmFilterApplied = false;
let isAfter6PmFilterApplied = false;
let isAcSeaterFilterApplied=false;

let isAcSleeperFilterApplied=false;
let isACFilterApplied = false;
let isSleeperFilterApplied = false;
let isSeaterFilterApplied = false;


let isUpperFilterApplied = false;
let isMiddleFilterApplied = false;
let isLowerFilterApplied = false;
function filterBefore6AmTrains() {
  const trainData = trainList.trainData;
  const filteredTrains = [];
  for (let i = 0; i < trainData.length; i++) {
    let departureTime = getPlacesTime(trainData[i].trainID,trainList.from);
    console.log("departure time is ");
    console.log(trainData[i].trainId+"==>>"+trainList.from);
    let hours = parseInt(departureTime.split(":")[0]);
    if (hours < 6) {
      filteredTrains.push(trainData[i]);
    }
  }
  return filteredTrains;
}
$("#input-date").change(function () {
  let date = new Date($(this).val());
  
  currentDate=date.toISOString();
  currentDate=currentDate.slice(0, 10);
  $("#date").html(currentDate);
  inputDate(currentDate);
  console.log("the current date is"+currentDate)
});
$("#nextDay").click(function () {
  let date = new Date($("#date").html());
  date.setDate(date.getDate() + 1);
  currentDate=date.toISOString();
  currentDate=currentDate.slice(0, 10);
  $("#date").html(currentDate);
  
  inputDate(currentDate);
  console.log("the current date is"+currentDate)
});
$("#prevDay").click(function () {
  let date = new Date($("#date").html());
  date.setDate(date.getDate() - 1);
  currentDate=date.toISOString();
  currentDate=currentDate.slice(0, 10);
  $("#date").html(currentDate);
  inputDate(currentDate);
  

  console.log("the current date is"+currentDate)
});
  
$("input#upper").click(function () {
  if (!isUpperFilterApplied) {
    isUpperFilterApplied = true;
    if (!isMiddleFilterApplied) {
      $(".Middle").css("visibility", "hidden");
    }
    if (!isLowerFilterApplied) {
      $(".Lower").css("visibility", "hidden");
    }
    $(".Upper").css("visibility", "visible");
  } else {
    isUpperFilterApplied = false;
    if (isLowerFilterApplied || isMiddleFilterApplied) {
      $(".Upper").css("visibility", "hidden");
    } else {
      $(".Lower").css("visibility", "visible");
      $(".Middle").css("visibility", "visible");
    }
  }
});
$("input#middle").click(function () {
  if (!isMiddleFilterApplied) {
    isMiddleFilterApplied = true;
    if (!isUpperFilterApplied) {
      $(".Upper").css("visibility", "hidden");
    }
    if (!isLowerFilterApplied) {
      $(".Lower").css("visibility", "hidden");
    }
    $(".Middle").css("visibility", "visible");
  } else {
    isMiddleFilterApplied = false;
    if (isUpperFilterApplied || isLowerFilterApplied) {
      $(".Middle").css("visibility", "hidden");
    } else {
      $(".Lower").css("visibility", "visible");
      $(".Upper").css("visibility", "visible");
    }
  }
});

$("input#lower").click(function () {
  if (!isLowerFilterApplied) {
    isLowerFilterApplied = true;
    if (!isUpperFilterApplied) {
      $(".Upper").css("visibility", "hidden");
    }
    if (!isMiddleFilterApplied) {
      $(".Middle").css("visibility", "hidden");
    }
    $(".Lower").css("visibility", "visible");
  } else {
    isLowerFilterApplied = false;
    if (isUpperFilterApplied || isMiddleFilterApplied) {
      $(".Lower").css("visibility", "hidden");
    } else {
      $(".Middle").css("visibility", "visible");
      $(".Upper").css("visibility", "visible");
    }
  }
});
$("#ACSeater").click(()=>{
  console.log("ac seater button is cliked");
  $(".ACSeater").toggleClass("active-ACSeater");
  $(".ACSeater").click(function(){return false;});
 

});
$("#ACSleeper").click(()=>{
  console.log("ac seater button is cliked");
  $(".ACsleeper").toggleClass("active-ACsleeper");
  $(".ACsleeper").click(function(){return false;});
  $(".active-ACsleeper").click(function(){return true;});

});
$("#Sleeper").click(()=>{
  console.log("sleeper button is cliked");
  $(".sleeper").toggleClass("active-sleeper");
  $(".sleeper").click(function(){return false;});
  $(".active-sleeper").click(function(){return true;});
  });
  $("#Seater").click(()=>{
    console.log("clicked");
    $(".seater").toggleClass("active-seater");
    $(".seater").click(function(){return false;});
  $(".active-Seater").click(function(){return true;});
    });
function filter6Amto12PmTrains() {
  const trainData = trainList.trainData;
  const filteredTrains = [];
  for (let i = 0; i < trainData.length; i++) {
    let departureTime = getPlacesTime(trainData[i].trainID,trainList.from);
    let hours = parseInt(departureTime.split(":")[0]);
    if (hours >= 6 && hours < 12) {
      filteredTrains.push(trainData[i]);
    }
  }
  return filteredTrains;
}
function filter12Pmto6PmTrains() {
  const trainData = trainList.trainData;
  const filteredTrains = [];
  for (let i = 0; i < trainData.length; i++) {
    let departureTime = getPlacesTime(trainData[i].trainID,trainList.from);
    let hours = parseInt(departureTime.split(":")[0]);
    let minutes = parseInt(departureTime.split(":")[1]);
    if ((hours >= 12 && hours < 18) || (hours == 18 && minutes == 0)) {
      filteredTrains.push(trainData[i]);
    }
  }
  return filteredTrains;
}
function filterAfter6PmTrains() {
  const trainData = trainList.trainData;
  const filteredTrains = [];
  for (let i = 0; i < trainData.length; i++) {
    let departureTime = getPlacesTime(trainData[i].trainID,trainList.from);
    let hours = parseInt(departureTime.split(":")[0]);
    let minutes = parseInt(departureTime.split(":")[1]);
    if (hours > 18 || (hours == 18 && minutes > 0)) {
      filteredTrains.push(trainData[i]);
    }
  }
  return filteredTrains;
}
$("#before6am").click(function () {
  let filteredTrains = [];
  if (!isBefore6AmFilterApplied) {
    filteredTrains = filterBefore6AmTrains();
    isBefore6AmFilterApplied = true;
  } else {
    isBefore6AmFilterApplied = false;
  }
  if (isFrom6Amto12PmFilterApplied) {
    console.log("Inside 6-12");
    const tempTrains = filter6Amto12PmTrains();
    filteredTrains = filteredTrains.concat(tempTrains);
  }
  if (isFrom12Pmto6PmFilterApplied) {
    const tempTrains = filter12Pmto6PmTrains();
    filteredTrains = filteredTrains.concat(tempTrains);
  }
  if (isAfter6PmFilterApplied) {
    const tempTrains = filterAfter6PmTrains();
    filteredTrains = filteredTrains.concat(tempTrains);
  }
  if(!isAfter6PmFilterApplied&&!isFrom6Amto12PmFilterApplied&&!isFrom12Pmto6PmFilterApplied&&!isBefore6AmFilterApplied){
    console.log("hey im from before 6pm if+++++++++++++++++++++++++++++++++++++++++++++++++++++")
    generateTrain(trainList.trainData);
  }else{
    console.log("hey im from before 6pm else+++++++++++++++++++++++++++++++++++++++++++++++++")

  generateTrain(filteredTrains);
  }
});
$("#6amto12pm").click(function () {
  let filteredTrains = [];
  if (!isFrom6Amto12PmFilterApplied) {
    filteredTrains = filter6Amto12PmTrains();
    isFrom6Amto12PmFilterApplied = true;
  } else {
    isFrom6Amto12PmFilterApplied = false;
  }
  if (isBefore6AmFilterApplied) {
    const tempTrains = filterBefore6AmTrains();
    filteredTrains = filteredTrains.concat(tempTrains);
  }
  if (isFrom12Pmto6PmFilterApplied) {
    const tempTrains = filter12Pmto6PmTrains();
    filteredTrains = filteredTrains.concat(tempTrains);
  }
  if (isAfter6PmFilterApplied) {
    const tempTrains = filterAfter6PmTrains();
    filteredTrains = filteredTrains.concat(tempTrains);
  }
  if(!isAfter6PmFilterApplied&&!isFrom6Amto12PmFilterApplied&&!isFrom12Pmto6PmFilterApplied&&!isBefore6AmFilterApplied){
    console.log("hey im from  6am to 12pm if+++++++++++++++++++++++++++++++++++++++++++++++++")

    generateTrain(trainList.trainData);
  }else{
    console.log("hey im from  6am to 12pm else+++++++++++++++++++++++++++++++++++++++++++++++")

  generateTrain(filteredTrains);
  }
});
$("#12pmto6pm").click(function () {
  let filteredTrains = [];
  if (!isFrom12Pmto6PmFilterApplied) {
    filteredTrains = filter12Pmto6PmTrains();
    isFrom12Pmto6PmFilterApplied = true;
  } else {
    isFrom12Pmto6PmFilterApplied = false;
  }
  if (isFrom6Amto12PmFilterApplied) {
    const tempTrains = filter6Amto12PmTrains();
    filteredTrains = filteredTrains.concat(tempTrains);
  }
  if (isBefore6AmFilterApplied) {
    const tempTrains = filterBefore6AmTrains();
    filteredTrains = filteredTrains.concat(tempTrains);
  }
  if (isAfter6PmFilterApplied) {
    const tempTrains = filterAfter6PmTrains();
    filteredTrains = filteredTrains.concat(tempTrains);
  }
  if(!isAfter6PmFilterApplied&&!isFrom6Amto12PmFilterApplied&&!isFrom12Pmto6PmFilterApplied&&!isBefore6AmFilterApplied){
    console.log("hey im from  12pm to 6am if++++++++++++++++++++++++++++++++++++++++++++++++++++++")

    generateTrain(trainList.trainData);
  }else{
    console.log("hey im from  12pm to 6am else++++++++++++++++++++++++++++++++++++++++++++++++++++++")

  generateTrain(filteredTrains);
  }
});
$("#after6pm").click(function () {
  let filteredTrains = [];
  if (!isAfter6PmFilterApplied) {
    filteredTrains = filterAfter6PmTrains();
    isAfter6PmFilterApplied = true;
  } else {
    isAfter6PmFilterApplied = false;
  }
  if (isFrom6Amto12PmFilterApplied) {
    const tempTrains = filter6Amto12PmTrains();
    filteredTrains = filteredTrains.concat(tempTrains);
  }
  if (isFrom12Pmto6PmFilterApplied) {
    const tempTrains = filter12Pmto6PmTrains();
    filteredTrains = filteredTrains.concat(tempTrains);
  }
  if (isBefore6AmFilterApplied) {
    const tempTrains = filterBefore6AmTrains();
    filteredTrains = filteredTrains.concat(tempTrains);
  }
  if(!isAfter6PmFilterApplied&&!isFrom6Amto12PmFilterApplied&&!isFrom12Pmto6PmFilterApplied&&!isBefore6AmFilterApplied){
    console.log("hey im from after 6pm if+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")

    generateTrain(trainList.trainData);
  }else{
    console.log("hey im from after 6pm else++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")

  generateTrain(filteredTrains);
  }
});
console.log(trainList);
$("#list-box").html("");
function generateDuration(arrival, departure) {
  var arrivalHours = 0;
  var arrivalMinutes = 0;
  var departureHours = 0;
  var departueMinutes = 0;
  var temp = "";
  for (var j = 0; j < arrival.length; j++) {
    if (arrival[j] == ":") {
      arrivalHours = Number(temp);
      temp = "";
    } else {
      temp += arrival[j];
    }
    if (j == arrival.length - 1) {
      arrivalMinutes = Number(temp);
    }
  }
  temp = "";
  for (var j = 0; j < departure.length; j++) {
    if (departure[j] == ":") {
      departureHours = Number(temp);
      temp = "";
    } else {
      temp += departure[j];
    }
    if (j == departure.length - 1) {
      departueMinutes = Number(temp);
    }
  }
  var duration;
  var hours;
  var minutes;
  if (arrivalHours > departureHours) {

    hours=arrivalHours - departureHours;
   // duration = String(arrivalHours - departureHours);
   console.log("if hours duration"+arrivalHours+"   "+departureHours)
  } else if(arrivalHours<departureHours) {
    console.log("else hours duration"+arrivalHours+"   "+departureHours)

    hours=(24 + arrivalHours) - departureHours;
   // duration = String(24 + arrivalHours - departureHours);
  }else{
    hours=0;
  }
  if (arrivalMinutes > departueMinutes) {
     minutes=arrivalMinutes - departueMinutes;
     console.log("if minutes duration"+departueMinutes+"   "+arrivalHours)

    if(minutes==60){
      hours+=1;
      minutes=0;
    }
    //duration += ":" + String(arrivalMinutes - departueMinutes);
  } else if(arrivalMinutes<departueMinutes) {
    minutes=(60+arrivalMinutes) - departueMinutes;
    console.log("else minutes duration"+departueMinutes+"   "+arrivalMinutes)

    if(minutes==60){
      hours+=1;
      minutes=0;
    }
   // duration += ":" + String(60 + arrivalMinutes - departueMinutes);
  }else{
    minutes=0;
  }
  duration =String(hours+":"+minutes)
  return duration;
}
function availableSeats(){

}

function generateTrain(array) {
  console.log("the generate method is called");
  
  
  let trainListText = "";
  for (var i = 0; i < array.length; i++) {
    trainListText +=
      " <div class='list-of-train'>" +
      "<div>" +
      "<span style='margin-left: 0%;'id=''>" +
      "<h3>"+array[i].trainName +"</h3>"+
      "</span>" +
      "<span class='trainCol' style='margin-left: 20%' id='trainDeparture'>" +
      array[i].departureTime +
      "</span>" +
      "<span class='trainCol'id='arraival'>" +
      array[i].arraivalTime +
      "</span>" +
      "<span class='trainCol'id='duration'>" +
      generateDuration(array[i].arraivalTime, array[i].departureTime) +
      " hours</span>" +
      "<span class='trainCol'id='seatsAvailable'>" +
      array[i].availableSeats +
      " seats available</span>" +
      "</div>" +
      "<div class='trainrow2'>" +
      "<span class='trainCol2'>" +
      array[i].initialPlace +
      "</span>" +
      "<span class='trainCol2'>  " +
      array[i].destination +
      "</span>" +
      "</div>" +
      "<div class='trainrow2'>" +
      "<span class='trainCol' >" +
      "<b>searched for </b>"+
      "</span>" +
      "<span class='trainCol' >" +
      "<b>"+trainList.from+" at"+getPlacesTime( array[i].trainID,trainList.from)+" </b>"+
      "</span>" +
      "<span class='trainCol'>" +
      "<b>"+trainList.to+" at "+getPlacesTime( array[i].trainID,trainList.to)+" </b>"+
      "</span>" +
      "</div>" +
      
     
      "<div>" +
      " <button class='selectTrain' onclick=showOptions('" +
      array[i].trainID +
      "')>select Train</button>" +
      "</div>" +
      "<div id='" +
      array[i].trainID +
      "'></div>" +
      "<div style='display: flex;'>" +
      "<div id='" +
      array[i].trainID +
      "A' class='selection'></div>" +
      "<div id='" +
      array[i].trainID +
      "B' class='selection'></div>" +
      "<div id='" +
      array[i].trainID +
      "C' class='selection'></div>" +
      "</div>" +
      "</div>";
  }
  $("#list-box").html(trainListText);
}
$(document).ready(function () {
  let today = new Date();
  $("#date").html(today.toDateString());
  $("#container-List").hide();
  $("#payment-banner").show();
  $(".pageoverlay").hide();
  $("#select-seat-banner").hide();
  $("#boradingBox").hide();
  $(".screenOff").hide();
  $("#registerBookingName").hide();
});
let trainCheck = true;
let checkBoarding = false;
var paymentCheck=false;
var boardingCheck=false;
var checkBoardingData=false;
var checkDroppingData=false;


let trainId = "";
let currentContainerId = "";
var currentSelectedContainer='';
var totalPayment=0;
let boardingData;
var endOfInitial=0;
var endOfDestination=0;
var previousBoardingId='';
var previousDroppingId='';
let bookedSeats={};
var array=[];
var previousTrainId='';
var bookTrain={};
var seatListData=[];
function generateCompartment(id) {
  console.log("the generate compartment is "+id);
  var train = trainList.trainData.find((train) => train.trainID == id);
  console.log("train is ");
  console.log(train);
  var container =
    "<div style='width: 100%;margin-left: 18%;'>" +
    "<div class='container-choice'>" +
    "<img src='client3/engine1.png'><span class='ACSeater active-ACSeater'>";
  var i = 0;
  for (var i = 0; i < Number(train.AcSeater); i++) {
	    console.log("ac for loop");
	    container +=
	      "<button id='ACT"+(i+1)+"'onclick=showSeat('ACT"+(i+1) +"')><img src='client3/container2.png'></button>";
	  }
  container += "</span>" + "<span class='ACsleeper active-ACsleeper'>";
  for (var i = 0; i < Number(train.AcSleeper); i++) {
    container +="<button id='ACS"+(i+1) +"'onclick=showSeat('ACS"+(i+1) +"')><img src='client3/container2.png'></button>";
  }
  container += "</span>" + "<span class='sleeper active-sleeper'>";
  for (var i = 0; i < Number(train.sleeper); i++) {
    container +="<button id='SLG"+(i+1)+"'onclick=showSeat('SLG" +(i + 1) +"')><img src='client3/container2.png'></button>";
  }
  container += "</span>" + "<span class='seater active-seater'>";
  for (var i = 0; i < Number(train.seater); i++) {
    container += "<button id='SGH" +(i + 1) +"'onclick=showSeat('SGH" +(i + 1) +"')><img src='client3/container2.png'></button>";
  }
  container += "</span>" + "</div>" + "</div>";
  return container;
}

function showOptions(id) {
    console.log("showoption is "+id);
  if (trainId == id || trainId == "") {
    trainId = id;
    if (trainCheck) {
      trainCheck = false;
      console.log("generateCompartment"+id);
      $("#" + id).html(generateCompartment(id));
    } else {
      trainCheck = true;
      paymentCheck=false;
      boardingCheck=false;
      totalPayment=0;
      bookedSeats={}
      $("#" + id).html("");
      $("#" + id + "A").html("");
      $("#" + id + "B").html("");
      $("#" + id + "C").html("");
    }
  } else {
    document.getElementById(trainId).innerHTML = "";
     $("#" + trainId + "A").html("");
      $("#" + trainId + "B").html("");
      $("#" + trainId + "C").html("");
    trainId = id;
    trainCheck = true;
    if (trainCheck) {
      trainCheck = false;
      $("#" + id).html(generateCompartment(id));
    } else {
      totalPayment=0;
      trainCheck = true;
      paymentCheck=false;
      boardingCheck=false;
      bookedSeats={}
      $("#" + id).html("");
      $("#" + id + "A").html("");
      $("#" + id + "B").html("");
      $("#" + id + "C").html("");
    }
  }
}
function showSeat(containerID) {
  
  let txt = "";
  currentSelectedContainer=containerID;
  if (containerID.startsWith("ACS") || containerID.startsWith("SLG")){
    $("#" + trainId + "A").html($("#sleeper").html());
   
  }
  else {
    $("#" + trainId + "A").html($("#seater").html());
  }
  checkReservedSeats(containerID);
    
}
function checkReservedSeats(container){
  console.log(container);
  console.log(trainId);
  console.log(trainList.seatListData);
 // var trainTemp=trainList.seatListData;
 var trainTemp=seatListData;
  var seatArrayTemp=[]
  var subArray1=[]
  var subArray2;
  var subArray3;
  for(var i=0;i<trainTemp.length;i++){
    seatArrayTemp=Object.keys(trainTemp);
    subArray1=Object.keys(trainTemp[seatArrayTemp[i]]);
    subArray2=trainTemp[seatArrayTemp[i]];
    if(subArray2[trainId]!=undefined){
      // console.log("hey its working");
        for(var j=0;j<subArray2[trainId].length;j++){
        subArray3=subArray2[trainId];
        // console.log("the current coach is "+container);
        if(subArray3[j]['coach']==container){
        if(currentDate==subArray3[j]['date']){
          if(trainList.user==subArray3[j]['bookedBy']){
          document.getElementById(subArray3[j]['seatName']).style.backgroundColor='blue';
          }else{
            document.getElementById(subArray3[j]['seatName']).style.backgroundColor='red';
          }
        }
          
        }
      }
      
    }
  
   

  }
  var trainNames=Object.keys(bookedSeats);
  var tag='';
    for(var i=0;i<trainNames.length;i++){
        for(var j=0;j<bookedSeats[trainNames[i]].length;j++){
          if(trainNames[i]==container)
          document.getElementById(bookedSeats[trainNames[i]][j]['coach']).style.backgroundColor="green";
        }
    }
  // var trainListtemp=trainList.seatListData;
  //  var temp=trainListtemp.find(train=>train==trainId);
  // console.log(temp);
}
function checkOut() {
      if(trainList['user']!='null'){
      $(".screenOff").show();
      $("#registerBookingName").show();
      var ticketList='';
    var trainNames=Object.keys(bookedSeats);
      var tag='';
        for(var i=0;i<trainNames.length;i++){
            for(var j=0;j<bookedSeats[trainNames[i]].length;j++){
            tag+="<h3> train no:"+trainId+"</h3>"+
              "<label for='name'><b>coach/seat :</b><i>"+trainNames[i]+" / "+bookedSeats[trainNames[i]][j]['coach']+"</i></label>"+
            "<input type='text' placeholder='Enter passenger name' id='"+bookedSeats[trainNames[i]][j]['coach']+"D'>";
            }
        }
        $("#passengerRegister").html(tag);
      console.log(bookedSeats);
    }else{
      setRecords();
      alert("you have register first");
    }
}
var totalSeats=0;
function checkCoach(){
  console.log(Object.keys(bookedSeats));
  var trainTemp=seatListData;
    var seatArrayTemp=[]
    var subArray1=[]
    var subArray2;
    var subArray3;
    var getCoach=[]
    for(var i=0;i<trainTemp.length;i++){
      seatArrayTemp=Object.keys(trainTemp);
      subArray1=Object.keys(trainTemp[seatArrayTemp[i]]);
      subArray2=trainTemp[seatArrayTemp[i]];
      if(subArray2[trainId]!=undefined){
        console.log("hey its working");
          for(var j=0;j<subArray2[trainId].length;j++){
          subArray3=subArray2[trainId];
          if(subArray3[j]['date']==currentDate){
            console.log(subArray3[j]['date']+"=>"+j+"=>"+currentDate);
         getCoach.push(subArray3[j]['coach']);
         totalSeats++;
        }
         
        }
      }
    }
    console.log("the coach is ");
    console.log(getCoach);  
    var set=new Set(getCoach);
    getCoach=Array.from(set);
    return getCoach;
  }
function payment(){
  checkSeats().then((condition)=>{
    console.log("the condition is"+condition +'kjk');
    if(condition==='false'){
          console.log(bookedSeats);
          totalSeats=0;
          // console.log("the booked seats are"+totalSeats+"with trainid "+trainId);
          var dataBaseCoaches=checkCoach();
          
          bookTrain={}
          var coachArray=[];
          var temp;
          var trainNames=Object.keys(bookedSeats);
          dataBaseCoaches=dataBaseCoaches.concat(trainNames);
          console.log("the total seats where"+totalSeats);
          console.log(dataBaseCoaches);
          console.log("after coaches is ");
          var set=new Set(dataBaseCoaches);
          dataBaseCoaches=Array.from(set);
          console.log(dataBaseCoaches);
          var checkInputName=true;
          for(var i=0;i<trainNames.length;i++){
            
            for(var j=0;j<bookedSeats[trainNames[i]].length;j++){
              if($("#"+bookedSeats[trainNames[i]][j]['coach']+"D").val()==''){
                checkInputName=false;
              }
              
                temp={
                  'name':$("#"+bookedSeats[trainNames[i]][j]['coach']+"D").val(),
                  'seat':bookedSeats[trainNames[i]][j]['coach'],
                  'coach':trainNames[i],
                  'fare':bookedSeats[trainNames[i]][j]['fare']
                }
                totalSeats++;
                coachArray.push(temp);
            }
        }
        if(document.getElementById("passengerRegister").innerHTML.length==0){
         console.log("ite completed");
          checkInputName=false;
        }
        console.log($("#passengerRegister").html())
        var train=trainList.trainData.find(train=>train.trainID==trainId);

        var totalCoach=Number(train['container']);
        var allSeats=totalCoach*15;
        console.log(train['container']);
        var unbookedCoach=totalCoach-(dataBaseCoaches.length);
        var bookedCoach=dataBaseCoaches.length;
        var Seats=totalSeats;
        var unbookedSeats=allSeats-Seats;
            bookTrain={
              'trainId':trainId,
              'booked':coachArray,
              'from':currentBoardingData,
              'to':currentDroppingData,
              'amount':totalPayment,
              'date':currentDate,
              'unBookedCoach':unbookedCoach,
              'bookedCoach':bookedCoach,
              'unBookedSeats':unbookedSeats,
              'bookedSeat':Seats,
              'name':train.trainName
            }
            console.log(bookTrain);
            if(checkInputName){
              bookTickets(bookTrain);
            }else{
              alert("pls fill up the nameList correctly");
            }
           
      }else{

            alert("some of your seat already have been booked ");
            cancelBooking();
            callDefault();
            // defaultCall();
          }
     })
      
}
function buttonBD() {
 
  if (checkBoarding) {
    checkBoarding = false;
    document.getElementById("boardingPoint2").classList.remove("hide");
    document.getElementById("droppingPoint").classList.add("hide");
  } else {
    checkBoarding = true;
    document.getElementById("droppingPoint").classList.remove("hide");
    document.getElementById("boardingPoint2").classList.add("hide");
  }
}
function bookSeats(id) {
  if(document.getElementById(id).style.backgroundColor!="red"&&document.getElementById(id).style.backgroundColor!="blue"){

   if(!boardingCheck){
     boardingData=getBoardingAndDropping();
     console.log(boardingData);
    boardingCheck=true;
   
    $("#" + trainId + "B").html($("#boardingBox").html());
    var boardingPlaces=[];
    var boardingTime=[];

  for(var i=0;i<boardingData.length;i++){
    boardingPlaces=boardingPlaces.concat(Object.keys(boardingData[i]));
    boardingTime.push(boardingData[i][boardingPlaces[i]]);
  }
  console.log(boardingPlaces);
  console.log(boardingTime);
  endOfInitial=boardingPlaces.findIndex(element => {
    console.log("hey1"+element.toLowerCase()+"=>"+trainList.from.toLowerCase());

    return element.toLowerCase() == trainList.from.toLowerCase();
  });
  endOfDestination=boardingPlaces.findIndex(element => {
    console.log("hey"+element.toLowerCase()+"=>"+trainList.to.toLowerCase());

    return element.toLowerCase() == trainList.to.toLowerCase();
  });
    console.log(endOfDestination+"====><===="+endOfInitial)
    generateBoardingPlaces(boardingPlaces,boardingTime);
    genrationDroppingPlaces(boardingPlaces,boardingTime); 
    
    }
   }
   updateSeats(id);
}
function getBoardingAndDropping(){
  console.log("the boarding trainId is "+trainId);
  let boardingData=[];
  var settings = {
    "url": "http://localhost:8080/TicketBooking1/getAllTheStopsData?trainID="+trainId,
    "method": "POST",
    "timeout": 0,
    "async":false
  };
  
  $.ajax(settings).done(function (response) {
  
    boardingData= JSON.parse(response);
  });
  return boardingData;
}
function generateBoardingPlaces(boardingPlaces,time){
  var index = boardingPlaces.findIndex(element => {
    console.log(element.toLowerCase()+"=>"+trainList.from.toLowerCase());

    return element.toLowerCase() == trainList.from.toLowerCase();
  });
  console.log("the from index is "+index);
  var boarding='';
 if((index+2<(boardingPlaces.length)/2)&&(index<(endOfDestination-2))){
   for(var i=index;i<=index+2;i++){
    boarding+= "<div style='margin-top: 20px;'><input type='checkbox' id='"+boardingPlaces[i]+"A' onclick=callCheckBoarding('"+boardingPlaces[i]+"')>"+
     "<span style='margin-left: 20px;' id='"+boardingPlaces[i]+"B'>"+boardingPlaces[i]+"</span><span style='margin-left:5px'>"+time[i]+"</span> </div>";
     $("#listPlaces1").html(boarding);
     }
 }else{
  if(index==0){
    index=1;
  }
  console.log("the methods called ")
   for(var i=index-1;i<=index+1;i++){
     if(boardingPlaces[i]!=undefined&&i<endOfDestination){
     boarding+= "<div style='margin-top: 20px;'><input type='checkbox' id='"+boardingPlaces[i]+"A' onclick=callCheckBoarding('"+boardingPlaces[i]+"')>"+
     "<span style='margin-left: 20px;' id='"+boardingPlaces[i]+"B'>"+boardingPlaces[i]+"</span><span style='margin-left:5px'>"+time[i]+"</span> </div>";
     $("#listPlaces1").html(boarding);
     }
   }
 }
}
function genrationDroppingPlaces(boardingPlaces,time){
  var index = boardingPlaces.findIndex(element => {
    console.log(element.toLowerCase()+"=>"+trainList.to.toLowerCase());
    return element.toLowerCase() == trainList.to.toLowerCase();
  });
  console.log("the to index is "+index);
  var boarding='';
 if((index-2>(boardingPlaces.length)/2)&&index>(endOfInitial+2)){
  console.log("heyyyyyyyyyyyyyyy")
   for(var i=index-2;i<=index;i++){
   
    console.log(boardingPlaces[i]);
     if(boardingPlaces[i]!=undefined){
    boarding+= "<div style='margin-top: 20px;'><input type='checkbox' id='"+boardingPlaces[i]+"A' onclick=callCheckDropping('"+boardingPlaces[i]+"')>"+
     "<span style='margin-left: 20px;' id='"+boardingPlaces[i]+"B'>"+boardingPlaces[i]+"</span><span style='margin-left:5px'>"+time[i]+"</span> </div>";
     $("#listPlaces2").html(boarding);
     }
   }
 }else{
  console.log("hey its else")
  if(index==(boardingPlaces-1)){
    index--;
  }
   for(var i=index+1;i>index-1;i--){
     if(boardingPlaces[i]!=undefined&&i>(endOfInitial)){
     boarding+= "<div style='margin-top: 20px;'><input type='checkbox' id='"+boardingPlaces[i]+"A' onclick=callCheckDropping('"+boardingPlaces[i]+"')>"+
     "<span style='margin-left: 20px;' id='"+boardingPlaces[i]+"B'>"+boardingPlaces[i]+"</span><span style='margin-left:5px'>"+time[i]+"</span> </div>";
     $("#listPlaces2").html(boarding);
     }
   }
 }
}
function callCheckBoarding(id){
  
  if(previousBoardingId==''){
    currentBoardingData=id;
  }
 else if(previousBoardingId==id){
    console.log("the current boarding id is "+id)
    if(document.getElementById(id+'A').checked==false){
      currentBoardingData=defaultBoarding;
    }else{
      currentBoardingData=id;
    }
   
  }else{
   $('#'+previousBoardingId+'A').prop("checked",false);
   console.log("the previous boarding id is "+previousBoardingId);
   console.log("the current boarding id is "+id);
   currentBoardingData=id;
  }
  $("#boarding-from").text(currentBoardingData);
  previousBoardingId=id;
  if(!paymentCheck){
    $("#" + trainId + "C").html($('#payment-banner').html());
    paymentCheck=true;
  }
}
function callCheckDropping(id){
  console.log("the drop methods is called ");
  if(previousDroppingId==''){
   console.log("1 is called ");
   currentDroppingData=id;
  }
  else if(previousDroppingId==id){
    
    if(document.getElementById(id+'A').checked==false){
      currentDroppingData=defaultDropping;
    }else{
      currentDroppingData=id;
    }

  }else{
   $('#'+previousDroppingId+'A').prop("checked",false);
   currentDroppingData=id;
  }
  $("#dropping-to").text(currentDroppingData);
  previousDroppingId=id;
  if(!paymentCheck){
    $("#" + trainId + "C").html($('#payment-banner').html());
    paymentCheck=true;
  }
}
var previousSeatList={};
function updateSeats(id){
  var currentfare;
  var train = trainList.trainData.find((train) => train.trainID == trainId);

  if(document.getElementById(id).style.backgroundColor==''){
    console.log(bookedSeats);
    if(currentSelectedContainer.startsWith("ACT")){
      totalPayment+=Number(train.AcSeaterFare);
      currentfare=Number(train.AcSeaterFare);
    }else if(currentSelectedContainer.startsWith("ACS")){
      totalPayment+=Number(train.AcSleeperFare);
      currentfare=Number(train.AcSleeperFare);
    }else if(currentSelectedContainer.startsWith("SLG")){
      totalPayment+=Number(train.sleeperFare);
      currentfare=Number(train.sleeperFare);
    }else if(currentSelectedContainer.startsWith("SGH")){
      totalPayment+=Number(train.seaterFare);
      currentfare=Number(train.seaterFare);
    }
    console.log("its nothing");
          if(previousTrainId!=''&&previousTrainId!=trainId){
            bookTrain[previousTrainId]=bookedSeats;
            bookedSeats={};
        }
        var temp={}
          previousTrainId=trainId;
          if(bookedSeats[currentSelectedContainer]==undefined){
          array=[];
          temp={"coach":id,"fare":currentfare};
          array.push(temp);
            
          bookedSeats[currentSelectedContainer]=array;
          }else{
            array=[];
            array=bookedSeats[currentSelectedContainer];
            temp={"coach":id,"fare":currentfare};
            array.push(temp);
            bookedSeats[currentSelectedContainer]=array
          }
          previousSeatList=bookedSeats;

        paymentBox();
        document.getElementById(id).style.backgroundColor='green';
  }else if(document.getElementById(id).style.backgroundColor=='green'){
      console.log("its green");
      console.log("before delete")
      console.log(bookedSeats);
      array=bookedSeats[currentSelectedContainer];
      console.log("before array")
      console.log(array);
      var demoTemp=array.find(arr=>arr.coach==id);
      console.log(demoTemp);
     
      var temp =new Set(array);
      console.log(temp)
      console.log(totalPayment+"=>"+demoTemp['fare']);
      console.log("before payment"+totalPayment);
      totalPayment-=demoTemp['fare'];
      temp.delete(demoTemp);
      console.log("after payment"+totalPayment)
      array=[];
      temp.forEach((value)=>array.push(value));
      console.log(array);
      bookedSeats[currentSelectedContainer]=array;
      console.log("after delete");
      console.log(bookedSeats);
      paymentBox();
      document.getElementById(id).style.backgroundColor='';
  }else if(document.getElementById(id).style.backgroundColor=='red'){
    console.log("its red");
  }
   console.log(document.getElementById(id).style.backgroundColor);
}
function paymentBox(){
  
$("#amt").text("INR "+totalPayment);
$("#boarding-from").text(currentBoardingData);
$("#dropping-to").text(currentDroppingData);

var listofTrainsCoach;
var ticketList='';
var trainNames=Object.keys(bookedSeats);
 ticketList= "<h4 id="+trainId+">Train :"+trainId+"</h4>";
    for(var i=0;i<trainNames.length;i++){
      ticketList+="<div><span><b>Coach no :"+trainNames[i]+"</b>";
        for(var j=0;j<bookedSeats[trainNames[i]].length;j++){
          ticketList+="<span><i>, "+bookedSeats[trainNames[i]][j]['coach']+"</i></span>";
        }
      ticketList+="</div>";                       
    }
    
// var listOfTrains=Object.keys(bookTrain);
//     var trainTemp=[];
//     var coachTemp;
//     var seatListTemp;
//     for(var k=0;k<listOfTrains.length;k++){
//     ticketList+= "<h4 id="+listOfTrains[k]+">Train :"+listOfTrains[k]+"</h4>";
//       trainTemp=Object.keys(bookTrain[listOfTrains[k]]);

//     for(var i=0;i<trainTemp.length;i++){
//       ticketList+="<div><span><b>tickets no :"+trainTemp[i]+"</b>";
//       seatListTemp=bookTrain[listOfTrains[k]][trainTemp[i]]
//         for(var j=0;j<seatListTemp.length;j++){
//           ticketList+="<span><i>, "+seatListTemp[j]+"</i></span>";
//         }
//       ticketList+="</div>";                       
//     }
//   }


    $("#bookedTrainDetails").html(ticketList);
 
}

function bookTickets(data){
  console.log(data);
  var req=new XMLHttpRequest();
   req.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      if(this.responseText=='true'){
        backToTrainList();
        alert("your booking is successful");
       
      
      }else{
        backToTrainList();
        alert("your booking is not sucessful successful");
      }
      
    }
  };
  req.open("POST", "http://localhost:8080/TicketBooking1/bookSeats", true);
  req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  req.send("seatList="+JSON.stringify(data));
}
function backToTrainList(){
  $(".screenOff").hide();
  $("#registerBookingName").hide();
  showOptions(trainId);
  totalPayment=0;
  bookedSeats={};
  callDefault();
  // defaultCall();
}
function cancelBooking(){
  
  console.log("the cancel booking method is called");
  
  var req=new XMLHttpRequest();
   req.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      $(".screenOff").hide();
      $("#registerBookingName").hide();
      console.log("the train id is "+trainId)
      showOptions(trainId);
      totalPayment=0;
      $("#bookedTrainDetails").html("");
      $("#amt").text("INR  0");
      bookedSeats={};
      
    }
  };
  req.open("POST", "http://localhost:8080/TicketBooking1/endRecord", true);
  req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  req.send();
}

function checkSeats(){
  return new Promise((resolve)=>{
    var data={}
    data['array']=bookedSeats;
    data['id']=trainId;
    data['date']=currentDate
  
  var req= new XMLHttpRequest();
  req.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      resolve(this.responseText);
    }
  };
  req.open("POST", "http://localhost:8080/TicketBooking1/checkSeats", true);
  req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  req.send("seatChecking="+JSON.stringify(data));
  });
  
}
