MyTalk.Router.map(function() {
  this.resource('logged', { path: '/lists' } , function() {
    this.resource('list', { path: ':list_id' });
  });
});

MyTalk.IndexRoute = Ember.Route.extend({
 
  renderTemplate: function(controller, model) { 
    this.render('index');
    this.render('guest');
  }
});

MyTalk.LoggedRoute = Ember.Route.extend({
  model: function(params) {
    return MyTalk.List.find(params.list_id);
  }
  
});

MyTalk.LoggedIndexRoute = Ember.Route.extend({
  redirect: function() {
    globalList = this.modelFor('logged').toArray()[1];
    this.replaceWith('list', globalList);
  }
});

