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
  socket: undefined,
  bulkCommit: true,

  init: function() {    
    socket = this.getSocket();
    this._super();
  },
 
  find: function (store, type, id) {
    console.log('find ' + id);    
  },

  findQuery: function(store, type, query, recordArray) {
    console.log("findQuery"); 
  },

  findMany: function(store, type, ids, query) {
    console.log("findQuery");  
  },

  findAll: function (store, type) { 
    console.log("findAll");  
  },

  createRecord: function(store, type, record) {
    console.log("CreateRecord");
    this.get('socket').send( record );
    this.didCreateRecord(store, type, record, undefined);
  },

  createRecords: function(store, type, records) {
    console.log("createRecords");
  },

  updateRecord: function(store, type, record) {
    console.log("updateRecord");
  },

  updateRecords: function(store, type, records) {
    /*
    var re = sss[0];
re.forEach(function(dd){
console.log(dd.get("transaction").get('giu'));
});

e vedi: http://stackoverflow.com/questions/15132531/prevent-ember-data-bulk-commit-for-single-item/15133409#15133409
*/
    window.sss.pushObject(records);
    console.debug("updateRecords "+records+ " " + type);
  },

  deleteRecord: function(store, type, record) {
    console.log("deleteRecord");
  },

  deleteRecords: function(store, type, records) {
    console.log("deleteRecords");
  }

});



