MyTalk.CallingRoute = Ember.Route.extend({
  renderTemplate: function() {
    this.render('calling', { into: 'index', outlet: 'content' });
  }
});