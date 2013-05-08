function createSignalingChannel() {
    var ws;
    if(window.location.search.split('=')[1] == 1) {
        ws = new WebSocket("ws://localhost:8080/MyTalk/atmosphere/chat/all?X-Atmosphere-Transport=websocket");
        ws.onopen=function() {
            console.log('connected 1');
        }
    }
    else {
        ws = new WebSocket("ws://192.168.43.3:8080/MyTalk/atmosphere/chat/user?X-Atmosphere-Transport=websocket");
        ws.onopen=function() {
            console.log('connected 2');
        }
    }
    ws.onclose=function() {
        console.log("houston we have a problem here!!!!");
    }
    return ws;
    
}


var signalingChannel = createSignalingChannel();
var pc;
var configuration = {iceServers: [{url: "stun:stun.l.google.com:19302"}]};

// run start(true) to initiate a call
function start(isCaller) {
    pc = new webkitRTCPeerConnection(configuration,{optional: [{RtpDataChannels: true}]});

    // send any ice candidates to the other peer
    pc.onicecandidate = function (evt) {
        console.log('send candidate');
        signalingChannel.send(JSON.stringify({ "candidate": evt.candidate }));
    };

    // once remote stream arrives, show it in the remote video element
    pc.onaddstream = function (evt) {
        var remoteView = document.getElementById('remote');
        remoteView.src = URL.createObjectURL(evt.stream);
        remoteView.play();
    };
    
    pc.onremovestream = function(evt) {
        document.getElementById("remote").src = "";
    }

    // get the local stream, show it in the local video element and send it
    navigator.webkitGetUserMedia({ "audio": true, "video": true }, function (stream) {
        var selfView = document.getElementById('local');
        selfView.src = URL.createObjectURL(stream);
        selfView.play();
        pc.addStream(stream);

        if (isCaller)
            pc.createOffer(gotDescription);
        else {
            console.log(pc.remoteDescription);
            pc.createAnswer(gotDescription);
        }
        function gotDescription(desc) {
            pc.setLocalDescription(desc);
            console.log('send sdp');
            signalingChannel.send(JSON.stringify({ "sdp": desc }));
        }
    });
}

signalingChannel.onmessage = function (evt) {
    if(evt.data == "user esce") {
        return;
    }
    if(evt.data == "idie") {
        document.getElementById('remote').src = "";
        return;
    }
    console.log('onmessage'); 
    if (!pc)
        start(false);
    
    var signal = JSON.parse(evt.data);
    if (signal.sdp)
        pc.setRemoteDescription(new RTCSessionDescription(signal.sdp));
    else if(signal.candidate)
        pc.addIceCandidate(new RTCIceCandidate(signal.candidate));
};