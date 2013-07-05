/**
* Filename: AddCall.js
* Package: com.mytalk.client.model.storage.processing.processor.outcoming.stats
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
  process: function (ari) {
    console.debug("Processor "+this.get('name')+" non esistente TODO");
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

  // TODO FARE METODO


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
