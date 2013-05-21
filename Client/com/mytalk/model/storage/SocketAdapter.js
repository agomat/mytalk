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
  socket: undefined, // TODO spostare minin WebSocketConnection in RequestManagery
  bulkCommit: true,

  init: function() {    
    socket = this.getSocket();
    this._super();
  },
 
  find: function (store, type, id) {
    console.log('find ' + id);    
  },

  findQuery: function(store, type, query, recordArray) {
    console.log('findQuery'); 
  },

  findMany: function(store, type, ids, query) {
    console.log('findQuery');  
  },

  findAll: function (store, type) { 
    console.log('findAll');  
  },

  createRecord: function(store, type, record) {
    console.log('CreateRecord');
    var context = this;
    var processor = record.get('transaction').get('request');
    
    var onSuccess = function(json){
      context.didCreateRecord(store, type, record, json);
    }; // TODO fare in modo che appaia una scritta "Login in corso"
    
    var onError = function(){
      context.didError(store, type, record);
    }; // TODO fare in modo che appaia una scritta "Connessione con il server persa"

    if(processor == "Login") {
      var req = MyTalk.processor.Login.create({});
      req.processRequest(record, this.get('socket'), onSuccess, onError);
    }
    
  },

  createRecords: function(store, type, records) {
    console.log('CreateRecords');
    return this.createRecord(store, type, records.list[0]); // single bulk commit
  },

  updateRecord: function(store, type, record) {
    console.log('updateRecord '+ record);
    if (! window.sss ) window.sss = []; window.sss.pushObject(record);
    record.get('transaction').get('giu');
    //var re = sss[0];
    //re.forEach(function(dd){
    //  console.log(dd.get('transaction').get('giu'));
  },

  updateRecords: function(store, type, records) {
    console.log('updateRecords '+ record);
    return this.updateRecord(store, type, records.list[0]); // single bulk commit
  },

  deleteRecord: function(store, type, record) {
    console.log('deleteRecord');
  },

  deleteRecords: function(store, type, records) {
    console.log('deleteRecords');
  }

});



