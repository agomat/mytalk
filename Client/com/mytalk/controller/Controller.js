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
  },

  filter:function(value){
    var context=this;
    var fcontent=[];
   
    if(value){
      context.get('content').get('users').forEach(function(entry){
        if((entry.get('fullNameConc')).indexOf(value) !== -1) {
          entry.set('unmatched',false);
        }
        else{
          entry.set('unmatched',true);
        }
      });

    }
    else{
      context.get('content').get('users').forEach(function(entry){
        entry.set('unmatched',false);
     });
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
  
  userToBlacklist:function(userId){
    var confirmation = confirm("Sei sicuro di mettere l'utente " + MyTalk.User.find(userId).get('fullName') +" nella Blacklist?");
    if(confirmation){
      var lists = MyTalk.List.find();
      lists.forEach(function(list) {
        list.get('users').removeObject( MyTalk.User.find(userId) );
        //list.get('transaction').reopen({giu:2}); // passing some data here
        //list.get('transaction').commit(); // Commit take non effects if user do not appears in current list `list`
      });
      var blacklist = MyTalk.List.find(1);
      blacklist.get('users').addObject(MyTalk.User.find(userId));
      //blacklist.get('transaction').reopen({giu:3});
      //blacklist.get('transaction').commit();
    } 
  }
  
});
