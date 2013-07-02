/**
* Filename: ProfileController.js
* Package: com.mytalk.client.controller.controller
* Dependencies: com.mytalk.client.model.storage.processing.ProcessorFactory
* Author: Campese Stefano
* Date: 2013-07-01
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-07-01 | SC        | [+] Scrittura classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*
*  Controller deputato alla visualizzazione e modifica del profilo utente.
*
*/

MyTalk.ProfileController = Ember.ObjectController.extend({

  updateProfile:function(newName, newSurname, newUsername, newEmail, newPassword, passwordConf){
    var processorFactory = MyTalk.ProcessorFactory.create({});
    var processor = processorFactory.createProcessorProduct( "UpdateAccount" );
    processor.process({
      name: newName,
      surname: newSurname,
      username: newUsername,
      email: newEmail,
      password: newPassword,
    });
  },

  deleteAccount:function(){
    var processorFactory = MyTalk.ProcessorFactory.create({});
    var processor = processorFactory.createProcessorProduct( "DeleteAccount" );
    processor.process({
      username: me.get('username'),
    });
  }


});