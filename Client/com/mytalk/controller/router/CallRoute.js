MyTalk.CallRoute = Ember.Route.extend({
  renderTemplate: function() {
    this.render('call', { into: 'index', outlet: 'content' });
  },
 
});