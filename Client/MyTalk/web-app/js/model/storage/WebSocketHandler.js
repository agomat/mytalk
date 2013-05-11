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
        list: [{ id: 0, username:'user_1',online:true,nome:'Nome1',cognome:'Cognome1',ip:'192.168.1.1' },
               { id: 1, username:'user_2',online:true,nome:'Nome2',cognome:'Cognome2',ip:'192.168.1.2' },
               { id: 2, username:'user_3',online:true,nome:'Nome3',cognome:'Cognome3',ip:'192.168.1.3' },
               { id: 3, username:'user_4',online:false,nome:'Nome4',cognome:'Cognome4',ip:'192.168.1.4' },
               { id: 4, username:'user_5',online:true,nome:'Nome5',cognome:'Cognome5',ip:'192.168.1.5' },
               { id: 5, username:'user_6',online:true,nome:'Nome6',cognome:'Cognome6',ip:'192.168.1.6' },
               { id: 6, username:'user_7',online:false,nome:'Nome7',cognome:'Cognome7',ip:'192.168.1.7' },
               { id: 7, username:'user_8',online:true,nome:'Nome8',cognome:'Cognome8',ip:'192.168.1.8' },
               { id: 8, username:'user_9',online:true,nome:'Nome9',cognome:'Cognome9',ip:'192.168.1.9' }]
      },
      type = MyTalk.WUser,
      adapter = store.adapterForType(type);

      obj = ' { "list" : {"id" : 1, "name" : "Nome", "user" : [1,2] }, "users": [{ "id":1, "username": "user1", "list": 1 },{ "id":2, "username":"user2", "list" : 1}] } ';

      obj = JSON.parse(obj);



      if(cc.count==0) {
       cc.count=4;
       console.log("ok");
       store.load( MyTalk.List, obj);
       //adapter.load(store, type, obj);
      }
      // liste

      
      obj = {
        id: 0,
        list: [ { id:0, name:'NomeLista1', list: [1,3] },
                { id:1, name:'NomeLista2', list: [5,4] },
              ]
      },
      type = MyTalk.WList,
      adapter = store.adapterForType(type);

      if(cc.count==0) {
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