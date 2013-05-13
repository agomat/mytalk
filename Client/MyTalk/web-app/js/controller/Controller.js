MyTalk.DashboardController = Ember.Controller.extend({});

MyTalk.IndexController = Ember.ObjectController.extend({
  appStateBinding: Ember.Binding.oneWay('MyTalk.StateManager.currentState.name'),
  appState: null,
  isAuthenticated: function () {
    return (this.get('appState') == 'isAuthenticated');
  }.property('appState'),
  login:function(user,pass) {
    MyTalk.Authentication.createRecord({id:1,username:user,password:pass}).get('transaction').commit();
    // se autenticato:
    var credentials = {}; // TODO vedere se spostare create record in login statemanager
    MyTalk.StateManager.send("login", credentials); 
  },
  register:function(name,surname,username,email,password,password_conf){
    console.log('controller register: '+name +" "+ surname +" "+ username +" "+ email +" "+ password +" "+ password_conf);
  },
  ipCall:function(ip) {
    console.log('controller ipCAll: '+ip)
  }
});

MyTalk.ListsController = Ember.ArrayController.extend({
  sortProperties: ['name'],
  
  createList:function(){

    alert();
  },
   newUser: function(currentList) {
        currentList.get('users').createRecord(
          {username:'New User from List', online: true}
          );
    },

  

});


MyTalk.UlistController = Ember.ObjectController.extend({
deleteList:function(){
    alert();

  },

  renameList:function(){

    alert();
  },

 setupController: function() {
    this.controllerFor('ulist').set('model', MyTalk.List.find());
  }  

});
 

    
MyTalk.UsersController = Ember.ArrayController.extend({
    new: function() {
    this.content.createRecord({
        username: 'New User from List',
        online: true
    })},
    setupController: function() {
    this.controllerFor('users').set('model', MyTalk.User.find());
  } , 

  deleteUser:function(user){

    alert(user);
    var c=location.hash.split('/')[2];
    alert(c);
  }
});

