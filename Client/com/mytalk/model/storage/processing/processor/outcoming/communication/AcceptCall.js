MyTalk.processor.AcceptCall = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct,{
  name: 'AcceptCall',

  process: function (params) {
  	alert("FARE AcceptCall processor:\n manca da spedire al chiamante le mie info (SDP e candidati)");
  	return;
    var store = DS.get("defaultStore");

  	var callee = params.callee;
  	var RTCinfo = params.RTCinfo;
  	var myself = MyTalk.PersonalData.find(0);

  	// ottengo l'id
  	var id = MyTalk.Call.find().get('length'); 
    var record = MyTalk.Call.createRecord({id:id, speaker: callee, caller:true, startDate: new Date() });
    var transaction = record.get('transaction');

    transaction.reopen({
      processor: this,
      myIp: myself.get('ip'),
      myUserId: myself.get('userId'),
      speakerIp: callee.get('ip'),
      speakerUserId: callee.get('id'),
      RTCinfo: RTCinfo
    });

    transaction.commit();
  },
  
  sendToServer: function (socket, record, onSent) {

  },

  getProcessorName: function () {
    return this.get('name');
  } 

});