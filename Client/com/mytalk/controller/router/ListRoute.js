MyTalk.ListRoute = Ember.Route.extend({
  renderTemplate: function() {
    this.render('list', { into: 'logged', outlet: 'list' });
  }
});
