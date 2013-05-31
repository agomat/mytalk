MyTalk.IndexController = Ember.ObjectController.extend(MyTalk.RequestHelper, {
  appStateBinding: Ember.Binding.oneWay('MyTalk.StateManager.currentState.name'),
  appState: null,

  isAuthenticated: function () {
    return (this.get('appState') == 'isAuthenticated');
  }.property('appState'),

  login: function makeLogin(username, password) {
    
    var ProcessorFactory = MyTalk.ProcessorFactory.create({});
    var processor = ProcessorFactory.createProcessorProduct( this.computeRequestName( arguments ) );
    processor.process({
      username: username,
      password: password
    });

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

MyTalk.LoggedController = Ember.ObjectController.extend(MyTalk.RequestHelper, {
  sortProperties: ['name'],
  needs:['users'],
  createList:function makeListCreate(){

    var newName = prompt("Digita il nome della nuova lista: ");
    if(newName) {
      var list=this.get('content');
      var test=true;

      list.forEach(function(t){
        if(t.get('name')==newName){
          test=false;
        }
      });
      
      if(test==true){
        var ProcessorFactory = MyTalk.ProcessorFactory.create({});
        var processor = ProcessorFactory.createProcessorProduct( this.computeRequestName( arguments ) );
        processor.process({
          id: MyTalk.List.find().get('length'),
          name: newName
        });
        this.get('controllers.users').set('newList',MyTalk.List.find().get('length'));
      }
      else {
        alert("Esiste già una lista con questo nome");
      }
    }  
  },
  
});


MyTalk.ListController = Ember.ObjectController.extend(MyTalk.RequestHelper, {
  sortProperties: ['name'],

  deleteList: function makeListDelete(){
    var listName = this.get('content').get('name');
    var listId = this.get('content').get('id');
    var response = confirm('Sei sicuro di voler eliminare la lista '+listName+'?');
    if(response == true){
      var context = this.get('target.router');
      var ProcessorFactory = MyTalk.ProcessorFactory.create({});
      var processor = ProcessorFactory.createProcessorProduct( this.computeRequestName( arguments ) );
      processor.process({
        listId: listId,
        name: listName
      });
      context.replaceWith('list', MyTalk.List.find(0) );
    }
  },
  renameList: function makeUpdateListName(){ 
    var listId = this.get('content').get('id');
    var listName = this.get('content').get('name');
    
    var newName = prompt("Digita il nuovo nome della lista:");
    var lists = MyTalk.List.find();
    var test = true;

    if(newName){
      lists.forEach( function(t){
        if(t.get('name') == newName){
          test=false;
        }
      });
      if(test==true){
        var ProcessorFactory = MyTalk.ProcessorFactory.create({});
        var processor = ProcessorFactory.createProcessorProduct( this.computeRequestName( arguments ) );
        processor.process({
          listId: listId,
          newName: newName,
          oldName: listName,
        });

        
      }
    else {
      alert("Esiste già una lista con quel nome");
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
  },

  umatchedReset:function(){
    this.get('content').get('users').forEach(function(entry){
        entry.set('unmatched',false);
     });
  }.observes(this),

  
});

MyTalk.UsersController = Ember.ArrayController.extend(MyTalk.RequestHelper, {
  sortProperties:['name'],
  needs: ['logged','list'], 
  newList:null,
  selectArray: [],
  selectedValue: null,
  userId: null,
  userName: null,

  filteredUsers : function(){
    var filteredUsers = [];
    var items = this.get('content');
    items.forEach(function(u){
      if(u.get('unmatched') == false)
        filteredUsers.push(u);
    });
    return filteredUsers;
  }.property('content.@each.unmatched').cacheable(),

  addUser: function (user){
    document.getElementById('adduser').style.display='block';
    var n=user.get('name')+" "+user.get('surname');
    this.set('userId',user.get('id'));
    this.set('userName',n);
  },

  closeSelect:function(){
    document.getElementById('adduser').style.display='none';
    this.set('selectedValue',null);
    this.set('userId',null);
    this.set('userName',null);
  },
  doAddUser:function (){
    var currentListId=this.get('controllers.list.content.id');
    if(this.selectedValue!=null && this.selectedValue!=currentListId && this.selectedValue>1){
   
      if(currentListId!=1){
        var list=MyTalk.List.find(this.selectedValue);
        var ProcessorFactory = MyTalk.ProcessorFactory.create({});
        var processor = ProcessorFactory.createProcessorProduct("ListUserAdd");
        processor.process({
          userId: this.userId,
          list: list 
        });

        document.getElementById('adduser').style.display='none';
        this.set('selectedValue',null);
        this.set('userId',null);
        this.set('userName',null);
      }
      
      
    }
    else{
      alert('Selezionare una lista corretta');
    }
  },
  deleteUser:function (user){
    var userId = user.get('id');
    var confirmation = confirm("Sei sicuro di eliminare l'utente " + user.get('fullName') +" dalla lista?");
    if(confirmation){
      var listId = this.get('controllers.list.content.id');
      if(listId==1){
        var currentList = MyTalk.List.find(listId).get('users');
        currentList.removeObject( user );
      }
      else{
        var list = MyTalk.List.find(listId);
        var ProcessorFactory = MyTalk.ProcessorFactory.create({});
        var processor = ProcessorFactory.createProcessorProduct("ListUserRemove");
        processor.process({
          userId: userId,
          list: list 
        });
      }
    }
  },
  userToBlacklist: function makeBlackListAdd(user){
    var confirmation = confirm("Sei sicuro di mettere l'utente " + user.get('fullName') +" nella Blacklist?");
    var userId = user.get('id');
    if(confirmation){
      var lists = MyTalk.List.find().toArray();
      lists = lists.rejectProperty('id',0);
      lists.forEach(function(list) {
          var ProcessorFactory = MyTalk.ProcessorFactory.create({});
          var processor = ProcessorFactory.createProcessorProduct("ListUserRemove");
          processor.process({
            userId: userId,
            list: list 
          });
      });
      var ProcessorFactory = MyTalk.ProcessorFactory.create({});
      var processor = ProcessorFactory.createProcessorProduct( this.computeRequestName( arguments ) );
      processor.process({
        userId: userId,
      });
    } 
  }
});



MyTalk.CallingController = Ember.ObjectController.extend({

  callInfoBinding: Ember.Binding.oneWay("MyTalk.StateManager.get('isBusy').get('callInfo')"),

  needs: ['users'],
  messages: [],
  statistics: Ember.Object.create({ // vedere se tenerli qui...!
    duration: null,
    sentBytes: null,
    receivedBytes: null
  }),

});


/*
  sendMessage:function(message){
    var context=this.get('messages');
    var temp=new Array();

    if(context){
      context.forEach(function(t){
         temp.pushObject(t);
      })
      temp.pushObject(Ember.Object.create({sender:true,text:message}));
      temp.pushObject(Ember.Object.create({sender:false,text:"messaggio prova"}));
      this.set('messages',temp);
    }
    else{

        temp.pushObject(Ember.Object.create({sender:true,text:message}));
        temp.pushObject(Ember.Object.create({sender:false,text:"messaggio prova"}));
        this.set('messages',temp);
    }
  },
*/