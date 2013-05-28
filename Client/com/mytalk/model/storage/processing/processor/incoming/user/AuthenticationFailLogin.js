MyTalk.processor.AuthenticationFailLogin = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct, {
	process: function() {
		//MyTalk.Authentication.find(0).deleteRecord();
		alert("Username o password non validi!");
	}
});