MyTalk.Router.map(function(match) {
  this.route("index", {path:'/'});
  this.route("lists");
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
  renderTemplate: function(controller, model) {
    this.render('lists');
    this.render('lists_t'); 
    this.render('spaces',{
      outlet:'spaces',
      into:'logged'
    });
  }
});