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
*/

MyTalk.ListController = Ember.ObjectController.extend({
  sortProperties: ['name'],

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

  umatchedReset:function(){
    this.get('content').get('users').setEach('unmatched',false);
  }.observes(this)
});