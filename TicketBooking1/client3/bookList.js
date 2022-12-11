let date; 
function getHistory(){
    var settings = {
        "url": "http://localhost:8080/TicketBooking1/getAllHistory",
        "method": "POST",
        "timeout": 0,
      };
      
      $.ajax(settings).done(function (response) {
        let history;
        var count=1;
        response = JSON.parse(response);
        console.log(response);
         $("#history-container").html("");
        for (let key in response){
            history = response[key];
            $("#history-container").append(
                $('<div></div>').attr("class","content")
            .append(
                $("<div></div>").attr("id","dateAndPay"+(count++)).append(
                    $("<span></span>").html("date :"+history.date)
                ).append(
                    $("<span></span").html("tickets :"+history.fare+"rs").css("float","right")
                )
            ).append(
                $("<div></div>").append(
                    $("<h4></h4>").html("train id :"+history.trainId)
                )
            ).append(
                $("<div></div>").append(
                    $("<span></span>").append(
                        $("<i>From</i>")
                    ).append(
                        $("<i></i>").html(history.from)
                    )
                ).append(
                    $("<span></span>").append(
                    ).append(
                        $("<i></i>").html("to :"+history.to).css("float","right")
                    )
                ).append(
                    $("<div></div>").append(
                        $("<span></span>").html("passenger name :"+history.passengerName)
                    ).append(
                        $("<span></span>").html("seat name"+history.coach+"/"+history.seatName).css("float","right")
                    )
                )
            )
            )
            console.log($("#dateAndPay"+count));
            if(checkDate(history.date)){
                console.log("the count is "+count);
                document.getElementById("dateAndPay"+(count-1)).innerHTML+=
                    "<span><button onclick=cancelTicket('"+history.trainId+"','"+history.date+"','"+history.seatName+"','"+history.coach+"')>cancel tickets</button></span>";
            
            }   
            }
        })
    }$(document).ready(function () {
        getHistory();
    });
    
      function checkDate(inputdate){
       var  officialDate = new Date();
        var givenDate=new Date(inputdate);
        var  getDate2=givenDate.toISOString();
        getDate2 = getDate2.slice(0,10);
        var getDate=  officialDate.toISOString();
        getDate=getDate.slice(0,10);
        
        if(officialDate.getFullYear()>givenDate.getFullYear()){
            return false;
        }else if(officialDate.getFullYear()==givenDate.getFullYear()){
            if(officialDate.getMonth()>givenDate.getMonth()){
                return false;
            }else if(officialDate.getMonth()==givenDate.getMonth()){
                if(officialDate.getDate()>givenDate.getDate()){
                    return false;
                 }else{
                    return true;
                 }
            }else return true;
        }else return true;
          
    }
      function cancelTicket(id,date,seatName,coach){
        
                var req= new XMLHttpRequest();
        req.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
            console.log(this.responseText);
            console.log("the history called");
            getHistory();
            }
        };
        req.open("POST", "http://localhost:8080/TicketBooking1/deleteSeats", true);
        req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        req.send("trainId="+id+"&seat="+seatName+"&date="+date+"&coach="+coach);
        
      }