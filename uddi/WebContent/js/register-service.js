$(function() {
	$("#serviceName").tooltip();
	$("#serviceEndpoint").tooltip();
	$("#description").tooltip();
	$("#domain").tooltip();
	$("#subDomain").tooltip();
	$("#supportEmail").tooltip();
	$("#supportChat").tooltip();
	
	var protocols = ["HTTP", "HTTPS"];	
    $( "#protocol" ).autocomplete({ source: protocols, minLength: 0 });
    
	var serviceTypes = ["Free", "Educational", "Enterprise"];	
    $( "#serviceType" ).autocomplete({ source: serviceTypes, minLength: 0 });
    
    var serviceFacade = new ServiceFacade();
    $("#btnRegisterNewService").click(function() { serviceFacade.upsertService(); });
});

function ServiceFacade() {

	var _this = this;
	_this.appRoot = appConfig.getBaseUrl();//$("#hfAppRoot").val();
	
	this.upsertService = function() {
		var service = {};
		if($("#serviceId").html().length > 0)
			service.id = $("#serviceId").html();
		service.name = $("#serviceName").val();
		service.description = $("#description").val();
		service.domain = $("#domain").val();
		service.subDomain = $("#subDomain").val();
		service.endpoint = $("#serviceEndpoint").val();
		service.supportEmail = $("#supportEmail").val();
		service.supportChat = $("#supportChat").val();
		
		$.ajax({
			  type: "POST",
			  url: _this.appRoot + "/service/upsert.ctl",
			  contentType: "application/json",
			  dataType: "json",
			  data: JSON.stringify(service),			  
			  success: function(responseData) {
				  $("#feedback").css("color", "green");
				  $("#feedback").html(responseData.message);
				  $("#serviceId").text(responseData.data.id);
				  $("#newServiceLabel").hide();
				  $("#serviceIdSection").show();
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
	
	/*this.updateService = function() {
		var service = {};
		service.name = $("#serviceName").val();
		service.description = $("#description").val();
		service.domain = $("#domain").val();
		service.subDomain = $("#subDomain").val();
		service.endpoint = $("#serviceEndpoint").val();
		service.supportEmail = $("#supportEmail").val();
		service.supportChat = $("#supportChat").val();
		
		$.ajax({
			  type: "POST",
			  url: _this.appRoot + "/service/update.ctl",
			  contentType: "application/json",
			  dataType: "json",
			  data: JSON.stringify(service),			  
			  success: function(data) { 
 
			  },
			  error: function(err, err2) {

			  },			  
			  complete: function() { 
 
			  }
		});
	};*/

}