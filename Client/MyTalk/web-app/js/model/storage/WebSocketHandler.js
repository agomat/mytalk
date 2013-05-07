/**
* Filename: WebsocketHandler.js
* Package: com.mytalk.client [...]
* Author: Mattia Agostinetto
* Editor: Sublime Text 2
* Date: 2013-04-26
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-05-01 | MA        | [#] Eseguita la composizione della classe con il Mixin MyTalk.IPAddressProxy e modificato il costruttore rendendo disponibile alla classe l'indirizzo IP del Mixin
* 0.1     | 2013-04-26 | MA        | [+] Creazione classe e codifica metodi init, newConnection, send, closeConnection
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

MyTalk.WebSocketHandler = Ember.Object.extend(MyTalk.IPAddressProxy, MyTalk.RequestManager, {
  uri: 'ws://'+window.location.host+'/MyTalk/atmosphere/chat/',
  param: [{name:'X-Atmosphere-Transport',value:'websocket'}],
  ws: {},
  count: 0,

  init: function() {  // TODO PASSARE COME PARAMETRI LA CALLBACK onmessage
    
  	this.param.pushObject({name:'ip',value:this.get('IP')});
    this.ws.all = new WebSocket(this.uri + 'all' + '?' + $.param(this.param));                                                                 
    
    // callback
    this.ws.all.onopen = function() {
      console.log('Connection established /all');
    };
    this.ws.all.onclone = function() {
      console.log('Connection closed /' + 'all');
    };
    var cc = this;
    this.ws.all.onmessage = function(data) {
      console.log('WS: New message arrived from server');        // <---------------------------
      
      var store = DS.get('defaultStore'),
      obj = {
        id: 0,
        list: [{ id: 0, username:'user_1',online:true,nome:'Nome',cognome:'Cognome',ip:'192.168.1.1', user:0 },
               { id: 1, username:'user_2',online:true,nome:'Nome',cognome:'Cognome',ip:'192.168.1.1', user:0 }]
      },
      type = MyTalk.WUser,
      adapter = store.adapterForType(type);

      if(cc.count==0){
       adapter.load(store, type, obj);
      }
      // liste

      
      obj = {
        id: 0,
        list: [{ id:0, name:'NOME',list: [{ username:'user_2',online:false,nome:'Nome',cognome:'Cognome',ip:'192.168.1.1', user:0 }] },
               ]
      },
      type = MyTalk.WList,
      adapter = store.adapterForType(type);

      if(cc.count==9) {
       cc.count=4;
       adapter.load(store, type, obj);
      }
    };
    this._super();
  },

  openUserConnection: function(onOpen) {
    var resource = this.get('username');
  	this.ws.user = new WebSocket(this.uri + resource + '?' + $.param(this.param));
    // callback
    this.ws.user.onopen = onOpen;
    this.ws.user.onclose = function() {
      console.log('Connection closed /' + resource);
    };
    this.ws.user.onmessage = function(data) {
      alert('New message /' + resource + ': ' + data);  // <---------------------------
    };

  },

  closeConnection: function(resource) { 
  	this.ws[resource].close();
  	this.ws[resource] = undefined;
  },

  send: function(data) { 
    ///////// l'ARI lo costruisce il RequestManager /////////
    var a = MyTalk.Authentication.find(0).get('username');
    var r = 'some';
    var i = data;
    var ari = new MyTalk.ARI( a, r, i );
    /////////////////////////////////////////////////////////
    this.ws.user.send( JSON.stringify( ari ) );
  }

});