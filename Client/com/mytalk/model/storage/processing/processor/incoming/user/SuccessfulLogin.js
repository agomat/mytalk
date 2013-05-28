MyTalk.processor.SuccessfulLogin = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct, {
  name: 'SuccessfulLogin',

  process: function (ari) {
    var store = DS.get("defaultStore");
    
    // popolamento liste
    var worldList = JSON.parse(ari.info).worldList;
    store.loadMany(MyTalk.List, worldList);
    adapter = store.adapterForType(MyTalk.List);
    adapter.didFindMany(store, MyTalk.List, worldList);
    // creazione lista generale
    MyTalk.List.createRecord({id:0, name:'Tutti i contatti'}).get('users').addObjects( MyTalk.User.find( ) );
    //MyTalk.List.find(0).get('transaction').commit(); // vedere se da problemi ai ragazzi del server
    
    // aggiornamento personal data
    var worldPersonalData = JSON.parse(ari.info).worldPersonalData;
    store.loadMany(MyTalk.PersonalData, worldPersonalData);
    adapter = store.adapterForType(MyTalk.PersonalData);
    adapter.didFindMany(store, MyTalk.PersonalData, worldPersonalData);
    //MyTalk.PersonalData.setProperties({});

    MyTalk.StateManager.send("login"); 
  }
});
