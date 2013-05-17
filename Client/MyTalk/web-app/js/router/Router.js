MyTalk.Router.map(function() {
  this.resource('index', { path: '/' }, function() {
    this.resource('guest', { path: '/' });
    this.resource('logged', { path: '/lists' } , function() {
      this.resource('list', { path: '/:list_id' });
    });
  });
});

MyTalk.IndexRoute = Ember.Route.extend({
  renderTemplate: function(controller, model) { 
    this._super();
    this.render('header', { into: 'index', outlet: 'head' });
  },
  model: function() {
    return MyTalk.PersonalData.find();
  }
});

MyTalk.GuestRoute = Ember.Route.extend({
  renderTemplate: function() {
    this.render('guest', { into: 'index', outlet: 'content' });
  }
});

MyTalk.LoggedRoute = Ember.Route.extend({
  renderTemplate: function() {
    this.render('logged', { into: 'index', outlet: 'content' });
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
