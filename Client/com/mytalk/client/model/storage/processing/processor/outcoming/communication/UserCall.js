/**
* Filename: UserCall.js
* Package: com.mytalk.client.model.storage.processing.processor.outcoming.communication
* Dependencies:  com.mytalk.client.model.storage.processing.processor.outcoming.AbstractOutProcessorProduct
*                com.mytalk.client.model.modelstruct.Authentication
*                com.mytalk.client.model.modelstruct.Call
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
* Processore che viene eseguito quando l'utente sceglie di rifiutare una chiamata in arrivo
*
*/

MyTalk.processor.UserCall = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct,{
  /**
  * Memorizza il nome del processore
  * 
  * @property -name
  * @type {String}
  */
  name: 'UserCall',
 /**
  * Il metodo deve creare un nuovo record nel model _DS.Call_ inserendo i propri e la propria configurazione WebRTC
  *
  * @method +process
  * @param {Object} Stringa JSON che rappresenta il pacchetto ARI
  * @return {Void}
  * @override CCMOD2.processing.processor.outcoming$AbstractOutProcessorProduct$
  */
  process: function (params) {
    var myself = MyTalk.PersonalData.find(0);
    var speaker = params.speaker;
    var RTCinfo = params.RTCinfo;

    var nextId = MyTalk.Call.find().get('length');
    var record = MyTalk.Call.createRecord({id: nextId, speaker: speaker, caller: true, startDate: new Date() });
    var transaction = record.get('transaction');

    transaction.reopen({
      processor: this,
      myIp: myself.get('ip'),
      myUserId: myself.get('userId'),
      speakerIp: speaker.get('ip'),
      speakerUserId: speaker.get('id'),
      RTCinfo: RTCinfo
    });

    transaction.commit();
  },
 /**
  * Il metodo deve inviare al server un ARI avente richiesta _UserCall_ fornendo i propri dati personali e la 
  * propria configurazione WebRTC
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
    auth.username = MyTalk.Authentication.find(0).get('username');
    auth.password = MyTalk.Authentication.find(0).get('password');
    auth.ip = MyTalk.PersonalData.find(0).get('ip');

    ARI.auth = auth;
    ARI.req = this.get('name');



    ARI.info = new Object();
     ARI.info.myIp = record.get('transaction').get('myIp');
     ARI.info.myUserId = record.get('transaction').get('myUserId');
     ARI.info.speakerIp = record.get('transaction').get('speakerIp');
     ARI.info.speakerUserId = record.get('transaction').get('speakerUserId');
     ARI.info.RTCinfo = record.get('transaction').get('RTCinfo');
    
    ARI.info = JSON.stringify( ARI.info );

    var wasSent = socket.send( JSON.stringify(ARI) );
    onSent( this.getProcessorName(), wasSent );
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