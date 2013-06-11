/**
* Filename: CallState.js
* Package: com.mytalk.client.controller.statemanager
* Dependencies: com.mytalk.client.controller.router.Router
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
* fornisce metodi per entrare o uscire dallo stato di ``chiamata stabilita'' o ``chiamata in terminazione''
*
*/

MyTalk.CallState = Ember.StateManager.create({

  /**
   * Proprietà che abilita iul login
   * @property -enableLoggin           
   * @type {Boolean}                   
   *
  */ 

  enableLogging: true,

  /**
   * Proprietà che contiene il nome dello stato attuale dell'utente, di default è settato a $"isNotBusy"$.
   * @property -initialState           
   * @type {String}                   
   *
  */

  initialState: 'isNotBusy',

  /**
   * Proprietà che rappresenta l'oggetto $Ember State$ che definisce lo stato corrente dell'utente e le operazioni associate a tale stato.
   * @property +isNotBusy           
   * @type {Ember.State}                   
   *
  */

  isNotBusy: Ember.State.create({
    enter: function () {
     
    },
    beingBusy: function (manager, context) {
      var lastContext = manager.isBusy.get('callData');
      context.reopen( lastContext ); 
      manager.isBusy.set('callData', context);
      manager.transitionTo( context.get('path') );
    },
  }),
 
  /**
   * Proprietà che rappresenta l'oggetto $Ember State$ che definisce lo stato corrente dell'utente e le operazioni associate a tale stato.
   * @property +isBusy           
   * @type {Ember.State}                   
   *
  */

  isBusy: Ember.State.create({
    callData: Ember.Object.create({}),
    
    beingConnected: function (manager, context) {
      var lastContext = manager.isBusy.get('callData');
      context.reopen( lastContext ); 
      manager.isBusy.set('callData', context);
      manager.transitionTo( 'isConnected' );
    },

    beingFree: function (manager) {
      manager.isBusy.set('callData', Ember.Object.create({}));
      manager.transitionTo( 'isNotBusy' );
      MyTalk.Router.router.transitionTo('logged.index');
    },
 
  /**
   * Proprietà che rappresenta l'oggetto $Ember State$ che definisce lo stato di una chiamata uscente e le operazioni associate a tale stato.
   * @property +outcomingCall           
   * @type {Ember.State}                   
   *
  */

    outcomingCall: Ember.State.create({
      enter: function (manager) {
        MyTalk.Router.router.transitionTo('calling', manager.isBusy.get('callData').speaker );
      }

    }),
  /**
   * Proprietà che rappresenta l'oggetto $Ember State$ che definisce lo stato di una chiamata entrante e le operazioni associate a tale stato.
   * @property +incomingCall           
   * @type {Ember.State}                   
   *
  */

    incomingCall: Ember.State.create({
      enter: function (manager) {
        MyTalk.Router.router.transitionTo('calling', manager.isBusy.get('callData').speaker );
      },

    }),
  /**
   * Proprietà che rappresenta l'oggetto $Ember State$ che definisce lo stato attuale della connessione e le operazioni associate a tale stato.
   * @property +outcomingCall           
   * @type {Ember.State}                   
   *
  */
    isConnected: Ember.State.create({
      enter: function (manager) {
        var callData = manager.isBusy.get('callData');
        
        if ( callData.isCaller ) {
          var RTCmanager = callData.RTCmanager;
          var RTCinfo = callData.RTCinfo;
        
          RTCmanager.setSDP( RTCinfo.sdp );

          for(var i=0; i<RTCinfo.ice.length; ++i) { //TODO possibilità di delegare il ciclo for
            RTCmanager.addICE( RTCinfo.ice[i] );
          }        }
      },

    })

  })

});