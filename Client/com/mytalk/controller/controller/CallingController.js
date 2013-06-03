MyTalk.CallingController = Ember.ObjectController.extend({
  RTCinfo: MyTalk.CallState.get('isBusy').get('RTCinfo'), // TODO: sistemare con un binding?
  callState: null,
  callStateBinding: Ember.Binding.oneWay('MyTalk.CallState.currentState.name'),
  isIncomingCall: Ember.computed.equal('callState','incomingCall'),
  isConnected: Ember.computed.equal('callState','isConnected'),
  messages: [],

  acceptCall: function(user){
    var RTCmanager = MyTalk.PeerConnection.create({});
    var ProcessorFactory = MyTalk.ProcessorFactory.create({});
    var processor = ProcessorFactory.createProcessorProduct("AcceptCall");

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

    RTCmanager.start(beforeCandidatesCreation,onCandidatesReady);
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