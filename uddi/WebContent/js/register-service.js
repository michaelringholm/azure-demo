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
    $("#btnRegisterNewService").click(function() { serviceFacade.insertService(); });
});

function ServiceFacade() {

	var _this = this;
	_this.appRoot = appConfig.getBaseUrl();//$("#hfAppRoot").val();
	
	this.insertService = function() {
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
			  url: _this.appRoot + "/service/insert.ctl",
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
	};
	
	this.updateService = function() {
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
	};	

}