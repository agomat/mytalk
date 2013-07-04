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
    var userId = info.list.id;
    var username = info.list.username;
    var myUsername = MyTalk.PersonalData.find(0).get('username');
    if (myUsername && myUsername != username) {
      if ( MyTalk.User.find(userId).get('name') === undefined ) {
        var store = DS.get("defaultStore");
        var adapter = store.adapterForType(MyTalk.User);

        MyTalk.User.find(userId).get('stateManager').goToState('loaded');

        store.loadMany(MyTalk.User, info);
        adapter.didFindMany(store, MyTalk.User, info);

        MyTalk.List.find(0).get('users').addObject( MyTalk.User.find(userId) );
        MyTalk.List.find(0).get('stateManager').goToState('saved');
      } else {
        MyTalk.User.find(userId).set('online', info.list.online );
        MyTalk.User.find(userId).set('ip', info.list.ip );
        MyTalk.User.find(userId).set('name', info.list.name );
        MyTalk.User.find(userId).set('surname', info.list.surname );
        MyTalk.User.find(userId).set('md5', info.list.md5 );
        MyTalk.User.find(userId).get('stateManager').goToState('saved');
        MyTalk.List.find().forEach(function(list){
          if(list.get('id') > 0) {
            var found = false;
            list.get('users').forEach(function(user){
              if (user.get('id') == userId){
                found = true;
              }
            });
            if(found) {
              list.get('users').removeObject( MyTalk.User.find(userId) );
              list.get('users').addObject( MyTalk.User.find(userId) );
              list.get('stateManager').goToState('saved');
            }
          } else {
              list.get('users').removeObject( MyTalk.User.find(userId) );
              list.get('users').addObject( MyTalk.User.find(userId) );
              list.get('stateManager').goToState('saved');
          }
        });
      }
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