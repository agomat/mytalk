/**
* Filename: UpdateAccount.js
* Package: com.mytalk.client.model.storage.processing.processor.outcoming.user
* Dependencies:  TODO
* Author: Agostinetto Mattia
* Date: 2013-07-01
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-07-01 | MA        | [+] Stesura dell'intera classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*
* Processore che viene eseguito quando l'utente si registra
*
*/


MyTalk.processor.UpdateAccount = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct,{
  /**
  * Memorizza il nome del processore
  * 
  * @property -name
  * @type {String}
  */
  name: 'UpdateAccount',
 /**
  * TODO
  *
  * @method +process
  * @param {Object} Stringa JSON che rappresenta il pacchetto ARI
  * @return {Void}
  * @override CCMOD2.processing.processor.outcoming$AbstractOutProcessorProduct$
  */
  process: function (params) {
    var record = MyTalk.PersonalData.find(0).setProperties(params);
    var transaction = record.get('transaction');

    transaction.reopen({
      processor: this
    });

    transaction.commit();
  },
 /**
  * TODO
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

    ARI.auth = null;
    ARI.req = this.get('name');
    
    var info = new Object();
    info.worldPersonalData = new Object();
    info.worldPersonalData.pd = new Object();
    info.worldPersonalData.pd.username = record.get('username');
    info.worldPersonalData.pd.password = record.get('password');
    info.worldPersonalData.pd.name = record.get('name');
    info.worldPersonalData.pd.surname = record.get('surname');
    info.worldPersonalData.pd.email = record.get('email');
    
    ARI.info = JSON.stringify(info);
    
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