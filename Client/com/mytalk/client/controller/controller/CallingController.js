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
*
* Controller deputato alla gestione di una chiamata e della chat realativa, 
* gestisce in oltra il template $calling$
*
*/

MyTalk.CallingController = Ember.ObjectController.extend({

  /**
   * Proprietà necessaria per immagazzinare lo stato corrente  della chiamata
   * @property -callState           
   * @type {String}                   
   *
  */  

  callState: null,
  /**
   * Proprietà necessaria per la gestione dello stato dell'applicazione durante la chiamata 
   * @property -callStateBinding           
   * @type {Binding}                   
   *
  */ 
  callStateBinding: Ember.Binding.oneWay('MyTalk.CallState.currentState.name'),
  
  /**
   * Proprietà necessaria determinare se si è o meno in questo sottostato 
   * @property -isIncomingCall           
   * @type {Boolean}                   
   *
  */ 

  isIncomingCall: Ember.computed.equal('callState','incomingCall'),
  
  /**
   * Proprietà necessaria determinare se si è o meno in questo sottostato 
   * @property - isConnected           
   * @type {Boolean}                   
   *
  */

  isConnected: Ember.computed.equal('callState','isConnected'),
  
  /**
   * Proprietà che contiene tutti i messaggi scambiati in chat 
   * @property +messages           
   * @type {Array<ChatMessage>}                   
   *
  */

  messages: [],

  /**
   * Proprietà che contiente la connessione peer
   * @property -RTCmanager          
   * @type {PeerConnection}                   
   *
  */

  RTCmanager: undefined,
  
  /**
   * Viene invocato all'ingresso dell'utente nel route CallingRoute se isIncomingCall è falso, quindi se l'utente 
   * è il chiamante. Crea il processo UserCall che trasmette al server i dati necessari ad avviare la comunicazione
   * come l'utente da chiamare e i propri "Session Description Protocol" e "ICE candidates". Definisce le funzioni 
   * che inviano i pacchetti al server e che gestiscono l'arrivo di un messaggio nel canale dati. Infine chiama il 
   * metodo start di PeerConnection.
   *
   * @method +call
   * @param {User} user è l'oggetto utente che si sta chiamando.
   * @return {Void} 
  */

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
  
  /**
   * Molto simile al metodo call. Gestisce l'arrivo di una chiamata da parte di un altro utente. Genera il processo AcceptCall.
   * Definisce una funzione che chiama i metodi setSDP e addICE di PeerConnection per aggiungere i pacchetti ricevuti. Invia
   * i dati locali. Chiama il metodo start di PeerConnection ma qui con il booleano a false.
   *
   * @method +acceptCall 
   * @param {User} user è l'oggetto utente che ci sta contattando.
   * @return {Void} 
  */

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

  /**
   * Questo metodo si occupa di terminare una chiamata chiudendo la connessione peer e svuotando l'array $messages$ 
   * contenente i messaggi scambiati in chat.
   *
   * @method +closeCall 
   * @param {User} user è l'oggetto utente che ci sta contattando.
   * @return {Void} 
  */

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
//       MyTalk.CallState.send('beingFree');
    }
    else {
      //GESTIRE RIFIUTA CHIAMATA
    }
  },
  
  onClose: function() {
//     this.set('messages',[]);
    MyTalk.CallState.send('beingFree');
  },

  /**
   * Questo metodo si occupa di inviare i messaggi scritti nella chat e di gestire il comportamento della vista della chat.
   * Il metodo si occupa di: aggiornare l'array messages del controller inserendo i messaggi inviati,  
   * invocare il metodo $send$ dell'RTCmanager necessario ad inviare il messaggio all'utente con il quale si sta comunicando e
   * di effettuare lo scroll automatico della chat una volta aggiornato l'array message.
   * Lo scroll automatico viene eseguito al fine di avere sempre in primo piano l'utlimo messaggio.
   *
   * @method +sendMessage 
   * @param {String} message è il testo del messaggio inviato.
   * @return {Void} 
  */

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
