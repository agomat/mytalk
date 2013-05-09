MyTalk.PeerConnection = Ember.Object.extend({
    
    RTCPeerConnection: undefined,
    getUserMedia: undefined,
    attachMediaStream: undefined,
    reattachMediaStream: undefined,
    webrtcDetectedBrowser: undefined,
    signalingChannel: undefined,
    pc: undefined,
    configuration: {iceServers: [{url: "stun:stun.l.google.com:19302"}]},
    isCaller: false,
    optsDataChann: {optional: [{RtpDataChannels: true}]},
    dataChannel: undefined,
    user: undefined,
    
    createSignalingChannel: function() {
    var ws;
    if(window.location.search.split('=')[1] == 1) {
        ws = new WebSocket("ws://"+window.location.host+"/MyTalk/atmosphere/chat/all?X-Atmosphere-Transport=websocket");
        ws.onopen=function() {
            console.log('connected 1');
        }
    }
    else {
        ws = new WebSocket("ws://"+window.location.host+"/MyTalk/atmosphere/chat/user?X-Atmosphere-Transport=websocket");
        ws.onopen=function() {
            console.log('connected 2');
        }
    }
    ws.onclose=function() {
        console.log("houston we have a problem here!!!!");
    }
    return ws;
    
    },
    
    trace: function(text) {
        // This function is used for logging.
        if (text[text.length - 1] == '\n') {
            text = text.substring(0, text.length - 1);
        }
        console.log((performance.now() / 1000).toFixed(3) + ": " + text);
    },
    
    init: function() {
        this._super();
        
        this.signalingChannel = this.createSignalingChannel();
                
        var context = this;

        this.signalingChannel.onmessage = function (evt) {
            if(evt.data == "user esce") {
                return;
            }
            if(evt.data == "idie") {
                document.getElementById('remote').src = "";
                return;
            }
            console.log('onmessage'); 
            if (typeof(context.pc) == 'undefined')
                context.start(false);
            
            var signal = JSON.parse(evt.data);
            if (signal.sdp)
                context.pc.setRemoteDescription(new context.RTCSessionDescription(signal.sdp));
            else if(signal.candidate)
                context.pc.addIceCandidate(new context.RTCIceCandidate(signal.candidate));
        };
        
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
    
    getMedia: function(peerConn,signChann,isCaller) {
        
        var context = this;
        
        function onSuccess(stream) {
            var selfView = document.getElementById('local');
            //selfView.src = URL.createObjectURL(stream);
            context.attachMediaStream(selfView,stream);
            peerConn.addStream(stream);

            if(isCaller)
                peerConn.createOffer(gotDescription);
            else {
                console.log(peerConn.remoteDescription);
                peerConn.createAnswer(gotDescription);
            }
            function gotDescription(desc) {
                peerConn.setLocalDescription(desc);
                console.log('send sdp');
                signChann.send(JSON.stringify({ "sdp": desc }));
            } 
        }
        function onError(e) {
            console.error('Non e\' stato possibile ottenere accesso agli stream audio e video.\n' + e);
        }
        this.getUserMedia({ "audio": true, "video": true }, onSuccess, onError);
    },
    
    start: function(bool) {
        this.isCaller = bool;
        this.pc = new this.RTCPeerConnection(this.configuration, this.optsDataChann);
        var signChann = this.signalingChannel;
        this.user = "user "+window.location.search.split('=')[1];
        if(this.webrtcDetectedBrowser === "chrome") {
          this.dataChannel = this.pc.createDataChannel('RTCDataChannel', {reliable: false});
          this.setChannelEvents(this.dataChannel, this.user);
        }        
        var context = this;
        
        // invia iceCandidate all'altro peer
        this.pc.onicecandidate = function(evt) {
            console.debug('send candidate');
            signChann.send(JSON.stringify({ "candidate": evt.candidate }));
        };

        // visualizza lo stream remoto quando viene aggiunto
        this.pc.onaddstream = function(evt) {
            var remoteView = document.getElementById('remote');
            context.attachMediaStream(remoteView,evt.stream);
            //remoteView.src = URL.createObjectURL(evt.stream);
        };
        
        this.pc.onremovestream = function(evt) {
            document.getElementById("remote").src = "";
        }

        // get the local stream, show it in the local video element and send it
        this.getMedia(this.pc,this.signalingChannel,this.isCaller);
    },

    setChannelEvents: function(channel, user) {
        
        channel.onmessage = function (event) {
            var msg = JSON.parse(event.data);
            $('#textArea').append(msg.user+': '+msg.message+'<br/>');
        };

        channel.onopen = function () {
            console.debug('RTCDataChannel connected.');
        };
        channel.onclose = function (e) {
            console.error(e);
        };
        channel.onerror = function (e) {
            console.error(e);
        };
    },
    
    send: function(message) {
        var msg = JSON.stringify({"user" : this.user, "message" : message});
        this.dataChannel.send(msg);
        $('#textArea').append(this.user+': '+message+'<br/>');
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
