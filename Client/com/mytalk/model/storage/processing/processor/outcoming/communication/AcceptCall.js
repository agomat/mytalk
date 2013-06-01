MyTalk.processor.AcceptCall = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct,{
  name: 'AcceptCall',

  process: function (params) {

    var store = DS.get("defaultStore");

    var caller = params.caller;
    var RTCinfo = params.RTCinfo;
    var myself = MyTalk.PersonalData.find(0);

    // ottengo l'id
    var id = MyTalk.Call.find().get('length'); 
    var record = MyTalk.Call.createRecord({id: id, speaker: caller, caller:false, startDate: new Date() });
    var transaction = record.get('transaction');

    transaction.reopen({
      processor: this,
      myIp: myself.get('ip'),
      myUserId: myself.get('userId'),
      speakerIp: caller.get('ip'),
      speakerUserId: caller.get('id'),
      RTCinfo: RTCinfo
    });

    transaction.commit();
  },
  
  sendToServer: function (socket, record, onSent) {
    var ARI = new Object();

    var auth = new Object();
    auth.username = MyTalk.Authentication.find(0).get('username');
    auth.password = MyTalk.Authentication.find(0).get('password');
    auth.ip = MyTalk.PersonalData.find(0).get('ip');

    ARI.auth = auth;
    ARI.req = this.get('name');



    ARI.info = new Object();
     ARI.info.myIp = record.get('transaction').get('myIp');
     ARI.info.myUserId = record.get('transaction').get('myUserId');
     ARI.info.speakerIp = record.get('transaction').get('speakerIp');
     ARI.info.speakerUserId = record.get('transaction').get('speakerUserId');
     ARI.info.RTCinfo = record.get('transaction').get('RTCinfo');
    
    ARI.info = JSON.stringify( ARI.info );

    var wasSent = socket.send( JSON.stringify(ARI) );
    onSent( this.getProcessorName(), wasSent );
  },

  getProcessorName: function () {
    return this.get('name');
  } 

});