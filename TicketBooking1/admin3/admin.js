let leftNavClass='.r1';
var trainsData;
var previousUpdateData;
var previousPlaceUpdateData;
var previousTimeUpdatedata;
var req = new XMLHttpRequest();
function defaultLoad(){
req.onreadystatechange=function(){
    if(this.readyState==4&&this.status==200){
        console.log("testing");
        trainsData=JSON.parse(this.response);
        console.log("the json is ");
        console.log(trainsData);
        viewTrains();
        queryBookings();
        home();
        homeData();
    }    
}
req.open("POST", "http://localhost:8080/TicketBooking1/trainData", true);
req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
req.send();
}

function home(){
    $(".r1").addClass("active");
    $(leftNavClass).removeClass("active");
    leftNavClass = '.r1';
    $(".content").show();
    $(".earning").hide();
    $(".todaytrain").hide();
    $(".registration").hide();
    $(".addTrains").hide();
    $(".view-Booking").hide();
    $(".view-trains").hide();
    let earnings = 0;
    let passengers = 0;
    var settings = {
        "url": "http://localhost:8080/TicketBooking1/trainData",
        "method": "POST",
        "timeout": 0,
        async : false
      };
      var date = new Date();
      $.ajax(settings).done(function (response) {
        response = JSON.parse(response);
        console.log(response);
        response = response.trainData;
        for (let index in response){
            var settings1 = {
                "url": "http://localhost:8080/TicketBooking1/queryBookingByDate?date=" + date.getFullYear() +"-"+(parseInt(date.getMonth())+1) +"-"+date.getDate(),
                "method": "POST",
                "timeout": 0,
                async: false
            };
            console.log(settings1);
            $.ajax(settings1).done(function (response) {
                response = JSON.parse(response);
                console.log(response);
                for (let i in response){
                    earnings += parseInt(response[i].fare);
                    passengers++;
                }
            });
        }
      });
      $("#homeEarnings").html("&#8377; "+ earnings);
      $("#homePassenger").html(passengers);
   
}

