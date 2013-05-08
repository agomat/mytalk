MyTalk.LoggedController = Ember.Controller.extend({});

MyTalk.IndexController=Ember.ObjectController.extend({
  appStateBinding: Ember.Binding.oneWay('MyTalk.StateManager.currentState.name'),
  appState: null,
  isAuthenticated: function () {
    return (this.get('appState') == 'isAuthenticated');
  }.property('appState'),
  login:function(email,pass) {
		MyTalk.Authentication.createRecord({id:1,username:email,password:pass}).get('transaction').commit();
    // se autenticato:
    var credentials = {} // TODO vedere se spostare create record in login statemanager
    MyTalk.StateManager.send("login", credentials);	
	},
  register:function(name,surname,username,email,password,password_conf){
  	console.log('controller register: '+name +" "+ surname +" "+ username +" "+ email +" "+ password +" "+ password_conf);
	},
  ipCall:function(ip) {
  	console.log('controller ipCAll: '+ip)
  }
});