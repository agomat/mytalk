(function () {

    function cmd(op,originId, destId, swarmId, chunkId,data) {
        this.op = op;
        this.originId = originId;
        this.destId = destId;
        this.swarmId = swarmId;
        this.chunkId = chunkId;
        this.data = data;
    }

    var encode = function(cmdObj) {
        return JSON.stringify(cmdObj);
    }

    proto64 = {};

    proto64.ORIGINID_TAG = 0; //string
    proto64.DESTID_TAG = 1;
    proto64.SWARMID_TAG = 2; // string
    proto64.CHUNKID_TAG = 3; // uint32
    proto64.DATA_TAG = 4; // UInt8array
    proto64.SDP_TAG = 5; // UInt8array
    proto64.NEED_CHUNK = 6; // uint32
    proto64.MESSAGE = 7;

    proto64.message = function(originId, destId, message){
        cmdObj = new cmd(this.MESSAGE,originId,destId,null,null,message);
        return encode(cmdObj);
    }

    proto64.need = function (originId, destId, swarmId, chunkId) {
        cmdObj = new cmd(this.NEED_CHUNK, originId, destId, swarmId, chunkId, null);
        return encode(cmdObj);
    }

    proto64.send = function (originId, destId, swarmId, chunkId, data) {
        cmdObj = new cmd(this.DATA_TAG, originId, destId, swarmId, chunkId, data);
        return encode(cmdObj);
    }

    proto64.decode = function(encoded) {
        return JSON.parse(encoded);
    }

})();





