 var app=angular.module("AutoTextSummarizer",["ngRoute", 'ngCookies']);

 app.run(['$rootScope', function($rootScope) {
        $rootScope.API_URL = 'http://localhost:8080';
    }]);
 
 //for Download
 app.config(['$compileProvider',
	    function ($compileProvider) {
	        $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|tel|file|blob):/);
		}]);

 app.config(function($routeProvider, $locationProvider) {
        $routeProvider
        .when("/", {
            templateUrl : "views/home.html"
        })
        .when("/home", {
            templateUrl : "views/home.html"
        })
        .when("/history", {
            templateUrl : "views/history.html"
        })
         .when("/profile", {
            templateUrl : "views/profile.html"
        })
        //$locationProvider.hashPrefix('');
        $locationProvider.html5Mode(true);
    });


  app.controller("AtsController", ['$scope', '$rootScope', '$http', '$cookies', '$cookieStore', function($scope, $rootScope,$http,$cookies,$cookieStore, $timeout){
        $rootScope.resetSession = function(){
	        $rootScope.loggedIn = false;
	        $rootScope.userId = null;
	        $rootScope.user = {}
	        $cookieStore.put('loggedIn', $rootScope.loggedIn);
	        $cookieStore.put('userId', $rootScope.userId);
	        $cookieStore.put('user', $rootScope.user);
	    }
        
        $rootScope.today = new Date();
        $rootScope.historyLen=null;
        
	   // Cookies 
	    $rootScope.loggedIn = $cookieStore.get('loggedIn');
	    $rootScope.userId = $cookieStore.get('userId');
	    $rootScope.user = $cookieStore.get('user');
	
	    console.log('login', $rootScope.loggedIn);
	    
	    if(typeof($rootScope.loggedIn) === 'undefined'||$rootScope.loggedIn==false){
	        $rootScope.resetSession()
	
	    }else{
	    	$http.get($rootScope.API_URL+'/ats/user/'+$rootScope.userId).then(function(response){
	                var id = response.data.id;
	                if (typeof(id) !== 'undefined'){
	                    $rootScope.user = response.data;
	                    $rootScope.loggedIn = true;
	                    $rootScope.userId = response.data.id;
	                    $cookieStore.put('loggedIn', $rootScope.loggedIn);
	                    $cookieStore.put('userId', $rootScope.userId);
	                    $cookieStore.put('user', $rootScope.user);
	                }else{
	                }
	        });
	
	    }
     $scope.logout=function()
      {
    	 
         	var retVal = confirm("Are You Sure to Logout ?");
                 if( retVal == true ) {
                	 $rootScope.resetSession();
                	 window.location.replace("http://localhost:8080/");
                     return true;
                 } else {
                    $scope.loggedIn=true;
                    return false;
                 }
      }
   }]);

  var loginController=function($scope,$http,$rootScope, $cookies, $cookieStore){
	    $scope.user = {};
	    console.log('Hello Log in controller!!!');
	    $scope.userLogin=function(user)
	    {
	  
	      console.log('Hello Login');
	      var user=$scope.user;
	      console.log(user);
	       $http({
	          url:'http://127.0.0.1:8080/ats/login',
	          method: "POST",
	          data:user
	        }).then(function(response){
	                
	                console.log(response.data);
	                var mydata = response.data;
	                if(mydata.userEmail==null)
	                {
	                  swal("Sorry!", "Incorrect username or Password !", "error");
	                }
	                else
	                { 
	                    var userid = mydata.id;
		                console.log('logged in user : ' + userid );
		                $rootScope.userId = userid;
		                $rootScope.loggedIn = true;
		                $cookieStore.put('loggedIn', $rootScope.loggedIn);   
		                $cookieStore.put('userId', $rootScope.userId);
		                $rootScope.helloUser=mydata.userFullname;	
		                window.location.href = '/home';
		                swal("Welcome "+$rootScope.helloUser+"!", "Logging in successful!!", "success");
		                
		             }    
	           }, 
	        function(response){
	          swal("Oops!", "Error In Logging In!"+response, "warning");
	              });

	    }

	     $scope.forgotPassword=function()
	      {
	        alert($scope.forgotEmail);
	        var s='http://127.0.0.1:8080/ats/forgotpassword/'+$scope.forgotEmail;
	        alert(s);
	        $http({
	            url:s,
	            method: "GET",
	        }).then(function(response){
	            var mydata = response.data;
	            alert(mydata);
	            swal("Hello", mydata, "success");
	           }, 
	        function(response){
	        	   alert(response.data);
	          //swal("Oops!", "Error In Sending Email!"+response, "warning");
	              });
	          }
	  };

  var signupController =  function($scope, $http){
      $scope.user = {};
      $scope.errorMsz="";
      console.log('Hello Sign Up!!!');
      
      $scope.validatePassword = function(pw1, pw2){
    	  	if(pw1 != pw2){
				return "Two passwords not matched";
    	  		}

			if(pw1.length < 6){
				return "Password must be at least 6 characters";
				}

			return true;
		}

      
      $scope.userSignup = function(){
    	  
        var user = $scope.user;
         console.log("Hello Signup");
          console.log(user);
          
          var v = $scope.validatePassword(user.userPass,user.userConpass);

			if(v != true){
				alert(v)
				return;
			}

        $http({
          url: 'http://127.0.0.1:8080/ats/register',
          method: "POST",
          data: user
        }).then(function(response){
          var data=response.data;
          if(data==true)
          {
        	  swal("Welcome "+user.userFullname+"!", "Registration Successfull!!", "success");
              $scope.loggedIn=true;
           }
           else{
        	   swal("Sorry!", "Registration Failed!"+response, "error");

           }
        }, 
        function(response){
        	swal("Error!", "Registration Failed!"+response, "warning");

        });
      }
    };

    var summaryController =  function($scope,$rootScope,$http,$cookies,$cookieStore){

      $scope.source={};  
      $scope.getSourceContent=function()
      {
        var source=$scope.source;
       	$scope.source.userActive=$rootScope.loggedIn;
        $scope.source.userId=$rootScope.userId;
        console.log(source);   
        if(source.sourceUrl==null){
        	$scope.urlError="Please enter the url first!";
        	return false;
        }
        $http({
          url: 'http://127.0.0.1:8080/ats/getcontent',
          method: "POST",
          data: source
        }).then(function(response){
          console.log(response.data);
          var data=response.data;
          console.log(response);          
          if(data.sourceText==null){
        	  swal("Problem!", "Please enter the valid url", "error");
        	
	          }
          else
        	  {
	          	source.sourceText=data.sourceText;
        	  }
        }, 
        function(response){
        	swal("Error!", "Problem in parsing URL!!!", "error");
        });
      }


      $scope.summarizer=function()
      {   
        var source=$scope.source;
        $scope.source.userActive=$rootScope.loggedIn; //for checking user logged in or not
        $scope.source.userId=$rootScope.userId;
        if(source.sourceText==null){
        	 $scope.sourceError="Please enter the source first!";
        	 return false;
        }
        
        if(source.sentenceNumber==null ||source.sentenceNumber==0)
        	{
        	 $scope.numberError="Please enter the number!";
        	 return false;
        	}
        $http({
          url: 'http://127.0.0.1:8080/ats/summary',
          method: "POST",
          data: source
        }).then(function(response){
          var data=response.data;
          console.log(response);  

          $rootScope.mysummary=data.finalSummary;
          $scope.sumLength=(data.finalSummary).length;

          var text="",i;
          for (i = 0; i <($scope.sumLength); i++) {
            text +=  ($rootScope.mysummary[i])+"\n\n";
          }
          $rootScope.myFinal=text;
          $rootScope.isSummary=true;

        }, 
        function(response){
          swal("Oops Sorry!", "Problem in Generating Summary!", "warning");

        });
      } 
      
      $scope.copyText=function(){
    	  var copiedText= $rootScope.myFinal;
    	  copiedText.select();
    	  document.execCommand("copy");
    	  alert("text copied");
      }
      
      $scope.fileChanged = function() { 
    	  var reader = new FileReader();
    	  reader.onload = function(e) {
    	      $scope.$apply(function() {
    	          $scope.source.sourceText = reader.result;
    	      });
    	  };
    	 
      var csvFileInput = document.getElementById('fileInput');    
      var csvFile = csvFileInput.files[0];
      reader.readAsText(csvFile);
    }
      
      $scope.downloadText=function(myFinal){
    	  var content =myFinal ;
	  		var blob = new Blob([ content ], { type : 'text/plain' });
	  		$scope.downloadUrl = (window.URL || window.webkitURL).createObjectURL( blob );
    	  
      }
      
      
   }; 
   
   
   var historyController = function($scope,$rootScope,$http){
	   console.log("Hello History Controller"); 
	   
	   //function for display history data from database to the table
	      $scope.myHistory=function(){
	    	 // window.location.href = '/history';  
	    	  var myUrl='http://127.0.0.1:8080/ats/history?id='+$rootScope.userId;
	          $http({
	          url:myUrl,
	          method: "GET",
	        }).then(function(response){   
	        		console.log(response);
	                console.log(response.data);
//	                $rootScope.histories = response.data;
//	                alert($rootScope.histories.length);	                
	                $scope.histories = response.data;
	                $rootScope.historyLen=$scope.histories.length;	               	               
	           }, 
	        function(response){
	          swal("Sorry!", "Unable to load history!"+response, "warning");
	              });
	       }
	      
	      //Function for delete row from history table
	      $scope.deleteRow= function (i,hid) {
	    	  var isConfirmed = confirm("Are you sure to delete this record ?");
	          if(isConfirmed){
	        	  $http({
	    	          url:'http://127.0.0.1:8080/ats/history/'+hid,
	    	          method: "POST",
	    	        }).then(function(response){   
	    	        	 var ddata=response.data;
	    	             if(ddata==true){
	    	            	$scope.histories.splice(i, 1);
	    	            	alert("Deleted Sucessfully");
	    	              }
	    	              else{
	    	           	   alert("Problem in Deletion");
	    	              }
	    	           },
	    	        function(response){
	    	          swal("Sorry!", "Unable to delete!"+response, "warning");
	    	              });
		    	  
	          }else{
	            return false;
	          }	  
	       }
	      

	      //Function for download the summary history 
	      $scope.downloadHistory=function(sumText){
	    	var content =sumText ;
	  		var blob = new Blob([ content ], { type : 'text/plain' });
	  		$scope.downloadUrl = (window.URL || window.webkitURL).createObjectURL( blob );
	    	  
	      }

	   };

     
   var profileController =  function($scope,$rootScope,$http,$cookieStore,$cookies){

		  console.log("Hello ProfileController");
	      
	      $scope.updateProfile=function()
	      {
	        var user=$scope.user;
	        console.log(user);
	        if((user.userPass).length<6){
	        	$scope.profilePassErr="*Password must be at least 6 character*";
	        	return false;
	        }
	        $http({
	          url: 'http://127.0.0.1:8080/ats/update/'+$rootScope.userId,
	          method: "POST",
	          data: user
	        }).then(function(response){
	          console.log(response);
	          var data=response.data;	          
	          if(data.userEmail==null)
              {
                swal("Sorry!", "Update failed !", "error");
              }
              else
              { 
            	  	var userid = data.id;
	                console.log('logged in user : ' + userid );
	                $rootScope.userId = userid;
	                $rootScope.loggedIn = true;
	                $cookieStore.put('loggedIn', $rootScope.loggedIn);   
	                $cookieStore.put('userId', $rootScope.userId);
	                $rootScope.helloUser=data.userFullname;	
	                swal("Successfull!", "Profile updated!!!", "success");
	                window.location.href = '/home';
	             }    
         }, 
	     function(response){
	        	swal("Problem!", "error in updating !!!"+response, "error");
	        });
	      }
     
	   }; 
	   

    app.controller('signupController', signupController);
    app.controller('loginController', loginController);    
    app.controller('summaryController', summaryController);
    app.controller('historyController', historyController);
    app.controller('cryptoController', cryptoController);
    app.controller('profileController', profileController); 
    

