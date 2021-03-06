/**
* Filename: GiveCalls.js
* Package: com.mytalk.client.model.storage.processing.processor.incoming.stats
* Dependencies:  com.mytalk.client.model.storage.processing.processor.incoming.AbstractInProcessorProduct
*                com.mytalk.client.model.modelstruct.WCall
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
* Processore che viene eseguito quando il server ha inviato al client lo storico delle chiamate.
*
*/

MyTalk.processor.GiveCalls = Ember.Object.extend(MyTalk.AbstractInProcessorProduct, {
  /**
  * Memorizza il nome del processore
  * 
  * @property -name
  * @type {String}
  */
  name: 'GiveCalls',


 /**
  * Il metodo deve caricare nel model WCall il campo informativo del pacchetto _ARI_
  *
  * @method +process
  * @param {String} Stringa JSON che rappresenta il pacchetto ARI
  * @return {Void}
  * @override CCMOD2.processing.processor.incoming$AbstractInProcessorProduct$
  */
  process: function (ari) {
    var obj = JSON.parse(ari.info);
    var store = DS.get('defaultStore'),
      type = MyTalk.WCall,
      adapter = store.adapterForType(type);

    adapter.load(store, type, obj);
    adapter.didFindRecord(store, type, obj,0);
  },

  /**
  * Il metodo deve ritornare l'attributo _name_
  *
  * @method +getProcessorName
  * @return {String}
  * @override CCMOD2.processing.processor.incoming$AbstractInProcessorProduct$
  */
  getProcessorName: function () {
    return this.get('name');
  }
});