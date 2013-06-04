/**
* Filename: ProcessorFactory.js
* Package: com.mytalk.client.model.storage.processing
* Dependencies:  com.mytalk.client.model.storage.processing.processor.incoming.communication.*
*                com.mytalk.client.model.storage.processing.processor.incoming.list.*
*                com.mytalk.client.model.storage.processing.processor.incoming.stats.*
*                com.mytalk.client.model.storage.processing.processor.incoming.user.*
*                com.mytalk.client.model.storage.processing.processor.outcoming.communication.*
*                com.mytalk.client.model.storage.processing.processor.outcoming.list.*
*                com.mytalk.client.model.storage.processing.processor.outcoming.stats.*
*                com.mytalk.client.model.storage.processing.processor.outcoming.user.*
* Author: Mattia Agostinetto
* Date: 2013-05-01
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-05-01 | MA        | [+] Scrittura classe e factory method
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

MyTalk.ProcessorFactory = Ember.Object.extend({
  
  createProcessorProduct: function(requestName) { 
    var processor = eval("MyTalk.processor." + requestName);
    if( processor ) {
      return processor.create({});
    } else {
      console.error('Processor ' + requestName + ' does not exists');
      return null;
    }
  }

});