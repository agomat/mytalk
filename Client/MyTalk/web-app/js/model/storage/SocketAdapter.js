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

    var context = this;
    if (true) { // se il record è di autentificazione...
      this.socket.openUserConnection(function() {
        // onOpen callback
        context.socket.send( record );
        context.didCreateRecord(store, type, record, undefined);
      });
    } else {
      // tutti gli altri record
    }
    
  }
});


DS.SocketAdapter.map('MyTalk.User', {
  primaryKey: 'username',
  //username: DS.attr('string'),
});

DS.SocketAdapter.map('MyTalk.WUser', {
  list: {embedded: 'always'}
});

DS.SocketAdapter.map('MyTalk.WList', {
  list: {embedded: 'always'}
});

DS.SocketAdapter.map('MyTalk.List', {
  list: {embedded: 'always'}
});

DS.SocketAdapter.map('MyTalk.WCall', {
  list: {embedded: 'always'}
});