MyTalk.PersonalData = Ember.Object.extend({

  username: null,
  password: null,


  setUsername: function(name){

  	this.set('username',name)
  },

  setPassword: function(pass){

  	this.set('password',pass);
  },

  setAll: function(name,pass){

	this.setUsername(name);
	this.setPassword(pass);
  },

  getUsername: function(){

  	return this.username;
  }

  getPassword: function(){

  	return this.password;
  }
});
 
