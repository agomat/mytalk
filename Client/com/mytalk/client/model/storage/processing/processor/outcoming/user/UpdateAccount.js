/**
* Filename: UpdateAccount.js
* Package: com.mytalk.client.model.storage.processing.processor.outcoming.user
* Dependencies:  com.mytalk.client.model.storage.processing.processor.outcoming.AbstractOutProcessorProduct
*                com.mytalk.client.model.storage.ARI
*                com.mytalk.client.model.modelstruct.PersonalData
*                com.mytalk.client.model.modelstruct.Authentication
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
* Processore che viene eseguito quando l'utente sceglie di modificare le proprie informazioni personali
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
  * Il metodo deve aggiornare i nuovi dati del profilo nel model _DS.PersonalData_ e _DS.Authentication_
  *
  * @method +process
  * @param {Object} Stringa JSON che rappresenta i nuovi dati di profilo
  * @return {Void}
  * @override CCMOD2.processing.processor.outcoming$AbstractOutProcessorProduct$
  */
  process: function (params) {
    var username = MyTalk.Authentication.find(0).get('username');
    var password = MyTalk.Authentication.find(0).get('password');
    
    if (params.password == null){
      delete params.password;
    }

    // Aggiornamento PersonalData
    var recordPd = MyTalk.PersonalData.find(0).setProperties(params);
    var transaction = recordPd.get('transaction');

    transaction.reopen({
      processor: this,
      username: username,
      password: password
    });

    // Aggiornamento Authentication
    var recordAuth = MyTalk.Authentication.find(0).setProperties(params);
    recordAuth.get('stateManager').goToState('saved');

    transaction.commit();
  },
 /**
  * Il metodo deve inviare al server un ARI avente richiesta _UpdateAccount_ e campo dati informativo le informazioni del profilo modificate
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

    ARI.req = this.getProcessorName();

    var auth = new Object();
    auth.username = record.get('transaction').get('username');
    auth.password = record.get('transaction').get('password');
    auth.ip = MyTalk.PersonalData.find(0).get('ip');

    ARI.auth = auth;
    
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