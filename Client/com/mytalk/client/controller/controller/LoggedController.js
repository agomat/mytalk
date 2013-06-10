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
* Description: Controller per visualizzazione delle liste dell'utente che ha effettuato il login.
*
*/

MyTalk.LoggedController = Ember.ObjectController.extend({
  sortProperties: ['name'],

  /**
   * Questo metodo è deputato alla creazione di una nuova lista.
   *
   * @method +createList                                     
   * @return {Void} 
  */

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
        var processorFactory = MyTalk.ProcessorFactory.create({});
        var processor = processorFactory.createProcessorProduct( "ListCreate" );
        processor.process({
          id: list.get('length'),
          name: newName
        });
      }
      else {
        alert("Esiste già una lista con questo nome");
      }
    }  
  },
  
});