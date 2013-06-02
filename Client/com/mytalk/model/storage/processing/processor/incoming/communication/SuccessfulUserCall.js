MyTalk.processor.SuccessfulUserCall = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct,{
  name: 'SuccessfulUserCall',

  process: function (ari) {
    var payload = JSON.parse( ari.info );

    // TODO vedere se sono impegnato in altra conversazione

    var callData = Ember.Object.create({
      path: 'isBusy.incomingCall',
      speaker: MyTalk.User.find( payload.myUserId ),
      RTCinfo: JSON.parse(payload.RTCinfo)
    });

    MyTalk.CallState.send('beingBusy', callData);
  },

  getProcessorName: function() {
  	return this.get('name');
  } 
});
