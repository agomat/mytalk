/**
* Filename: IpNotLoggedLogin.js
* Package: com.mytalk.client.model.storage.processing.processor.incoming.user
* Dependencies: com.mytalk.client.model.storage.processing.processor.incoming.AbstractInProcessorProduct
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
* Processore che viene eseguito quando il server rileva che l'utente con quell'IP non è loggato nel sistema. Il client
* deve ignorare questo tipo di richiesta e di conseguenza il metodo _process_ deve avere corpo vuoto
*
*/

MyTalk.processor.IpNotLoggedLogin = Ember.Object.extend(MyTalk.AbstractInProcessorProduct, {
  name: 'IpNotLoggedLogin',

  process: function (ari) {
    console.error("Processor "+this.get('name')+" non esistente TODO");
  },
  
  getProcessorName: function () {
    return this.get('name');
  }
});

  /**
  * Memorizza il nome del processore
  * 
  * @property -name
  * @type {String}
  */
 /**
  * Il metodo deve avere corpo vuoto
  *
  * @method +process
  * @param {String} Stringa JSON che rappresenta il pacchetto ARI
  * @return {Void}
  * @override CCMOD2.processing.processor.incoming$AbstractInProcessorProduct$
  */
  /**
  * Il metodo deve ritornare l'attributo _name_
  *
  * @method +getProcessorName
  * @return {String}
  * @override CCMOD2.processing.processor.incoming$AbstractInProcessorProduct$
  */