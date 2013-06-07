/**
* Filename: WebSocketConnection.js
* Package: com.mytalk.client.model.storage
* Dependencies: com.mytalk.client.model.storage.processing.ProcessorFactory
* Author: Agostinetto Mattia
* Date: 2013-04-26
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-04-26 | MA        | [+] Creazione Mixin WebSocketConnection
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
      Ember.run.later(this, function(){
        var ws = new WebSocket(this.get('resource'));
        ws.onopen = function() {
          console.log('[WS] Connection established');
        };
        ws.onclose = function() {
          console.log('[WS] Connection closed');
          alert('Unable to reach the WebServer! Maybe it is down.');
        };
        ws.onmessage = function(msg) {
          var ari = JSON.parse( msg.data );
          var processorFactory = MyTalk.ProcessorFactory.create({});
          var processor = processorFactory.createProcessorProduct( ari.req );
          processor.process( ari );
        }
        this.set('socket', ws);
      }, 300);
    }
  },

});