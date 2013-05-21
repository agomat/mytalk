MyTalk.IndexController = Ember.ObjectController.extend({
  appStateBinding: Ember.Binding.oneWay('MyTalk.StateManager.currentState.name'),
  appState: null,

  isAuthenticated: function () {
    return (this.get('appState') == 'isAuthenticated');
  }.property('appState'),

  login: function login(username, password) {
    var record = MyTalk.Authentication.createRecord({
      id: 0,
      username: username,
      password: password,
      ip: "//TODO"
    });

    var transaction = record.get('transaction');

    transaction.reopen({
      request: "Login" // TODO usare mixin per ricavare  nome della funzione
    });

    transaction.commit();
  },

  register:function(name, surname, username, email, password1, password2){
    console.log('controller register: '+name +" "+ surname +" "+ username +" "+ email +" "+ password1 +" "+ password2);
    // TODO
  },

  ipCall:function(ip) {
    console.log('controller ipCAll: '+ip);
    // TODO
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
  check: true,

  checkList: function(){
    var id = this.get('content').get('id');
    if(id < 2){
      this.setProperties({check:false});
    }
    else{
      this.setProperties({check:true});
    }
  }.observes(this),
  
  deleteList: function(){
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
  renameList: function(){ 

    var id = this.get('content').get('id');
    var name = this.get('content').get('name');
    
    var newName = prompt("Digita il  nuovo nome della lista: ","Nome della lista");
    var list = MyTalk.List.find();
    var test = true;


      if(newName!="Nome della lista"){
        list.forEach( function(t){
          if(t.get('name') == newName){
            test=false;
           }
        });

        if(test==true){
          this.get('content').setProperties({name:newName});
        }
        
        else{
          alert("Esiste già una lista con questo nome");
        }
      }
  }

});

MyTalk.UsersController = Ember.ArrayController.extend({
  blacklist: false,
  alluser: false,   
  needs: ['list'],
    
  call: function(id){
    console.log("call "+id);
    // TODO
  },
  addUser: function(){
    console.log('addUser');
    // TODO
  },
  specialList: function(){ 
    var id = this.get('controllers.list.content.id');
    if(id == 0){
      this.set('alluser',true);
    }
    else if(id == 1){
      this.set('blacklist',true);
    }
  },
  deleteUser:function(userId){
    var user = MyTalk.User.find(userId);
    var confirmation = confirm("Sei sicuro di eliminare l'utente " + user.get('fullName') +" dalla lista?");
    if(confirmation){
      var listId = this.get('controllers.list.content.id');
      var currentList = MyTalk.List.find(listId).get('users');
      currentList.removeObject( user );
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
