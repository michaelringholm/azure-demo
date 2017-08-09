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
    
	var methods = ["GET", "POST"];	
    $( "#method" ).autocomplete({ source: methods, minLength: 0 });    
    
	var serviceTypes = ["Free", "Educational", "Enterprise"];	
    $( "#serviceType" ).autocomplete({ source: serviceTypes, minLength: 0 });
    
    var serviceFacade = new ServiceFacade();
    $("#btnRegisterNewService").click(function() { serviceFacade.upsertService(); });
    $("#btnTestService").click(function() { serviceFacade.testService(); });
    $(".addPayloadParameter").click(function() {serviceFacade.addPayloadParameter();});
    
    $(document).keypress(function(e) {
        if(e.which == 13) {
            console.log('You pressed enter!');
        }
    });
    
    var serviceId = httpUtil.urlParam("serviceId");
    if(serviceId) {
    	console.log("Found service id " + serviceId + " as input, populating fields!");
    	serviceFacade.getService(serviceId);
    }
    else
    	console.log("No service id found as input, this will be a new service!");
});

function ServiceFacade() {

	var _this = this;
	_this.appRoot = appConfig.getBaseUrl();//$("#hfAppRoot").val();
	
	var buildPayload = function(payload) {
		$(".payloadParameter").each( function() {
			var paramName = $(this).find(".paramName").val();
			var paramValue = $(this).find(".paramValue").val();
			
			if(paramName && paramValue) {
				var param = {};
				param.paramName = paramName;
				param.paramValue = paramValue;
				payload.parameters.push(param);
			}
		});
	};
	
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
		service.method = $("#method").val();
		service.protocol = $("#protocol").val();
		
		service.payload = {};
		service.payload.parameters = [];		
		buildPayload(service.payload);
		
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
	
	this.getService = function(serviceId) {
		var service = {};
		service.id = serviceId;
		
		$.ajax({
			  type: "POST",
			  url: _this.appRoot + "/service/searchById.ctl",
			  contentType: "application/json",
			  dataType: "json",
			  data: JSON.stringify(service),			  
			  //data: "serviceId=" + serviceId,
			  success: function(responseData) {
				  $("#feedback").css("color", "green");
				  $("#feedback").html(responseData.message);
				  $("#serviceId").text(responseData.data.id);
				  $("#serviceName").val(responseData.data.name);
				  $("#serviceEndpoint").val(responseData.data.endpoint);
				  $("#domain").val(responseData.data.domain);
				  $("#subDomain").val(responseData.data.subDomain);
				  $("#description").val(responseData.data.description);
				  $("#supportEmail").val(responseData.data.supportEmail);
				  $("#supportChat").val(responseData.data.supportChat);
				  $("#method").val(responseData.data.method);
				  $("#protocol").val(responseData.data.protocol);
				  
				  if(responseData.data.payload && responseData.data.payload.parameters) {
					  
					  var missingParamPlaceholders = responseData.data.payload.parameters.length - $(".payloadParameter").length;
					  for(var i=0; i<missingParamPlaceholders; i++)
						  _this.addPayloadParameter();
					  
					  var index = 0;
					  while(index < responseData.data.payload.parameters.length) {
						  // change first to nth
						  $(".payloadParameter:eq(" + index + ")").find(".paramName").val(responseData.data.payload.parameters[index].paramName);
						  $(".payloadParameter:eq(" + index + ")").find(".paramValue").val(responseData.data.payload.parameters[index].paramValue);
						  index++;
					  }
				  }
				  
				  $("#newServiceLabel").hide();
				  $("#serviceIdSection").show();
			  },
			  error: function(err) {
				  /*if(err.responseJSON && err.responseJSON.message && err.responseJSON.message.indexOf("given name already exist")>0) {
					  $("#feedback").css("color", "red");
					  $("#feedback").html("Service name already in use!");
				  }
				  else {
					  $("#feedback").css("color", "red");
					  $("#feedback").html("An error occured, service was not registered!");
				  }*/
			  },			  
			  complete: function() { 
 
			  }
		});
	};
	
	this.testService = function() {
		var serviceModel = {};
		var service = {};
		service.endpoint = $("#serviceEndpoint").val();
		service.method = $("#method").val();
		serviceModel.service = service;
				
		service.payload = {};
		service.payload.parameters = [];		
		buildPayload(service.payload);
		
		$.ajax({
			  type: "POST",
			  url: _this.appRoot + "/service/test.ctl",
			  contentType: "application/json",
			  dataType: "json",
			  data: JSON.stringify(serviceModel),
			  success: function(responseData) {
				  $("#feedback").css("color", "green");
				  $("#feedback").html(responseData.message);
				  $("#testOutput").text(responseData);
				  //$("#newServiceLabel").hide();
				  //$("#serviceIdSection").show();
			  },
			  error: function(err) {
				  /*if(err.responseJSON && err.responseJSON.message && err.responseJSON.message.indexOf("given name already exist")>0) {
					  $("#feedback").css("color", "red");
					  $("#feedback").html("Service name already in use!");
				  }
				  else {
					  $("#feedback").css("color", "red");
					  $("#feedback").html("An error occured, service was not registered!");
				  }*/
			  },			  
			  complete: function() { 
 
			  }
		});
	};	
	
	this.addPayloadParameter = function() {
		var extraParam = $(".payloadParameter").first().clone();
		$(extraParam).find(".addPayloadParameter").remove();
		$("#payloadParameters").append(extraParam);		
	};

}