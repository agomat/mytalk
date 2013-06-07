/**
* Filename: SuccessfulLoginAsAnonymous.js
* Package: com.mytalk.client.model.storage.processing.processor.incoming.user
* Dependencies: com.mytalk.client.model.storage.processing.processor.incoming.AbstractInProcessorProduct
* Author: Agostinetto Mattia
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

MyTalk.processor.SuccessfulLoginAsAnonymous = Ember.Object.extend(MyTalk.AbstractInProcessorProduct, {
  name: 'SuccessfulLoginAsAnonymous',

  process: function (ari) {
    var Ipart = JSON.parse(ari.info);
    var sideload = Ipart.worldPersonalData;
    var store = DS.get("defaultStore");

    store.loadMany(MyTalk.PersonalData, sideload);
    adapter = store.adapterForType(MyTalk.PersonalData);
    adapter.didFindMany(store, MyTalk.PersonalData, sideload); 

    sideload = { authentication: {id: 0,ip: MyTalk.PersonalData.find(0).get('ip')} };
    store.load(MyTalk.Authentication, sideload);
    adapter = store.adapterForType(MyTalk.Authentication);
    adapter.didFindRecord(store, MyTalk.Authentication, sideload,0); 
  },
  
  getProcessorName: function () {
  	return this.get('name');
  } 
});
