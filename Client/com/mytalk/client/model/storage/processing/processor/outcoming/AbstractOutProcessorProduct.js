/**
* Filename: AbstractOutProcessorProduct.js
* Package: com.mytalk.client.model.storage.processing.processor.outcoming
* Dependencies:
* Author: Agostinetto Mattia
* Date: 2013-05-01
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
*  0.2     | 2013-05-03 | MA        | [+] Stesura metodo sendToServer
* 0.1      | 2013-05-01 | MA        | [+] Creazione del Mixin
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*
* Classe astratta per il processing delle richieste che il client genera dopo aver rilevato una modifica al Model.
* Ogni processore che tratta modifiche al client deve estendere questa classe e ridefinirne i suoi metodi.
* I processori ridefiniscono il metodo _process_ per poter eseguire le modifiche opportune al model. In seguito
* _SocketAdapter_ chiamerà automaticamente il metodo _sendToServer_ che verrà ridefinito dai processori per 
* inviare al server le modifiche che hanno inficiato il Model
*/

MyTalk.AbstractOutProcessorProduct = Ember.Mixin.create({
  
  /**
  * Metodo astratto che prende in input un hash contenente le informazioni da scrivere (o modificare) nel Model.
  *
  * @method +process
  * @params {Object} hash contenente le infomazioni che dovranno essere inserite, modificate o aggiornate nel Model
  * @return {Void}
  */
  process: function (params) {
    console.error('The method process() [com.mytalk.model.storage.processing.processor.outcoming.AbstractOutProcessorProduct] cannot be called beacuse it is abstract');
  },

  /**
  * Metodo astratto che si occupa di inviare al server, mediante la connessione WebSocket, il record che è stato
  * modificato, aggiornato o creato.
  *
  * @method +sendToServer
  * @params {Websocket} riferimento alla connessione WebSocket attiva
  * @params {Object} record interessato alla modifica che dovrà essere inviato al server
  * @params {Function} callback che deve essere eseguita se l'invio del messaggio al server ha avuto esito positivo
  * @return {Void}
  */
  sendToServer: function (socket, record, onSent) {
    console.error('The method sendToServer() [com.mytalk.model.storage.processing.processor.outcoming.AbstractOutProcessorProduct] cannot be called beacuse it is abstract');
  },

  /**
  * Metodo astratto che ritorna il nome del processore
  *
  * @method +getProcessorName
  * @return {String}
  */
  getProcessorName: function () {
    console.error('The method getProcessorName() [com.MyTalk.model.storage.processing.processor.outcoming.AbstractOutProcessorProduct] cannot be called beacuse it is abstract');
  } 

});

