/**
* Filename: PeerConnection.js
* Package: com.mytalk.client.communicator
* Dependencies: 
* Author: Griggio Massimo
* Date: 2013-04-23
*
* Diary:
*
| Version | Date     | Developer | Changes
* --------+------------+-----------+------------------
* 0.1   | 2013-04-23 | MG    | [+] Scrittura classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*
* La classe si occupa di interfacciarsi con le APIs WebRTC. Fornisce dei metodi per gestire 
* la connessione peer to peer tra i client. Inoltre agisce da adattatore per permettere sia
* al browser Google Chrome che a Mozilla Firefox di utilizzare l'applicazione, scegliendo in
* base al browser identificato i metodi corretti di WebRTC da invocare, dato che le APIs non 
* sono ancora stabili e non sono state implementate secondo lo standard definito, ma in modo 
* diverso da ogni browser.
*/

MyTalk.PeerConnection = Ember.Object.extend({
  
  RTCPeerConnection: undefined,
  getUserMedia: undefined,
  attachMediaStream: undefined,
  reattachMediaStream: undefined,
  webrtcDetectedBrowser: undefined,
  pc: undefined,
  configuration: undefined,
  isCaller: false,
  opts: {'optional': [{'DtlsSrtpKeyAgreement': true}, {'RtpDataChannels': true }]},
  dataChannelOpts: undefined,
  setDataChannelBinaryType: function(channel) { },
  dataChannel: undefined,
  myStream: undefined,
  mySDP: undefined,
  myICE: [],
  
  init: function() {
    this._super();
    
    if (navigator.mozGetUserMedia) {
      this.webrtcDetectedBrowser = "firefox";
      
      this.RTCPeerConnection = mozRTCPeerConnection;
      this.configuration = {iceServers: [{url: 'stun:216.93.246.18:3478'}, {url: 'stun:66.228.45.110:3478'}, {url: 'stun:173.194.78.127:19302'}]};
      this.RTCSessionDescription = mozRTCSessionDescription;
      this.dataChannelOpts = {reliable: true};
      this.RTCIceCandidate = mozRTCIceCandidate;
      
      this.setDataChannelBinaryType = function(channel) {
        channel.binaryType = 'blob';
      };

      this.getUserMedia = navigator.mozGetUserMedia.bind(navigator);

      this.attachMediaStream = function(element, stream) {
        element.mozSrcObject = stream;
      };

      this.reattachMediaStream = function(to, from) {
        to.mozSrcObject = from.mozSrcObject;
      };

      MediaStream.prototype.getVideoTracks = function() {
        return [];
      };

      MediaStream.prototype.getAudioTracks = function() {
        return [];
      };
    } 
    else if (navigator.webkitGetUserMedia) {
      this.webrtcDetectedBrowser = "chrome";
      this.RTCPeerConnection = webkitRTCPeerConnection;
      this.configuration = {iceServers: [{url: "stun:stun.l.google.com:19302"}]};
      this.RTCSessionDescription = RTCSessionDescription;
      this.dataChannelOpts = {reliable: false};
      this.RTCIceCandidate = RTCIceCandidate;
      this.getUserMedia = navigator.webkitGetUserMedia.bind(navigator);

      this.attachMediaStream = function(element, stream) {
        if (typeof element.srcObject !== 'undefined') {
        element.srcObject = stream;
        } else if (typeof element.mozSrcObject !== 'undefined') {
        element.mozSrcObject = stream;
        } else if (typeof element.src !== 'undefined') {
        element.src = URL.createObjectURL(stream);
        } else {
        //Error attaching stream to element
        }
      };

      this.reattachMediaStream = function(to, from) {
        to.src = from.src;
      };

      if (!webkitMediaStream.prototype.getVideoTracks) {
        webkitMediaStream.prototype.getVideoTracks = function() {
        return this.videoTracks;
        };
        webkitMediaStream.prototype.getAudioTracks = function() {
        return this.audioTracks;
        };
      }

      if (!webkitRTCPeerConnection.prototype.getLocalStreams) {
        webkitRTCPeerConnection.prototype.getLocalStreams = function() {
        return this.localStreams;
        };
        webkitRTCPeerConnection.prototype.getRemoteStreams = function() {
        return this.remoteStreams;
        };
      }
    } 
    else {
      alert("Il tuo browser sembra non supportare WebRTC");
    }
  },
  
  setSDP: function(sdp) {
    this.pc.setRemoteDescription(new this.RTCSessionDescription(sdp));
  },
  
  addICE: function(iceCandidate) {
    this.pc.addIceCandidate(new this.RTCIceCandidate(iceCandidate));
  },
  
  closeConnection: function(onClose) {
    this.myStream.stop();
    this.pc.close();
    onClose( this.isCaller );
    this.myStream = undefined;
    this.pc = undefined;
    this.isCaller = false;
    this.dataChannel = undefined;
    this.mySDP = undefined;
    this.myICE = [];
  },
  
  getMedia: function(peerConn, isCaller, onCandidatesReady,onDataChannelMessage) {
    
    var context = this;
    
    function onSuccess(stream) {
      
      context.myStream = stream;
      var selfView = document.getElementById('local');
      if(selfView) context.attachMediaStream(selfView,stream);
      
      peerConn.addStream(stream);
      
      if(isCaller) {
        context.dataChannel = context.pc.createDataChannel('RTCDataChannel', context.dataChannelOpts);
        context.setDataChannelBinaryType(context.dataChannel);
        context.setChannelEvents(context.dataChannel, onDataChannelMessage);
        peerConn.createOffer(gotDescription, function(){ alert("Failed to create rtc offer."); });
      }
      else peerConn.createAnswer(gotDescription, function(){ alert("Failed to create rtc answer."); });
      
      function gotDescription(desc) {
        peerConn.setLocalDescription(desc);
        context.mySDP = desc;
      } 
    }
    function onError(e) {
      alert('Non siamo riusciti ad ottenere l\'accesso alle periferiche di acquisizione audio e video. Assicurati di aver concesso all\'applicazione i permessi necessari. Tuttavia WebRTC potrebbe non riconoscere correttamente la tua WebCam e/o il tuo microfono.');
    }
    this.getUserMedia({ "audio": true, "video": true }, onSuccess, onError);
  },
  
  start: function(beforeCandidatesCreation,onCandidatesReady,onClose,onDataChannelMessage,bool) {
    this.isCaller = bool;
    this.pc = new this.RTCPeerConnection(this.configuration, this.opts);

    window.PC = this.pc;

    var context = this;
    
    // invia iceCandidate all'altro peer
    beforeCandidatesCreation(this);
    this.pc.onicecandidate = function(evt) {
      if(evt.candidate) {
        context.myICE.push(evt.candidate);
      }
      else {
        window.CN = context.get('myICE');
        var RTCinfo = new Object();
        RTCinfo.sdp = context.get('mySDP');
        RTCinfo.ice = context.get('myICE');
        onCandidatesReady( RTCinfo );
      }
    };

    // visualizza lo stream remoto quando viene aggiunto
    this.pc.onaddstream = function(evt) {
      var remoteView = document.getElementById('remote');
      if(remoteView) context.attachMediaStream(remoteView,evt.stream);
    };
    
    this.pc.oniceconnectionstatechange = function(evt) {
      if(evt.target.iceConnectionState == "disconnected") {
      context.closeConnection(onClose);
      }
    };
    
    this.pc.ondatachannel = function(evt) {
      context.dataChannel = evt.channel || evt;
      context.setDataChannelBinaryType(context.dataChannel);
      context.setChannelEvents(context.dataChannel, onDataChannelMessage);
    };
    
    this.pc.onclosedconnection = function(evt) {
      context.closeConnection(onClose);
    };

    // get the local stream, show it in the local video element and send it
    this.getMedia(this.pc,this.isCaller,onCandidatesReady,onDataChannelMessage);
    
  },

  setChannelEvents: function(channel, onDataChannelMessage) {
    
    channel.onmessage = function(msg) {
      onDataChannelMessage(msg);
    };
    
    channel.onopen = function() {
      //console.debug('RTCDataChannel connected.');
    };
    
    channel.onclose = function(e) {
      //console.debug('RTCDataChannel closed.');
    };
    
    channel.onerror = function(e) {
      //console.error('RTCDataChannel error. Channel closed.');
    };
  },
  
  send: function(msg) {
    this.dataChannel.send(msg);
  }
    
});


