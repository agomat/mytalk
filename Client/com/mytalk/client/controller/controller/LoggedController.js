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

 /**
   * Proprietà che contiene nomi e id delle liste nelle quali mettere un utente, questà porpietà esclude sia 
   * la $Blacklist$ che la lista $Tutti i contatti$.
   * @property +fiteredList          
   * @type {Array<Object>}                   
   *
  */

  filteredList:[],
 
  
  /**
   * Proprietà che contiene l'id dell'ultima lista creato o inserita.
   * @property -lastListId           
   * @type {Number}                   
   *
  */

  lastListId: null,
  
  /**
   * Questo metodo è deputato all'aggiornamento dell'ultimo id inserito di una lista, tale metodo infatti 
   * si occupa di osservare il model al fine di permettere il corretto inserimento di una nuova lista, 
   * senza cosi creare incoerenze o sen za rischiare di sovrascrivere una lista già esistente con quella nuova.
   * per far ciò l metodo aggiorna la proprietà lastListId, campo dati in cui viene mantenuto aggiornato 
   * l'Id dell'ultima lista.
   * Il metodo scorre l'intero gruppo di liste prendendo l'id di ognuna e ponenedolo in array di sopporto, 
   * questa operazione è resa necessaria al fine di poter odrinare l'array di supporto in ornide crescente 
   * per poi prelevare l'utlimo Id che siucuramente sarà l'ultimo inserito.
   * Fatto questo il metodo aggiorna il campo dati lastListId.
   
   *
   * @method -updateLastListId                                     
   * @return {Void} 
  */

  updateLastListId:function(){
    var list = this.get('content');
    var ids = [];
    list.forEach(function(l){
     ids.push( parseInt(l.get('id')) );
    });
    ids.sort(function(a,b){
      return a - b;
    }); 
    this.set('lastListId',ids.get('lastObject'));
  }.observes(this),
 
 /**
   * Questo metodo è deputato alla ricerca di una lista.
   * Il metodo riceve come argomento l'id della lista da cercare, se la ricarca da esito negativo il metodo
   * ritornerà $falso$, $vero$ altrimenti.
   *
   * @param {Number} numero dell'id della lista cercare             
   * @method -searchList                                     
   * @return {Boolean} 
  */
  
  searchList:function(idx){
    var test = this.get('content');
    var found=false;
    test.forEach(function(t){
      if(t.get('id')==idx){
        found=true;
      }
    });
    return found;
  },
  
  /**
   * Questo metodo è deputato all'aggiornamento della proprietà $filteredList$, tale proprietà è necessaria 
   * alla select di aggiunta utente ad una lista.
   * Il metodo si occupa di aggiornare la propietà ogni qual volta che una lista viene creata,eliminata o rinominata
   * inoltre, aggiorna la propietà alla prima volta che avviene il login.
   * Il metoddo riceve due argomenti $idx$ e $na$ questi due argomenti sono necessari quando
   * questo metodo viene richiamato dall'aggiunta di una nuova lista.
   *
   * @param {Number} numero dell'id della lista creata
   * @param {String} nome della lista creata
   * @method +updateSelect                                     
   * @return {Void} 
  */
  
  updateSelect:function(idx, na){
    var c=this.get("content");
    var item=new Array;
    c.forEach(function(i){
      if(i.get('id') >1){
        item.push(i);
      }
    });
    if(idx!=null && na!=null){
      if(!this.searchList(idx)){
        var obj=Ember.Object.create({id:idx,name:na});
        item.push(obj);
      }
    }
    this.set('filteredList',item);
  }.observes(this),

  /**
   * Questo metodo è deputato alla creazione di una nuova lista.
   * Il metodo si occupa di controllare che il nome della nuova lista che si intende creare sia valido 
   * (il nome non deve essere nullo o esistere già).
   * Nel caso il nome sia valido il metodo crea un'istanza del processore adeguato 
   * al quale sarà delegato il lavoro di creare la nuova lista.
   * Nel caso in cui il nome non sia valido il metodo 
   * mostra un messaggio di errore tramite un alert di JavaScript.
   * Il metodo infine deve richiare il metodo $updateSelect$ del controller al fine di aggiornare 
   * la select nella quale si selezionano le liste nelle quali aggiungere gli utenti.
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
        var newId = this.get('lastListId') + 1;
        this.set('lastListId',newId);
        var processorFactory = MyTalk.ProcessorFactory.create({});
        var processor = processorFactory.createProcessorProduct( "ListCreate" );
        processor.process({
          id: newId,
          name: newName
        });
        this.updateSelect(newId,newName);
      }
      else {
        alert("Esiste già una lista con questo nome");
      }
    }  
  },
  
});
