$(function() {
	$("#serviceName").tooltip();
	$("#description").tooltip();
	$("#domain").tooltip();
	$("#subDomain").tooltip();
    
	var serviceTypes = ["Free", "Educational", "Enterprise"];	
    $( "#serviceType" ).autocomplete({ source: serviceTypes, minLength: 0 });
    
    var searchServiceFacade = new SearchServiceFacade();
    $("#btnSearchServices").click(function() { searchServiceFacade.search(); });
    
    searchServiceFacade.welcomeAnimation();
});

function SearchServiceFacade() {

	var _this = this;
	_this.appRoot = appConfig.getBaseUrl();
	console.log('appRoot is [' + _this.appRoot + ']');
	
	this.search = function() {
		var keywords = $("#keywords").val();
		
		$.ajax({
			  type: "POST",
			  url: _this.appRoot + "/service/search.ctl",
			  contentType: "application/json",
			  dataType: "json",
			  data: JSON.stringify(keywords),			  
			  success: function(responseData) {
				  //$("#feedback").css("color", "green");
				  //$("#feedback").html(responseData.message);
				  
				  $("#hitCount").html(responseData.message);
				  $("#searchResults").html("");
				  //$("#serviceId").text(responseData.data.id);
				  for(var serviceIndex=0; serviceIndex < responseData.data.length; serviceIndex++) {
					  var service = responseData.data[serviceIndex];
					  console.log(service.name);
					  var searchResult = $("#searchResultTemplate").clone();
					  searchResult.attr("id", service.id);
					  searchResult.find(".name").html(service.name);
					  searchResult.find(".endpoint").html(service.endpoint);
					  searchResult.find(".description").html(service.description);
					  searchResult.appendTo("#searchResults");
				  }
				  
				  //$("#newServiceLabel").hide();
				  //$("#serviceIdSection").show();
			  },
			  error: function(err) {
				  if(err.responseJSON && err.responseJSON.message && err.responseJSON.message.indexOf("given name already exist")>0) {
					  $("#feedback").css("color", "red");
					  $("#feedback").html("Service name already in use!");
				  }
				  else {
					  $("#feedback").css("color", "red");
					  $("#feedback").html("An error occured, service was not registered!");
				  }
			  },			  
			  complete: function() { 
 
			  }
		});
	};
	
	this.welcomeAnimation = function() {
		$("#stelinnoRobot").effect("slide", 1500);
		$("#stelinnoRobot").effect("bounce", 1500);
		$("#helpText").effect("pulsate", 3000);
		$("#helpText").delay(4000).effect("clip", 1000);
	};
}