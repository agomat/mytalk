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
            document.getElementById('chat').style.display = 'none';

            this.webrtcDetectedBrowser = "firefox";

            this.RTCPeerConnection = mozRTCPeerConnection;

            this.RTCSessionDescription = mozRTCSessionDescription;

            this.RTCIceCandidate = mozRTCIceCandidate;

            this.getUserMedia = navigator.mozGetUserMedia.bind(navigator);

            this.attachMediaStream = function(element, stream) {
                console.log("Attaching media stream");
                element.mozSrcObject = stream;
                element.play();
            };

            this.reattachMediaStream = function(to, from) {
                console.log("Reattaching media stream");
                to.mozSrcObject = from.mozSrcObject;
                to.play();
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
    
    getMedia: function(peerConn, isCaller) {
        
        var context = this;
        
        function onSuccess(stream) {
            
            var selfView = document.getElementById('local');
            console.debug("ATTACHED local "+selfView);
            if(selfView) context.attachMediaStream(selfView,stream);
            
            peerConn.addStream(stream);

            if(isCaller)
                peerConn.createOffer(gotDescription);
            else {
                peerConn.createAnswer(gotDescription);
            }
            function gotDescription(desc) {
                peerConn.setLocalDescription(desc);
                console.log('add sdp'+ desc.toString());
                context.mySDP = desc;
            } 
        }
        function onError(e) {
            console.error('Non e\' stato possibile ottenere accesso agli stream audio e video.\n' + e);
        }
        this.getUserMedia({ "audio": true, "video": true }, onSuccess, onError);
    },
    
    start: function(beforeCandidatesCreation,onCandidatesReady,bool) {
        this.isCaller = bool;
        this.pc = new this.RTCPeerConnection(this.configuration, this.optsDataChann);
        if(this.webrtcDetectedBrowser === "chrome") {
          this.dataChannel = this.pc.createDataChannel('RTCDataChannel', {reliable: false});
          this.setChannelEvents(this.dataChannel, this.user);
        }        
        var context = this;
        
        // invia iceCandidate all'altro peer
        beforeCandidatesCreation(this);
        this.pc.onicecandidate = function(evt) {
            if(evt.candidate) {
              context.myICE.push(evt.candidate);
            }
            else {
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
        
        this.pc.onremovestream = function(evt) {
            document.getElementById("remote").src = "";
        }

        // get the local stream, show it in the local video element and send it
        this.getMedia(this.pc,this.isCaller);
    },

    setChannelEvents: function(channel, user) {
        
//         channel.onmessage = function (event) {
//             var msg = JSON.parse(event.data);
//             $('#messages').append('<li class="received_message">'+msg.message+'</li>');
//         };
// 
//         channel.onopen = function () {
//             console.debug('RTCDataChannel connected.');
//         };
//         channel.onclose = function (e) {
//             console.error(e);
//         };
//         channel.onerror = function (e) {
//             console.error(e);
//         };
    },
    
//     send: function(message) {
//         console.debug(message);
//         var msg = JSON.stringify({"message" : message});
//         this.dataChannel.send(msg);
//         $('#messages').append('<li class="sent_message">'+message+'</li>');
//     }
        
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
