/**
* Filename: AppState.js
* Package: com.mytalk.client.controller.statemanager
* Dependencies: com.mytalk.client.controller.router.Router
* Author: Agostinetto Mattia
* Date: 2013-04-23
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-04-23 | MA       | [+] Scrittura classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

MyTalk.AppState = Ember.StateManager.create({
  
  enableLogging: true,
  initialState: "isNotAuthenticated",

  isAuthenticated: Ember.State.create({
    beingNotAuthenticated: function (manager, context) {
      manager.transitionTo('isNotAuthenticated');
    },
  }),

  isNotAuthenticated: Ember.State.create({
    beingAuthenticated: function (manager, context) {
      manager.transitionTo('isAuthenticated');
      MyTalk.Router.router.transitionTo('logged.index');
    }
  })

});