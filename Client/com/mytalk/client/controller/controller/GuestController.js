/**
* Filename: IndexController.js
* Package: com.mytalk.client.controller.controller
* Dependencies: com.mytalk.client.controller.statemanager.AppState
*               com.mytalk.client.model.storage.processing.ProcessorFactory
* Author: Campese Stefano
* Date: 2013-04-23
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-04-23 | SC        | [+] Scrittura classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

MyTalk.GuestController = Ember.ObjectController.extend({
  register:function(name, surname, username, email, password1, password2){
    var processorFactory = MyTalk.ProcessorFactory.create();
    var processor = processorFactory.createProcessorProduct("CreateAccount");
    processor.process({
      name: name,
      surname: surname,
      username: username,
      email: email,
      password: password1
    });
  }
});
