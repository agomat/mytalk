/**
* Filename: AuthenticationFailGetCalls.js
* Package: com.mytalk.storage.processing.processor.incoming.stats
* Dependencies: com.mytalk.storage.processing.processor.incoming.AbstractInProcessorProduct
* Author: Mattia Agostinetto
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

MyTalk.processor.AuthenticationFailGetCalls = Ember.Object.extend(MyTalk.AbstractInProcessorProduct, {
  name: 'AuthenticationFailGetCalls',

  process: function (ari) {
    console.error("Processor "+this.get('name')+" non esistente TODO");
  },
  
  getProcessorName: function () {
    return this.get('name');
  }
});