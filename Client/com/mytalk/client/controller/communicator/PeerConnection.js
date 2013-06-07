/**
* Filename: PeerConnection.js
* Package: com.mytalk.client.communicator
* Dependencies: 
* Author: Griggio Massimo
* Date: 2013-04-23
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-04-23 | MG        | [+] Scrittura classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

MyTalk.PeerConnection = Ember.Object.extend({
    
    RTCPeerConnection: undefined,
    getUserMedia: undefined,
    attachMediaStream: undefined,
    reattachMediaStream: undefined,
    webrtcDetectedBrowser: undefined,
    pc: undefined,
    configuration: {iceServers: [{url: "stun:stun.l.google.com:19302"}]},
    isCaller: false,
    optsDataChann: {optional: [{RtpDataChannels: true}]},
    dataChannel: undefined,
    myStream: undefined,
    mySDP: undefined,
    myICE: [],
    
    trace: function(text) {
        // This function is used for logging.
        if (text[text.length - 1] == '\n') {
            text = text.substring(0, text.length - 1);
        }
        console.log((performance.now() / 1000).toFixed(3) + ": " + text);
    },
    
    init: function() {
        this._super();
        
        if (navigator.mozGetUserMedia) {
            console.log("This appears to be Firefox");
//             document.getElementById('chat').style.display = 'none';

            this.webrtcDetectedBrowser = "firefox";

            this.RTCPeerConnection = mozRTCPeerConnection;
            
            this.configuration = { iceServers: [{ url: 'stun:216.93.246.18:3478'}, { url: 'stun:66.228.45.110:3478'}, { url: 'stun:173.194.78.127:19302'}] }

            this.RTCSessionDescription = mozRTCSessionDescription;

            this.RTCIceCandidate = mozRTCIceCandidate;

            this.getUserMedia = navigator.mozGetUserMedia.bind(navigator);

            this.attachMediaStream = function(element, stream) {
                console.log("Attaching media stream");
                element.mozSrcObject = stream;
            };

            this.reattachMediaStream = function(to, from) {
                console.log("Reattaching media stream");
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
            console.log("This appears to be Chrome");

            this.webrtcDetectedBrowser = "chrome";

            this.RTCPeerConnection = webkitRTCPeerConnection;
            
            this.RTCSessionDescription = RTCSessionDescription;

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
                console.log('Error attaching stream to element.');
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
            console.error("Il tuo browser sembra non supportare WebRTC.");
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
      this.myStream = undefined;
      this.pc = undefined;
      this.isCaller = false;
      this.dataChannel = undefined;
      this.mySDP = undefined;
      this.myICE = [];
      onClose();
    },
    
    getMedia: function(peerConn, isCaller, onCandidatesReady,onDataChannelMessage) {
        
        var context = this;
        
        function onSuccess(stream) {
            
            context.myStream = stream;
            var selfView = document.getElementById('local');
            console.debug("ATTACHED local "+selfView);
            if(selfView) context.attachMediaStream(selfView,stream);
            
            peerConn.addStream(stream);
            if(context.webrtcDetectedBrowser === "firefox") {
              console.log('datachannel');
              context.dataChannel = context.pc.createDataChannel('RTCDataChannel', {});
              context.setChannelEvents(context.dataChannel, onDataChannelMessage);
            }

            if(isCaller)
                peerConn.createOffer(gotDescription);
            else {
                peerConn.createAnswer(gotDescription);
            }
            function gotDescription(desc) {
                peerConn.setLocalDescription(desc);
                console.log('add sdp'+ desc.toString());
                context.mySDP = desc;
                if(context.webrtcDetectedBrowser === "firefox") {
                  console.log("sendig firefox candidates");
                  var RTCinfo = new Object();
                  RTCinfo.sdp = context.get('mySDP');
                  onCandidatesReady( RTCinfo );
                }
                window.mysdp = desc;
            } 
        }
        function onError(e) {
            console.error('Non e\' stato possibile ottenere l\'accesso agli stream audio e video.\n' + e);
        }
        this.getUserMedia({ "audio": true, "video": true }, onSuccess, onError);
    },
    
    start: function(beforeCandidatesCreation,onCandidatesReady,onClose,onDataChannelMessage,bool) {
        this.isCaller = bool;
        this.pc = new this.RTCPeerConnection(this.configuration, this.optsDataChann);
        window.PC = this.pc;
        if(this.webrtcDetectedBrowser === "chrome") {
          this.dataChannel = this.pc.createDataChannel('RTCDataChannel', {reliable: false});
          this.setChannelEvents(this.dataChannel, onDataChannelMessage);
        }        
        var context = this;
        
        // invia iceCandidate all'altro peer
        beforeCandidatesCreation(this);
        this.pc.onicecandidate = function(evt) {
            if(evt.candidate) {
              context.myICE.push(evt.candidate);
              console.log('candidato non nullo');
            }
            else {
              console.log('candidato nullo');
              var RTCinfo = new Object(); // max: sistemate meglio
              RTCinfo.sdp = context.get('mySDP');
              RTCinfo.ice = context.get('myICE');
              onCandidatesReady( RTCinfo );
            }
        };

        // visualizza lo stream remoto quando viene aggiunto
        this.pc.onaddstream = function(evt) {
          var remoteView = document.getElementById('remote');
          console.debug("ATTACHED remote"+remoteView);
          if(remoteView) context.attachMediaStream(remoteView,evt.stream);
        };
        
        this.pc.oniceconnectionstatechange = function(evt) {
          if(evt.target.iceConnectionState == "disconnected") {
            context.closeConnection(onClose);
          }
        };

        // get the local stream, show it in the local video element and send it
        this.getMedia(this.pc,this.isCaller,onCandidatesReady,onDataChannelMessage);
    },

    setChannelEvents: function(channel, onDataChannelMessage) {
        
        channel.onmessage = function(msg) {
            onDataChannelMessage(msg);
        };

        channel.onopen = function () {
            console.debug('RTCDataChannel connected.');
        };
        channel.onclose = function (e) {
            console.debug(e);
        };
        channel.onerror = function (e) {
            console.error(e);
        };
    },
    
    send: function(message) {
      this.dataChannel.send(message);
    }
        
});

// function dumpStats(obj) {
//       var statsString ='';
//       if (obj.names) {
//         names = obj.names();
//         var t=JSON.stringify(names);
//         for (var i = 0; i < names.length; ++i) 
//         {
//              statsString += '"';
//              statsString += names[i];
//              statsString += '":"';
//              statsString += obj.stat(names[i]);
//              statsString += '",';
//         }
//     }
//       return statsString;
// }
// var callback  =  function(stats) {
//   var all = stats.result();
//   for(var i in all)
//    console.log(dumpStats(all[i].local));
// }
// 
// conn.pc.getStats(callback);
