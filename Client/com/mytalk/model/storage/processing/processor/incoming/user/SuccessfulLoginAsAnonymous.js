MyTalk.processor.SuccessfulLoginAsAnonymous = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct, {
  name: 'SuccessfulLoginAsAnonymous',

  process: function (ari) {
  	var Ipart = JSON.parse(ari.info);
    var sideload = Ipart.worldPersonalData;
	  var store = DS.get("defaultStore");
	  store.load(MyTalk.PersonalData, sideload);
	  adapter = store.adapterForType(MyTalk.PersonalData);
	  adapter.didFindRecord(store, MyTalk.PersonalData, sideload,0); 
  }
});
