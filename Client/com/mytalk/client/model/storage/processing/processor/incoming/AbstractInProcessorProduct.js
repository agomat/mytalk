/**
* Filename: AbstractInProcessorProduct.js
* Package: com.mytalk.client.model.storage.processing.processor.incoming
* Dependencies:
* Author: Mattia Agostinetto
* Date: 2013-05-01
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
*	0.2     | 2013-05-03 | MA        | [+] Stesura metodo sendToServer
* 0.1	    | 2013-05-01 | MA        | [+] Creazione del Mixin
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

MyTalk.AbstractInProcessorProduct = Ember.Mixin.create({
  
  process: function (params) {
  	console.error('The method process [com.MyTalk.model.storage.processing.processor.incoming.AbstractInProcessorProduct] cannot be called beacuse it is abstract');
  },

  getProcessorName: function () {
  	console.error('The method getProcessorName [com.MyTalk.model.storage.processing.processor.incoming.AbstractInProcessorProduct] cannot be called beacuse it is abstract');
  } 

});

