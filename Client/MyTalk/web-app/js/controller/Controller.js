MyTalk.DashboardController = Ember.Controller.extend({
 setupController: function() {
    this.controllerFor('dashboard').set('model', MyTalk.PersonalData.find());
  }
});

MyTalk.IndexController = Ember.ObjectController.extend({
  /*appStateBinding: Ember.Binding.oneWay('MyTalk.StateManager.currentState.name'),
  appState: null,
  isAuthenticated: function () {
    return (this.get('appState') == 'isAuthenticated');
  }.property('appState'),*/
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
  appStateBinding: Ember.Binding.oneWay('MyTalk.StateManager.currentState.name'),
  appState: null,
  isAuthenticated: function () {
    return (this.get('appState') == 'isAuthenticated');
  }.property('appState'),
  
  createList:function(){

    var r = prompt("Digita il nome della nuova lista: ","Scrivi qui il nome della lista");
    newList=this.get('content');
    var id = parseInt(this.get('content').get('lastObject').get('id'))+1;
    newList.forEach( function(t){
    
      if(t.get('name')!=r){
        
        if(t.get('id')== (id-1)){
          MyTalk.List.createRecord({id: id,name:r,});
        }
      }
      else{

        alert("Esiste già una lista con questo nome, cambialo.");
      }
    });
    
    
    
  },
   
  newUser: function(currentList) {
        currentList.get('users').createRecord(
          {username:'New User from List', online: true}
          );
    },

  

});


MyTalk.UlistController = Ember.ObjectController.extend({
  sortProperties: ['name'],
  deleteList:function(id){
    l=this.get('content').get('name');
    var r=confirm('Sei sicuro di voler eliminare la lista '+l+'?');
      if(r==true){
      
        nextList=MyTalk.List.find();
        context=this.get('target.router');
    
        this.get('content').deleteRecord();
        //this.get('store').commit();

        nextList.forEach( function(t){
    
          if(t.get('name')!='null'){
        
            context.replaceWith('ulist', t);
          }
        });
      } 
    
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

    n=MyTalk.User.find(user).get('name');
    n=n+" ";
    n=n+MyTalk.User.find(user).get('surname');
    
    var r=confirm("Sei sicuro di eliminare l'utente " + n +" dalla lista ?");
    if(r==true){
      var c = location.hash.split('/')[2];
      var list = MyTalk.List.find(c).get('users');
      list.removeObject( MyTalk.User.find(user) );
    }
  },
  
  userToBlacklist:function(user){

    n=MyTalk.User.find(user).get('name');
    n=n+" ";
    n=n+MyTalk.User.find(user).get('surname');
    
    var r=confirm("Sei sicuro di mettere l'utente " + n +" nella Blacklist ?");
   
    if(r==true){
      
      var c = location.hash.split('/')[2];
      var list = MyTalk.List.find(c).get('users');
      list.removeObject( MyTalk.User.find(user) );
      
      blackList = MyTalk.List.find();
      blackList.forEach( function(t){
    
        if(t.get('name')=='Blacklist'){
        
          MyTalk.List.find(t.get('id')).get('users').addObject(MyTalk.User.find(user));
        
        }
      });
    } 

     
  }


  
});

