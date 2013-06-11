/**
* Filename: UsersController.js
* Package: com.mytalk.client.controller.controller
* Dependencies: com.mytalk.client.model.storage.processing.ProcessorFactory
*               com.mytalk.client.controller.controller.LoggedController
*               com.mytalk.client.controller.controller.ListController
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
*  Controller deputato alla gestione degli utenti presenti in una lista e alla gestione del template $users$
*
*/

MyTalk.UsersController = Ember.ArrayController.extend({
  sortProperties:['name'],
  needs: ['logged','list'], 
 
  /**
   * Proprietà necessaria per memorizzare l'id della lista selezionata nella quale aggiungere l'utente 
   * @property +selectedValue           
   * @type {Number}                   
   *
  */ 
  
  selectedValue: null,
  
  /**
   * Proprietà necessaria per la memorizzazione dell'id dell'utente da aggiungere in una lista
   * @property -userId           
   * @type {Number}                   
   *
  */ 
  
  userId: null,
  
  /**
   * Proprietà necessaria per la memorizzazione del nome dell'utente da aggiungere in una lista
   * @property -userName           
   * @type {String}                   
   *
  */ 

  userName: null,

  /**
   * Questo metodo è deputato al filtraggio effettivo degli utenti in base alla ricerca.
   * Il metodo preleva, dal content del controller,  tutti gli utenti la cui proprietà $unmatched$ è settata a $false$ mettendo tali utenti un array
   * chiamato fileteredUsers, tale array viene ritornato alla vista che mostrandolo, espone il risultato della ricerca
   *
   * @method +filteredUsers                                     
   * @return {Array<User>} 
  */

  filteredUsers : function(){
    var filteredUsers = [];
    var items = this.get('content');
    items.forEach(function(u){
      if(u.get('unmatched') == false)
        filteredUsers.push(u);
    });
    return filteredUsers;
  }.property('content.@each.unmatched').cacheable(),
  
  /**
   * Questo metodo è deputato alle gestione inziale dell'aggiunta di un utente ad una nuova lista,
   * inoltre si occupa di mostrare la select contente l'elenco delle liste nelle quali poter aggiungere l'utente selezionato.
   * Il metodo si occupa di settare i campi $userId$ e $userName$ rispettivamente con l'id e il nome dell'utente preso in considerazione
   *
   * @method +addUsers 
   * @param {User} user è l'oggetto utente che si vuole aggiungere alla lista.
   * @return {Void} 
  */

  addUser: function (user){
    document.getElementById('adduser').style.display='block';
    var n=user.get('name')+" "+user.get('surname');
    this.set('userId',user.get('id'));
    this.set('userName',n);
  },

  /**
   * Questo metodo si occupa di transitare nel template $calling$ dove inzierà il processo di chiamata all'utente selezionato.
   *
   * @method +userCall 
   * @param {User} user è l'oggetto utente che si vuole contattare.
   * @return {Void} 
  */

  userCall: function(user){
    this.transitionToRoute('calling', user);
  },

  /**
   * Questo metodo si occupa di bloccare il tentativo di contattare un utente offline mostrando un messaggio di errore tramite alert di javascript
   *
   * @method +userCall 
   * @param {User} user è l'oggetto utente che si vuole contattare.
   * @return {Void} 
  */

  cantCall: function(user){
    alert('Non puoi chiamare '+ user.get('fullName') +' poiché è offline');
  },

  /**
   * Questo metodo si occupa di chiudere il popup che gestisce l'aggiunta dell'utente ad una lista settando in campi $userId$ e $userName$ e $selectedValue$ a $NULL$
   *
   * @method +closeSelect 
   * @return {Void} 
  */

  closeSelect:function(){
    document.getElementById('adduser').style.display='none';
    this.set('selectedValue',null);
    this.set('userId',null);
    this.set('userName',null);
  },

  /**
   * Questo metodo si occupa di eseguire l'aggiunta vera e propria dell'utente all lista selezionata controllando che quest'ultima sia una lista valida (non deve essere: ne vuota ne Blacklist ne Tutti i contatti ne la lista corrente) 
   * Una volta controllato che la lista nella quale si vuole aggiungere l'utente sia valida il meto crea un'istanza del processore delegato dell'operazione di aggiungere l'untente alla lista. 
   * Terminata l'aggiunta si occupa di settare a NULL i campi $userId$, $userName$ e $selectedValue$.
   * Nel caso in cui la lista selezionata non sia valida viene mostrato un messaggio di errore tramite alert di javascript
   *
   * @method +doAddUser 
   * @return {Void} 
  */

  doAddUser:function (){
    var currentListId=this.get('controllers.list.content.id');
    if(this.selectedValue!=null && this.selectedValue!=currentListId && this.selectedValue>1){
   
      if(currentListId!=1){
        var list=MyTalk.List.find(this.selectedValue);
        var processorFactory = MyTalk.ProcessorFactory.create({});
        var processor = processorFactory.createProcessorProduct("ListUserAdd");
        processor.process({
          userId: this.userId,
          list: list 
        });

        document.getElementById('adduser').style.display='none';
        this.set('selectedValue',null);
        this.set('userId',null);
        this.set('userName',null);
      }
      
      
    }
    else{
      alert('Selezionare una lista corretta');
    }
  },

  /**
   * Questo metodo si occupa di eliminare dalla lista corrente l'utente selezionato.
   * Prima di procedere alla creazione dell'istanza del processore delegato a tale operaziuone il metodo chiede conferma all'utente tramite il confirm di javascript, 
   * in caso affermativo l'operazione viene eseguita
   *
   * @method +deleteUser 
   * @param {User} user è l'oggetto utente che si vuole eliminare.
   * @return {Void} 
  */

  deleteUser:function (user){
    var userId = user.get('id');
    var confirmation = confirm("Sei sicuro di eliminare l'utente " + user.get('fullName') +" dalla lista?");
    if(confirmation){
      var listId = this.get('controllers.list.content.id');
      if(listId==1){
        var currentList = MyTalk.List.find(listId).get('users');
        currentList.removeObject( user );
      }
      else{
        var list = MyTalk.List.find(listId);
        var processorFactory = MyTalk.ProcessorFactory.create({});
        var processor = processorFactory.createProcessorProduct("ListUserRemove");
        processor.process({
          userId: userId,
          list: list 
        });
      }
    }
  },

  /**
   * Questo metodo si occupa di mettere l'utente selezionato nella Blacklist, il metodo inoltre rimuove 
   * l'utente dalle altre liste tranne che nella lista  $Tutti i contatti$.
   * Prima di procedere alla creazione dei processori delegati di tale lavoro il metodo chiede conferma all'utente tramite 
   * un confirm di javascript
   *
   * @method +userCall 
   * @param {User} user è l'oggetto utente che si vuole contattare.
   * @return {Void} 
  */

  userToBlacklist: function (user){
    var confirmation = confirm("Sei sicuro di mettere l'utente " + user.get('fullName') +" nella Blacklist?");
    var userId = user.get('id');
    if(confirmation){
      var lists = MyTalk.List.find().toArray();
      lists = lists.rejectProperty('id',0);
      lists.forEach(function(list) {
          var processorFactory = MyTalk.ProcessorFactory.create({});
          var processor = processorFactory.createProcessorProduct("ListUserRemove");
          processor.process({
            userId: userId,
            list: list 
          });
      });
      var processorFactory = MyTalk.ProcessorFactory.create({});
      var processor = processorFactory.createProcessorProduct( "BlackListAdd" );
      processor.process({
        userId: userId,
      });
    } 
  }
});