/**
* Rappresenta il costruttore con il nome standard $RTCPeerConnection$, a cui Chrome si riferisce
* con il nome $webkitRTCPeerConnection$ mentre Firefox con $mozRTCPeerConnection$. Crea un'istanza 
* di connessione peer to peer.
* 
* @property -RTCPeerConnection
* @type {function}
*/
 
/** 
* Rappresenta il metodo con il nome standard $getUserMedia$, implementato da Chrome come $webkitGetUserMedia$
* e da Firefox come $mozGetUserMedia$. Chiede all'utente il permesso di utilizzare le periferiche di 
* acquisizione audio e video.
* 
* @property -getUserMedia
* @type {function}
*/

/**
* Funzione che consente di inserire uno stream video all'interno di un tag $<video>$ HTML5. Necessaria 
* dato che ogni browser fornisce questa funzionalità in modo diverso, sarà quindi diversa a seconda del 
* browser identificato.
* 
* @method -attachMediaStream
* @param {html video element} Riferimento all'elemento in cui inserire lo stream
* @param {video stream} Lo stream video da inserire
* @return {void}
*/

/** 
* Il nome del browser identificato.
* 
* @property -webrtcDetectedBrowser
* @type {string}
*/

/** 
* La connessione peer to peer attiva viene salvata in questa proprietà.
* 
* @property -pc
* @type {RTCPeerConnection}
*/
 
