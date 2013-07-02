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
*  Controller deputato alla visualizzazione, modifica o eliminazione del profilo utente.
*
*/

MyTalk.ProfileController = Ember.ObjectController.extend({


  /**
   * Questo metodo è deputato all'aggiornamento del profilo utente.
   * Il metodo si occupa di controllare che l'utente confermi la sua intenzione di aggiornare il profilo
   * (la richiesta di conferma avviene tramite $confirm$ JavaScript).
   * Nel caso l'utente confermi questa decisione il metodo crea un'istanza del processore adeguato 
   * al quale sarà delegato il lavoro di aggiornare effettivamente l'account.
   *
   * @method +updateProfile                                     
   * @return {Void} 
  */

  updateProfile:function(newName, newSurname, newUsername, newEmail, newPassword, passwordConf){
    var c=confirm("Sei sicuro di voler aggiornare i cambiamenti?");
    if(c){
      var processorFactory = MyTalk.ProcessorFactory.create({});
      var processor = processorFactory.createProcessorProduct( "UpdateAccount" );
      processor.process({
       name: newName,
       surname: newSurname,
       username: newUsername,
       email: newEmail,
       password: newPassword,
      });
    }
  },
  
  /**
   * Questo metodo è deputato all'eliminazione dell'account utente.
   * Il metodo si occupa di controllare che l'utente confermi la sua intenzione di eliminare il profilo
   * (la richiesta di conferma avviene tramite $confirm$ JavaScript).
   * Nel caso l'utente confermi questa decisione il metodo crea un'istanza del processore adeguato 
   * al quale sarà delegato il lavoro di elimare effettivamente l'account.
   * Il metodo, inoltre richiama una il metodo $logout()$ per disconnettere l'utente dal sistema dopo l'eliminazione 
   * al fine di creare stati di inconsistenza.
   *
   * @method +deleteAccount                                     
   * @return {Void} 
  */

  deleteAccount:function(){
    var c=confirm("Sei sicuro di voler eliminare il tuo account?");
    if(c){
      var processorFactory = MyTalk.ProcessorFactory.create({});
      var processor = processorFactory.createProcessorProduct( "DeleteAccount" );
      processor.process({});
    }
  }



});