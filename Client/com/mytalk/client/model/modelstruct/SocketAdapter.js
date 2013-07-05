/**
* Filename: SocketAdapter.js
* Package: com.mytalk.client.model.storage
* Dependencies:  com.mytalk.client.model.storage.WebSocketConnection
* Author: Agostinetto Mattia
* Date: 2013-04-26
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-05-01 | MA        | [#] Eseguita la composizione della classe con il Mixin MyTalk.WebSocketConnection e modificato il costruttore rendendo disponibile alla classe il socket del Mixin
* 0.1     | 2013-04-26 | MA        | [+] Creazione classe e codifica parziale del metodo createRecord
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*
* Questa classe ridefinisce il {\color{blue}\href{http://goo.gl/izGuA}{RESTAdapter}} di _ember-data_ in modo che possa interfacciarsi correttamente
* attraverso una connessione WebSocket, per eseguire operazioni CRUD. _RESTAdapter_ permette di comunicare
* con un server HTTP per trasmettere i dati via JSON. I metodi della classe che si andranno a 
* ridefinire saranno invocati automaticamente da Ember.js ogni qual volta avvenga una modifica
* ad un Model che estende _DS.Model_. Le modifiche che Ember.js osserva possono essere catalogate con le
* classiche operazioni CRUD $Create$, $Read$, $Update$, $Delete$. In particolare ci interessa essere
* notificati per gli aggiornamenti dei seguenti cambiamenti a cui un Model è soggetto: $Creazione di un Record$, $Aggiornamento di un Record$
* $Cancellazione di un Record$. _RESTAdapter_ supporta anche operazioni di $Read$ per rispondere
* a query complesse che il client potrebbe avere bisogno. A questo proposito, dovendo minimizzare
* le interazioni con il server in caso di lettura dei dati dal Model, queste funzionalità non
* verranno implementate dal programmatore. Tali metodi tuttavia dovranno essere ridefiniti ed avranno corpo vuoto.
* \\
* La classe _SocketAdapter_ deve poter accedere alla classe _WebSocketConnection_ che detiene la
* connessione attiva al socket. Questa classe deve essere accessibile mediante composizione dalla classe
* _SocketAdapter_. I metodi che dovranno essere ridefiniti sono $createRecord$, $updateRecord$, e $deleteRecord$
*/

