MyTalk.DashboardController = Ember.ObjectController.extend({
 setupController: function(controller, model) {
    this.controllerFor('PersonalData').set('content', MyTalk.PersonalData.find(0));
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
    var ids = parseInt(this.get('content').get('lastObject').get('id'))+1;
    var test=false;
    newList.forEach( function(t){
    
      if(t.get('name')==r){
        test=true;
      }
    });

    if(test==false){

          MyTalk.List.createRecord({id: ids,name:r,});
    }
    else{

      alert("Esiste già una lista con questo nome, cambialo.");
    }
  },
   
  addUser: function(currentList) {
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
    var n=this.get('content').get('name');
    if(n=="Tutti i contatti" || n=="Blacklist"){
      alert('Non è possibile modificare il nome della lista '+n+'.');
    }
    else{
      var r = prompt("Digita il  nuovo nome della lista: ","Scrivi qui il nuovo nome");
      newList=MyTalk.List.find();
      var test=false;
      newList.forEach( function(t){
    
        if(t.get('name')==r){
          test=true;
        }
      });
        
        if(test==false){
          this.get('content').setProperties({name:r});
        }
        else{

        alert("Esiste già una lista con questo nome, cambialo.");
        }
    }

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

