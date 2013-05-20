MyTalk.ARI = Ember.Object.extend({
  auth: undefined,
  req: undefined,
  info: undefined,
  
  init: function(a, r, i) {
    this.set('auth', a);
    this.set('req', r);
    this.set('info', JSON.stringify(i));
    this._super();
  },

  getAuth: function() {
  	return this.get('auth');
  },
  
  getReq: function() {
  	return this.get('req');
  },
  
  getInfo: function() {
  	return this.get('info');
  },

  setAuth: function(a) {
  	this.set('auth', a);
  },
  
  setReq: function(r) {
  	this.set('req', r);
  },
  
  setInfo: function(i) {
  	this.set('info', JSON.stringify(i));
  },
  
});