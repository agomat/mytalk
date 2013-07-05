/**
* Filename: CallingController.js
* Package: com.mytalk.client.controller.controller
* Dependencies: com.mytalk.client.controller.controller.ChatMessage
*          com.mytalk.client.controller.controller.CallInfo
*          com.mytalk.client.controller.statemanager.CallState
*          com.mytalk.client.controller.comunicator.PeerConnection
*          com.mytalk.client.model.storage.processing.ProcessorFactory
*          com.mytalk.client.model.modelstruct.WCall
*          com.mytalk.client.model.modelstruct.Call
*          com.mytalk.client.model.storage.processing.processor.outcoming.UserCall
*          com.mytalk.client.model.storage.processing.processor.outcoming.AcceptCall
*          com.mytalk.client.model.storage.processing.processor.outcoming.RefuseCall
* Author: Agostinetto Mattia
* Date: 2013-04-23
*
* Diary:
*
| Version | Date     | Developer | Changes
* --------+------------+-----------+------------------
* 0.3    | 2013-07-02 | SC      | [+] Aggiunta funzionalità salva statistiche
* 0.2    | 2013-06-07 | SC      | [+] Aggiunta funzionalità chat
* 0.1    | 2013-04-23 | MA      | [+] Scrittura classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*
* Controller deputato alla gestione di una chiamata e della chat realativa, 
* gestisce inoltre il template $calling$
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
  * Proprietà che contine un oggetto MyTalk.CallInfo al cui interno 
  * vengono salvate le statistiche di una chimata
  * @property +stats      
  * @type {MyTalk.CallInfo}             
  *
  */

  stats:MyTalk.CallInfo.create({}),
  
  /**
  * Proprietà necessaria a mostrare nella vista la velocità attuale di connessione
  * @property +bitrate       
  * @type {String}             
  *
  */

  bitrate:null,

  /**
  * Proprietà che contiente la connessione peer
  * @property -RTCmanager       
  * @type {PeerConnection}             
  *
  */

  RTCmanager: undefined,
  
  /**
  * Questo metodo viene invocato alla creazione di un'istanza di questo controller,
  * tale metodo garantisce l'aggiunta della callback $onclose$ dinamicamente.
  * Questa soluzione è stata pensata dai progettisti in quanto c'è la necessità di ottenere il $this$ cioè l'istanza attuale
  * del controller, in modo tale da poter salvare e resettare le statistiche delle chiamate.
  *
  * @method -init
  * @return {Void} 
  */

  init: function(){
   this._super();
   var context = this;
   this.onClose = function() {
    context.saveStats();
    context.clearStats();
    document.getElementById('closeCall').play();
    MyTalk.CallState.send('beingFree');
   };
  },

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
   var time=new moment().lang('it');
   this.set('stats.date',time);
   this.set('stats.sender',true);
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
    document.getElementById('modem-dial').play();
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
   this.getStats();
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
   var time=new moment().lang('it');
   this.set('stats.date',time);
   this.set('stats.sender',false);
   MyTalk.CallState.get('isBusy').set('accepted', true);
   document.getElementById('ring').pause();
   this.RTCmanager = MyTalk.PeerConnection.create();
   var processorFactory = MyTalk.ProcessorFactory.create({});
   var processor = processorFactory.createProcessorProduct("AcceptCall");
   var context = this;

   // callback 1
   var beforeCandidatesCreation = function(local) {
    var RTCinfo = MyTalk.CallState.get('isBusy').get('callData').RTCinfo;
    local.setSDP( RTCinfo.sdp );
    
    for(var i=0; i<RTCinfo.ice.length; ++i) {
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
   this.getStats();
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
   MyTalk.CallState.get('isBusy').set('accepted', true);
   if(this.get('isConnected')) {
    this.RTCmanager.closeConnection(this.onClose);
    
//     MyTalk.CallState.send('beingFree');
   }
   else {
    //GESTIRE RIFIUTA CHIAMATA
    var RTCinfo = MyTalk.CallState.get('isBusy').get('callData').RTCinfo;
    var processorFactory = MyTalk.ProcessorFactory.create({});
    var processor = processorFactory.createProcessorProduct("RefuseCall");
    processor.process({
      speaker: user,
      RTCinfo: JSON.stringify(RTCinfo) 
    });
    this.onClose();
   }
  },

  
 /**
  * Questo metodo si occupa di salvare le statistiche dell'ultima chiamata ricevuta/effettuata.
  * Una volta invocato, questo metodo,crea un'istanza del processore adeguato 
  * al quale sarà delegato il lavoro di salvare nel model le statistiche ottenute.
  *
  * @method -saveStats
  * @return {Void} 
  */


  saveStats:function(){
   var processorFactory = MyTalk.ProcessorFactory.create({});
   var processor = processorFactory.createProcessorProduct( "AddCall" );
   var stats=this.get('stats');
    processor.process({
      //speaker: stats.get('speaker'),
      startDate: stats.get('date'),
      duration:stats.get('duration'),
      sentBytes:stats.get('sentBytes'),
      receivedBytes:stats.get('receivedBytes')
    });

  },
 
 /**
  * Questo metodo si occupa di di resettare il campo dati contenente le statistiche
  * delle chiamate ricevute/effettuate.
  *
  * @method -clearStats
  * @return {Void} 
  */

  clearStats:function(){
   this.set('stats',MyTalk.CallInfo.create({}));
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
    
  },
  
  
  /**
  * Questo metodo si occupa di ottenere dall'oggetto RTCmanager le statistiche della connessione peer.
  * per fare questo il metodo sfrutta il metodo ononimo $getStats()$, proprietario dell'oggetto $RTCmanager.pc$
  * il metodo ottiene tutti i dati necessari, i byte ricevuti/trasmessi e la velocità istantanea; inoltre si occupa
  * di avviare il timer necessario all conteggio della durata della chiamata.
  * il tutto viene eseguito ogni secondo in modo da garantire una giusta efficienza nel campionamento e nel conteggio
  * dei secondi della durata di una chiamata.
  *
  * @method -getStats
  * @return {Void} 
  */


  getStats: function() {
   var bytePrev = 0;
   var timestampPrev = 0;
   var context = this;
   var statCollector = setInterval(function() {
    var display = function(str) {
      context.set('bitrate',str);
    }
    if(context.RTCmanager.pc){
    context.RTCmanager.pc.getStats(function(stats) {
      var statsString = '';
      var results = stats.result();
      var bitrateText = 'N/A';
      context.get('stats').set('duration',context.get('stats.duration')+1);
      for (var i = 0; i < results.length; ++i) {
       var res = results[i];
        if(res.type=='ssrc' && res.stat('googFrameHeightSent')){
          context.get('stats').set('sentBytes',res.stat('bytesSent'));
        }
        if (res.type == 'ssrc' && res.stat('googFrameHeightReceived')) {
          context.get('stats').set('receivedBytes',res.stat('bytesReceived'));
          var bytesNow = res.stat('bytesReceived');
          if (timestampPrev > 0) {
           var bitRate = Math.round((bytesNow - bytesPrev) * 8 /
            (res.timestamp - timestampPrev));
           bitrateText = bitRate + ' kbits/sec';
          }
          timestampPrev = res.timestamp;
          bytesPrev = bytesNow;
        }
      }
      display(bitrateText);
    });
    }
   else{
    clearInterval(statCollector);
   }
   }, 1000);
  }
});

