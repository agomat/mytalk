/**
* Filename: UsersController.js
* Package: com.mytalk.client.controller.controller
* Dependencies: com.mytalk.client.model.storage.processing.ProcessorFactory
*               com.mytalk.client.controller.statemanager.CallState
*               com.mytalk.client.controller.comunicator.PeerConnection
*               com.mytalk.client.controller.controller.LoggedController
*               com.mytalk.client.controller.controller.ListController
*               com.mytalk.client.model.modelstruct.List
*               com.mytalk.client.model.modelstruct.User
* Author: Campese Stefano
* Date: 2013-04-23
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-04-23 | SC        | [+] Scrittura classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

MyTalk.UsersController = Ember.ArrayController.extend({
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

  userCall: function(user){

    //TODO verificare se sono impegnato in altra conversazione

    var RTCmanager = MyTalk.PeerConnection.create({});
    var processorFactory = MyTalk.ProcessorFactory.create({});
    var processor = processorFactory.createProcessorProduct("UserCall");

    // callback 1
    var beforeCandidatesCreation = function() {
      var callData = Ember.Object.create({
        path: 'isBusy.outcomingCall',
        RTCmanager: RTCmanager, 
        speaker: user,
      });

      MyTalk.CallState.send('beingBusy',callData);
    };

    // callback 2
    var onCandidatesReady = function(RTCinfo) {
      processor.process({
        speaker: user,
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
        var processorFactory = MyTalk.ProcessorFactory.create({});
        var processor = processorFactory.createProcessorProduct("ListUserAdd");
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
        var processorFactory = MyTalk.ProcessorFactory.create({});
        var processor = processorFactory.createProcessorProduct("ListUserRemove");
        processor.process({
          userId: userId,
          list: list 
        });
      }
    }
  },
  userToBlacklist: function (user){
    var confirmation = confirm("Sei sicuro di mettere l'utente " + user.get('fullName') +" nella Blacklist?");
    var userId = user.get('id');
    if(confirmation){
      var lists = MyTalk.List.find().toArray();
      lists = lists.rejectProperty('id',0);
      lists.forEach(function(list) {
          var processorFactory = MyTalk.ProcessorFactory.create({});
          var processor = processorFactory.createProcessorProduct("ListUserRemove");
          processor.process({
            userId: userId,
            list: list 
          });
      });
      var processorFactory = MyTalk.ProcessorFactory.create({});
      var processor = processorFactory.createProcessorProduct( "BlackListAdd" );
      processor.process({
        userId: userId,
      });
    } 
  }
});