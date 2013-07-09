
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

        calcBwInKbps:function (timeInSec, sizeInBytes) {
            return (sizeInBytes / 1024) / timeInSec;
        },

        addToPendingChunks:function (chunksIds, peerId) {
            if (chunksIds.length == 0) return;
            var id = setTimeout(this.expireChunks, this.CHUNK_EXPIRATION_TIMEOUT, chunksIds, peerId);
        },

        checkHasEntireFile:function () {
            if (this.numOfChunksReceived == this.numOfChunksInFile) {
                console.log("I have the entire file");
                this.hasEntireFile = true;
                this.ws.sendDownloadCompleted();
                this.saveFileLocally();
            }
        },

        saveFileLocally:function () {
            var array = new Uint8Array((this.numOfChunksInFile-1)*this.CHUNK_SIZE+this.chunks[this.numOfChunksInFile-1].byteLength);
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
            //window.FS = new client();
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
                    thi$.numOfChunksToAllocate = Math.floor(thi$.numOfChunksToAllocate/1.3);
                }else if(thi$.numOfChunksToAllocate < thi$.maxNumOfChunksToAllocate){
                    thi$.numOfChunksToAllocate++;
                }
                if (thi$.incomingChunks[peerId] < thi$.requestThresh) {
                    thi$.requestChunks(peerId);
                }

            };

        }
    };
})();
