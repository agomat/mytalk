MyTalk.processor.SuccessfulAcceptCall = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct,{
  name: 'SuccessfulAcceptCall',

  process: function (ari) {

  	var payload = JSON.parse( ari.info );
    var caller = MyTalk.User.find( payload.myUserId );

    MyTalk.CallState.send('beingBusy',{
      path: 'isBusy.isConnected',
      RTCinfo: {
        //myIP: null, //           inutili!
        //myUserId: null,
        //myRTCinfo: null,
        speakerIp: payload.myIP,
        speakerUserId: payload.myUserId,
        speakerRTCinfo: JSON.parse(payload.RTCinfo)
      }
    });
    


/*

Ember.Object.create({
		  reason: null,
      stats: {
        duration: null,
        sentBytes: null,
        receivedBytes: null,
      },
      RTCinfo: {
        myIP: null,
        myUserId: null,
        myRTCinfo: null,
        speakerIp: null,
        speakerUserId: null,
        speakerRTCinfo: null
      }
    }),


*/


  },

  getProcessorName: function () {
  	return this.get('name');
  } 
});