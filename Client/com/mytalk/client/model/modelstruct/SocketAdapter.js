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
* Descrizione della _classe_
*/

DS.SocketAdapter = DS.RESTAdapter.extend(MyTalk.WebSocketConnection, {
  /**
  * bulkCommit consente di ... scrivere
  * 
  * @property -bulkCommit
  * @type {boolean}
  * @override \href{http://www.thomasboyt.com/ember-data-docs/}{ember-data}$\href{https://github.com/emberjs/data/blob/master/packages/ember-data/lib/adapters/rest_adapter.js#L79}{RESTAdapter}
  *
  */
  bulkCommit: true,

  /**
  * Descizione del costruttore init
  *
  * @method +init
  * @constructor
  * @override \href{http://www.thomasboyt.com/ember-data-docs/}{ember-data}$\href{https://github.com/emberjs/data/blob/master/packages/ember-data/lib/adapters/rest_adapter.js#L84}{API RESTAdapter}
  * @return {Object}
  */
  init: function() {
    this.createSocket();
    this._super();
  },
 
 /**
  * Descizione del metodo find
  *
  * @method -find
  * @param {DS.Store} aaaaaaaaaaaaaa
  * @param {string} ee
  * @param {number} 
  * @override \href{http://www.thomasboyt.com/ember-data-docs/}{ember-data}$\href{https://github.com/emberjs/data/blob/master/packages/ember-data/lib/adapters/rest_adapter.js#L252}{RESTAdapter}
  * @return {Void}
  */
  find: function (store, type, id) {
    console.debug('[Adapter] find ' + id);    
  },

 /**
  * Descizione del metodo findQuery
  *
  * @method -findQuery
  * @param {DS.Store} 
  * @param {string} 
  * @param {Object} 
  * @param {Array di Object} 
  * @override \href{http://www.thomasboyt.com/ember-data-docs/}{ember-data}$\href{https://github.com/emberjs/data/blob/master/packages/ember-data/lib/adapters/rest_adapter.js#L274}{RESTAdapter}
  * @return {Void}
  */
  findQuery: function(store, type, query, recordArray) {
    console.debug('[Adapter] findQuery'); 
  },

 /**
  * Descizione del metodo findMany
  *
  * @method -findMany
  * @param {DS.Store} desc1
  * @param {string} desc2
  * @param {Array di number} desc3
  * @param {Object} desc4
  * @override \href{http://www.thomasboyt.com/ember-data-docs/}{ember-data}$\href{https://github.com/emberjs/data/blob/master/packages/ember-data/lib/adapters/rest_adapter.js#L285}{RESTAdapter}
  * @return {Void}
  */
  findMany: function(store, type, ids, query) {
    console.debug('[Adapter] findQuery');  
  },

 /**
  * Descizione del metodo findAll
  *
  * @method -findAll
  * @param {DS.Store} 
  * @param {string} 
  * @override \href{http://www.thomasboyt.com/ember-data-docs/}{ember-data}$\href{https://github.com/emberjs/data/blob/master/packages/ember-data/lib/adapters/rest_adapter.js#L261}{RESTAdapter}
  * @return {Void}
  */
  findAll: function (store, type) { 
    console.debug('[Adapter] findAll' + type);  
  },

 /**
  * Descizione del metodo createRecord
  *
  * @method -createRecord
  * @param {DS.Store} 
  * @param {string} 
  * @param {Object} 
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
        // TODO fare in modo che appaia una scritta per es "Login in corso"
      } else {
        context.didError(store, type, record, '');      
        console.debug("The processor "+processorName+" was unable to sent the request to WebServer");
        // TODO fare in modo che appaia una scritta "Connessione con il server persa"
      }
    };

    processor.sendToServer(this.get('socket'), record, onSent);
    
  },

 /**
  * Descizione del metodo createRecords
  *
  * @method -createRecords
  * @param {DS.Store} 
  * @param {string} 
  * @param {Object} 
  * @override \href{http://www.thomasboyt.com/ember-data-docs/}{ember-data}$\href{https://github.com/emberjs/data/blob/master/packages/ember-data/lib/adapters/rest_adapter.js#L142}{RESTAdapter}
  * @return {Void}
  */
  createRecords: function(store, type, records) {
    console.debug('[Adapter] CreateRecords');
    this.createRecord(store, type, records.list[0]); // single bulk commit
  },

 /**
  * Descizione del metodo updateRecord
  *
  * @method -updateRecord
  * @param {DS.Store} 
  * @param {string} 
  * @param {Object} 
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
        // TODO fare in modo che appaia una scritta per es "Login in corso"
      } else {
        context.didError(store, type, record, '');      
        console.log("[Processor] The processor "+processorName+" was unable to sent the request to WebServer");
        // TODO fare in modo che appaia una scritta "Connessione con il server persa"
      }
    };

    processor.sendToServer(this.get('socket'), record, onSent);
  },

 /**
  * Descizione del metodo updateRecords
  *
  * @method -updateRecords
  * @param {DS.Store} 
  * @param {string} 
  * @param {Array di Object} 
  * @override \href{http://www.thomasboyt.com/ember-data-docs/}{ember-data}$\href{https://github.com/emberjs/data/blob/master/packages/ember-data/lib/adapters/rest_adapter.js#L185}{RESTAdapter}
  * @return {Void}
  */
  updateRecords: function(store, type, records) {
    console.debug('[Adapter] updateRecords');
    this.updateRecord(store, type, records.list[0]); // single bulk commit
  },

 /**
  * Descizione del metodo deleteRecord
  *
  * @method -deleteRecord
  * @param {DS.Store} 
  * @param {string} 
  * @param {Object} 
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
        // TODO fare in modo che appaia una scritta per es "Login in corso"
      } else {
        context.didError(store, type, record, '');      
        console.log("[Processor] The processor "+processorName+" was unable to sent the request to WebServer");
        // TODO fare in modo che appaia una scritta "Connessione con il server persa"
      }
    };

    processor.sendToServer(this.get('socket'), record, onSent);
  },

 /**
  * Descizione del metodo deleteRecords
  *
  * @method -deleteRecords
  * @return {tipo} desc
  * @param {DS.Store} 
  * @param {string} 
  * @param {Array di Object} 
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