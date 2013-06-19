/**
* Filename: StateUpdate.js
* Package: com.mytalk.client.model.storage.processing.processor.incoming.user
* Dependencies: com.mytalk.client.model.storage.processing.processor.incoming.AbstractInProcessorProduct
*               com.mytalk.client.model.modelstruct.PersonalData
*               com.mytalk.client.model.modelstruct.User
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
* Processore che viene eseguito quando il server invia al client un cambiamento di stato di qualche contatto
*
*/

MyTalk.processor.StateUpdate = Ember.Object.extend(MyTalk.AbstractInProcessorProduct, {
  /**
  * Memorizza il nome del processore
  * 
  * @property -name
  * @type {String}
  */
  name: 'StateUpdate',
 /**
  * Il metodo deve avere caricare nel model _DS.User_ gli utenti che il server gli ha inviato
  *
  * @method +process
  * @param {String} Stringa JSON che rappresenta il pacchetto ARI
  * @return {Void}
  * @override CCMOD2.processing.processor.incoming$AbstractInProcessorProduct$
  */
  process: function(ari) {
    var info = JSON.parse(ari.info);
    var username = info.list.username;
    var myUsername = MyTalk.PersonalData.find(0).get('username');
    if (myUsername && myUsername != username) {
      var store = DS.get("defaultStore");
      store.loadMany(MyTalk.User, info);
      var adapter = store.adapterForType(MyTalk.User);
      adapter.didFindMany(store, MyTalk.User, info);
    }

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