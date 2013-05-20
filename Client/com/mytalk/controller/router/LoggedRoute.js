MyTalk.LoggedRoute = Ember.Route.extend({
  renderTemplate: function() {
    this.render('logged', { into: 'index', outlet: 'content' });
  },
  model: function() {
    return MyTalk.List.find(); 
  }
});