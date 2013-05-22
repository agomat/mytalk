/**
* Filename: RequestManager.js
* Package: com.mytalk.client [...]
* Author: Mattia Agostinetto
* Editor: Sublime Text 2
* Date: 2013-04-26
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-05-01 | MA        | [+] Creazione del Mixin
*		  |			   |           | [+] <scrivere>
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

MyTalk.AbstractOutProcessorProduct = Ember.Mixin.create({
  
  process: function (params) {
  	console.error('The method processRequest() [com.MyTalk.model.storage.processing.RequestManager] cannot be called beacuse it is abstract');
  },

  sendToServer: function (socket, record, onSent) {
  	console.error('The method processRequest() [com.MyTalk.model.storage.processing.RequestManager] cannot be called beacuse it is abstract');
  },

  getProcessorName: function () {
  	console.error('The method getRequestName() [com.MyTalk.model.storage.processing.RequestManager] cannot be called beacuse it is abstract');
  } 

});

