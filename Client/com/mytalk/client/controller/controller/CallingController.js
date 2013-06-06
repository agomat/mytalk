/**
* Filename: CallingController.js
* Package: com.mytalk.client.controller.controller
* Dependencies: com.mytalk.client.controller.statemanager.CallState
*               com.mytalk.client.controller.comunicator.PeerConnection
*               com.mytalk.client.model.storage.processing.ProcessorFactory
*               com.mytalk.client.model.modelstruct.WCall
*               com.mytalk.client.model.modelstruct.Call
* Author: Agostinetto Mattia
* Date: 2013-04-23
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-04-23 | MA        | [+] Scrittura classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

MyTalk.CallingController = Ember.ObjectController.extend({
  callState: null,
  callStateBinding: Ember.Binding.oneWay('MyTalk.CallState.currentState.name'),
  isIncomingCall: Ember.computed.equal('callState','incomingCall'),
  isConnected: Ember.computed.equal('callState','isConnected'),
  msgs: [],
  RTCmanager: undefined,
  
  call: function(user) {
    //TODO verificare se sono impegnato in altra conversazione
    this.RTCmanager = MyTalk.PeerConnection.create();
    var processorFactory = MyTalk.ProcessorFactory.create({});
    var processor = processorFactory.createProcessorProduct("UserCall");

    // callback 1
    var context = this;
    var beforeCandidatesCreation = function() {
      var callData = Ember.Object.create({
        path: 'isBusy.outcomingCall',
        RTCmanager: context.RTCmanager, 
        speaker: user,
      });
      MyTalk.CallState.send('beingBusy', callData);
    };

    // callback 2
    var onCandidatesReady = function(RTCinfo) {
      processor.process({
        speaker: user,
        RTCinfo: JSON.stringify(RTCinfo) 
      });
    };
  
    this.RTCmanager.start(beforeCandidatesCreation,onCandidatesReady,this.onClose,this.onMessage,true);
  },
  acceptCall: function(user){
    this.RTCmanager = MyTalk.PeerConnection.create();
    var processorFactory = MyTalk.ProcessorFactory.create({});
    var processor = processorFactory.createProcessorProduct("AcceptCall");

    // callback 1
    var beforeCandidatesCreation = function(local) {
      var RTCinfo = MyTalk.CallState.get('isBusy').get('callData').RTCinfo;
      local.setSDP( RTCinfo.sdp );
      
      for(var i=0; i<RTCinfo.ice.length; ++i) { //TODO possibilità di delegare il ciclo for
       local.addICE( RTCinfo.ice[i] );
      }

      var callData = Ember.Object.create({
        isCaller: false
      });

      MyTalk.CallState.send('beingConnected',callData);
    };

    // callback 2
    var onCandidatesReady = function(RTCinfo) {
      processor.process({
        speaker: user,
        RTCinfo: JSON.stringify(RTCinfo) 
      });
    };

    this.RTCmanager.start(beforeCandidatesCreation,onCandidatesReady,this.onClose,this.onMessage,false);
  },

  closeCall: function(user){
    /*
    // TODO: rifiutare la chiamata
    var processorFactory = MyTalk.ProcessorFactory.create({});
    var processor = processorFactory.createProcessorProduct("RefuseCall");
    processor.process({});
    */
    // patch:
    if(this.get('isConnected')) {
      this.RTCmanager.closeConnection(this.onClose);
    }
    else {
      //GESTIRE RIFIUTA CHIAMATA
    }
  },
  
  onClose: function() {
    context.msgs = [];
    MyTalk.CallState.send('beingFree');
  },
  
  sendMessage: function(message) {
    this.onMessage(message);
    this.RTCmanager.send(message);
  },
  
  onMessage: function(message) {
    var context = this;
    window.ciao = context.msgs;
    context.msgs.push(message);
  }

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