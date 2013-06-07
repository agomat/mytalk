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
*/

DS.SocketAdapter = DS.RESTAdapter.extend(MyTalk.WebSocketConnection, { 
  bulkCommit: true,

  init: function() {    
    this.createSocket();
    this._super();
  },
 
  find: function (store, type, id) {
    console.debug('[Adapter] find ' + id);    
  },

  findQuery: function(store, type, query, recordArray) {
    console.debug('[Adapter] findQuery'); 
  },

  findMany: function(store, type, ids, query) {
    console.debug('[Adapter] findQuery');  
  },

  findAll: function (store, type) { 
    console.debug('[Adapter] findAll');  
  },

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

  createRecords: function(store, type, records) {
    console.debug('[Adapter] CreateRecords');
    return this.createRecord(store, type, records.list[0]); // single bulk commit
  },

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

  updateRecords: function(store, type, records) {
    console.debug('[Adapter] updateRecords');
    return this.updateRecord(store, type, records.list[0]); // single bulk commit
  },

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

  deleteRecords: function(store, type, records) {
    console.debug('[Adapter] deleteRecords');
    return this.deleteRecord(store, type, records.list[0]); // single bulk commit
  }

});

/*
 * Mapping configuration for sideload and foreign keys
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