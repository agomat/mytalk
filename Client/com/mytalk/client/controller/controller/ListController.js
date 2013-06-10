/**
* Filename: ListController.js
* Package: com.mytalk.client.controller.controller
* Dependencies: com.mytalk.client.model.storage.processing.ProcessorFactory
*               com.mytalk.client.model.modelstruct.List
*               com.mytalk.client.model.modelstruct.User
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
* Description: Controller deputato alla gestione delle liste di utenti
*
*/

MyTalk.ListController = Ember.ObjectController.extend({
  //sortProperties: ['name'], TODO

  /**
   * Questo metodo è deputato all'eliminazione di una lista.
   *
   * @method +deleteList                                     
   * @return {Void} 
  */

  deleteList: function (){
    alert(this.get('content').get('name'));
    var listName = this.get('content').get('name');
    var listId = this.get('content').get('id');
    var response = confirm('Sei sicuro di voler eliminare la lista '+listName+'?');
    if(response == true){
      var context = this.get('target.router');
      var processorFactory = MyTalk.ProcessorFactory.create({});
      var processor = processorFactory.createProcessorProduct( "ListDelete" );
      processor.process({
        listId: listId,
        name: listName
      });
      context.replaceWith('list', MyTalk.List.find(0) );
    }
  },

  /**
   * Questo metodo è deputato alla rinominazione di una lista
   *
   * @method +renameList                                     
   * @return {Void} 
  */

  renameList: function (){ 
    var listId = this.get('content').get('id');
    var listName = this.get('content').get('name');
    
    var newName = prompt("Digita il nuovo nome della lista:");
    var lists = MyTalk.List.find();
    var test = true;

    if(newName){
      lists.forEach( function(t){
        if(t.get('name') == newName){
          test=false;
        }
      });
      if(test==true){
        var processorFactory = MyTalk.ProcessorFactory.create({});
        var processor = processorFactory.createProcessorProduct( "UpdateListName" );
        processor.process({
          listId: listId,
          newName: newName,
          oldName: listName,
        });

      }
    else {
      alert("Esiste già una lista con quel nome");
      }
    }
  },

   /**
   * Questo metodo è deputato al filtraggio degli utenti, secondo la ricerca effettuata, all'interno della lista corrente.
   *
   * @method +filter
   * @param {String} value contiene la stringa con cui fare la ricerca degli nutenti all'interno della lista.
   * @return {Void} 
  */

  filter:function(value){
    var context=this;
    var fcontent=[];
   
    if(value){
      context.get('content').get('users').forEach(function(entry){
        if((entry.get('fullNameConc')).indexOf(value) !== -1) {
          entry.set('unmatched',false);
        }
        else{
          entry.set('unmatched',true);
        }
      });

    }
    else{
      context.get('content').get('users').setEach('unmatched',false);
    }
  },

  /**
   * Questo metodo è deputato al reset della ricerca delgli utenti  nella lista corrente.
   *
   * @method -umatchedReset                                     
   * @return {Void} 
  */

  umatchedReset:function(){
    this.get('content').get('users').setEach('unmatched',false);
  }.observes(this)
});