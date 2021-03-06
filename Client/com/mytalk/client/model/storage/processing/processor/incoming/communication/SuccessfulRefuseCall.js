/**
* Filename: SuccessfulRefuseCall.js
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
* Processore che viene eseguito quando il contatto che si desidera chiamare è offline
*
*/

MyTalk.processor.SuccessfulRefuseCall = Ember.Object.extend(MyTalk.AbstractInProcessorProduct, {
  /**
  * Memorizza il nome del processore
  * 
  * @property -name
  * @type {String}
  *
  */
  name: 'SuccessfulRefuseCall',

  /**
  * Il metodo deve convertire la stringa in un oggetto mediante il metodo _JSON.parse_.
  * Deve poi creare un hash da passare allo StateManager _CallState_ transitando allo stato "isNotBusy".
  *
  * @method +process
  * @param {String} Stringa JSON che rappresenta il pacchetto ARI
  * @return {Void}
  * @override CCMOD2.processing.processor.incoming$AbstractInProcessorProduct$
  */
  process: function (ari) {
    document.getElementById('modem-dial').pause();
    document.getElementById('busy').play();
    RTCmanager = MyTalk.CallState.get('isBusy').get('callData').RTCmanager;
    RTCmanager.closeConnection(function(){});
    MyTalk.CallState.send('beingFree');
    alert("MyTalk, messaggio gratuito: l'utente da lei chiamato potrebbe essere occupato o non raggiungibile, la preghiamo di riprovare più tardi, grazie!");
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