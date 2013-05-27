MyTalk.processor.SuccessfulLoginAsAnonymous = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct, {
  name: 'SuccessfulLoginAsAnonymous',

  process: function (ari) {
  	var Ipart = JSON.parse(ari.info);
    var sideload = Ipart.worldPersonalData;
	  var store = DS.get("defaultStore");

	  store.load(MyTalk.PersonalData, sideload);
	  adapter = store.adapterForType(MyTalk.PersonalData);
	  adapter.didFindRecord(store, MyTalk.PersonalData, sideload,0); 

    sideload = { authentication: {id: 0,ip: MyTalk.PersonalData.find(0).get('ip')} };
    store.load(MyTalk.Authentication, sideload);
    adapter = store.adapterForType(MyTalk.Authentication);
    adapter.didFindRecord(store, MyTalk.Authentication, sideload,0); 
  },
  
  getProcessorName: function () {
  	return this.get('name');
  } 
});
