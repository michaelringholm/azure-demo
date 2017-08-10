$(function() {
	$("#serviceName").tooltip();
	$("#description").tooltip();
	$("#domain").tooltip();
	$("#subDomain").tooltip();
    
	var serviceTypes = ["Free", "Educational", "Enterprise"];	
    $( "#serviceType" ).autocomplete({ source: serviceTypes, minLength: 0 });
    
    var uddiHomeFacade = new UDDIHomeFacade();    
    uddiHomeFacade.welcomeAnimation();
});

function UDDIHomeFacade() {

	var _this = this;
	_this.appRoot = appConfig.getBaseUrl();
	console.log('appRoot is [' + _this.appRoot + ']');
	
	this.welcomeAnimation = function() {
		$("#stelinnoRobot").effect("slide", 1500);
		$("#stelinnoRobot").effect("bounce", 1500);
		$("#helpText1").effect("pulsate", 3000);
		$("#helpText1").delay(2000).effect("clip", 1000)		
		
		$( "#stelinnoRobot" ).delay(2500).animate( {left: "-=450"}, 3000, function() {});
		$( "#stelinnoRobot" ).delay(500).animate( {left: "+=450"}, 3000, function() {});
		
		$("#helpText2").delay(5000).effect("pulsate").delay(4000).effect("clip");
		$("#helpText3").delay(9000).effect("pulsate");
		
		//$("#stelinnoRobot").delay(5000).css({left:"20px"});
		//$("#stelinnoRobot").attr("css", "left:20px");
		//$("#stelinnoRobot").attr("left", "20px");
		//$("#stelinnoRobot").attr("top", "20px");
		/*$("#stelinnoRobot").effect("slide", 1500);
		$("#stelinnoRobot").effect("bounce", 1500);
		$("#helpText").effect("pulsate", 3000);
		$("#helpText").delay(4000).effect("clip", 1000);*/
	};
}