function homeData(){
    $("#alive").text(trainsData.trainData.length);
}
function addTrains(){
    $(".r2").addClass("active");
    $(leftNavClass).removeClass("active");
    leftNavClass='.r2';
    $(".content").hide();
    $(".earning").hide();
    $(".todaytrain").hide();
    $(".registration").hide();
    $(".addTrains").show();
    $(".view-Booking").hide();
    $(".view-trains").hide();
}
function viewBooking(){
    $(".r3").addClass("active");
    $(leftNavClass).removeClass("active");
    leftNavClass='.r3';
    $(".content").hide();
    $(".earning").hide();
    $(".todaytrain").hide();
    $(".registration").hide();
    $(".addTrains").hide();
    $(".view-Booking").show();
    $(".view-trains").hide();
    
}
function trainsStatus(){
    $(".r4").addClass("active");
    $(leftNavClass).removeClass("active");
    leftNavClass='.r4';
    $(".content").hide();
    $(".earning").hide();
    $(".todaytrain").hide();
    $(".registration").hide();
    $(".addTrains").hide();
    $(".view-Booking").hide();
    $(".view-trains").show();
    
}
function earnings(){
    $(".content").show();
    $(".earning").show();
    $(".todaytrain").hide();
    $(".registration").hide();
    $(".addTrains").hide();
    $(".view-Booking").hide();
    $(".view-trains").hide();
}
function todayTrain(){
    $(".content").show();
    $(".earning").hide();
    $(".todaytrain").show();
    $(".registration").hide();
    $(".addTrains").hide();
    $(".view-Booking").hide();
    $(".view-trains").hide();
}
function registration(){
    $(".content").show();
    $(".earning").hide();
    $(".todaytrain").hide();
    $(".registration").show();
    $(".addTrains").hide();
    $(".view-Booking").hide();
    $(".view-trains").hide();
}
function addAStop5(tag){
    $('.stops').append(`
    <div style="display:flex;">
        <input type="text" value="`+ tag.parentNode.childNodes[1].value +`" disabled/>
        <input style="margin-left: 10px;" type="text" value="`+ tag.parentNode.childNodes[3].value +`" disabled/>
        <i onclick="removeTheStopTag(this)" style="margin-top: 20px; margin-left: 10px;" class="fa fa-window-close" aria-hidden="true"></i>
    </div>
    `)
    tag.parentNode.childNodes[1].value = "";
    tag.parentNode.childNodes[3].value = "";
}
function addAStop(tag){
    $('.stops').append(`
    <div style="display:flex;">
        <input type="text" value="`+ tag.parentNode.childNodes[1].value +`" disabled/>
        <input style="margin-left: 10px;" type="text" value="`+ tag.parentNode.childNodes[3].value +`" disabled/>
        <i onclick="removeTheStopTag(this)" style="margin-top: 20px; margin-left: 10px;" class="fa fa-window-close" aria-hidden="true"></i>
    </div>
    `)
    tag.parentNode.childNodes[1].value = "";
    tag.parentNode.childNodes[3].value = "";
}
function removeTheStopTag(tag){
    tag.parentNode.remove();
}
function viewTrains(){
    $('#all-trains').html("");
    var tableRow= "<tr><th>Date</th><th>Train Number</th><th>Name</th>"+
        " <th>Boarding Point</th>"+
         "<th>Destination Point</th>"+
         "<th>Departure Time </th>"+
         "<th>Arrival Time</th>"+
         "<th>AC / Seater</th>"+
         "<th>AC / Sleeper</th>"+
         "<th>AC /seater fare</th>"+
         "<th>AC /sleeper fare</th>"+
         "<th>Coach</th>"+
     "</tr>";
     $('#all-trains').append(tableRow);
    for (var i in trainsData.trainData){
        
        var tableRow = $("<tr ></tr>");
        tableRow.append($("<td></td>").html(`<input class='table-input' placeholder="trainID" value="`+ trainsData.trainData[i].trainID +`" disabled></input>`));
        tableRow.append($("<td></td>").html(`<input class='table-input' placeholder="trainName" value="`+ trainsData.trainData[i].trainName +`" disabled></input>`));
        tableRow.append($("<td></td>").html(`<input class='table-input' placeholder="initialPlace" value="`+ trainsData.trainData[i].initialPlace +`" disabled></input>`));
        tableRow.append($("<td></td>").html(`<input class='table-input' placeholder="destination" value="`+ trainsData.trainData[i].destination +`" disabled></input>`));
        tableRow.append($("<td></td>").html(`<input class='table-input' placeholder="departureTime" value="`+ trainsData.trainData[i].departureTime +`" disabled></input>`));
        tableRow.append($("<td></td>").html(`<input class='table-input' placeholder="arraivalTime" value="`+ trainsData.trainData[i].arraivalTime +`" disabled></input>`));
        tableRow.append($("<td></td>").html(`<input class='table-input' placeholder="AC/seater" value="`+ trainsData.trainData[i].AcSeater +" / "+ trainsData.trainData[i].seater +`" disabled></input>`));
        tableRow.append($("<td></td>").html(`<input class='table-input' placeholder="AC/sleeper" value="`+ trainsData.trainData[i].AcSleeper +" / "+ trainsData.trainData[i].sleeper +`" disabled></input>`));
        tableRow.append($("<td></td>").html(`<input class='table-input' placeholder="AC/Seater fare" value="`+ trainsData.trainData[i].AcSeaterFare +" / "+ trainsData.trainData[i].seaterFare +`" disabled></input>`));
        tableRow.append($("<td></td>").html(`<input class='table-input' placeholder="AC/sleeperFare" value="`+ trainsData.trainData[i].AcSleeperFare +" / "+ trainsData.trainData[i].sleeperFare +`" disabled></input>`));
        tableRow.append($("<td></td>").html(`<input class='table-input' placeholder="bookedCoach" value="`+ trainsData.trainData[i].container +`" disabled></input>`));

        tableRow.append($("<td></td>").html(`<button onclick=getSelectedTrain("`+trainsData.trainData[i].trainID+`")>select</button>`));

        tableRow.append($("<td></td>").html(`<span><i onclick="editTableData(this, `+ trainsData.trainData[i].trainID +`)" class='fa fa-edit edit-button'></i><span></span><i onclick="deleteTableData(this, `+ trainsData.trainData[i].trainID +`)" class="fa fa-trash edit-button" aria-hidden="true"></i><span>`));
        $('#all-trains').append(tableRow);
    }
}
function viewTrainsByDate(trainDate){
    $('#all-trains-ByDate').html("");
    var tableRow= "<tr><th>Date</th><th>Train Number</th><th>Name</th>"+
                       " <th>Boarding Point</th>"+
                        "<th>Destination Point</th>"+
                        "<th>Departure Time </th>"+
                        "<th>Arrival Time</th>"+
                        "<th>AC / Seater</th>"+
                        "<th>AC / Sleeper</th>"+
                        "<th>AC /seater fare</th>"+
                        "<th>AC /sleeper fare</th>"+
                        "<th>bookedSeats</th>"+
                        "<th>availableSeats</th>"+
                       
                        "<th>Coach</th>"+
                    "</tr>";
    $('#all-trains-ByDate').append(tableRow);
    for (var i in trainDate.trainDataWithDate){
        
         tableRow = $("<tr ></tr>");
        tableRow.append($("<td></td>").html(`<input class='table-input' placeholder="date" value="`+ trainDate.trainDataWithDate[i].date +`" disabled></input>`));
        tableRow.append($("<td></td>").html(`<input class='table-input' placeholder="trainID" value="`+ trainDate.trainDataWithDate[i].trainID +`" disabled></input>`));
        tableRow.append($("<td></td>").html(`<input class='table-input' placeholder="trainName" value="`+ trainDate.trainDataWithDate[i].trainName +`" disabled></input>`));
        tableRow.append($("<td></td>").html(`<input class='table-input' placeholder="initialPlace" value="`+ trainDate.trainDataWithDate[i].initialPlace +`" disabled></input>`));
        tableRow.append($("<td></td>").html(`<input class='table-input' placeholder="destination" value="`+ trainDate.trainDataWithDate[i].destination +`" disabled></input>`));
        tableRow.append($("<td></td>").html(`<input class='table-input' placeholder="departureTime" value="`+ trainDate.trainDataWithDate[i].departureTime +`" disabled></input>`));
        tableRow.append($("<td></td>").html(`<input class='table-input' placeholder="arraivalTime" value="`+ trainDate.trainDataWithDate[i].arraivalTime +`" disabled></input>`));
        tableRow.append($("<td></td>").html(`<input class='table-input' placeholder="AC/seater" value="`+ trainDate.trainDataWithDate[i].AcSeater +" / "+ trainDate.trainDataWithDate[i].seater +`" disabled></input>`));
        tableRow.append($("<td></td>").html(`<input class='table-input' placeholder="AC/sleeper" value="`+ trainDate.trainDataWithDate[i].AcSleeper +" / "+ trainDate.trainDataWithDate[i].sleeper +`" disabled></input>`));
        tableRow.append($("<td></td>").html(`<input class='table-input' placeholder="AC/Seater fare" value="`+ trainDate.trainDataWithDate[i].AcSeaterFare +" / "+ trainDate.trainDataWithDate[i].seaterFare +`" disabled></input>`));
        tableRow.append($("<td></td>").html(`<input class='table-input' placeholder="AC/sleeperFare" value="`+ trainDate.trainDataWithDate[i].AcSleeperFare +" / "+ trainDate.trainDataWithDate[i].sleeperFare +`" disabled></input>`));
        tableRow.append($("<td></td>").html(`<input class='table-input' placeholder="bookedSeats" value="`+ trainDate.trainDataWithDate[i].bookedSeats +`" disabled></input>`));
        tableRow.append($("<td></td>").html(`<input class='table-input' placeholder="availableSeats" value="`+ trainDate.trainDataWithDate[i].availableSeats +`" disabled></input>`));
        tableRow.append($("<td></td>").html(`<input class='table-input' placeholder="bookedCoach" value="`+ trainDate.trainDataWithDate[i].container +`" disabled></input>`));
        $('#all-trains-ByDate').append(tableRow);
    }
}
function getSelectedTrain(id){
console.log(id);
var trainDate;
var req = new XMLHttpRequest();
req.onreadystatechange=function(){
    if(this.readyState==4&&this.status==200){
       
        trainDate=JSON.parse(this.response);
        console.log(trainDate);
       viewTrainsByDate(trainDate);
      
        
    }    
}
req.open("POST", "http://localhost:8080/TicketBooking1/trainDataWithDate", true);
req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
req.send("getTrain="+id);
}
function viewTrainswithDate(){

}
function deleteTableData(tag, trainID){
    
    var settings = {
        "url": "http://localhost:8080/TicketBooking1/deleteTrain?trainID=" + trainID,
        "method": "GET",
        "timeout": 0,
      };
      
      $.ajax(settings).done(function(){
        tag.parentNode.parentNode.parentNode.remove();
      });
}
let editableID='';
function editTableData(tag, trainID){
    if($('#updateTheTrainData').length){
        $('#updateTheTrainData').click();
    }
   
    console.log(previousUpdateData);
    editableID = trainID;
    let inputsTags = tag.parentNode.parentNode.parentNode.children;
    previousUpdateData=[];
    for (var i = 0; i < inputsTags.length; i++) {
        inputsTags[i].children[0].disabled  = false;
        previousUpdateData.push(inputsTags[i].children[0].value);
    }
   
    
    inputsTags[0].children[0].disabled  = true;
    
    tag.parentNode.innerHTML = `<i id="updateTheTrainData" onclick="updateTheData(this,`+trainID+`)" class="fa fa-check" aria-hidden="true"></i>`;
    var settings = {
        "url": "http://localhost:8080/TicketBooking1/getAllTheStopsData?trainID=" + trainID,
        "method": "POST",
        "timeout": 0,
    };
     previousPlaceUpdateData=[];
        previousTimeUpdatedata=[];
    $.ajax(settings).done(function (response) {
        var trainsStopsArray = JSON.parse(response);
        $("#stops-editor").append(`<h3>Stops</h3>    `)
        trainsStopsArray.forEach(function(key){
            
            Object.keys(key).forEach(function(key1){
                previousPlaceUpdateData.push(key1);
                previousTimeUpdatedata.push(key[key1]);
                $("#stops-editor").append(`
                <div class="stop-editor" style="display:flex;">
                    <input type="text" value="`+key1+`"/>
                    <input style="margin-left: 10px;" type="text" value="` + key[key1] +`"/>
                    <i onclick="removeTheStopTag(this)" style="margin-top: 20px; margin-left: 10px;" class="fa fa-window-close" aria-hidden="true"></i>
                </div>          
                `)
            })
        })
        $("#stops-editor").append(`
            
            <i onclick="addAStop()" id="tagAdder" class="fa fa-plus" aria-hidden="true"></i>
        `)
    });
    
}
function addAStop(){
   
    $("#tagAdder").remove();
    $("#stops-editor").append(`
        <div class="stop-editor" style="display:flex;">
            <input type="text" value=""/>
            <input style="margin-left: 10px;" type="text" value=""/>
            <i onclick="removeTheStopTag(this)" style="margin-top: 20px; margin-left: 10px;" class="fa fa-window-close" aria-hidden="true"></i>
        </div>          
    `)
    $("#stops-editor").append(`
            <i onclick="addAStop()" id="tagAdder" class="fa fa-plus" aria-hidden="true"></i>
    `)
}
function hourSeparate(value){
    var temp='';
    for(var i=0;i<value.length;i++){
        if(value[i]==':'){
            break;
        }
        temp+=value[i];
    }
    return Number(temp);
}
function minuteSeparate(value){
    var temp='';
    for(var i=0;i<value.length;i++){
        if(value[i]==':'){
            temp='';
            i++;
        }
        temp+=value[i];
    }
    return Number(temp);
}
function checkComparison(hour1,hour2,minutes1,minutes2){
    if(hour1<hour2){
        console.log("true");
        return true;
    }else if(hour1==hour2){
        if(minutes1<minutes2){
            console.log("true");
            return true;
        }else{
            console.log("false");
            return false;
        }
    }else{
        console.log("false");
        return false;
    }
}
function updateTheData(tag, trainID){
    var checkUpdate=false;
    let inputsTags = tag.parentNode.parentNode.parentNode.children;
    let newJson = {};
    console.log("the update method is called");
    for (var i = 0; i < inputsTags.length; i++) {
        inputsTags[i].children[0].disabled  = true;
        var newValue =  inputsTags[i].children[0].value;
        
        newJson[inputsTags[i].children[0].placeholder] = newValue;
        if(previousUpdateData[i]!=inputsTags[i].children[0].value){
            checkUpdate=true;
        }
    }
    inputsTags[11].children[0].disabled  = false;
    newJson["stops"] = []
    var count=1;
    var tempArray=[];
    $("#stops-editor").children('div').each(function(){
        var stop = {};
        console.log(this.children[0].value+"=>"+this.children[1].value);
        stop[this.children[0].value] = this.children[1].value;
        hourSeparate(this.children[1].value);
        minuteSeparate(this.children[1].value);
        if((previousPlaceUpdateData[count-1])!=(this.children[0].value)||previousTimeUpdatedata[count-1]!=this.children[1].value){
            checkUpdate=true;
        }
        stop['order']=count++;
        console.log("the stop is ");
       
        
        tempArray.push(stop);
        console.log("the tempArray is ");
        console.log(tempArray);
    });
    var key1;
    var key2
    console.log("the temp array is ");
    console.log(tempArray)
    for(var i=0;i<tempArray.length;i++){      
        for(var j=i+1;j<tempArray.length;j++){
         key1= Object.keys(tempArray[i]);
         key2=Object.keys(tempArray[j]);
         if(!checkComparison(hourSeparate(tempArray[i][key1[0]]),hourSeparate(tempArray[j][key2[0]]),minuteSeparate(tempArray[i][key1[0]]),minuteSeparate(tempArray[j][key2[0]]))){
                var temp=tempArray[i];
                tempArray[i]=tempArray[j];
                tempArray[j]=temp;
            }
        }
    }
    console.log("the  converted temp is");
    
    for(var i=0;i<tempArray.length;i++){
        tempArray[i]['order']=i+1;
    }
    console.log(tempArray);
    newJson["stops"]=tempArray;
    
   
    var url;
    var data;
    console.log("the new json is ");
    console.log(newJson)
    console.log(trainID+"=>"+editableID);
    if(trainID==newJson.trainID){
        console.log("the train id is true");
        url="http://localhost:8080/TicketBooking1/updateTrainData";
        data= "newData=" + JSON.stringify(newJson);
    }

    var settings = {
        "url": url,
        "method": "POST",
        "timeout": 0,
        "data" : data,
        async: false
      };
      if(checkUpdate){
        console.log("new json file is ")
      $.ajax(settings).done(function (response) {
        console.log(newJson);
        tag.parentNode.innerHTML = `<i onclick="editTableData(this, `+ trainID +`)" class='fa fa-edit edit-button'></i><span></span><i onclick="deleteTableData(this, `+ trainID +`)" class="fa fa-trash edit-button" aria-hidden="true"></i>`;
        $("#stops-editor").html("");
        alert("the train updated successfully");
      });
    }else{
        alert("the train data not updated");
        tag.parentNode.innerHTML = `<i onclick="editTableData(this, `+ trainID +`)" class='fa fa-edit edit-button'></i><span></span><i onclick="deleteTableData(this, `+ trainID +`)" class="fa fa-trash edit-button" aria-hidden="true"></i>`;
        $("#stops-editor").html("");
    }
    }

