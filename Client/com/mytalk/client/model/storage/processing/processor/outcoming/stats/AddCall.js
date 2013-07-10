/**
* Filename: AddCall.js
* Package: com.mytalk.client.model.storage.processing.processor.outcoming.stats
* Dependencies:  com.mytalk.client.model.storage.processing.processor.outcoming.AbstractOutProcessorProduct
*                com.mytalk.client.model.storage.ARI
*                com.mytalk.client.model.modelstruct.Call
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
* Processore che viene eseguito quando la chiamata viene terminata
*
*/


MyTalk.processor.AddCall = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct,{
  /**
  * Memorizza il nome del processore
  * 
  * @property -name
  * @type {String}
  */
  name: 'AddCall',
 /**
  * Il metodo deve inserire nel model _DS.Call_ un nuovo record contenente i dettagli della chiamata
  *
  * @method +process
  * @param {Object} Stringa JSON che rappresenta il pacchetto ARI
  * @return {Void}
  * @override CCMOD2.processing.processor.outcoming$AbstractOutProcessorProduct$
  */
  process: function (params) {
    var id = 'r'+Math.floor(Math.random()*999999*16);
    var record = MyTalk.Call.createRecord({
      id: id,
      speaker: MyTalk.User.find(params.speaker),
      caller: params.caller,
      startDate: params.startDate,
      duration: params.duration,
      byteSent: params.sentBytes,
      byteReceived: params.receivedBytes
    });
    var transaction = record.get('transaction');

    transaction.reopen({
      processor: this
    });

    transaction.commit();
  },
 /**
  * Il metodo deve inviare al server un ARI avente richiesta _AddCall_ 
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

    var info = new Object();
    info.w_call = new Object();
    info.w_call.list = [{id:0, speaker_id: record.get('speaker').get('id'), caller: record.get('caller'), start_date: record.get('startDate'), duration: record.get('duration'), byte_sent: record.get('byteSent'), byte_received: record.get('byteReceived')}];
    info.w_call.total_byte_sent = 0;
    info.w_call.total_byte_received = 0;
    info.w_call.total_duration = 0;

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
