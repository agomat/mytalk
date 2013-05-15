MyTalk.DashboardController = Ember.ObjectController.extend({
 setupController: function(controller, model) {
    this.controllerFor('PersonalData').set('content', MyTalk.PersonalData.find(0));
  }
});

MyTalk.IndexController = Ember.ObjectController.extend({

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

    var newName = prompt("Digita il nome della nuova lista: ","Nome della lista");
    var List=this.get('content');
    var test=true;

    List.forEach( function(t){
      if(t.get('name')==newName){
        test=false;
      }
    });

    if(newName!="Nome della lista"){
      if(test==true){
            var ids = parseInt(this.get('content').get('lastObject').get('id'))+1;
            MyTalk.List.createRecord({id:ids, name:newName,});
      }
      else if(newName!=null){
        alert("Esiste già una lista con questo nome");
      }
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
    var l=this.get('content').get('name');
    var r=confirm('Sei sicuro di voler eliminare la lista '+l+'?');
      if(r==true){
      
        var List=MyTalk.List.find(4);
        var context=this.get('target.router');
        this.get('content').deleteRecord();
        context.replaceWith('ulist', List);
          
      } 
    
  },
 
  renameList:function(){
    var newName = prompt("Digita il  nuovo nome della lista: ","Nome della lista");
    var list=MyTalk.List.find();
    var test=true;

    list.forEach( function(t){
      if(t.get('name')==newName){
        test=false;
      }
    });

    if(newName!="Nome della lista"){
      if(test==true){
            this.get('content').setProperties({name:newName});
      }
      else if(newName!=null){
        alert("Esiste già una lista con questo nome");
      }
    }
  },

 setupController: function() {
    this.controllerFor('ulist').set('model', MyTalk.List.find());
  }  

});
 

   
MyTalk.UsersController = Ember.ArrayController.extend({
  
  controll:true,
  
  check:function(){

    if(location.hash.split('/')[2]==4){
      this.setProperties({controll:false});
    }
    else{
        
        if(location.hash.split('/')[2]==3){
            this.setProperties({controll:false});
        }
    }
  },
  
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

     
  },

    
    


  
});

