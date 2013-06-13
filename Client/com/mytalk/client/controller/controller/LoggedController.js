/**
* Filename: LoggedController.js
* Package: com.mytalk.client.controller.controller
* Dependencies: com.mytalk.client.model.storage.processing.ProcessorFactory
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
*  Controller deputato alla visualizzazione delle liste dell'utente che ha effettuato il login.
*
*/

MyTalk.LoggedController = Ember.ObjectController.extend({
  sortProperties: ['name'],
  lastListId: null,

  /**
   * Questo metodo è deputato alla creazione di una nuova lista.
   * Il metodo si occupa di controllare che il nome della nuova lista che si intende creare sia valido 
   * (il nome non deve essere nullo o esistere già).
   * Nel caso il nome sia valido il metodo crea un'istanza del processore adeguato 
   * al quale sarà delegato il lavoro di creare la nuova lista.
   * Nel caso in cui il nome non sia valido il metodo 
   * mostra un messaggio di errore tramite un alert di javascript.
   *
   * @method +createList                                     
   * @return {Void} 
  */

  updateLastListId:function(){
    
    var list=this.get('content');
    var r = list.getEach('id');
    r.sort(); 
    this.set('lastListId',r.get('lastObject'));
    console.log(this.get('lastListId'));
  }.observes(this),

  createList:function (){

    var newName = prompt("Digita il nome della nuova lista: ");
    if(newName) {
      var list=this.get('content');
      var test=true;

      list.forEach(function(t){
        if(t.get('name')==newName){
          test=false;
        }
      });
      
      if(test==true){
        var id=this.get('lastListId')+1;
        this.set('lastListId',id);
        var processorFactory = MyTalk.ProcessorFactory.create({});
        var processor = processorFactory.createProcessorProduct( "ListCreate" );
        processor.process({
          id: id,
          name: newName
        });
      }
      else {
        alert("Esiste già una lista con questo nome");
      }
    }  
  },
  
});
