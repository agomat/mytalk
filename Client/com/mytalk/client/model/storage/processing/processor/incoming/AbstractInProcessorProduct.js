/**
* Filename: AbstractInProcessorProduct.js
* Package: com.mytalk.client.model.storage.processing.processor.incoming
* Dependencies:
* Author: Agostinetto Mattia
* Date: 2013-05-01
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
*  0.2    | 2013-05-03 | MA        | [+] Stesura metodo sendToServer
*  0.1    | 2013-05-01 | MA        | [+] Creazione del Mixin
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe astratta per il processing delle richieste che il server invia al client al fine di aggiornare il Model.
* Ogni processore che tratta modifiche al Model del client deve estendere questa classe e ridefinirne i suoi metodi.
* Le sottoclassi devono ridefinire il metodo _process_ per poter aggiornare il Model secondo la richiesta ricevuta
* dal server 
*
*/

MyTalk.AbstractInProcessorProduct = Ember.Mixin.create({

  /**
  * Metodo astratto che prende in input un hash contenente le informazioni da scrivere (o modificare) nel Model.
  *
  * @method +process 
  * @params {String} stringa contenente le infomazioni che dovranno essere inserite, modificate o aggiornate nel Model
  * @return {Void}
  */
  process: function (ari) {
    console.debug('The method process [com.mytalk.client.model.storage.processing.processor.incoming.AbstractInProcessorProduct] cannot be called beacuse it is abstract');
  },
  
  /**
  * Metodo astratto che ritorna il nome del processore
  *
  * @method +getProcessorName
  * @return {String}
  */
  getProcessorName: function () {
    console.debug('The method getProcessorName [com.mytalk.client.model.storage.processing.processor.incoming.AbstractInProcessorProduct] cannot be called beacuse it is abstract');
  } 

});

