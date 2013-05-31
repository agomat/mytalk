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
    var record = MyTalk.List.createRecord({id:0, name:'Tutti i contatti'});
    record.get('users').addObjects( MyTalk.User.find() );
    var transaction = record.get('transaction');
    transaction.reopen({
      processor: this
    });
    transaction.commit();   

    // aggiornamento personal data
    var worldPersonalData = JSON.parse(ari.info).worldPersonalData;
    store.loadMany(MyTalk.PersonalData, worldPersonalData);
    adapter = store.adapterForType(MyTalk.PersonalData);
    adapter.didFindMany(store, MyTalk.PersonalData, worldPersonalData);

    MyTalk.AppState.send("beingAuthenticated"); 
  },

   sendToServer: function (socket, record, onSent) { // not override
     onSent( this.getProcessorName(), true );
   },

  getProcessorName: function () {
    return this.get('name');
  } 
});
