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

MyTalk.RequestManager = Ember.Mixin.create({
  
  processRequest: function (record, onSuccess, onError) {
  	Ember.assert('The method processRequest() [com.MyTalk.model.storage.processing.RequestManager] cannot be called beacuse it is abstract',false);
  },

  getRequestName: function () {
  	Ember.assert('The method getRequestName() [com.MyTalk.model.storage.processing.RequestManager] cannot be called beacuse it is abstract',false);
  },  

  // concrete method
  getProcessorByName: function (requestName) {
  
  }


});

