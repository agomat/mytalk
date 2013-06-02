MyTalk.processor.SuccessfulAcceptCall = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct,{
  name: 'SuccessfulAcceptCall',

  process: function (ari) {
  	var payload = JSON.parse( ari.info );

    var callData = Ember.Object.create({
      isCaller: true,
      RTCinfo: JSON.parse(payload.RTCinfo)
    });
    MyTalk.CallState.send('beingConnected', callData);
  },

  getProcessorName: function() {
  	return this.get('name');
  } 
});