// --------------------------------------------------------ajax call----------------------
function trainRegistration(){
    var check=true;
    for(var i=0;i<trainsData.trainData.length;i++){
        if(trainsData.trainData[i].trainID==$("#trainId").val()||$("#trainId").val()==''){
            console.log(trainsData.trainData[i].trainID+"=>"+$("#trainId").val());
            check=false;
        }
    }
    
    if(check){
    console.log("here");
    var stops = [];
    var childcount=0;
    var checkCount=false;
    
    $('.stops').children('div').each(function () {
     
        if(this.childNodes[1].value=='' || this.childNodes[3].value==''){
            if(childcount==0){
                checkCount=true;
            }
        }else{
            console.log("counting");
            childcount++;
        stops.push({
            "name" : this.childNodes[1].value,
            "time" : this.childNodes[3].value,
        })
    }
    });
    var tableCheck=true;
    if(childcount<=1){
        console.log("its true");
       tableCheck=false; 
    }
    
    if($("#trainName").val()==''||
    $("#ac_seater_count").val()==''||
    $("#ac_seater_fare").val()==''||
    $("#seater_count").val()==''||
    $("#seater_fare").val()==''||
    $("#ac_sleeper_count").val()==''||
    $("#ac_sleeper_fare").val()==''||
    $("#seater_count").val()==''||
    $("#sleeper_fare").val()==''){
        tableCheck=false;
    }else{
    var theTrainData = {
        "trainId" : $("#trainId").val(),
        "trainName" : $("#trainName").val(),
        "seater" : {
            "ac" : {
                "compartment" : $("#ac_seater_count").val(),
                "fare" : $("#ac_seater_fare").val()
            },
            "normal" : {
                "compartment" : $("#seater_count").val(),
                "fare" : $("#seater_fare").val()
            }
        },
        "sleeper" : {
            "ac" : {
                "compartment" : $("#ac_sleeper_count").val(),
                "fare" : $("#ac_sleeper_fare").val()
            },
            "normal" : {
                "compartment" : $("#sleeper_count").val(),
                "fare" : $("#sleeper_fare").val()
            }
        }
    }
 
    if(childcount>=1){
        if(checkCount){
            console.log("true");
            var destination=stops[stops.length-1];
        console.log("the place values are"+destination);
        stops=stops.slice(0,stops.length-1);
        stops.push(destination);

        theTrainData.stops = stops;
        var orderCount=1;
        for(var i=0;i<theTrainData.stops.length;i++){
            theTrainData.stops[i].order=(orderCount++);
        }
        console.log(theTrainData.stops);
        }else{
            console.log("false");
        var destination=stops[0];
        
        stops=stops.slice(1,stops.length);
        stops.push(destination);

        theTrainData.stops = stops;
        var orderCount=1;
        for(var i=0;i<theTrainData.stops.length;i++){
            theTrainData.stops[i].order=(orderCount++);
        }
        console.log(theTrainData.stops);
        }
    }
}

    if(!tableCheck){
        if(childcount<=1)
            alert("the stops list should be atleast 2 stops");
        else
            alert("Please, Fill up the Rows correctly");
    }else{
        var req = new XMLHttpRequest();
        req.onreadystatechange=function(){
                if(this.readyState==4 && this.status==200){
                   alert("The train Registered sucessfully");
                   $("#trainId").val("");
                   $("#trainName").val("");
                }    
            }
        req.open("POST", "http://localhost:8080/TicketBooking1/registerTrain", true);
        req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        req.send("addTrain="+JSON.stringify(theTrainData));
        console.log("the table values are");
        console.log(theTrainData);
    }
}else{
    if($("#trainId").val()=='')
    alert("the id is empty");
    else{
        alert("the id is registered with another train");
    }
}
defaultLoad();
}
function setHeader(){
    var tableRow = $("<tr></tr>");
                tableRow.append(" <th>Train Number</th>");
                tableRow.append(" <th>Date</th>");
                tableRow.append(" <th>Passenger Name</th>");
                tableRow.append("<th>Fare</th>");
                tableRow.append("<th>Seat No</th>");
                tableRow.append(" <th>Booked By</th>");
                tableRow.append("<th>Coach</th>");
                $('#all-boookings').append(tableRow);
}

