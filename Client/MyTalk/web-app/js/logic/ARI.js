MyTalk.ARI = Ember.Object.extend({
  auth: undefined,
  request: undefined,
  info: undefined,
  
  init: function(a, r, i) {
    this.set('auth', a);
    this.set('request', r);
    this.set('info', JSON.stringify(i));
    this._super();
  }
  
});