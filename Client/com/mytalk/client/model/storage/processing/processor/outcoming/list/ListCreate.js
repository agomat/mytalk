/**
* Filename: ListCreate.js
* Package: com.mytalk.client.model.storage.processing.processor.outcoming.list
* Dependencies:  com.mytalk.client.model.storage.processing.processor.outcoming.AbstractOutProcessorProduct
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
* Processore che viene eseguito quando l'utente sceglie di creare una nuova lista
*
*/

MyTalk.processor.ListCreate = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct,{
  /**
  * Memorizza il nome del processore
  * 
  * @property -name
  * @type {String}
  */
 /**
  * Il metodo deve creare un nuovo record nel model _DS.List_
  *
  * @method +process
  * @param {Object} Stringa JSON che rappresenta il pacchetto ARI
  * @return {Void}
  * @override CCMOD2.processing.processor.outcoming$AbstractOutProcessorProduct$
  */
 /**
  * Il metodo deve inviare al server un ARI avente richiesta _ListCreate_ 
  *
  * @method +sendToServer
  * @param {WebSocket} instanza di connessione WebSocket
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
  name: 'ListCreate',

  process: function (params) {
    var record = MyTalk.List.createRecord(params);
    var transaction = record.get('transaction');

    transaction.reopen({
      processor: this
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
     ARI.info.listName = record.get('name');
     ARI.info.owner = MyTalk.PersonalData.find(0).get('username');
     ARI.info.newListName = null;
    
    ARI.info = JSON.stringify( ARI.info );

    var wasSent = socket.send( JSON.stringify(ARI) );
    onSent( this.getProcessorName(), wasSent );
  },

  getProcessorName: function () {
    return this.get('name');
  } 
});