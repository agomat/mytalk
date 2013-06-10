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
*
* Description: Controller per la gestione della registrazione dell'utente nel sistema e per la gestione del template $"guest"$
*
*/

MyTalk.GuestController = Ember.ObjectController.extend({
  /**
   * Questo metodo è deputato alla registrazione dell'utente nell'applicazione. Il metodo crea un'istanza del processore delegato di eseguire la creazione di un nuovo account.
   * Il metodo passa al processore tutti i parametri che riceve in ingresso.
   *
   * @method +register               
   * @param {String} nome dell'utente che si registra                     
   * @param {String} cognome dell'utente che si registra
   * @param {String} username dell'utente che si registra                 
   * @param {String} email dell'utente che si registra                    
   * @param {String} password1 dell'utente che si registra                
   * @param {String} password2 dell'utente che si registra                
   * @return {Void} 
  */
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
