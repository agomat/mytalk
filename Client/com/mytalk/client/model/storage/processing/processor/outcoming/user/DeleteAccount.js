/**
* Filename: DeleteAccount.js
* Package: com.mytalk.client.model.storage.processing.processor.outcoming.user
* Dependencies:  com.mytalk.client.model.storage.processing.processor.outcoming.AbstractOutProcessorProduct
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
* Processore che viene eseguito quando l'utente sceglie di cancellare il proprio account dal sistema
*
*/

MyTalk.processor.DeleteAccount = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct,{
  /**
  * Memorizza il nome del processore
  * 
  * @property -name
  * @type {String}
  */
  name: 'DeleteAccount',
 /**
  * Il metodo deve cancellare il record avente ID 0 nel model _DS.PersonalData_
  *
  * @method +process
  * @param {Object} Stringa JSON che rappresenta il pacchetto ARI
  * @return {Void}
  * @override CCMOD2.processing.processor.outcoming$AbstractOutProcessorProduct$
  */
  process: function (params) {
    var record = MyTalk.Authentication.find( 0 );
    record.deleteRecord();
    var transaction = record.get('transaction');

    transaction.reopen({
      processor: this,
      id: MyTalk.PersonalData.find( 0 ).get('id'),
      username: MyTalk.PersonalData.find( 0 ).get('username'),
      name: MyTalk.PersonalData.find( 0 ).get('name'),
      surname: MyTalk.PersonalData.find( 0 ).get('surname'),
      md5: MyTalk.PersonalData.find( 0 ).get('md5'),
      ip: MyTalk.PersonalData.find( 0 ).get('ip'),
      online: false
    });

    transaction.commit();
  },
 /**
  * Il metodo deve inviare al server un ARI avente richiesta _DeleteAccount_ per poter eliminare permanenetemente
  * l'account dal database del server
  *
  * @method +sendToServer
  * @param {WebSocket} instanza di connessione WebSocket
  * @param {Object} record Call da inviare al server
  * @param {Function} callback che deve essere eseguita quando il pacchetto risulta correttamente inviato al server
  * @return {Void}
  * @override CCMOD2.processing.processor.outcoming$AbstractOutProcessorProduct$
  */

  sendToServer: function (socket, record, onSent) {
    var ARI = new Object();

    var auth = new Object();
    auth.username = record.get('username');
    auth.password = record.get('password');
    auth.ip = record.get('ip');

    var credenzials = new Object();
    credenzials.username = record.get('username');
    credenzials.password = record.get('password');

    
    ARI.auth = auth;
    ARI.req = this.get('name');

    var aux = new Object();
    aux.list = new Object();
    aux.list.id = record.get('transaction').get('id');
    aux.list.username = record.get('transaction').get('username');
    aux.list.name = record.get('transaction').get('name');
    aux.list.surname = record.get('transaction').get('surname');
    aux.list.md5 = record.get('transaction').get('md5');
    aux.list.ip = record.get('transaction').get('ip');
    aux.list.online = record.get('transaction').get('online');

    ARI.info = JSON.stringify( aux );
    
    socket.send( JSON.stringify(ARI) );
    onSent( this.getProcessorName(), true );
    window.location = '';
  },

  /**
  * Il metodo deve ritornare l'attributo _name_
  *
  * @method +getProcessorName
  * @return {String}
  * @override CCMOD2.processing.processor.outcoming$AbstractOutProcessorProduct$
  */
  getProcessorName: function () {
    return this.get('name');
  }
});