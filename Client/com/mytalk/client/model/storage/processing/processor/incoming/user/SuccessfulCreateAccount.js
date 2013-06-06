/**
* Filename: SuccessfulCreateAccount.js
* Package: com.mytalk.client.model.storage.processing.processor.incoming.user
* Dependencies: com.mytalk.client.model.storage.processing.processor.incoming.AbstractInProcessorProduct
*               com.mytalk.client.model.storage.processing.ProcessorFactory
* Author: Griggio Massimo
* Date: 2013-05-23
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-05-23 | MG        | [+] Stesura dell'intera classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

MyTalk.processor.SuccessfulCreateAccount = Ember.Object.extend(MyTalk.AbstractInProcessorProduct, {
  name: 'SuccessfulCreateAccount',

  process: function(ari) {
    var info = JSON.parse(ari.info);
    var personalData = info.worldPersonalData;
    var user = personalData.pd;
    
    var processorFactory = MyTalk.ProcessorFactory.create({});
    var processor = processorFactory.createProcessorProduct( "Login" );
    processor.process({
      username: user.username,
      password: user.password
    });
  },
  
  getProcessorName: function () {
    return this.get('name');
  }
});