var appConfig = {};

$(function() {
	console.log("jquery is enabled!");
	appConfig = new AppConfig();
});

function AppConfig() {

	var _this = this;
	//_this.appRoot = $("#hfAppRoot").val();
	
	this.getBaseUrl = function() {
	    var re = new RegExp(/^.*\//);
	    return re.exec(window.location.href);
	}

}