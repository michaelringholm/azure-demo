var appConfig = {};

$(function() {
	console.log("jquery is enabled!");
	appConfig = new AppConfig();
	httpUtil = new HttpUtil();
});

function AppConfig() {

	var _this = this;
	//_this.appRoot = $("#hfAppRoot").val();
	
	this.getBaseUrl = function() {
	    var re = new RegExp(/^.*\//);
	    return re.exec(window.location.href);
	}

}

function HttpUtil() {
	var _this = this;
	
	this.urlParam = function(name) {
		var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
		return results[1] || 0;
	}
}