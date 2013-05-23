MyTalk.GuestRoute = Ember.Route.extend({
  renderTemplate: function() {
    this.render('guest', { into: 'index', outlet: 'content' });
  },
  model: function() {
    return MyTalk.PersonalData.find(1);
  }
});