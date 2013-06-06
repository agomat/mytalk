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

MyTalk.IndexController = Ember.ObjectController.extend({
  appStateBinding: Ember.Binding.oneWay('MyTalk.AppState.currentState.name'),
  appState: null,

  isAuthenticated: function () {
    return (this.get('appState') == 'isAuthenticated');
  }.property('appState'),

  login: function (username, password) {
    
    var processorFactory = MyTalk.ProcessorFactory.create({});
    var processor = processorFactory.createProcessorProduct( "Login" );
    processor.process({
      username: username,
      password: password
    });

  }

});