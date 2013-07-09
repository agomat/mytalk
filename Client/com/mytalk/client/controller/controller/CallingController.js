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
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.3     | 2013-07-02 | SC        | [+] Aggiunta funzionalità salva statistiche
* 0.2     | 2013-06-07 | SC        | [+] Aggiunta funzionalità chat
* 0.1     | 2013-04-23 | MA        | [+] Scrittura classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*
* Controller deputato alla gestione di una chiamata e della chat, 
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
  * Proprietà necessaria a determinare se si è o meno in questo sottostato 
  * @property -isIncomingCall        
  * @type {Boolean}             
  *
  */ 

  isIncomingCall: Ember.computed.equal('callState','incomingCall'),
  
  /**
  * Proprietà necessaria a determinare se si è o meno in questo sottostato 
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
  * Proprietà che contiene un oggetto MyTalk.CallInfo al cui interno 
  * vengono salvate le statistiche di una chiamata
  * @property +stats      
  * @type {MyTalk.CallInfo}             
  *
  */

  stats: MyTalk.CallInfo.create({}),
  
  /**
  * Proprietà necessaria a mostrare nella vista la velocità attuale di connessione
  * @property +bitrate       
  * @type {String}             
  *
  */

  bitrate: null,

  /**
  * Proprietà necessaria a mostrare nella vista la latenza di connessione
  * @property +rtt       
  * @type {String}             
  *
  */

  rtt: null,

  /**
  * Proprietà che contiene l'oggetto della connessione peer to peer
  * @property -RTCmanager       
  * @type {PeerConnection}             
  *
  */

  RTCmanager: null,
  
  /**
  * Questo metodo viene invocato alla creazione di un'istanza di questo controller,
  * tale metodo garantisce l'aggiunta della callback $onclose$ dinamicamente.
  * Questa soluzione è stata pensata dai progettisti in quanto c'è la necessità di ottenere il $this$ cioè l'istanza attuale
  * del controller, in modo tale da poter salvare e reimpostare le statistiche delle chiamate.
  *
  * @method -init
  * @return {Void} 
  */

  init: function(){
    this._super();
    var context = this;
    this.onClose = function( isCaller ) {
      context.set('messages',[]);
      if( isCaller ) {
        context.saveStats();
      }
      context.clearStats();
      document.getElementById('closeCall').play();
      MyTalk.CallState.send('beingFree');
    };
  },

  /**
  * Viene invocato all'ingresso dell'utente nel "Route" $CallingRoute$ se $isIncomingCall$ ha valore _false_, quindi se l'utente 
  * è il chiamante. Crea il processore $UserCall$ che trasmette al server i dati necessari ad avviare la comunicazione,
  * tra i quali, l'utente da chiamare e i propri "Session Description Protocol" e "ICE candidates". Definisce le funzioni 
  * che inviano i pacchetti al server e che gestiscono l'arrivo di un messaggio nel canale dati. Infine chiama il 
  * metodo start di PeerConnection.
  *
  * @method +call
  * @param {User} user è l'oggetto utente che si sta chiamando.
  * @return {Void} 
  */

  call: function(user) {
    this.set('stats.caller',true);
    this.set('stats.speaker',user.get('id'));
    this.RTCmanager = MyTalk.PeerConnection.create();
    var processorFactory = MyTalk.ProcessorFactory.create({});
    var processor = processorFactory.createProcessorProduct("UserCall");

     // callback 1
    var context = this;
    var beforeCandidatesCreation = function() {

      // file-transfer
      window.RTCmanager = context.RTCmanager;

      var callData = Ember.Object.create({
        path: 'isBusy.outcomingCall',
        RTCmanager: context.RTCmanager, 
        speaker: user,
      });
      MyTalk.CallState.send('beingBusy', callData);
      context.set( 'stats.date', new moment().lang('it') );
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
      try{
        //console.debug( ">> mandante -> "+ message.data );
        var WARI = JSON.parse(message.data);
        if( typeof WARI.r !== "undefined" ) {
          // manda il WARI.r - esmimo senza mime type
          window.RTCmanager.send(JSON.stringify( window.FS.chunks[ WARI.r ] ));
          //console.log( "chunk n "+ WARI.r + " mandato" );
          // progess bar
          $.fn.progessBar(WARI.r);

          if(window.timeout) {
            clearTimeout(timeout);
            window.timeout = null;
          }
          window.timeout = setTimeout(function(){ if( !WARI.l ) { alert("Rimando il pacchetto n"+WARI.r); window.RTCmanager.send(JSON.stringify( window.FS.chunks[ WARI.r ] )); } }, 3000);

          if (WARI.l) {
            setTimeout(function(){
              $.fn.progessBar(-1);
              FS.reset();
            },3300);
          } 

        } else {
          // ricevente 
          if ( WARI.id == -1 ) {
            FS.filename = WARI.filename;
            FS.numOfChunksInFile = WARI.numOfChunks;
            $.fn.progessBar(0, FS.numOfChunksInFile, FS.filename);
          }
          else {
            FS.numOfChunksReceived++;
            FS.chunks[WARI.id] = Base64Binary.decode(WARI.chunk);
            $.fn.progessBar(WARI.id);
            //console.debug("Ricevuto "+ (WARI.id+1) +" chunk di "+FS.numOfChunksInFile);
            if( (WARI.id + 1) == FS.numOfChunksInFile ){
              //console.debug("Ho l'intero file formato da "+FS.numOfChunksInFile+ "chunks");
              FS.hasEntireFile = true;
              FS.saveFileLocally();
              FS.reset();
              setTimeout(function(){
                $.fn.progessBar(-1);
              },3300);
              return;
            } 
          }
          // richiedi un chunk id -> WARI.id+1
          var aux = new Object();
          aux.r = WARI.id+1;
          if( (aux.r+1) == FS.numOfChunksInFile ) aux.l = true; // notifica che è l'ultimo
          //console.log("Richiedo: "+aux.r);
          window.RTCmanager.send( JSON.stringify(aux) );
        }
      } catch(e) {
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
      }
    };
     
    this.RTCmanager.start(beforeCandidatesCreation,onCandidatesReady,this.onClose,onDataChannelMessage,true);
    this.getStats();
  },
  
  /**
  * Molto simile al metodo $call$. Gestisce l'arrivo di una chiamata da parte di un altro utente. Genera il processo $AcceptCall$.
  * Definisce una funzione che chiama i metodi $setSDP$ e $addICE$ di $PeerConnection$ per aggiungere i pacchetti ricevuti. Invia
  * i dati locali. Chiama il metodo $start$ di $PeerConnection$ con il booleano a false.
  *
  * @method +acceptCall 
  * @param {User} user è l'oggetto utente che ci sta contattando.
  * @return {Void} 
  */

  acceptCall: function(user){
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
    
      // file-transfer
      window.RTCmanager = context.RTCmanager;

      for(var i=0; i<RTCinfo.ice.length; ++i) {
        local.addICE( RTCinfo.ice[i] );
      }

      var callData = Ember.Object.create({
        isCaller: false
      });

      MyTalk.CallState.send('beingConnected',callData);
      context.set( 'stats.date', new moment().lang('it') );
    };

    // callback 2
    var onCandidatesReady = function(RTCinfo) {
      processor.process({
        speaker: user,
        RTCinfo: JSON.stringify(RTCinfo) 
      });
    };

   var onDataChannelMessage = function(message) {
      try{
        //console.debug( ">> mandante -> "+ message.data );
        var WARI = JSON.parse(message.data);
        if( typeof WARI.r !== "undefined" ) {
          // manda il WARI.r - esmimo senza mime type
          window.RTCmanager.send(JSON.stringify( window.FS.chunks[ WARI.r ] ));
          //console.log( "chunk n "+ WARI.r + " mandato" );
          // progess bar
          $.fn.progessBar(WARI.r);

          if(window.timeout) {
            clearTimeout(timeout);
            window.timeout = null;
          }
          window.timeout = setTimeout(function(){ if( !WARI.l ) { alert("Rimando il pacchetto n"+WARI.r); window.RTCmanager.send(JSON.stringify( window.FS.chunks[ WARI.r ] )); } }, 3000);

          if (WARI.l) {
            setTimeout(function(){
              $.fn.progessBar(-1);
              FS.reset();
            },3300);
          } 

        } else {
          // ricevente 
          if ( WARI.id == -1 ) {
            FS.filename = WARI.filename;
            FS.numOfChunksInFile = WARI.numOfChunks;
            $.fn.progessBar(0, FS.numOfChunksInFile, FS.filename);
          }
          else {
            FS.numOfChunksReceived++;
            FS.chunks[WARI.id] = Base64Binary.decode(WARI.chunk);
            $.fn.progessBar(WARI.id);
            //console.debug("Ricevuto "+ (WARI.id+1) +" chunk di "+FS.numOfChunksInFile);
            if( (WARI.id + 1) == FS.numOfChunksInFile ){
              //console.debug("Ho l'intero file formato da "+FS.numOfChunksInFile+ "chunks");
              FS.hasEntireFile = true;
              FS.saveFileLocally();
              FS.reset();
              setTimeout(function(){
                $.fn.progessBar(-1);
              },3300);
              return;
            } 
          }
          // richiedi un chunk id -> WARI.id+1
          var aux = new Object();
          aux.r = WARI.id+1;
          if( (aux.r+1) == FS.numOfChunksInFile ) aux.l = true; // notifica che è l'ultimo
          //console.log("Richiedo: "+aux.r);
          window.RTCmanager.send( JSON.stringify(aux) );
        }
      } catch(e) {
      var msg = context.get('messages');
      var temp = [];

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
    }
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
    MyTalk.CallState.get('isBusy').set('accepted', true);
    if(this.get('isConnected')) {
      this.RTCmanager.closeConnection(this.onClose);
    }
    else {
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
      speaker: stats.get('speaker'),
      caller: stats.get('caller'),
      startDate: stats.get('date'),
      duration: stats.get('duration'),
      sentBytes: stats.get('sentBytes'),
      receivedBytes: stats.get('receivedBytes')
    });

  },
 
 /**
  * Questo metodo si occupa di reimpostare il campo dati contenente le statistiche
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
  * Il metodo si occupa di: aggiornare l'array $messages$ del controller inserendo i messaggi inviati,  
  * invocare il metodo $send$ di $RTCmanager$ necessario ad inviare il messaggio all'utente con il quale si sta comunicando e
  * di effettuare lo scroll automatico della chat una volta aggiornato l'array $message$.
  * Lo scroll automatico viene eseguito al fine di avere sempre in primo piano l'ultimo messaggio.
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
    }
    else {
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
  * Questo metodo si occupa di ottenere dall'oggetto $RTCmanager$ le statistiche della connessione peer.
  * Per fare questo il metodo sfrutta il metodo omonimo $getStats()$, proprietario dell'oggetto $RTCmanager.pc$.
  * Il metodo ottiene tutti i dati necessari, i byte ricevuti/trasmessi e la velocità istantanea; inoltre si occupa
  * di avviare il timer necessario al conteggio della durata della chiamata.
  * Il tutto viene eseguito ogni secondo in modo da garantire una giusta efficienza nel campionamento e nel conteggio
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
      var display = function(b,r) {
        context.set('bitrate',b);
        context.set('rtt',r);
      }
      if(context.RTCmanager.pc){
        context.RTCmanager.pc.getStats(function(stats) {
          var results = stats.result();
          var bitRate = rtt = 0;
          context.get('stats').set('duration',context.get('stats.duration')+1);
          if ( context.RTCmanager.get('webrtcDetectedBrowser') == 'chrome' ) {
            for (var i = 0; i < results.length; ++i) {
              var res = results[i];
              if(res.type=='ssrc' && res.stat('googFrameHeightSent')){
                context.get('stats').set('sentBytes',res.stat('bytesSent'));
                rtt = res.stat('googRtt');
              }
              if (res.type == 'ssrc' && res.stat('googFrameHeightReceived')) {
                context.get('stats').set('receivedBytes',res.stat('bytesReceived'));
                var bytesNow = res.stat('bytesReceived');
                if (timestampPrev > 0) {
                  bitRate = Math.round((bytesNow - bytesPrev) * 8 /
                  (res.timestamp - timestampPrev));
                }
                timestampPrev = res.timestamp;
                bytesPrev = bytesNow;
              }
            }
          }

          display(bitRate,rtt);
        });
      }
      else{
        clearInterval(statCollector);
      }
    }, 1000);
  }
});