(function () {
    client = function () {
        this.clientId = 2;
        this.peerConnections = {};
        this.requestThresh; //how many chunk till new request
        this.numOfChunksToAllocate;
        this.maxNumOfChunksToAllocate;
        this.configureBrowserSpecific();
        this.CHUNK_SIZE;//bytes
        this.CHUNK_EXPIRATION_TIMEOUT = 2000;
        this.peerConnectionImpl;
        this.dataChannels = {};
        this.registerEvents();
        this.chunks = {};// <id, arrybuffer>
        this.chunkRead = 0;
        this.numOfChunksInFile;
        this.BW_INTERVAL = 500;
        this.lastCycleTime = Date.now();
        this.numOfChunksReceived = 0;
        this.hasEntireFile = false;
        this.incomingChunks = {}; //<peerId , numOfChunks>
        this.missingChunks = [];
        this.pendingChunks = [];
        this.lastCycleUpdateSizeInBytes = 0;
        this.firstTime = true;
        this.startTime;
        this.totalAvarageBw;
        this.filename;
    };

    client.prototype = {
        configureBrowserSpecific:function () {
            if (window.mozRTCPeerConnection) {
                this.requestThresh = 70; //how many chunk till new request
                this.numOfChunksToAllocate = 95;
                this.maxNumOfChunksToAllocate = 200;
                this.CHUNK_SIZE = 50000;
                ///this.peerConnectionImpl = peerConnectionImplFirefox;
            } else if (window.webkitRTCPeerConnection) {
                this.requestThresh = 70; //how many chunk till new request
                this.numOfChunksToAllocate = 95;
                this.maxNumOfChunksToAllocate = 99;
                this.CHUNK_SIZE = 800;
                ///this.peerConnectionImpl = peerConnectionImplChrome;
            }
        },

        updateMetadata:function (metadata) {
            this.metadata = metadata[0];
            this.numOfChunksInFile = metadata[0].numOfChunks;
            for (var i = 0; i < this.numOfChunksInFile; ++i)
                this.missingChunks[i] = 1;
        },

        chunkFile:function (binaryFile) {
            this.numOfChunksInFile = Math.ceil(binaryFile.byteLength / this.CHUNK_SIZE)
            for (var i = 0; i < this.numOfChunksInFile; i++) {
                var start = i * this.CHUNK_SIZE;
                this.chunks[i] = binaryFile.slice(start, start + this.CHUNK_SIZE);
            }
        },

        addChunk:function(i,binaryChunk){
            this.chunks[i] = binaryChunk;
        },

        addChunks:function(binarySlice){
            this.numOfChunksInSlice = Math.ceil(binarySlice.byteLength / this.CHUNK_SIZE);
            for (var i = 0; i < this.numOfChunksInSlice; i++) {
                var start = i * this.CHUNK_SIZE;
                this.chunks[this.chunkRead] = binarySlice.slice(start,Math.min(start + this.CHUNK_SIZE,binarySlice.byteLength));
                this.chunkRead++;
            }
            if(this.chunkRead == this.numOfChunksInFile){
                this.hasEntireFile = true;
            }
        },

        prepareToReadFile:function(fileSize){
            this.numOfChunksInFile = Math.ceil(fileSize / this.CHUNK_SIZE);
        },


        addFile:function (body) {
            this.chunkFile(body);
            this.hasEntireFile = true;
        },

        /*
        receiveChunk:function (originId, chunkId, chunkData) {
            if (this.pendingChunks.hasOwnProperty(chunkId)) {
                delete this.pendingChunks[chunkId];
                this.incomingChunks[originId]--;
            }
            if (!this.chunks.hasOwnProperty(chunkId)) {
                this.numOfChunksReceived++;
                this.chunks[chunkId] = chunkData;
                this.updateProgress();
                this.checkHasEntireFile();
            }
        },

        updateProgress:function () {
            if (this.firstTime) {
                this.startTime = Date.now();
                this.firstTime = false;
            }
            var percentage = this.numOfChunksReceived / this.numOfChunksInFile;
            var currentProgressUpdateSizeInSize = this.numOfChunksReceived * this.CHUNK_SIZE; //in bytes
            var rate;

            var currentTime = Date.now();
            var cycleDuration = currentTime - this.lastCycleTime;
            var cycleSize = this.numOfChunksReceived * this.CHUNK_SIZE - this.lastCycleUpdateSizeInBytes

            if (cycleDuration > this.BW_INTERVAL) {
                rate = this.calcBwInKbps(cycleDuration / 1000, cycleSize);
                this.lastCycleTime = currentTime
                this.lastCycleUpdateSizeInBytes = this.numOfChunksReceived * this.CHUNK_SIZE;
            }

            if (this.numOfChunksReceived == this.numOfChunksInFile) {
                this.totalAvarageBw = this.calcBwInKbps((currentTime - this.startTime) / 1000, this.numOfChunksInFile * this.CHUNK_SIZE)
            }

            //if(this.numOfChunksReceived*this.CHUNK_SIZE - this.lastProgressUpdateSizeInSize > 50000){
            // rate = this.calcBwInKbps()
            // }


            ///radio('downloadProgress').broadcast(percentage * 100, rate, this.totalAvarageBw);


        },
        */

        calcBwInKbps:function (timeInSec, sizeInBytes) {
            return (sizeInBytes / 1024) / timeInSec;
        },

        addToPendingChunks:function (chunksIds, peerId) {
            if (chunksIds.length == 0) return;
            var id = setTimeout(this.expireChunks, this.CHUNK_EXPIRATION_TIMEOUT, chunksIds, peerId);
//            console.log(id);
        },

        requestChunks:function (targetId) {
            var chunkIds = [];
            var tempChunks = 0;
            for (var chunkId in this.missingChunks) {
                chunkIds.push(chunkId);
                delete this.missingChunks[chunkId];
                this.pendingChunks[chunkId] = 1;
                tempChunks++;
                if (tempChunks >= this.numOfChunksToAllocate)
                    break;
            }
            this.incomingChunks[targetId] += chunkIds.length;
            this.addToPendingChunks(chunkIds, targetId);
            DC.send(proto64.need(this.clientId, 1, 1, chunkIds));
        },

        checkHasEntireFile:function () {
            if (this.numOfChunksReceived == this.numOfChunksInFile) {
                //ToDo: anounce has file base64.decode the strings and open it
                console.log("I have the entire file");
                this.hasEntireFile = true;
                this.ws.sendDownloadCompleted();
                this.saveFileLocally();
            }
        },

        saveFileLocally:function () {
            var array = new Uint8Array(898);
            //var array = new Uint8Array((this.numOfChunksInFile-1)*this.CHUNK_SIZE+this.chunks[this.numOfChunksInFile-1].byteLength);
            window.arr = array;
            console.log(this.CHUNK_SIZE*0);
            console.log(this.CHUNK_SIZE*1);
            console.log(this.chunks[0]);
            console.log(this.chunks[1]);
            for (var i = 0; i < this.numOfChunksInFile; ++i) {
                array.set(this.chunks[i], i*this.CHUNK_SIZE);
            }
            var blob = new Blob([array]);
            this.saveLocally(blob, this.filename);
        },

        saveLocally: function(blob, name) {
            if (!window.URL && window.webkitURL)
                window.URL = window.webkitURL;
            var a = document.createElement('a');
            a.download = name;
            a.setAttribute('href', window.URL.createObjectURL(blob));
            document.body.appendChild(a);
            a.click();
        },

        //init true if this peer initiated the connection
        ensureHasPeerConnection:function (peerId, init) {
            if (!this.peerConnections[peerId]) {
                this.peerConnections[peerId] = new this.peerConnectionImpl(this.ws, this.clientId, peerId, init);
            }
        },

        registerEvents:function () {
            var thi$ = this;
            /**
             * remove pending chunks from the pending and add back to the missing
             * @param chunksIds that might still be pending
             */
            this.expireChunks = function (chunksIds, peerId) {
                var expire = 0;
                for (var i = 0; i < chunksIds.length; i++) {
                    var chunkId = chunksIds[i];
                    if (chunkId in thi$.pendingChunks) {
                        expire++;
//                        console.log('expiring chunk ' + chunkId);
                        // let's expire this chunk
                        delete thi$.pendingChunks[chunkId];
                        thi$.missingChunks[chunkId] = 2;
                        thi$.incomingChunks[peerId]--;
                    }
                }
                //flow-control: currently this mechanism isn't very effective
                if(expire){
                    console.log("Expired " + expire + " chunks");
//                    console.log("numOfChunksToAllocate: " + thi$.numOfChunksToAllocate);
                    thi$.numOfChunksToAllocate = Math.floor(thi$.numOfChunksToAllocate/1.3);
                }else if(thi$.numOfChunksToAllocate < thi$.maxNumOfChunksToAllocate){
                    thi$.numOfChunksToAllocate++;
                }
//                console.log(thi$.numOfChunksToAllocate);
                if (thi$.incomingChunks[peerId] < thi$.requestThresh) {
                    thi$.requestChunks(peerId);
                }

            };
            /*
            //websockets events
            radio('receivedRoomMetadata').subscribe([function (files) {
                this.updateMetadata(files);
            }, this]);

            radio('socketConnected').subscribe([function () {
                this.clientId = this.ws.socket.socket.sessionid;
                console.log('got an id: ' + this.clientId);
            }, this]);

            radio('receivedMatch').subscribe([function (message) {
                if (this.hasEntireFile)
                    return;
                for (var i = 0; i < message.clientIds.length; ++i) {
                    this.ensureHasPeerConnection(message.clientIds[i], true);
                    this.peerConnections[message.clientIds[i]].setupCall();
                }
            }, this]);

            radio('receivedOffer').subscribe([function (msg) {
                this.ensureHasPeerConnection(msg.originId, false);
                this.peerConnections[msg.originId].handleMessage(msg);
            }, this]);

            //PeerConnection events
            radio('commandArrived').subscribe([function (msg) {
                var cmd = proto64.decode(msg.data);
                if (cmd.op == proto64.NEED_CHUNK) {
                    console.debug(">>> ONE");
                    for (var i = 0; i < cmd.chunkId.length; ++i) {
                        var chunkId = cmd.chunkId[i];
//                        console.log("received NEED_CHUNK command " + chunkId);
                        if (chunkId in this.chunks) {
                            DC.send(proto64.send(this.clientId, 1, 1, chunkId, Base64Binary.encode(this.chunks[chunkId])));

                        } else {
                            console.warn('I dont have this chunk' + chunkId);
                        }
                    }
                } else if (cmd.op == proto64.DATA_TAG) {
                    console.debug(">>> TWO");
                   console.log("received DATA_TAG command with chunk id " + cmd.chunkId);
                    this.receiveChunk(cmd.originId, cmd.chunkId, Base64Binary.decode(cmd.data));
                    if (!this.hasEntireFile && this.incomingChunks[cmd.originId] < this.requestThresh) {
                        this.requestChunks(cmd.originId);
                    }
                } else if (cmd.op == proto64.MESSAGE) {
                    console.debug(">>> THREE");
                    console.log("peer " + cmd.originId + " sais: " + cmd.data);
                }
            }, this]);

            radio('connectionReady').subscribe([function (targetId) {
                this.incomingChunks[targetId] = 0;
                if (0 in this.chunks) {
                    console.log('got chunk 0');
                } else {
                    console.log('requesting chunk 0');
                    this.requestChunks(targetId);
                }
            }, this]);
            */

        }
    };
})();
