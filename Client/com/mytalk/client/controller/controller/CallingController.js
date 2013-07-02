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

  stats:MyTalk.CallInfo.create({}),

  bitrate:null,

  r:null,

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
      
//       MyTalk.CallState.send('beingFree');
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
  
  onClose: function() {
//     this.set('messages',[]);
    document.getElementById('closeCall').play();
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
     
  },

  getStats: function(connection) {
// Display statistics
var bytePrev = 0;
var timestampPrev = 0;

var context = this;

var statCollector = setInterval(function() {
  var display = function(str) {
    context.set('bitrate',str);
  }
  console.log('stats');
      context.RTCmanager.pc.getStats(function(stats) {
        var statsString = '';
        var results = stats.result();
        var bitrateText = 'No bitrate stats';
        for (var i = 0; i < results.length; ++i) {
          var res = results[i];
          statsString += '<h3>Report ';
          statsString += i;
          statsString += '</h3>';
            statsString += dumpStats(res,context);
            // The bandwidth info for video is in a type ssrc stats record
            // with googFrameHeightReceived defined.
            // Should check for mediatype = video, but this is not
            // implemented yet.
            if (res.type == 'ssrc' && res.stat('googFrameHeightReceived')) {
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
        document.getElementById('stats').innerHTML = statsString;
        //$('#stats').append(statsString);
         //$('#stats').append(bitrateText);
        display(bitrateText);
      });

}, 1000);

// Dumping a stats variable as a string.
// might be named toString?
function dumpStats(obj,c) {
  var context=c;
  var statsString = 'Timestamp:';
  var d;
  statsString += obj.timestamp;
  if (obj.id) {
     statsString += "<br>id ";
     statsString += obj.id;
  }
  if (obj.type) {
     statsString += " type ";
     statsString += obj.type;
  }
  if (obj.names) {
    names = obj.names();
    for (var i = 0; i < names.length; ++i) {
      console.log(names[i]+" "+i+"\n");
       statsString += '<br>';
       statsString += names[i];
       statsString += ':';
       statsString += obj.stat(names[i]);
       //c+= obj.stat(names[i]);
       if(i==4){
        d+=obj.stat(names[i]);
       }
       if(i==8){
       d+=obj.stat(names[i]);
       context.set('r',d);
     }
       //f.set('receivedBytes',obj.stat(names[i]));
    }
  } else {
    if (obj.stat('audioOutputLevel')) {
      statsString += "audioOutputLevel: ";
      statsString += obj.stat('audioOutputLevel');
      statsString += "<br>";
    }
  }
  return statsString;
}
  }

});

Ember.Handlebars.registerBoundHelper('date',function(date){
  return date.format("HH:mm:ss");
});
