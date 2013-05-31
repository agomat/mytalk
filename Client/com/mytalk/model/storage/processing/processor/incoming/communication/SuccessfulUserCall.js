MyTalk.processor.SuccessfulUserCall = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct,{
  name: 'SuccessfulUserCall',

  process: function (ari) {
  	/*
			1) salvo il campo info all'interno di uno stato dello statemanager
			( myIp= obbligatorio
			 	myUserId= obbligatorio se arriva da utente registrato altrimenti null
				speakerIp= obbligatorio
				speakerUserId= obbligatorio se arriva da utente registrato altrimenti null
				RTCinfo= obbligatorio )

			2) lo state manager deve provocare un cambiamento di vista mostrando "Nome Cognome ti sta chiamando"

  	*/

  	var payload = JSON.parse( ari.info );
    var caller = MyTalk.User.find( payload.myUserId );

    // TODO vedere se sono impegnato in altra conversazione
    MyTalk.CallState.send('beingBusy',{
      path: 'isBusy.incomingCall',
      RTCinfo: {
        myIP: null, //           inutili!
        myUserId: null,
        myRTCinfo: null,
        speakerIp: payload.myIP,
        speakerUserId: payload.myUserId,
        speakerRTCinfo: payload.myRTCinfo
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