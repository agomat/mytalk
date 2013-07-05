/**
* Filename: ListController.js
* Package: com.mytalk.client.controller.controller
* Dependencies: com.mytalk.client.controller.controller.LoggedController
*               com.mytalk.client.model.storage.processing.ProcessorFactory
*               com.mytalk.client.model.modelstruct.List
*               com.mytalk.client.model.modelstruct.User
*               com.mytalk.client.model.storage.processing.processor.outcoming.ListDelete
*               com.mytalk.client.model.storage.processing.processor.outcoming.UpdateListName
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
*  Controller deputato alla gestione delle liste di utenti
*
*/

MyTalk.ListController = Ember.ObjectController.extend({
  needs:['logged'],

  /**
   * Questo metodo è deputato all'eliminazione di una lista. 
   * Il metodo crea un'istanza del processore delegato all'eliminazione della lista,
   * prima di procedere con l'eliminazione si occupa di chiedere la conferma all'utente, in caso l'utente confermi la decisione, 
   * il metodo con l'eliminazione della lista invocando l'istanza del processore creata in precedenza e 
   * delegata dell'eliminazione della lista.
   * Terminata l'operazione il metodo aggiorna il template mostrando la lista $Tutti i contatti$.
   * Il metodo infine deve richiamare il metodo $updateSelect$ del $LoggedController$ al fine di aggiornare 
   * la select nella quale si selezionano le liste nelle quali aggiungere gli utenti.
   *
   * @method +deleteList                                     
   * @return {Void} 
  */

  deleteList: function (){
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
      this.get('controllers.logged').updateSelect();
      context.replaceWith('list', MyTalk.List.find(0) );
    }
  },

  /**
   * Questo metodo è deputato alla ridenominazione di una lista. 
   * Il metodo dopo aver ottenuto id e nome della lista mostra
   * un $prompt$ JavaScript  nel quale l'utente dovrà inserire il nuovo nome da assegnare alla lista in questione.
   * Prima di procedere con l'operazione il metodo verifica che il nome inserito sia valido, 
   * in caso affermativo il metodo si occupa di creare un'istanza del processore delegato alla ridenominazione della lista. 
   * Nel caso in cui il nuovo nome inserito non sia valido (il nome esiste già o è nullo) viene mostrato all'utente un messaggio d'errore con un alert JavaScript
   * impedendo cosi il proseguimento dell'operazione.
   * Il metodo infine deve richiamare il metodo $updateSelect$ del $LoggedController$ al fine di aggiornare 
   * la select nella quale si selezionano le liste nelle quali aggiungere gli utenti.
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
        this.get('controllers.logged').updateSelect();
      }
    else {
      alert("Esiste già una lista con quel nome");
      }
    }
  },

   /**
   * Questo metodo è deputato al filtraggio degli utenti, all'interno della lista corrente, in base alla ricerca effettuata.
   * Per effettuare tale operazione il metodo scorre tutti gli utenti della lista e controlla che la stringa di 
   * ricerca sia uguale alla proprietà $fullNameConc$ degli utenti.
   * In caso affermativo il metodo deve impostare il campo $unmatched$ degli utenti a false.
   * In caso negativo o nel caso in cui la stringa sia vuota il metodo deve impostare la propietà $unmatched$ a true.
   *
   * @method +filter
   * @param {String} value contiene la stringa con cui fare la ricerca degli utenti all'interno della lista.
   * @return {Void} 
  */

  filter:function(value){
    var context=this;
    var fcontent=[];
    
    value = $.trim(value).toLowerCase();
    if(value){
      context.get('content').get('users').forEach(function(entry){
        if((entry.get('fullNameConc')).indexOf(value) !== -1) {
          entry.set('unmatched',false);
        }
        else{
          entry.set('unmatched',true);
        }
        entry.get('stateManager').goToState('saved');
      });

    }
    else{
      context.get('content').get('users').setEach('unmatched',false);
    }
  },

  /**
   * Questo metodo deve azzerrare i risultati della ricerca degli utenti della lista corrente, 
   * tale metodo deve impostare la proprietà $unmatched$ degli utenti a $false$ in modo tale 
   * che tutti gli utenti della lista siano mostrati.
   *
   * @method -umatchedReset                                     
   * @return {Void} 
  */

  umatchedReset:function(){
    this.get('content').get('users').setEach('unmatched',false);
  }.observes(this)
});
