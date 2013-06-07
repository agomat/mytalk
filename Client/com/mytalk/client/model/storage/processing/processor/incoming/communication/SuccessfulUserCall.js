/**
* Filename: SuccessfulUserCall.js
* Package: com.mytalk.client.model.storage.processing.processor.incoming.communication
* Dependencies: com.mytalk.client.model.storage.processing.processor.incoming.AbstractInProcessorProduct
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
*/

MyTalk.processor.SuccessfulUserCall = Ember.Object.extend(MyTalk.AbstractInProcessorProduct,{
  name: 'SuccessfulUserCall',

  process: function (ari) {
    var payload = JSON.parse( ari.info );

    // TODO vedere se sono impegnato in altra conversazione

    var callData = Ember.Object.create({
      path: 'isBusy.incomingCall',
      speaker: MyTalk.User.find( payload.myUserId ),
      RTCinfo: JSON.parse(payload.RTCinfo)
    });

    MyTalk.CallState.send('beingBusy', callData);
  },

  getProcessorName: function() {
  	return this.get('name');
  } 
});
