/**
* Filename: SuccessfulAcceptCall.js
* Package: com.mytalk.client.model.storage.processing.processor.incoming.communication
* Dependencies: com.mytalk.client.model.storage.processing.processor.incoming.AbstractInProcessorProduct
*               com.mytalk.client.model.statemanager.CallState
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
* Processore che viene eseguito quando l'utente riceve il messaggio ARI di conferma chiamata da un altro peer.
*
*/

MyTalk.processor.SuccessfulAcceptCall = Ember.Object.extend(MyTalk.AbstractInProcessorProduct,{
  /**
  * Memorizza il nome del processore
  * 
  * @property -name
  * @type {String}
  *
  */
  name: 'SuccessfulAcceptCall',

  /**
  * Il metodo deve convertire la stringa in un oggetto mediante il metodo _JSON.parse_.
  * Deve poi creare un hash da passare allo StateManager _CallState_ transitando allo stato "beingConnected".
  * L'hash deve indicare il fatto che l'utente attuale sia il chiamante e deve passare
  * le informazioni WebRTC allo StateManager
  *
  * @method +process
  * @param {String} Stringa JSON che rappresenta il pacchetto ARI
  * @return {Void}
  * @override CCMOD2.processing.processor.incoming$AbstractInProcessorProduct$
  */
  process: function (ari) {
    var payload = JSON.parse( ari.info );

    var callData = Ember.Object.create({
      isCaller: true,
      RTCinfo: JSON.parse(payload.RTCinfo)
    });
    MyTalk.CallState.send('beingConnected', callData);
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