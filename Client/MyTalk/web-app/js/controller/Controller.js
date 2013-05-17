MyTalk.IndexController = Ember.ObjectController.extend({
  /* max: Non ricordo per che motivo servisse. Mattia */
  appStateBinding: Ember.Binding.oneWay('MyTalk.StateManager.currentState.name'),
  appState: null,
  isAuthenticated: function () {
    return (this.get('appState') == 'isAuthenticated');
  }.property('appState'),

  register:function(name,surname,username,email,password,password_conf){
    console.log('controller register: '+name +" "+ surname +" "+ username +" "+ email +" "+ password +" "+ password_conf);
  },
  ipCall:function(ip) {
    console.log('controller ipCAll: '+ip)
  }
});

MyTalk.LoggedController = Ember.ObjectController.extend({
  //needs: ['HeaderController'],
  sortProperties: ['name'],
  createList:function(){

    var newName = prompt("Digita il nome della nuova lista: ","Nome della lista");
    var list=this.get('content');
    var test=true;

    list.forEach(function(t){
      console.debug(t.get('name'));
      if(t.get('name')==newName){
        test=false;
      }
    });

    if(newName!="Nome della lista"){
      if(test==true){
            MyTalk.List.createRecord({name:newName});
      }
      else if(newName!=null){
        alert("Esiste già una lista con questo nome");
      }
    }

  }
   
});


MyTalk.ListController = Ember.ObjectController.extend({
  sortProperties: ['name'],
  check:true,

  checkList:function(){
    var id = this.get('content').get('id');
    if(id < 2){
      this.setProperties({check:false});
    }
    else{
      this.setProperties({check:true});
    }
  }.observes(this),
  

  
  deleteList:function(){
    var l=this.get('content').get('name');
    var id=this.get('content').get('id');
    
    var r=confirm('Sei sicuro di voler eliminare la lista '+l+'?');
      if(r==true){
      
        var list=MyTalk.List.find(0);
        var context=this.get('target.router');
        this.get('content').deleteRecord();
        context.replaceWith('list', list);
      }

    
  },
 
  renameList:function(){

    var id = this.get('content').get('id');
    var name = this.get('content').get('name');
    
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
  
  addUser: function(currentList) { // ??? by Mattia
          currentList.get('users').createRecord(
          {username:'New User from List', online: true}
          );
  }

});
MyTalk.ListCheck=Ember.run(function(){
  
});

 
MyTalk.UsersController = Ember.ArrayController.extend({
  blacklist:false,
  alluser:false, 
  check:function(){

    if(location.hash.split('/')[2]==0){
      this.setProperties({alluser:true});
    }
    else{
        
        if(location.hash.split('/')[2]==1){
            this.setProperties({blacklist:true});
        }
    }
  },

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


MyTalk.HeaderController = Ember.ArrayController.extend({

  
  appStateBinding: Ember.Binding.oneWay('MyTalk.StateManager.currentState.name'),
  appState: null,
  
  isAuthenticated: function () {
    return (this.get('appState') == 'isAuthenticated');
  }.property('appState'),

  login: function(user,pass) {
    MyTalk.Authentication.createRecord({id:1,username:user,password:pass}).get('transaction').commit();
    // se autenticato:
    var credentials = {}; // TODO vedere se spostare create record in login statemanager
     MyTalk.StateManager.send("login", credentials); 
  }
});
