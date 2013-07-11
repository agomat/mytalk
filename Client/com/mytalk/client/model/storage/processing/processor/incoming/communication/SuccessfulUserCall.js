/**
* Filename: SuccessfulUserCall.js
* Package: com.mytalk.client.model.storage.processing.processor.incoming.communication
* Dependencies: com.mytalk.client.model.storage.processing.processor.incoming.AbstractInProcessorProduct
*               com.mytalk.client.model.storage.processing.processor.outcoming.communication.RefuseCall
*               com.mytalk.client.controller.statemanager.CallState
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
* Processore che viene eseguito quando l'utente riceve il messaggio ARI contenente una richiesta di chiamata
* proveniente da un altro peer
*
*/

MyTalk.processor.SuccessfulUserCall = Ember.Object.extend(MyTalk.AbstractInProcessorProduct,{
  /**
  * Memorizza il nome del processore
  * 
  * @property -name
  * @type {String}
  *
  */
  name: 'SuccessfulUserCall',

  /**
  * Il metodo deve convertire la stringa in un oggetto mediante il metodo _JSON.parse_.
  * Deve poi creare un hash da passare allo StateManager _CallState_ transitando allo stato "isBusy.incomingCall".
  * L'hash deve contenere le informazioni WebRTC del chiamante da passare allo SteteManager
  * le informazioni WebRTC allo StateManager
  *
  * @method +process
  * @param {String} Stringa JSON che rappresenta il pacchetto ARI
  * @return {Void}
  * @override CCMOD2.processing.processor.incoming$AbstractInProcessorProduct$
  */
  process: function (ari) {
    var payload = JSON.parse( ari.info );
    var speaker;
    if(payload.myUserId) {
      speaker = MyTalk.User.find(payload.myUserId);
    }
    else {
      speaker = MyTalk.User.createRecord({ip: payload.myIp, username: null, name: "Utente", surname: "Anonimo"});
    }
    if (MyTalk.CallState.currentState.name != 'isNotBusy'){
      var processorFactory = MyTalk.ProcessorFactory.create({});
      var processor = processorFactory.createProcessorProduct("RefuseCall");
      processor.process({
        speaker: speaker,
        RTCinfo: 'RefuseCall' 
      });
    }
    else {
      var callData = Ember.Object.create({
        path: 'isBusy.incomingCall',
        speaker: speaker,
        RTCinfo: JSON.parse(payload.RTCinfo)
      });

      MyTalk.CallState.send('beingBusy', callData);
    }
  },

  /**
  * Il metodo deve ritornare l'attributo _name_
  *
  * @method +getProcessorName
  * @return {String}
  * @override CCMOD2.processing.processor.incoming$AbstractInProcessorProduct$
  */
  getProcessorName: function() {
    return this.get('name');
  } 
});
