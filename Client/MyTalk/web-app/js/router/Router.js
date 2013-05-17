MyTalk.Router.map(function() {
  this.resource('index', { path: '/' }, function() {
    this.resource('guest', {path: '/' });
    this.resource('logged', { path: '/lists' } , function() {
      this.resource('list', { path: ':list_id' });
    });
  });
});

MyTalk.IndexRoute = Ember.Route.extend({
  renderTemplate: function(controller, model) { 
    this._super();
    this.render('header', { into: 'index', outlet: 'head' });
  }
});

MyTalk.GuestRoute = Ember.Route.extend({
  renderTemplate: function() {
    this.render('guest', { outlet: 'content' });
  }
});

MyTalk.LoggedRoute = Ember.Route.extend({
  renderTemplate: function() {
    this.render('logged', { outlet: 'content' });
  },
  model: function() {
    return MyTalk.List.find(); 
  }
});

MyTalk.LoggedIndexRoute = Ember.Route.extend({
  redirect: function() {
    globalList = this.modelFor('logged').toArray()[1];
    this.replaceWith('list', globalList);
  }
});

MyTalk.ListRoute = Ember.Route.extend({
  renderTemplate: function() {
    this.render('list', { into: 'logged', outlet: 'list' });
  }
});

MyTalk.ApplicationRoute = Ember.Route.extend({
  setupController: function() {
    this.controllerFor('header').set('model', MyTalk.PersonalData.find());
  }
});