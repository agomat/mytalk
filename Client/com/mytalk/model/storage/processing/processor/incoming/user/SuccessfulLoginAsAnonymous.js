MyTalk.processor.SuccessfulLoginAsAnonymous = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct, {
  name: 'SuccessfulLoginAsAnonymous',

  process: function (ari) {
  	var Ipart = JSON.parse(ari.info);
  	var ip = Ipart.worldPersonalData.pd.ip;
    // CORRECT MODE

    var sideloadOK = Ipart.worldPersonalData; // cambiare pd -> personal_data

    // END
    var sideload = {
	          "personal_data": { id:0, "ip":ip}
	       };
	  var store = DS.get("defaultStore");
	  store.load(MyTalk.PersonalData, sideload);
	  adapter = store.adapterForType(MyTalk.PersonalData);
	  adapter.didFindRecord(store, MyTalk.PersonalData, sideload,0); 
  }
});
