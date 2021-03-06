/**
* Filename: IndexController.js
* Package: com.mytalk.client.controller.controller
* Dependencies: com.mytalk.client.model.statemanager.AppState
*               com.mytalk.client.model.storage.processing.ProcessorFactory
*               com.mytalk.client.model.storage.processing.processor.outcoming.Login
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
*
*  Controller deputato alla gestione dell'autenticazione e alla gestione del template $"index"$
*
*/

MyTalk.IndexController = Ember.ObjectController.extend({
  
  /**
   * Proprietà necessaria per la gestione dello stato dell'applicazione 
   * @property -appStateBinding           
   * @type {Binding}                   
   *
  */    

  appStateBinding: Ember.Binding.oneWay('MyTalk.AppState.currentState.name'),

  /**
   * Proprietà necessaria per memorizzare lo stato corrente 
   * @property -appState           
   * @type {String}                   
   *
  */    

  appState: null,
  
  /**
   * Questo metodo è deputato al controllo dello stato dell'applicazione.
   * Il metodo ritorna $true$ nel caso in cui lo stato sia $isAuthenticated$ false altrimenti.
   *
   * @method +isAuthenticated                                      
   * @return {Boolean} 
  */
 
  isAuthenticated: function () {
    return (this.get('appState') == 'isAuthenticated');
  }.property('appState'),
 
  /**
   * Questo metodo è deputato all'esecuzione dell'autenticazione nell'applicazione. 
   * Il metodo crea un'istanza del processore delegato di eseguire l'autenticazione
   * passandogli tutti i parametri ricevuti in ingresso.
   *
   * @method +login               
   * @param {String} username dell'utente che effettua l'autenticazione                                   
   * @param {String} password dell'utente che effettua l'autenticazione                        
   * @return {Void} 
  */
 
  login: function (username, password) {
    var processorFactory = MyTalk.ProcessorFactory.create({});
    var processor = processorFactory.createProcessorProduct( "Login" );
    processor.process({
      username: username,
      password: password
    });

  }

});
