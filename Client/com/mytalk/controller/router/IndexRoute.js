MyTalk.IndexRoute = Ember.Route.extend({
  renderTemplate: function(controller, model) { 
    this._super();
    this.render('header', { into: 'index', outlet: 'head' });
  },
  model: function() {
    return MyTalk.PersonalData.find();
  }
});
