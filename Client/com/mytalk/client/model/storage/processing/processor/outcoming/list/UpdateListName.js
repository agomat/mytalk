/**
* Filename: UpdateListName.js
* Package: com.mytalk.client.model.storage.processing.processor.outcoming.list
* Dependencies:  com.mytalk.client.model.storage.processing.processor.outcoming.AbstractOutProcessorProduct
*                com.mytalk.client.model.modelstruct.List
*                com.mytalk.client.model.modelstruct.Authentication
*                com.mytalk.client.model.modelstruct.PersonalData
*                com.mytalk.client.model.storage.ARI
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
* Processore che viene eseguito quando l'utente sceglie di aggiornare il nome di una lista personalizzata
*
*/

MyTalk.processor.UpdateListName = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct,{
  /**
  * Memorizza il nome del processore
  * 
  * @property -name
  * @type {String}
  */
 /**
  * Il metodo deve selezionare il record che corrisponde alla lista da rinominare e rinominarla
  *
  * @method +process
  * @param {Object} Stringa JSON che rappresenta il pacchetto ARI
  * @return {Void}
  * @override CCMOD2.processing.processor.outcoming$AbstractOutProcessorProduct$
  */
 /**
  * Il metodo deve inviare al server un ARI avente richiesta _UpdateListName_ 
  *
  * @method +sendToServer
  * @param {WebSocket} istanza di connessione WebSocket
  * @param {Object} record Call da inviare al server
  * @param {Function} callback che deve essere eseguita quando il pacchetto risulta correttamente inviato al server
  * @return {Void}
  * @override CCMOD2.processing.processor.outcoming$AbstractOutProcessorProduct$
  */
  /**
  * Il metodo deve ritornare l'attributo _name_
  *
  * @method +getProcessorName
  * @return {String}
  * @override CCMOD2.processing.processor.outcoming$AbstractOutProcessorProduct$
  */
  name: 'UpdateListName',

  process: function (params) {
    var record = MyTalk.List.find(params.listId);
    var transaction = record.get('transaction');
    
    record.set('name', params.newName);
    transaction.reopen({
      processor: this,
      oldListName: params.oldName
    });

    transaction.commit();
  },

  sendToServer: function (socket, record, onSent) {
    var ARI = new Object();

    var auth = new Object();
    auth.username = MyTalk.Authentication.find(0).get('username');
    auth.password = MyTalk.Authentication.find(0).get('password');
    auth.ip = MyTalk.PersonalData.find(0).get('ip');

    ARI.auth = auth;
    ARI.req = this.get('name');
    ARI.info = new Object();
     ARI.info.listName = record.get('transaction').get('oldListName');
     ARI.info.owner = MyTalk.PersonalData.find(0).get('username');
     ARI.info.newListName = record.get('name');
    
    ARI.info = JSON.stringify( ARI.info );

    socket.send( JSON.stringify(ARI) );
    onSent( this.getProcessorName(), true );
  },

  getProcessorName: function () {
    return this.get('name');
  } 
});