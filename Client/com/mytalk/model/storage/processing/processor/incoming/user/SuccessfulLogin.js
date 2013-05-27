MyTalk.processor.SuccessfulLogin = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct, {
  name: 'SuccessfulLogin',

  process: function (ari) {
    var worldList = JSON.parse(ari.info).worldList;
    var store = DS.get("defaultStore");
    store.loadMany(MyTalk.List, worldList);
    adapter = store.adapterForType(MyTalk.List);
    adapter.didFindMany(store, MyTalk.List, worldList);
    // creazione lista generale
    MyTalk.List.createRecord({id:0, name:'Tutti i contatti'}).get('users').addObjects( MyTalk.User.find( ) );
    //MyTalk.List.find(0).get('transaction').commit(); // vedere se da problemi ai ragazzi del server
    MyTalk.StateManager.send("login"); 
  }
});