function queryBookings() {
    console.log($("#date-input").val());
    if($("#date-input").val()==''&&$("#trainID-input").val()==''){
        var settings = {
            "url": "http://localhost:8080/TicketBooking1/queryAllBookings",
            "method": "POST",
            "timeout": 0,
        };
        $.ajax(settings).done(function (response) {
           console.log("the method is called");
          
            response = JSON.parse(response);
            console.log(response);
            $('#all-boookings').html("");
            setHeader();
            for (var i in response){
                var tableRow = $("<tr></tr>");
                tableRow.append($("<td></td>").html(response[i].trainId));
                tableRow.append($("<td></td>").html(response[i].date));
                tableRow.append($("<td></td>").html(response[i].passengerName));
                tableRow.append($("<td></td>").html(response[i].fare));
                tableRow.append($("<td></td>").html(response[i].seatName));
                tableRow.append($("<td></td>").html(response[i].bookedBy));
                tableRow.append($("<td></td>").html(response[i].coach));
                $('#all-boookings').append(tableRow);
            }
        });
    }else if($("#trainID-input").val()==''){
        var settings = {
            "url": "http://localhost:8080/TicketBooking1/queryBookingByDate?date="+ $("#date-input").val(),
            "method": "POST",
            "timeout": 0,
        };
        $.ajax(settings).done(function (response) {
            response = JSON.parse(response);
            console.log(response);
            $('#all-boookings').html("");
            setHeader();
            for (var i in response){
                var tableRow = $("<tr class='demo'></tr>");
                tableRow.append($("<td></td>").html(response[i].trainId));
                tableRow.append($("<td></td>").html(response[i].date));
                tableRow.append($("<td></td>").html(response[i].passengerName));
                tableRow.append($("<td></td>").html(response[i].fare));
                tableRow.append($("<td></td>").html(response[i].seatName));
                tableRow.append($("<td></td>").html(response[i].bookedBy));
                tableRow.append($("<td></td>").html(response[i].coach));
                $('#all-boookings').append(tableRow);
            }
        });
    }else if($("#date-input").val()==''){
        var settings = {
            "url": "http://localhost:8080/TicketBooking1/queryBookingByid?id="+$("#trainID-input").val(),
            "method": "POST",
            "timeout": 0,
        };
        $.ajax(settings).done(function (response) {
            response = JSON.parse(response);
            console.log(response);
            $('#all-boookings').html("");
            setHeader();
            for (var i in response){
                var tableRow = $("<tr class='demo'></tr>");
                tableRow.append($("<td></td>").html(response[i].trainId));
                tableRow.append($("<td></td>").html(response[i].date));
                tableRow.append($("<td></td>").html(response[i].passengerName));
                tableRow.append($("<td></td>").html(response[i].fare));
                tableRow.append($("<td></td>").html(response[i].seatName));
                tableRow.append($("<td></td>").html(response[i].bookedBy));
                tableRow.append($("<td></td>").html(response[i].coach));
                $('#all-boookings').append(tableRow);
            }
        });
    }else{
        var settings = {
            "url": "http://localhost:8080/TicketBooking1/queryBookings?trainID="+$("#trainID-input").val()+"&date=" + $("#date-input").val(),
            "method": "POST",
            "timeout": 0,
        };
        $.ajax(settings).done(function (response) {
            response = JSON.parse(response);
            console.log(response);
            $('#all-boookings').html("");
            setHeader();
            for (var i in response){
                var tableRow = $("<tr class='demo'></tr>");
                tableRow.append($("<td></td>").html(response[i].trainId));
                tableRow.append($("<td></td>").html(response[i].date));
                tableRow.append($("<td></td>").html(response[i].passengerName));
                tableRow.append($("<td></td>").html(response[i].fare));
                tableRow.append($("<td></td>").html(response[i].seatName));
                tableRow.append($("<td></td>").html(response[i].bookedBy));
                tableRow.append($("<td></td>").html(response[i].coach));
                $('#all-boookings').append(tableRow);
            }
        });
    }
    console.log($("#trainID-input").val());
   
}
defaultLoad();
