


function callName(val1,val2){
    var name =document.getElementById(val1).value;
    console.log(name);
    if(name.length>=8){
        document.getElementById(val2).innerHTML="";
    }
    else{
        document.getElementById(val2).innerHTML="the password more than 8 character ";
    }   
}

 function connect(url,param,data,id,role){
 var response;
     
     var req = new XMLHttpRequest();

 	req.onreadystatechange=function(){
 		 if(this.readyState==4&&this.status==200){
           console.log("testing");
           
           if(this.responseText.trim()=="true"){
        	   if(role=='signUp'){
                console.log('if signup'+role);
               document.getElementById(id).innerHTML="the mail already exist";
               document.getElementById("signUp-button").disabled = true;
              
            }
        	   else{
                console.log('if signin'+role);
                document.getElementById("signIn-button").disabled = false;
        		   document.getElementById(id).innerHTML="";}
        	   }
            else{
                if(role=='signUp'){
                	console.log("the response  is "+this.responseText);
                    console.log('else signup'+role);
                    document.getElementById("signUp-button").disabled = false;
                    document.getElementById(id).innerHTML="";
                }
                    else{
                        console.log('else signin'+role);
                        document.getElementById("signIn-button").disabled = true;
                        document.getElementById(id).innerHTML="the name doesn't exist";}
                    }
           }
          
        	   
           }
 		
 	
 	
   console.log("http://localhost:8080/TicketBooking1/"+url+"?"+param+"="+data);
 	req.open("GET", "http://localhost:8080/TicketBooking1/"+url+"?"+param+"="+data, true);
 	//req.open("POST", "http://localhost:8080/Ajax-3/demo", true);
	req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
 	req.send();

 
}

function signUpcheckEmail(){
    let pattern = '.+[a-z]{4}.*.@.[a-z]{3}.*.[.].[a-z]{2}';
	var email = document.getElementById("signUp-mail").value;
	console.log(email);
    if(email=='change'){
                document.getElementById("signUp-name").required=false;
                document.getElementById("signUp-pass").required=false;

        document.getElementById("signUp-nameMsg2").innerHTML="";
        document.getElementById("signUp-button").disabled = false;
    }
    else{
        if(email.match(pattern)){
            document.getElementById("signUp-name").required=true;
                document.getElementById("signUp-pass").required=true;
            document.getElementById("signUp-nameMsg2").innerHTML="loading";
            connect('checkMail','mail',email,'signUp-nameMsg2','signUp');
        }else{
            document.getElementById("signUp-name").required=true;
                document.getElementById("signUp-pass").required=true;
            document.getElementById("signUp-button").disabled = true;
            document.getElementById("signUp-nameMsg2").innerHTML="the mail should be XXXXX@XXXX.XXX format";
        }
}
	
}
function signIncheckEmail(){
    let pattern = '.+[a-z]{4}.*.@.[a-z]{3}.*.[.].+[a-z]{1}';
	var email = document.getElementById("signIn-mail").value;
	
    
	if(email.match(pattern)){
        document.getElementById("signIn-nameMsg2").innerHTML="loading";
        connect('checkMail','mail',email,'signIn-nameMsg2','signIn');
    }else{
        console.log("the msg sent");
        document.getElementById("signIn-button").disabled = true;
        document.getElementById("signIn-nameMsg2").innerHTML="the mail should be XXXXX@XXXX.XXX format";
       
    }
	
}
//----------------------------------------------------------------------------
$(document).ready(function(){
      $("#signUp").hide();
      $("#signIn").show();
  });
function callSignIN(){
          $("#signIn").show();
          $("#signUp").hide();
}
function callSignUp(){
    $("#signUp").show();
    $("#signIn").hide();
    document.getElementById("signIn-button").disabled = true;
	document.getElementById("signUp-button").disabled = true;
}
