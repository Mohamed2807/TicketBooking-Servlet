var req= new XMLHttpRequest();
function checkRecords(){
   
    req.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
        console.log("the record response is ")
          console.log(this.response);
    }
};
    req.open("POST", "http://localhost:8080/TicketBooking1/checkForRecord", true);
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    req.send();
}
function setRecords(){
    var recordData={}
    recordData['status']='yes';
    recordData['trainID']=trainId;
    recordData['container']=currentSelectedContainer;
    recordData['booked']=bookedSeats;
    recordData['date']=currentDate;

    req.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log("the record response is ")
              console.log(this.response);
              
             
        }
    };
        req.open("POST", "http://localhost:8080/TicketBooking1/setRecord", true);
        req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        req.send("record="+JSON.stringify(recordData));
}
function getRecords(){
    var doAction;
    var bookSeatAuto;
    req.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log("the record response is -------------------------------------")
              doAction=JSON.parse(this.response);
              console.log("the recorded trainID is "+doAction.trainID)
              if(doAction.status=='yes'){
              inputDate(doAction.date).then(()=>{
                showOptions(doAction.trainID);
              
              trainId=doAction.trainID;
              bookSeatAuto=doAction.booked;
              var trainNames=Object.keys(bookSeatAuto);
              for(var i=0;i<trainNames.length;i++){
                console.log(trainNames[i]+"----------------------------------------------------------")
                showSeat(trainNames[i]);
                for(var j=0;j<bookSeatAuto[trainNames[i]].length;j++){
                  bookSeats(bookSeatAuto[trainNames[i]][j]['coach']);
                }
                paymentBox();
            }
        
              });
              
            }

        }
    };
        req.open("POST", "http://localhost:8080/TicketBooking1/getRecord", true);
        req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        req.send();
}

