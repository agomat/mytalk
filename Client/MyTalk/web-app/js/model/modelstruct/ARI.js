MyTalk.ARI = Ember.Object.extend({
  auth: undefined,
  request: undefined,
  info: undefined,
  
  init: function(a, r, i) {
    this.set('auth', a);
    this.set('request', r);
    this.set('info', JSON.stringify(i));
    this._super();
  },

  getAuth: function() {
  	return this.get('auth');
  },
  
  getRequest: function() {
  	return this.get('request');
  },
  
  getInfo: function() {
  	return this.get('info');
  },

  setAuth: function() {
  	this.set('auth', auth);
  },
  
  setRequest: function(request) {
  	this.set('request', request);
  },
  
  setInfo: function(obj) {
  	this.set('info', JSON.stringify(obj));
  },

  setAll: function(insert, all, files, here) {
  	// TODO implementare
  }
  
});