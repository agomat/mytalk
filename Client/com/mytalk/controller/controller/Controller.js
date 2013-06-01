MyTalk.IndexController = Ember.ObjectController.extend(MyTalk.RequestHelper, {
  appStateBinding: Ember.Binding.oneWay('MyTalk.AppState.currentState.name'),
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

  call: function(user){

    //TODO verificare se sono impegnato in altra conversazione

    var RTCmanager = MyTalk.PeerConnection.create({});
    var ProcessorFactory = MyTalk.ProcessorFactory.create({});
    var processor = ProcessorFactory.createProcessorProduct("UserCall");

    var beforeCandidatesCreation = function() {
      MyTalk.CallState.send('beingBusy',{
        path: 'isBusy.outcomingCall',
        RTCmanager: RTCmanager,
        RTCinfo: {
          myIP: null, //           inutili!
          myUserId: null,
          myRTCinfo: null,
          speakerIp: user.get('ip'), // serve?
          speakerUserId: user.get('id'),
          speakerRTCinfo: "WTF inutility"  // serve?
        }
      });
    };

    var onCandidatesReady = function(RTCinfo) {
      processor.process({
        callee: user,
        RTCinfo: JSON.stringify(RTCinfo) 
      });
    };
  
    RTCmanager.start(beforeCandidatesCreation,onCandidatesReady,true);
  },

  cantCall: function(user){
    alert('Non puoi chiamare '+ user.get('fullName') +' poiché è offline');
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
  RTCinfo: MyTalk.CallState.get('isBusy').get('RTCinfo'),
  callState: null,
  callStateBinding: Ember.Binding.oneWay('MyTalk.CallState.currentState.name'),
  isIncomingCall: Ember.computed.equal('callState','incomingCall'),
  isConnected: Ember.computed.equal('callState','isConnected'),
  messages: [],

  acceptCall: function(user){
    var RTCmanager = MyTalk.PeerConnection.create({});
    var ProcessorFactory = MyTalk.ProcessorFactory.create({});
    var processor = ProcessorFactory.createProcessorProduct("AcceptCall");
    var context = this;

    var beforeCandidatesCreation = function(local) {
      // adding caller ice candidates
      //var RTCinfo = MyTalk.CallState.get("isBusy").get('RTCinfo'); // TODO ugly
      //var RTCinfo = caRTCinfollInfo.RTCinfo.speakerRTCinfo; // TODO ugly
      //alert(context.get('RTCinfo'));

      var RTCinfo = MyTalk.CallState.get('isBusy').get('RTCinfo');
      //alert(JSON.stringify(RTCinfo));

      local.setSDP( RTCinfo.speakerRTCinfo.sdp );
      //local.setSDP( context.get('RTCinfo').speakerRTCinfo.sdp );
      //console.debug("ADD SDP "+ JSON.stringify(RTCinfo.speakerRTCinfo.sdp) );
      
      for(var i=0; i<RTCinfo.speakerRTCinfo.ice.length; ++i) { //TODO possibilità di delegare il ciclo for
       local.addICE( RTCinfo.speakerRTCinfo.ice[i] );
       //console.debug("ADD ICE "+ JSON.stringify(RTCinfo.speakerRTCinfo.ice[i]) );
      }

      MyTalk.CallState.send('beingBusy',{ path: 'isBusy.isConnected' });
    };

    var onCandidatesReady = function(RTCinfo) {
      processor.process({
        caller: user,
        RTCinfo: JSON.stringify(RTCinfo) 
      });
    };

    RTCmanager.start(beforeCandidatesCreation,onCandidatesReady,false);
  },

  closeCall: function(user){
    /*
    // TODO: rifiutare la chiamata
    var ProcessorFactory = MyTalk.ProcessorFactory.create({});
    var processor = ProcessorFactory.createProcessorProduct("RefuseCall");
    processor.process({});
    */
    // patch:

    MyTalk.CallState.send('beingFree');
    MyTalk.Router.router.transitionTo("list",MyTalk.List.find(0));
  },

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