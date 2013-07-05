/**
* Filename: GetCalls.js
* Package: com.mytalk.client.model.storage.processing.processor.outcoming.stats
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
* Processore che viene eseguito quando l'utente sceglie di chiedere al server la lista dello storico delle chiamate
*
*/

MyTalk.processor.GetCalls = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct,{
  /**
  * Memorizza il nome del processore
  * 
  * @property -name
  * @type {String}
  */
  name: 'GetCalls',
 /**
  * TODO
  *
  * @method +process
  * @return {Void}
  * @override CCMOD2.processing.processor.outcoming$AbstractOutProcessorProduct$
  */
  process: function () {
    var record = MyTalk.WCall.createRecord({id:1}); // fix! TODO in caso essistesse già!
    record.get('stateManager').goToState('saved');
    record.deleteRecord();
    var transaction = record.get('transaction');

    transaction.reopen({
      processor: this
    });

    transaction.commit();
  },
 /**
  * Il metodo deve inviare al server un ARI avente richiesta _GetCalls_ 
  *
  * @method +sendToServer
  * @param {WebSocket} istanza di connessione WebSocket
  * @param {Object} record Call da inviare al server
  * @param {Function} callback che deve essere eseguita quando il pacchetto risulta correttamente inviato al server
  * @return {Void}
  * @override CCMOD2.processing.processor.outcoming$AbstractOutProcessorProduct$
  */

  sendToServer: function (socket, record, onSent) {
    var ARI = new Object();

    var auth = new Object();
    auth.username = MyTalk.Authentication.find(0).get('username');
    auth.password = MyTalk.Authentication.find(0).get('password');
    auth.ip = MyTalk.PersonalData.find(0).get('ip');

    ARI.auth = auth;
    ARI.req = this.get('name');
    ARI.info = null;
    
    socket.send( JSON.stringify(ARI) );
    onSent( this.getProcessorName(), true );
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