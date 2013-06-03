/**
* Filename: AuthenticationFailBlacklistRemove.js
* Package: com.mytalk.storage.processing.processor.incoming.list
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

MyTalk.processor.AuthenticationFailBlacklistRemove = Ember.Object.extend(MyTalk.AbstractInProcessorProduct, {
  name: 'AuthenticationFailBlacklistRemove',

  process: function (ari) {
    console.error("Processor "+this.get('name')+" non esistente TODO");
  },
  
  getProcessorName: function () {
  	return this.get('name');
  } 
});