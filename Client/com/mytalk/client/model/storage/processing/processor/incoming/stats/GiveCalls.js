/**
* Filename: GiveCalls.js
* Package: com.mytalk.client.model.storage.processing.processor.incoming.stats
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
  * TODO
  *
  * @method +process
  * @param {String} Stringa JSON che rappresenta il pacchetto ARI
  * @return {Void}
  * @override CCMOD2.processing.processor.incoming$AbstractInProcessorProduct$
  */
  process: function (ari) {
    var obj = {
          w_call:{
            id: 0,
            total_byte_sent: 6545,
            total_byte_received: 5545,
            total_duration: 2545,
            list: [
                    { id: 0, 
                      speaker_id: 2, 
                      caller:true,
                      start_date:"02-02-2013 16:44:56",
                      duration:600,
                      byte_sent:10000,
                      byte_received:10000
                    },
                    { id: 1, 
                      speaker_id: 1, 
                      caller:true,
                      start_date:"02-02-2013 16:44:56",
                      duration:600,
                      byte_sent:10000,
                      byte_received:10000
                    },
                    { id: 2, 
                      speaker_id: 5, 
                      caller:true,
                      start_date:"02-02-2013 16:44:56",
                      duration:600,
                      byte_sent:10000,
                      byte_received:10000
                    },
                  ]
          }
     };
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