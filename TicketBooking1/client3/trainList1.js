var searchedBoardingData;
function getTrainsData(){
    return new Promise((resolve)=>{
      var req= new XMLHttpRequest();
      req.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log(JSON.parse(this.response))
            trainList = JSON.parse(this.response);
            console.log("the json is ");
            console.log(trainList);
            console.log(trainList.boarding);
            console.log("the user is "+trainList['user']);
            if(trainList['user']=='null'){
              $("#register").show();
              $("#logout").hide();
              $("#wishList").hide();
              
            }else{
              $("#register").hide();
              $("#logout").show();
              $("#wishList").show();
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
            console.log("the users is "+trainList['user'])
            if(trainList['user']!='null'){
              console.log("the user is "+trainList.user)
                getRecords();
            }
          resolve();
        }
      };
      req.open("POST", "http://localhost:8080/TicketBooking1/getTrainListData", true);
      req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
      req.send();
      });
}

function getSeatList(){
    return new Promise((resolve)=>{
        
      
      var req= new XMLHttpRequest();
      req.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log(JSON.parse(this.response))
            seatListData=JSON.parse(this.response);
            resolve();
        }
      };
      req.open("POST", "http://localhost:8080/TicketBooking1/getSeatList", true);
      req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
      req.send();
      });
}
function getBoardingData(){
    return new Promise((resolve)=>{
       
      
      var req= new XMLHttpRequest();
      req.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log(JSON.parse(this.response))
            searchedBoardingData=JSON.parse(this.response);
            generateTrain(trainList.trainData);

            resolve();
        }
      };
      req.open("POST", "http://localhost:8080/TicketBooking1/getBoardingData", true);
      req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
      req.send();
      });
}
async function callDefault(){
     await getTrainsData();
    console.log("hey1");
    await getBoardingData();
    console.log("hey2");
    await getSeatList();
    console.log("hey3");
}
callDefault();
console.log("this file is readed");
function getPlacesTime(id,place){

  console.log("the place is "+place);
var trainIdList;
var placeList;
  for(var i=0;i<searchedBoardingData.length;i++){
      console.log(searchedBoardingData[i]);
          trainIdList=  Object.keys(searchedBoardingData[i])
          console.log("the trainIdlist is ");
          console.log(trainIdList);
          for(var j=0;j<trainIdList.length;j++){
              if(trainIdList[j]==id){
              console.log("the founded trainlist is ");
              console.log(searchedBoardingData[i][trainIdList[j]]);
              train=searchedBoardingData[i][trainIdList[j]].find(train=>train.stop.toLowerCase()==place.toLowerCase());
              console.log("the train stop is "+train.stop);
              console.log("the train time is "+train.time);
              return train.time;
          }
      }
  }
}