/** 
* Contiene i parametri da passare al costruttore $RTCPeerConnection$, che sono differenti a seconda del 
* browser.
* 
* @property -configuration
* @type {JSON anonymous object}
*/ 

/**
* Consente di sapere se l'utente che sta accedendo ai metodi è il chiamante o il chiamato. In base a questa
* proprietà si deciderà se chiamare il metodo WebRTC $createOffer$ o $createAnswer$.
* 
* @property -isCaller
* @type {boolean}
*/
 
/** 
* Contiene i parametri per la costruzione del canale dati, da passare al costruttore $RTCPeerConnection$.
* 
* @property -dataChannelOpts
* @type {JSON anonymous object}
*/
 
/** 
* In questa proprietà viene salvato il canale dati attivo. Il canale dati ($RTCDataChannel$) consente ai browser
* di scambiarsi uno stream di dati oltre a quelli audio e video.
* 
* @property -dataChannel
* @type {RTCDataChannel}
*/ 

/**
* Memorizza un riferimento allo stream video ottenuto dalla videocamera locale.
* 
* @property -myStream
* @type {video stream}
*/ 

/**
* Contiene il "Session Description Protocol" locale. Sarà poi inviato all'altro peer per iniziare la comunicazione.
* 
* @property -mySDP
* @type {RTCSessionDescription}
*/ 

/**
* Contiene gli "ICE candidates" generati che verranno poi inviati all'altro peer.
* 
* @property -myICE
* @type {Array<RTCIceCandidate>}
*/

/** 
* Costruttore della classe PeerConnection. Per prima cosa identifica il browser dell'utente. Poi, in base al browser
* identificato, assegna alle proprietà della classe le funzioni corrette da invocare, quindi ad esempio $RTCPeerConnection$
* conterrà $mozRTCPeerConnection$ nel caso di Firefox e $webkitRTCPeerConnection$ nel caso di Chrome.
* 
* @method +init
* @constructor
* @return {void}
*/

/** 
* Inserisce un $SDP$ come descrizione del peer remoto, chiamando il metodo corretto di $RTCPeerConnection$.
* 
* @method +setSDP
* @param {RTCSessionDescription} contiene la descrizione remota
* @return {void}
*/

/**
* Aggiunge alla connessione peer to peer gli "ICE candidates" del peer remoto.
* 
* @method +addICE
* @param {RTCIceCandidate} contiene una lista di "ICE Candidates"
* @return {void}
*/

/** 
* Chiude la connessione e rilascia l'accesso alle periferiche di acquisizione audio e video dell'utente. Richiama poi una 
* funzione del controller (passata per parametro) che comunica il termine della chiamata e modifica la vista.
* 
* @method +closeConnection
* @param {function} callback che deve essere invocata dopo aver eseguito il rilascio delle periferiche audio/video
* @return {void}
*/

/** 
* Chiede all'utente il permesso di accedere alle periferiche di acquisizione. Ottenuto il permesso inserisce lo stream video 
* locale nella pagina chiamando il metodo $attachMediaStream$. Poi procede a creare l'offerta (o la risposta nel caso $isCaller$ 
* sia false) da trasmettere all'altro peer.
* 
* @method -getMedia
* @return {void}
*/

/** 
* Chiama il costruttore RTCPeerConnection per istanziare una nuova connessione, la configura e apre il canale dati. Quando gli 
* "ICE candidates" sono pronti lo comunica al controller tramite la funzione $onCandidatesReady$. In seguito invoca il metodo $getMedia$.
* 
* @method +start
* @param {function} Funzione che comunica al controller che il pacchetto da inviare all'altro peer è pronto
* @param {function} Funzione che verrà chiamata alla chiusura della connessione
* @param {function} Funzione da chiamare all'arrivo di un messaggio nel canale dati
* @param {boolean} Rappresenta lo stato dell'utente (Chiamante | Chiamato). $isCaller$ viene inizializzato con questo valore
* @return {void}
*/ 

/** 
* Configura il canale dati definendo le azioni da compiere per i quattro possibili eventi $onmessage$, $onopen$, $onerror$ ed $onclose$.
* 
* @method -setChannelEvents
* @param {RTCDataChannel} Oggetto $DataChannel$
* @param {function} Funzione del controller da invocare in seguito all'evento $onmessage$.
* @return {void}
*/

/**
* Invia un messaggio attraverso il canale dati.
* 
* @method +send
* @param {string} contiene il messaggio da inviare all'altro peer
* @return {void}
*/
