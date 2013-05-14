MyTalk.Router.map(function(match) {
  this.route("index", {path:'/'});
  this.route("listr");
  this.resource("lists", function() {
    this.resource('ulist', {path: ':ulist_id' });
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
    return MyTalk.List.find();
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
  }
});


MyTalk.ListsIndexRoute = Ember.Route.extend({
    redirect: function () {
      firstItem = this.controllerFor('lists').get('content');
      var context = this;
    
      firstItem.forEach( function(t){
    
        if(t.get('name')=='Tutti i contatti'){
        
          context.replaceWith('ulist', t);
        }
      });
    }
});

