/**
* Filename: SuccessfulLogin.js
* Package: com.mytalk.client.model.storage.processing.processor.incoming.user
* Dependencies: com.mytalk.client.model.storage.processing.processor.incoming.AbstractInProcessorProduct
*               com.mytalk.client.model.modelstruct.Authentication
*               com.mytalk.client.model.modelstruct.AppState
*               com.mytalk.client.model.modelstruct.PersonalData
*               com.mytalk.client.model.modelstruct.User
*               com.mytalk.client.model.modelstruct.List
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
* Processore che viene eseguito quando il server ha correttamente autenticato l'utente
*
*/

MyTalk.processor.SuccessfulLogin = Ember.Object.extend(MyTalk.AbstractInProcessorProduct, {
  /**
  * Memorizza il nome del processore
  * 
  * @property -name
  * @type {String}
  */
  name: 'SuccessfulLogin',

 /**
  * Il metodo deve salvare nel model _DS.User_ la lista di tutti i contatti registrati nel sistema
  * e nel model _DS.PersonalData_ i dati personali che l'utente che si è appena autenticato
  *
  * @method +process
  * @param {String} Stringa JSON che rappresenta il pacchetto ARI
  * @return {Void}
  * @override CCMOD2.processing.processor.incoming$AbstractInProcessorProduct$
  */
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
