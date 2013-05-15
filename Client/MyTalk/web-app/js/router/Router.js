MyTalk.Router.map(function() {
  this.resource('logged', { path: '/lists' } , function() {
    this.resource('list', { path: ':list_id' });
  });
});

// MyTalk.ListrRoute=Ember.Route.extend({
//    redirect: function() {
//     this.transitionTo('lists');
//   }
// });

MyTalk.IndexRoute = Ember.Route.extend({
 
  renderTemplate: function(controller, model) { 
    this.render('index');
    this.render('guest');
  }
});

MyTalk.LoggedRoute = Ember.Route.extend({

  model: function() {
    return MyTalk.List.find();
  }
  
});

MyTalk.LoggedIndexRoute = Ember.Route.extend({
  redirect: function() {
    globalList = this.modelFor('logged');
    console.debug(globalList.toString());
    this.replaceWith('list', globalList);
  }
});


// MyTalk.ListsIndexRoute = Ember.Route.extend({
//   model: function() {
//     return MyTalk.List.find();
//   },
//   renderTemplate: function() {
//     this.render('lists/index');
//   },
//   redirect: function () {
//       firstItem = this.controllerFor('lists').get('content');
//       var context = this;
//     
//       firstItem.forEach( function(f){
//     
//         if(f.get('name')=='Tutti i contatti'){
//         
//           context.replaceWith('ulist', f);
//         }
//       });
//     }
// });

