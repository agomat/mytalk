MyTalk.Call= Ember.Object.extend({

  caller: null,
  receiver: null,
  duration: null,
  bytesent: null,
  bytereceived: null,

  getCaller: function() {
    return this.get('caller');
  }, 

  getReceiver: function() {
    return this.get('receiver');
  },

  getDuration: function() {
    return this.get('duration');
  },

  getSentByte: function() {
    return this.get('bytesent');
  },

  getReceivedByte: function() {
    return this.get('bytereceived');
  },

  setCaller: function(cname) {
    this.setProperties({caller:cname});
  },

  setReceiver: function(rname) {
    this.setProperties({receiver:rname});
  },

  setDuration: function(durate) {
    this.setProperties({duration:durate});
  },

  setSentByte: function(sbyte) {
    this.setProperties({bytesent:sbyte});
  },
  setReceivedByte: function(rbyte) {
    this.setProperties({receivedbyte:rbyte});
  },

  setAll: function(cname,rname,durate,sbyte,rbyte) {
    this.setCaller(cname);
    this.setReceiver(rname);
    this.setDuration(durate);
    this.setSentByte(sbyte);
    this.setReceivedByte(rbyte);
  },

  timeConverter: function(time) {
    //da capire come fare la conversione
  }

}); 
