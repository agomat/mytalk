MyTalk.Router.map(function(match) {
  this.route("index", {path:'/'});
  this.route("listr");
  this.resource("lists", function() {
    this.resource('list', {path: ':list_id' });
  });/*
  this.route("lists");
  this.resource('list', { path: ':list_id' });*/
});

MyTalk.ListrRoute=Ember.Route.extend({
   redirect: function() {
    this.transitionTo('lists');
  }
});

MyTalk.IndexRoute = Ember.Route.extend({
 
  model: function() {
    return MyTalk.WUser.find();
  },
  renderTemplate: function(controller, model) { 
    this.render('index'); 
    this.render('content'); 
  }
});

MyTalk.ListsRoute = Ember.Route.extend({
 
  model: function() {
    return MyTalk.List.find();
  },

  renderTemplate: function(controller, model) {
    this.render('lists');
    this.render('listscontent'); 
    /*this.render('spaces',{
      outlet:'spaces',
      into:'logged'
    });*/
  }
});

/*
MyTalk.ListRoute = Ember.Route.extend({
  model: function() {
    return MyTalk.WList.find();
  }
});*/