Ember.Handlebars.registerBoundHelper('date',function(date){
  if(date!=null){
   return date.format("HH:mm:ss");
  }
});


Ember.Handlebars.registerBoundHelper('hourconversion',function(seconds){
  var hstring="";
  var h=parseInt(seconds/3600);
  seconds=seconds-(h*3600);
  var min = parseInt(seconds/60);
  seconds=seconds-(min*60);
  if(h>0){
    if(h<10){
      hstring+="0"+h+"h ";
    }
    else{
      hstring+=h+"h ";
    }
  }
  if(min>0){
    if(min<10){
      hstring+="0"+min+"m ";
    }
    else{
      hstring+=min+"m ";
    }
  }
  if(seconds>0){
    hstring+=seconds+"s ";
  }
  return hstring;
 
});


 

Ember.Handlebars.registerBoundHelper('conversion',function(bytes){

   var precision=2;
   var kilobyte = 1024;
   var megabyte = kilobyte * 1024;
   var gigabyte = megabyte * 1024;
   var terabyte = gigabyte * 1024;
  
   if ((bytes >= 0) && (bytes < kilobyte)) {
      return bytes + ' B';
 
   } else if ((bytes >= kilobyte) && (bytes < megabyte)) {
      return (bytes / kilobyte).toFixed(precision) + ' KB';
 
   } else if ((bytes >= megabyte) && (bytes < gigabyte)) {
      return (bytes / megabyte).toFixed(precision) + ' MB';
 
   } else if ((bytes >= gigabyte) && (bytes < terabyte)) {
      return (bytes / gigabyte).toFixed(precision) + ' GB';
 
   } else if (bytes >= terabyte) {
      return (bytes / terabyte).toFixed(precision) + ' TB';
 
   } else {
      return bytes + ' B';
   }

});