/**
* Filename: StateUpdate.js
* Package: com.mytalk.client.model.storage.processing.processor.incoming.user
* Dependencies: com.mytalk.client.model.storage.processing.processor.incoming.AbstractInProcessorProduct
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

MyTalk.processor.StateUpdate = Ember.Object.extend(MyTalk.AbstractInProcessorProduct, {
  name: 'StateUpdate',
  process: function(ari) {
    var info = JSON.parse(ari.info);
  	var username = info.list.username;
  	var status = info.list.online;
    var myUsername = MyTalk.PersonalData.find(0).get('username');
    if (myUsername && myUsername != username) {
      var store = DS.get("defaultStore");
      store.loadMany(MyTalk.User, info);
      var adapter = store.adapterForType(MyTalk.User);
      adapter.didFindMany(store, MyTalk.User, info);
    }

  },

  getProcessorName: function () {
    return this.get('name');
  } 

});