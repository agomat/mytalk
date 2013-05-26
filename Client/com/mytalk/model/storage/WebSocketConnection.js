/**
* Filename: WebSocketConnection.js
* Package: com.mytalk.client [...]
* Author: Mattia Agostinetto
* Editor: Sublime Text 2
* Date: 2013-04-26
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1   | 2013-05-01 | MA        | [+] Creazione Mixin WebSocketConnection
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


MyTalk.WebSocketConnection = Ember.Mixin.create({
  resource: 'ws://'+window.location.hostname+':8887',
  socket: undefined,

  createSocket: function() {
    // Lazy creation
    if(!this.get('socket')) {
      var ws = new WebSocket(this.get('resource'));
      ws.onopen = function() {
        console.log('[WS] Connection established');
      };
      ws.onclone = function() {
        console.log('[WS] Connection closed');
      };
      ws.onmessage = function(msg) {
        var ari = JSON.parse( msg.data );
        var ProcessorFactory = MyTalk.ProcessorFactory.create({});
        var processor = ProcessorFactory.createProcessorProduct( ari.req );
        processor.process( ari ); // passagli l'originalRequest dell'history
      }
      this.set('socket', ws);
    }
  },

});