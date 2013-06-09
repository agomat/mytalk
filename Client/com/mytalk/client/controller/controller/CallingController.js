/**
* Filename: CallingController.js
* Package: com.mytalk.client.controller.controller
* Dependencies: com.mytalk.client.controller.controller.ChatMessage
*               com.mytalk.client.controller.statemanager.CallState
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
* 0.2     | 2013-06-07 | SC        | [+] Aggiunta funzionalità chat
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
  messages: [],
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

    var onDataChannelMessage = function(message) {
      
      var msg=context.get('messages');
      var temp=[];

      msg.forEach(function(t){
           temp.pushObject(t);
      });
      temp.pushObject(MyTalk.ChatMessage.create({text:message.data,sent:false,date:new moment()}));
      context.set('messages',temp);
      
      Ember.run.later(this, function(){
      $("#messages").animate({
        scrollTop:$("#messages")[0].scrollHeight - $("#messages").height()
        },300);
      }, 300);
  
    };
    
    
    this.RTCmanager.start(beforeCandidatesCreation,onCandidatesReady,this.onClose,onDataChannelMessage,true);
  },
  
  acceptCall: function(user){
    this.RTCmanager = MyTalk.PeerConnection.create();
    var processorFactory = MyTalk.ProcessorFactory.create({});
    var processor = processorFactory.createProcessorProduct("AcceptCall");
    var context = this;

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

    var onDataChannelMessage = function(message) {
      var msg=context.get('messages');
      var temp=[];

      msg.forEach(function(t){
           temp.pushObject(t);
      });
      temp.pushObject(MyTalk.ChatMessage.create({text:message.data,sent:false,date:new moment()}));
      context.set('messages',temp);
      
      Ember.run.later(this, function(){
      $("#messages").animate({
        scrollTop:$("#messages")[0].scrollHeight - $("#messages").height()
        },300);
      }, 300);
  
    };

    this.RTCmanager.start(beforeCandidatesCreation,onCandidatesReady,this.onClose,onDataChannelMessage,false);
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
      this.set('messages',[]);
      MyTalk.CallState.send('beingFree');
    }
    else {
      //GESTIRE RIFIUTA CHIAMATA
    }
  },
  
  onClose: function() {
//     this.set('messages',[]);
//     MyTalk.CallState.send('beingFree');
  },

  sendMessage:function(message){
    var context=this.get('messages');
    var temp=[];

    if(context){
      context.forEach(function(t){
        temp.pushObject(t);
      });
      temp.pushObject(MyTalk.ChatMessage.create({text:message,sent:true,date:new moment()}));
      this.RTCmanager.send(message);
      this.set('messages',temp);
    } else {
      temp.pushObject(MyTalk.ChatMessage.create({text:message,sent:true,date:new moment()}));
      this.RTCmanager.send(message);
      this.set('messages',temp);
      
    }
    
    Ember.run.later(this, function(){
      $("#messages").animate({
        scrollTop:$("#messages")[0].scrollHeight - $("#messages").height()
        },300);
    }, 300);
     
  }

});

Ember.Handlebars.registerBoundHelper('date',function(date){
  return date.format("HH:mm:ss");
});