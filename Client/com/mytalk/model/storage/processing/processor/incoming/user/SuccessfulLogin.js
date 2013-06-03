/**
* Filename: SuccessfulLogin.js
* Package: com.mytalk.storage.processing.processor.incoming.user
* Dependencies: com.mytalk.storage.processing.processor.incoming.AbstractInProcessorProduct
*               com.mytalk.controller.statemanager.AppState
* Author: Mattia Agostinetto
* Date: 2013-05-01
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-05-01 | MA        | [+] Stesura dell'intera classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

MyTalk.processor.SuccessfulLogin = Ember.Object.extend(MyTalk.AbstractInProcessorProduct, {
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
