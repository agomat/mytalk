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
*
* Processore che viene eseguito quando il server ha registrato la presenza di un utente ospite
*
*/

MyTalk.processor.SuccessfulLoginAsAnonymous = Ember.Object.extend(MyTalk.AbstractInProcessorProduct, {
  /**
  * Memorizza il nome del processore
  * 
  * @property -name
  * @type {String}
  */
  name: 'SuccessfulLoginAsAnonymous',
 /**
  * Il metodo deve popolare il model _DS.PersonalData_ con solamente il campo dati IP che corrisponde
  * all'indirizzo IP che il server ha assegnato al client
  *
  * @method +process
  * @param {String} Stringa JSON che rappresenta il pacchetto ARI
  * @return {Void}
  * @override CCMOD2.processing.processor.incoming$AbstractInProcessorProduct$
  */
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
  /**
  * Il metodo deve ritornare l'attributo _name_
  *
  * @method +getProcessorName
  * @return {String}
  * @override CCMOD2.processing.processor.incoming$AbstractInProcessorProduct$
  */
  getProcessorName: function () {
    return this.get('name');
  } 
});