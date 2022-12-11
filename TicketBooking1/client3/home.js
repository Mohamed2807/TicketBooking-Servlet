

let placeData={};
let places=[];

var inputFromList=[];
var inputToList=[];

var req = new XMLHttpRequest();
req.onreadystatechange=function(){
        if(this.readyState==4&&this.status==200){
        	placeData=JSON.parse(this.response);
			console.log(placeData);
           console.log(placeData.stopsData);
            for(var i=0;i<placeData.stopsData.length;i++){
            	places.push(placeData.stopsData[i].stop);
            }
			console.log(placeData);
            console.log(places);
            places=[...new Set(places)];
            console.log(places);
			
			if(placeData.user=='null'){
				$("#registration").show();
				$("#logout").hide();
				$("#book-history").hide();
			}else{
				$("#registration").hide();
				$("#logout").show();
				$("#book-history").show();
			}
		 //  initialPlace=[...new Set(json.initialPlaces)];
		  // destinedPlace=[...new Set(json.destinationPlaces)];
		  
        }    
    }
req.open("POST", "http://localhost:8080/TicketBooking1/getArea", true);
req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
req.send();

$("#input-from-list").hide();
$("#input-to-list").hide();


$(".inputForm").focusin(function(){
    console.log("called");
	$("#input-from-list").show();
	$(this).prev("label").addClass("inputLabel-focusIn");
});
// ---------------------------------------------------------------
$("#input-label-from").focusin(function(){
	console.log("the method is called ");
	$("#input-from-list").show();
	$("#input-to-list").hide();
	initialChoice();
});
$("#input-label-to").focusin(function(){
	$("#input-from-list").hide();
	$("#input-to-list").show();
	destinationChoice();
});
$("#input-label-from").focusout(function(){
	$("#input-from-list").hide();
});
$("#input-label-to").focusout(function(){
	$("#input-to-list").hide();
});

// --------------------------------------------------------------------------
$(".inputForm").focusout(function(){
	$("#input-from-list").hide();
	var inputForm = $(this).val();
	if (inputForm.length > 0) {
		$(this).prev("label").addClass("inputLabel-focusIn");
	} else {
		$(this).prev("label").removeClass("inputLabel-focusIn");
	}
});
$("#input-label-onward-date, #input-label-return-date").focusin(function(){
	$("#input-from-list").hide();
	$("#input-to-list").hide();
	$(this).attr("type", "date");
});
$("#input-label-onward-date, #input-label-return-date").focusout(function(){
	var inputFormDate = $(this).val();

	if (inputFormDate.length > 0) {
		$(this).attr("type", "date");
	} else {
		$(this).attr("type", "text");
	}
});
// --------------------------json file--------------------------
function randomInitial(){
	console.log("random initila method is called");
	inputFromList = [
		{ fromLocation : "chennai"},
		{ fromLocation : "madurai"},
		{ fromLocation : "coimbatore"},
	];
	var optionFrom;
var inputFromDatalist = $("#input-from-list");
$(".initialchild").remove();
for (var i = 0; i < inputFromList.length; i++) {
	optionFrom = $("<div class='initialchild'></div>");
	optionFrom.text( inputFromList[i].fromLocation);
	inputFromDatalist.append(optionFrom);
}
}
function randomDestination(){
	console.log("random destination  method is called");
	 inputToList = [
		{ toLocation : "coimbatore"},
		{ toLocation : "madurai"},
		{ toLocation : "chennai"}
	];
	var optionTo;
var inputToDatalist = $("#input-to-list");
$(".destinationchild").remove();
for (var i = 0; i < inputToList.length; i++) {
	optionTo = $("<div class='destinationchild'></div>");
	optionTo.text(inputToList[i].toLocation);
	inputToDatalist.append(optionTo);
}
}

function initialChoice(){
	
	var input=$('#input-label-from').val();
	var count=0;
	var check=true;
	if (input.length > 0) {
		inputFromList=[];
		for(var i=0;i<places.length;i++){
			count=0;
				if(places[i].length>=input.length){
					for(var j=0;j<input.length;j++){
						if(places[i][j].toLowerCase()==input[j].toLowerCase()){
							count++;
							check=true;
							console.log(places[i][j]+"=>"+input[j]);
						}else{
							check=false;
						}
						if(count==(input.length)&&check){
							console.log("got pushed");
							inputFromList.push(places[i]);
						}
					}
			}
		}
		
		var optionFrom;
		var inputFromDatalist = $("#input-from-list");
		$(".initialchild").remove();
		for (var i = 0; i < inputFromList.length; i++) {
			optionFrom = $("<div class='initialchild' id='"+(inputFromList[i])+"'onmouseover=putData('"+(inputFromList[i])+"','input-label-from')></div>");
			optionFrom.text(inputFromList[i]);
			inputFromDatalist.append(optionFrom);
		}
		console.log(inputFromList);
		
	}else{
		//randomInitial();
	}
}
function destinationChoice(){
	var input=$('#input-label-to').val();
	var count=0;
	var check=true;
	if (input.length > 0) {
		
		inputFromList=[];
		for(var i=0;i<places.length;i++){
			count=0;
			if(places[i].length>=input.length){
				for(var j=0;j<input.length;j++){
					if(places[i][j].toLowerCase()==input[j].toLowerCase()){
						count++;
						check=true;
						console.log(places[i][j]+"=>"+input[j]);
					}else{
						check=false;
					}
					if(count==(input.length)&&check){
						console.log("got pushed");
						inputFromList.push(places[i]);
					}
				}
			}
		}
		
		var optionFrom;
		var inputFromDatalist = $("#input-to-list");
		$(".destinationchild").remove();
		for (var i = 0; i < inputFromList.length; i++) {
			optionFrom = $("<div class='destinationchild' id='"+(inputFromList[i])+"'onmouseover=putData('"+(inputFromList[i])+"','input-label-to')></div>");
			optionFrom.text(inputFromList[i]);
			inputFromDatalist.append(optionFrom);
		}
		console.log(inputFromList);
		console.log("hey");
		
	}else{
		//randomDestination();
	}
}
function putData(fromId,toId){
	console.log("callled");
	$("#"+toId).val($('#'+fromId).text());
}
$("#input-label-from").change(function(){
	var dataSelect = $(this).val();
	console.log(dataSelect);

	$("#input-to-list").find("option[value='"+dataSelect+"']").remove();
});

//----------------------------------------------Ajax call----------------------------------------------------

