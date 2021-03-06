/**
* Filename: AppState.js
* Package: com.mytalk.client.model.statemanager
* Dependencies:
* Author: Agostinetto Mattia
* Date: 2013-04-23
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-04-23 | MA        | [+] Scrittura classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*
* fornisce metodi per entrare o uscire dallo stato di ``autenticazione''
*
*/

MyTalk.AppState = Ember.StateManager.create({

  /**
   * Proprietà che scrive il cambiamento di stato sulla console del browser
   * @property -enableLogging          
   * @type {Boolean}                   
   *
  */ 

  enableLogging: true,
  
  /**
   * Proprietà che contiene il nome dello stato attuale dell'applicazione, di default è impostato a $"isNotAuthenticated"$.
   * @property -initialState           
   * @type {String}                   
   *
  */

  initialState: "isNotAuthenticated",

  /**
   * Proprietà che rappresenta l'oggetto $Ember State$ che definisce lo stato corrente dell'applicazione e le operazioni associate a tale stato.
   * @property +isAuthenticated           
   * @type {Ember.State}                   
   *
  */

  isAuthenticated: Ember.State.create({
    beingNotAuthenticated: function (manager, context) {
      manager.transitionTo('isNotAuthenticated');
      MyTalk.Router.router.transitionTo('guest');
    }
  }),

  /**
   * Proprietà che rappresenta l'oggetto $Ember State$ che definisce lo stato corrente dell'applicazione e le operazioni associate a tale stato.
   * @property +isNotAuthenticated           
   * @type {Ember.State}                   
   *
  */

  isNotAuthenticated: Ember.State.create({
    beingAuthenticated: function (manager, context) {
      manager.transitionTo('isAuthenticated');
      MyTalk.Router.router.transitionTo('logged.index');
    }
  })

});
