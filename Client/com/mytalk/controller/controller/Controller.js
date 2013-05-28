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
      }
      else {
        alert("Esiste già una lista con questo nome");
      }
    }  
  }
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
  }

});

MyTalk.UsersController = Ember.ArrayController.extend(MyTalk.RequestHelper, {
  sortProperties:['name'],
  needs: ['list'],
  selectArray: [],
  selectedValue: null,
  userId: null,
  userName: null,
    
  call: function(user){
    if(user.get('online')){
      console.log("call "+user.get('id'));
      MyTalk.Router.router.transitionTo('call');
    }
    // TODO
  },


  
  loadSelect:function(){
    var temp=new Array();
      (MyTalk.List.find()).forEach(function(t){
        if(t.get('id')!=0 && t.get('id')!=1 ){
          temp.pushObject(Ember.Object.create({firstName: t.get('name'), id: t.get('id')}));
        }

     });
    this.set('this.selectArray',temp);
  }.property('selectArray'),

  
  addUser: function (userId){
    document.getElementById('adduser').style.display='block';
    var user = MyTalk.User.find(userId);
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
  doAddUser:function makeListUserAdd(){
    var currentListId=this.get('controllers.list.content.id');
    if(this.selectedValue!=null && this.selectedValue!=currentListId){
   
      if(currentListId!=1){
        var list=MyTalk.List.find(this.selectedValue);
        list.get('users').addObject(MyTalk.User.find(this.userId));

        //

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
  deleteUser:function(userId){
    var user = MyTalk.User.find(userId);
    var confirmation = confirm("Sei sicuro di eliminare l'utente " + user.get('fullName') +" dalla lista?");
    if(confirmation){
      var listId = this.get('controllers.list.content.id');
      if(listId==1){
        var currentList = MyTalk.List.find(listId).get('users');
        var generalList = MyTalk.List.find(0).get('users');
        currentList.removeObject( user );
        generalList.addObject( user );
      }
      else{
        var currentList = MyTalk.List.find(listId).get('users');
        currentList.removeObject( user );
      }
    }
  },
  userToBlacklist: function makeBlackListAdd(userId){
    var confirmation = confirm("Sei sicuro di mettere l'utente " + MyTalk.User.find(userId).get('fullName') +" nella Blacklist?");
    if(confirmation){
      var lists = MyTalk.List.find();
      lists.forEach(function(list) {
        list.get('users').removeObject( MyTalk.User.find(userId) );
        /*
        var ProcessorFactory = MyTalk.ProcessorFactory.create({});
        var processor = ProcessorFactory.createProcessorProduct( this.computeRequestName( arguments ) );
        processor.process({
          listId: listId,
          newName: newName,
          oldName: listName,
        });
        */
      });
      var ProcessorFactory = MyTalk.ProcessorFactory.create({});
      var processor = ProcessorFactory.createProcessorProduct( this.computeRequestName( arguments ) );
      processor.process({
        userId: userId,
      });
    } 
  }
});



MyTalk.CallController = Ember.ArrayController.extend({

  needs:['users'],
  messages:[],
  statistics:Ember.Object.create({duration:null,sentBytes:null,receivedBytes:null}),

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

   closeCall:function(){

    //MyTalk.Router.router.transitionTo('list');

  },
  


});
