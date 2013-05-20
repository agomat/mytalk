MyTalk.IndexController = Ember.ObjectController.extend({
  appStateBinding: Ember.Binding.oneWay('MyTalk.StateManager.currentState.name'),
  appState: null,
  isAuthenticated: function () {
    return (this.get('appState') == 'isAuthenticated');
  }.property('appState'),
  login: function(user,pass) {
    MyTalk.Authentication.createRecord({id:1,username:user,password:pass}).get('transaction').commit();
    var credentials = {};
    MyTalk.StateManager.send("login", credentials); 
  },
  register:function(name,surname,username,email,password,password_conf){
    console.log('controller register: '+name +" "+ surname +" "+ username +" "+ email +" "+ password +" "+ password_conf);
  },
  ipCall:function(ip) {
    console.log('controller ipCAll: '+ip)
  }
});

MyTalk.LoggedController = Ember.ObjectController.extend({
  sortProperties: ['name'],
  createList:function(){

    var newName = prompt("Digita il nome della nuova lista: ","Nome della lista");
    if(newName!="Nome della lista") {
      var list=this.get('content');
      var test=true;

      list.forEach(function(t){
        console.debug(t.get('name'));
        if(t.get('name')==newName){
          test=false;
        }
      });
      
      if(test==true){
            MyTalk.List.createRecord({id: MyTalk.List.find().get('length'), name: newName}).get('transaction').commit();
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
  }

});

MyTalk.UsersController = Ember.ArrayController.extend({
  blacklist:false,
  alluser:false,
  needs: ['list'],
    
  call:function(id){
      console.log(id);
  },

  addUser:function(){

  },

  check:function(){
 
    var id = this.get('controllers.list.content.id');
    
    if(id==0){
      this.setProperties({alluser:true});

    }
    else{
        
        if(id==1){
            this.setProperties({blacklist:true});
        }
    }
  },

  deleteUser:function(user){

    var n = null;

    this.get('content').forEach( function(t){
      if(t.get('id')==user){
        n = t.get('fullName');
      }
        
    });
    
    var r=confirm("Sei sicuro di eliminare l'utente " + n +" dalla lista ?");
    if(r==true){
      var id=this.get('controllers.list.content.id');
      var list = MyTalk.List.find(id).get('users');
      list.removeObject( MyTalk.User.find(user) );
    }
    
    
    
  },
  
  userToBlacklist:function(user){
    
    var r=confirm("Sei sicuro di mettere l'utente " + MyTalk.User.find(user).get('fullName') +" nella Blacklist ?");
   
    if(r==true){
      var lists = MyTalk.List.find();
      lists.forEach( function(list){

        //var transaction = DS.get('defaultStore').transaction();
        //transaction.add(this);

        list.get('users').removeObject( MyTalk.User.find(user) );
        //list.get('transaction').reopen({giu:2});
        //list.get('transaction').commit();
      });
      var record = MyTalk.List.find(1);
      record.get('users').addObject(MyTalk.User.find(user));
      record.get('transaction').reopen({giu:2});
      record.get('transaction').commit();
    } 
  }
  
});
