/**
* Filename: SocketAdapter.js
* Package: com.mytalk.client [...]
* Author: Mattia Agostinetto
* Editor: Sublime Text 2
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
    console.debug('find ' + id);    
  },

  findQuery: function(store, type, query, recordArray) {
    console.debug('findQuery'); 
  },

  findMany: function(store, type, ids, query) {
    console.debug('findQuery');  
  },

  findAll: function (store, type) { 
    console.debug('findAll');  
  },

  createRecord: function(store, type, record) {
    console.debug('CreateRecord');
    var context = this;
    var processor = record.get('transaction').get('processor');

    var onSent = function(processorName, success){
      if (success) {
        console.debug("The processor "+processorName+" has successfully sent the request to WebServer");
        // TODO fare in modo che appaia una scritta per es "Login in corso"
      } else {        
        console.debug("The processor "+processorName+" was unable to sent the request to WebServer");
        // TODO fare in modo che appaia una scritta "Connessione con il server persa"
      }
    };

    var onComplete = function(processorName, success){
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

    processor.sendToServer(this.get('socket'), record, onSent, onComplete);
    
  },

  createRecords: function(store, type, records) {
    console.debug('CreateRecords');
    return this.createRecord(store, type, records.list[0]); // single bulk commit
  },

  updateRecord: function(store, type, record) {
    console.debug('updateRecord '+ record);
    if (! window.sss ) window.sss = []; window.sss.pushObject(record);
    record.get('transaction').get('giu');
    //var re = sss[0];
    //re.forEach(function(dd){
    //  console.debug(dd.get('transaction').get('giu'));
  },

  updateRecords: function(store, type, records) {
    console.debug('updateRecords '+ record);
    return this.updateRecord(store, type, records.list[0]); // single bulk commit
  },

  deleteRecord: function(store, type, record) {
    console.debug('deleteRecord');
  },

  deleteRecords: function(store, type, records) {
    console.debug('deleteRecords');
  }

});


DS.SocketAdapter.configure('MyTalk.PersonalData', {
    sideloadAs: 'pd'
});


