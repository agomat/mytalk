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
                context.pc.setRemoteDescription(new RTCSessionDescription(signal.sdp));
            else if(signal.candidate)
                context.pc.addIceCandidate(new RTCIceCandidate(signal.candidate));
        };
        
        if (navigator.mozGetUserMedia) {
            console.log("This appears to be Firefox");

            this.webrtcDetectedBrowser = "firefox";

            // The RTCPeerConnection object.
            this.RTCPeerConnection = mozRTCPeerConnection;

            // The RTCSessionDescription object.
            this.RTCSessionDescription = mozRTCSessionDescription;

            // The RTCIceCandidate object.
            this.RTCIceCandidate = mozRTCIceCandidate;

            // Get UserMedia (only difference is the prefix).
            // Code from Adam Barth.
            this.getUserMedia = navigator.mozGetUserMedia.bind(navigator);

            // Attach a media stream to an element.
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

            // Fake get{Video,Audio}Tracks
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
            console.log("Browser does not appear to be WebRTC-capable");
        }
    },
    
    getMedia: function(peerConn,signChann,isCaller) {
        
        var context = this;
        
        function onSuccess(stream) {
            var selfView = document.getElementById('local');
            //selfView.src = URL.createObjectURL(stream);
            context.attachMediaStream(selfView,stream);
            selfView.play();
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
            console.log('Could not get access to local media stream.\n' + e);
        }
        this.getUserMedia({ "audio": true, "video": true }, onSuccess, onError);
    },
    
    start: function(bool) {
        this.isCaller = bool;
        this.pc = new this.RTCPeerConnection(this.configuration, this.optsDataChann);
        var signChann = this.signalingChannel;
	this.dataChannel = this.pc.createDataChannel('RTCDataChannel', {reliable: false});
	this.setChannelEvents(this.dataChannel, "user "+window.location.search.split('=')[1]);
        
        var context = this;
        
        // invia iceCandidate all'altro peer
        this.pc.onicecandidate = function(evt) {
            console.log('send candidate');
            signChann.send(JSON.stringify({ "candidate": evt.candidate }));
        };

        // visualizza lo stream remoto quando viene aggiunto
        this.pc.onaddstream = function(evt) {
            var remoteView = document.getElementById('remote');
            context.attachMediaStream(remoteView,evt.stream);
            //remoteView.src = URL.createObjectURL(evt.stream);
            remoteView.play();
        };
        
        this.pc.onremovestream = function(evt) {
            document.getElementById("remote").src = "";
        }

        // get the local stream, show it in the local video element and send it
        this.getMedia(this.pc,this.signalingChannel,this.isCaller);
    },

    setChannelEvents: function(channel, user) {
        channel.onmessage = function (event) {
            console.debug(user, 'received a message:', event.data);
        };

        channel.onopen = function () {
            channel.send('first text message over RTP data ports');
        };
        channel.onclose = function (e) {
            console.error(e);
        };
        channel.onerror = function (e) {
            console.error(e);
        };
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
