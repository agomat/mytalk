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
*
* Questa classe è un \href{http://emberjs.com/api/classes/Ember.Mixin.html}{_Ember.Mixin_}. 
* Incapsula una connessione WebSocket per poter essere utilizzata, mediante composizione, delle altre classi
* È un Singleton
*/


MyTalk.WebSocketConnection = Ember.Mixin.create({
  /**
  * Contiene la URL della connessione WebSocket
  * 
  * @property -resource
  * @type {String}
  *
  */
  resource: 'ws://'+window.location.hostname+':8887',

  /**
  * Contiene la connessione WebSocket
  * 
  * @property -socket
  * @type {WebSocket}
  *
  */
  socket: undefined,

  /**
  * Metodo getInstance del pattern Singleton. Crea una nuova connessione WebSocket se l'attributo
  * _socket_ è nullo e lo salva in _socket_ rendendo l'atributo _socket_ 
  * disponibile a tutte le classi che vogliano usare questo Mixin. 
  * \\
  * Il metodo, oltre a creare la connessione, deve fornire la callback _onmessage_ all'oggetto
  * WebSocket appena creato. La callback riceve come parametro la stringa del messaggio ricevuto
  * dal server. Dal messaggio va estratta la parte $richiesta$ (_req_) di _ARI_ per passarla
  * ad un oggetto _ProcessorFactory_ creato immediatamente prima. L'oggetto ritornerà il processore
  * atto a gestire la richiesta. Andrà invocato su di esso il metodo _process_ passandogli il
  * messaggio ricevuto dal server
  *
  * @method +createSocket
  * @return {Void}
  */
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