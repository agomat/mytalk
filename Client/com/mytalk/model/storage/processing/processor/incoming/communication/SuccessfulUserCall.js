MyTalk.processor.SuccessfulUserCall = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct,{
  name: 'SuccessfulUserCall',

  process: function (ari) {
  	/*
			2) salvo il campo info all'interno di uno stato dello statemanager
			( myIp= obbligatorio
			 	myUserId= obbligatorio se arriva da utente registrato altrimenti null
				speakerIp= obbligatorio
				speakerUserId= obbligatorio se arriva da utente registrato altrimenti null
				RTCinfo= obbligatorio )

			3) lo state manager deve provocare un cambiamento di vista mostrando "Nome Cognome ti sta chiamando"

  	*/

  	var payload = JSON.parse( ari.info );
    var caller = MyTalk.User.find( payload.myUserId );
    console.log( caller.get('fullName') );

  },

  getProcessorName: function () {
  	return this.get('name');
  } 
});