DS.SocketAdapter = DS.RESTAdapter.extend(MyTalk.WebSocketConnection, {
  /**
  * Questa proprietà se impostata a $true$ permette di generare tante richieste
  * da inviare al server in caso di modifica di più record alla volta all'interno di una data
  * transazione. La documentazione su questa proprietà è inesistente. Si rimanda secondo quanto
  * descritto a questo \href{http://goo.gl/TWZXK}{test di unità}.
  * 
  * @property -bulkCommit
  * @type {boolean}
  * @override \href{http://www.thomasboyt.com/ember-data-docs/}{ember-data}$\href{https://github.com/emberjs/data/blob/master/packages/ember-data/lib/adapters/rest_adapter.js#L79}{RESTAdapter}
  *
  */
  bulkCommit: true,

  /**
  * Il costruttore deve invocare il metodo _getInstance_ del Mixin WebSocketConnection in modo da
  * ottenere nello scope della classe il riferimento alla connessione WebSocket corrente. Successivamente
  * deve invocare il costruttore della classe da cui deriva 
  *
  * @method +init
  * @constructor
  * @override \href{http://www.thomasboyt.com/ember-data-docs/}{ember-data}$\href{https://github.com/emberjs/data/blob/master/packages/ember-data/lib/adapters/rest_adapter.js#L84}{RESTAdapter}
  * @return {Object}
  */
  init: function() {
    this.getInstance();
    this._super();
  },
 
 /**
  * Questo metodo viene invocato ogni volta che si utilizza il metodo \href{http://emberjs.com/guides/models/finding-models/}{_find_} su un Model che deriva
  * da _DS.Model_. Questo metodo deve avere corpo vuoto
  *
  * @method -find
  * @param {DS.Store} riferimento allo Store di _ember-data_ usato dall'applicazione corrente
  * @param {string} il nome del Model su cui Ember.js ha rilevato una operazione CRUD
  * @param {number} l'identificativo univoco del record interessato dalla operazione CRUD
  * @override \href{http://www.thomasboyt.com/ember-data-docs/}{ember-data}$\href{https://github.com/emberjs/data/blob/master/packages/ember-data/lib/adapters/rest_adapter.js#L252}{RESTAdapter}
  * @return {Void}
  */
  find: function (store, type, id) {
    console.debug('[Adapter] find ' + id);    
  },

 /**
  * Questo metodo viene invocato ogni volta che si utilizza il metodo \href{http://emberjs.com/guides/models/finding-models/}{_findQuery_} su un Model che deriva
  * da _DS.Model_. Questo metodo deve avere corpo vuoto
  *
  * @method -findQuery
  * @param {DS.Store} riferimento allo Store di _ember-data_ usato dall'applicazione corrente
  * @param {string} il nome del Model su cui Ember.js ha rilevato una operazione CRUD
  * @param {Object} oggetto JSON contenente i parametri della query da soddisfare su tale Model
  * @param {Array di Object} array di record che soddisfano al risultato della query
  * @override \href{http://www.thomasboyt.com/ember-data-docs/}{ember-data}$\href{https://github.com/emberjs/data/blob/master/packages/ember-data/lib/adapters/rest_adapter.js#L274}{RESTAdapter}
  * @return {Void}
  */
  findQuery: function(store, type, query, recordArray) {
    console.debug('[Adapter] findQuery'); 
  },

 /**
  * Questo metodo viene invocato ogni volta che si utilizza il metodo \href{http://emberjs.com/guides/models/finding-models/}{_findMany_} su un Model che deriva
  * da _DS.Model_. Questo metodo deve avere corpo vuoto 
  *
  * @method -findMany
  * @param {DS.Store} riferimento allo Store di _ember-data_ usato dall'applicazione corrente
  * @param {string} il nome del Model su cui Ember.js ha rilevato una operazione CRUD
  * @param {Array di number} array di id dei record interessati dalla operazione CRUD
  * @param {Object} oggetto JSON contenente i parametri della query da soddisfare su tale Model
  * @override \href{http://www.thomasboyt.com/ember-data-docs/}{ember-data}$\href{https://github.com/emberjs/data/blob/master/packages/ember-data/lib/adapters/rest_adapter.js#L285}{RESTAdapter}
  * @return {Void}
  */
  findMany: function(store, type, ids, query) {
    console.debug('[Adapter] findQuery');  
  },

 /**
  * Questo metodo viene invocato ogni volta che si utilizza il metodo \href{http://emberjs.com/guides/models/finding-models/}{_findAll_} su un Model che deriva
  * da _DS.Model_. Questo metodo deve avere corpo vuoto
  *
  * @method -findAll
  * @param {DS.Store} riferimento allo Store di _ember-data_ usato dall'applicazione corrente
  * @param {string} il nome del Model su cui Ember.js ha rilevato una operazione CRUD
  * @override \href{http://www.thomasboyt.com/ember-data-docs/}{ember-data}$\href{https://github.com/emberjs/data/blob/master/packages/ember-data/lib/adapters/rest_adapter.js#L261}{RESTAdapter}
  * @return {Void}
  */
  findAll: function (store, type) { 
    console.debug('[Adapter] findAll' + type);  
  },

 /**
  * Questo metodo viene invocato alla creazione di un nuovo record di un Model.
  * \\
  * Deve ottenere il processore interrogando la transazione del parametro _record_. Il metodo
  * poi deve definire una callback da passare al processore come parametro del suo metodo _sendToServer_ assieme al record che è stato creato.
  * La callback deve invocare $didCreateRecord$ o $didError$ (sull'oggetto $this$ corrente) a seconda che il processore sia
  * riuscito a inviare la modifica al server
  *
  * @method -createRecord
  * @param {DS.Store} riferimento allo Store di _ember-data_ usato dall'applicazione corrente
  * @param {string} il nome del Model su cui Ember.js ha rilevato una operazione CRUD
  * @param {Object} riferimento del record appena creato
  * @override \href{http://www.thomasboyt.com/ember-data-docs/}{ember-data}$\href{https://github.com/emberjs/data/blob/master/packages/ember-data/lib/adapters/rest_adapter.js#L125}{RESTAdapter}
  * @return {Void}
  */
  createRecord: function(store, type, record) {
    console.debug('[Adapter] CreateRecord');
    var context = this;
    var processor = record.get('transaction').get('processor');

    var onSent = function(processorName, success){
      if (success) {
        context.didCreateRecord(store, type, record);
        console.debug("The processor "+processorName+" has successfully sent the request to WebServer");
      } else {
        context.didError(store, type, record, '');      
        console.debug("The processor "+processorName+" was unable to sent the request to WebServer");
      }
    };

    processor.sendToServer(this.get('socket'), record, onSent);
    
  },

 /**
  * Questo metodo viene invocato quando, nella stessa transazione, vengono costruiti più record. 
  * Il parametro $bulkCommit$ descritto in precedenza non evita la chiamata di questo metodo a causa
  * di un bug di _ember-data_. Il metodo dovrà contenere una singola istruzione: \\ _this.createRecord(store, type, records.list[0])_
  *
  * @method -createRecords
  * @param {DS.Store} riferimento allo Store di _ember-data_ usato dall'applicazione corrente
  * @param {string} il nome del Model su cui Ember.js ha rilevato una operazione CRUD
  * @param {Array di Object} array di riferimenti dei record appena creati
  * @override \href{http://www.thomasboyt.com/ember-data-docs/}{ember-data}$\href{https://github.com/emberjs/data/blob/master/packages/ember-data/lib/adapters/rest_adapter.js#L142}{RESTAdapter}
  * @return {Void}
  */
  createRecords: function(store, type, records) {
    console.debug('[Adapter] CreateRecords');
    this.createRecord(store, type, records.list[0]); // single bulk commit
  },

 /**
  * Questo metodo viene invocato ogni qual volta un record venga modificato.
  * \\
  * Deve ottenere il processore interrogando la transazione del parametro _record_. Il metodo
  * poi deve definire una callback da passare al processore come parametro del suo metodo _sendToServer_ assieme al record che è stato modificato.
  * La callback deve invocare $didUpdateRecord$ o $didError$ (sull'oggetto $this$ corrente) a seconda che il processore sia
  * riuscito a inviare la modifica al server
  *
  * @method -updateRecord
  * @param {DS.Store} riferimento allo Store di _ember-data_ usato dall'applicazione corrente
  * @param {string} il nome del Model su cui Ember.js ha rilevato una operazione CRUD
  * @param {Object} riferimento al record appena modificato
  * @override \href{http://www.thomasboyt.com/ember-data-docs/}{ember-data}$\href{https://github.com/emberjs/data/blob/master/packages/ember-data/lib/adapters/rest_adapter.js#L165}{RESTAdapter}
  * @return {Void}
  */
  updateRecord: function(store, type, record) {
    console.debug('[Adapter] updateRecord');
    var context = this;
    var processor = record.get('transaction').get('processor');
    var onSent = function(processorName, success){
      if (success) {
        context.didUpdateRecord(store, type, record);
        console.log("[Processor] The processor "+processorName+" has successfully sent the request to WebServer");
      } else {
        context.didError(store, type, record, '');      
        console.log("[Processor] The processor "+processorName+" was unable to sent the request to WebServer");
      }
    };

    processor.sendToServer(this.get('socket'), record, onSent);
  },

 /**
  * Questo metodo viene invocato quando, nella stessa transazione, vengono aggiornati più record. 
  * Il parametro $bulkCommit$ descritto in precedenza non evita la chiamata di questo metodo a causa
  * di un bug di _ember-data_. Il metodo dovrà contenere una singola istruzione: \\ _this.updateRecord(store, type, records.list[0]);_
  *
  * @method -updateRecords
  * @param {DS.Store} riferimento allo Store di _ember-data_ usato dall'applicazione corrente
  * @param {string} il nome del Model su cui Ember.js ha rilevato una operazione CRUD
  * @param {Array di Object} array di riferimenti dei record appena creati
  * @override \href{http://www.thomasboyt.com/ember-data-docs/}{ember-data}$\href{https://github.com/emberjs/data/blob/master/packages/ember-data/lib/adapters/rest_adapter.js#L185}{RESTAdapter}
  * @return {Void}
  */
  updateRecords: function(store, type, records) {
    console.debug('[Adapter] updateRecords');
    this.updateRecord(store, type, records.list[0]); // single bulk commit
  },

 /**
  * Questo metodo viene invocato ogni qual volta un record venga cancellato.
  * \\
  * Deve ottenere il processore interrogando la transazione del parametro _record_. Il metodo
  * poi deve definire una callback da passare al processore come parametro del suo metodo _sendToServer_ assieme al record che è stato cancellato.
  * La callback deve invocare $didDeleteRecord$ o $didError$ (sull'oggetto $this$ corrente) a seconda che il processore sia
  * riuscito a inviare la modifica al server
  *
  * @method -deleteRecord
  * @param {DS.Store} riferimento allo Store di _ember-data_ usato dall'applicazione corrente
  * @param {string} il nome del Model su cui Ember.js ha rilevato una operazione CRUD
  * @param {Object} riferimento al record appena cancellato
  * @override \href{http://www.thomasboyt.com/ember-data-docs/}{ember-data}$\href{https://github.com/emberjs/data/blob/master/packages/ember-data/lib/adapters/rest_adapter.js#L211}{RESTAdapter}
  * @return {Void}
  */
  deleteRecord: function(store, type, record) {
    console.debug('[Adapter] deleteRecord');
    var context = this;
    var processor = record.get('transaction').get('processor');
    
    var onSent = function(processorName, success){
      if (success) {
        context.didDeleteRecord(store,type,record);
        console.log("[Processor] The processor "+processorName+" has successfully sent the request to WebServer");
      } else {
        context.didError(store, type, record, '');      
        console.log("[Processor] The processor "+processorName+" was unable to sent the request to WebServer");
      }
    };

    processor.sendToServer(this.get('socket'), record, onSent);
  },

 /**
  * Questo metodo viene invocato quando, nella stessa transazione, vengono cancellati più record. 
  * Il parametro $bulkCommit$ descritto in precedenza non evita la chiamata di questo metodo a causa
  * di un bug di _ember-data_. Il metodo dovrà contenere una singola istruzione: \\ _this.deleteRecord(store, type, records.list[0]);_
  *
  * @method -deleteRecords
  * @param {DS.Store} riferimento allo Store di _ember-data_ usato dall'applicazione corrente
  * @param {string} il nome del Model su cui Ember.js ha rilevato una operazione CRUD
  * @param {Array di Object} array di riferimenti dei record appena cancellati
  * @override \href{http://www.thomasboyt.com/ember-data-docs/}{ember-data}$\href{https://github.com/emberjs/data/blob/master/packages/ember-data/lib/adapters/rest_adapter.js#L226}{RESTAdapter}
  * @return {Void}
  */
  deleteRecords: function(store, type, records) {
    console.debug('[Adapter] deleteRecords');
    this.deleteRecord(store, type, records.list[0]); // single bulk commit
  }

});

/*
 * Mapping configuration for sideload
 */

DS.SocketAdapter.configure('MyTalk.PersonalData', {
  sideloadAs: 'pd'
});

DS.SocketAdapter.configure('MyTalk.List', {
  sideloadAs: 'userList'
});

DS.SocketAdapter.configure('MyTalk.User', {
  sideloadAs: 'list'
});

DS.SocketAdapter.map('MyTalk.WCall', {
  list: {embedded: 'always'}
});
