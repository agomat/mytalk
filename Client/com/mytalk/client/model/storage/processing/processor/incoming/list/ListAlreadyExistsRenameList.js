/**
* Filename: ListAlreadyExistsRenameList.js
* Package: com.mytalk.client.model.storage.processing.processor.incoming.list
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
*/

MyTalk.processor.ListAlreadyExistsRenameList = Ember.Object.extend(MyTalk.AbstractInProcessorProduct, {
  name: 'ListAlreadyExistsRenameList',

  process: function (ari) {
    console.error("Processor "+this.get('name')+" non esistente TODO");
  },
  
  getProcessorName: function () {
  	return this.get('name');
  } 
});