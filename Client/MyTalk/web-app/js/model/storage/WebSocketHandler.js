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
      this.send("aa");
      console.log('Connection established /all');
    };
    this.ws.all.onclone = function() {
      console.log('Connection closed /' + 'all');
    };
    var cc = this;
    this.ws.all.onmessage = function(data) {
      console.log('WS: New message arrived from server');        // <---------------------------
     

      Ember.run.later(this, function(){
      // popolamento per campese

      var json =      {
                    "lists": [
                                     { "id": 1, "name" : "Amici", "user_ids" : [1,14,20,25,16,22] },
                                     { "id": 2, "name" : "Familiari", "user_ids" : [7,19,9,10] },
                                     { "id": 3, "name" : "Blacklist", "user_ids" : [11,12] },
                                     { "id": 4, "name" : "Tutti i contatti", "user_ids" : [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30] },
                                     { "id": 5, "name" : "Università", "user_ids" : [13,14,15] },
                                     { "id": 6, "name" : "Lavoro", "user_ids" : [16,17,18,19,20,21,22,23,24] },
                                     { "id": 7, "name" : "Forum Adesivi", "user_ids" : [25,26,27,28,29,30] },
                             ],
                    "users": [
                                     { "id": 1, "username": "mattia", "online":false, "name": "Mattia", "surname": "Agostinetto", "ip": "192.168.1.2", "md5": "699b1b3f9c23e21f13b2ac9267942b01" },
                                     { "id": 2, "username": "marco", "online":true, "name": "Abbondanzio", "surname": "Cognome", "ip": "192.168.1.3", "md5": "6d8ebb117e8d83d74ea95fbdd0f87e13" },
                                     { "id": 3, "username": "luigi", "online":true, "name": "Amelio", "surname": "Cognome", "ip": "192.168.1.4", "md5": "4235db1aa1b11bcddb720dbc70b34a0f" },
                                     { "id": 4, "username": "elena", "online":false, "name": "Achille", "surname": "Cognome", "ip": "192.168.4.1", "md5": "e3a181e9cdd4757a8b416d93878770c5" },
                                     { "id": 5, "username": "marta", "online":false, "name": "Bertoldo", "surname": "Cognome", "ip": "192.163.1.1", "md5": "e5b8fa5e4f9e527ea335031bf2ec9a03" },
                                     { "id": 6, "username": "deborah", "online":true, "name": "Bassiano", "surname": "Cognome", "ip": "192.162.1.1", "md5": "6fbdff3ffb6f111d172759b4f05bea0e" },
                                     { "id": 7, "username": "luce", "online":false, "name": "Beatrice", "surname": "Cognome", "ip": "192.167.1.1", "md5": "200c020bef60e4d9c4c4ba1c85442026" },
                                     { "id": 8, "username": "giorgio", "online":true, "name": "Gemma", "surname": "Cognome", "ip": "192.168.1.4", "md5": "761377a69c44f620a0276a7790e09d4d" },
                                     { "id": 9, "username": "luigi", "online":true, "name": "Giocondo", "surname": "Cognome", "ip": "192.168.6.1", "md5": "adaa513a43f960202dd7d5f953b151c6" },
                                     { "id": 10, "username": "giovanni", "online":false, "name": "Gineto", "surname": "Cognome", "ip": "192.168.131", "md5": "38750c50c11e6054c2123073b17b18fc" },
                                     { "id": 11, "username": "alberto", "online":true, "name": "Onorino", "surname": "Cognome", "ip": "192.168.151", "md5": "822946942061078fe89f96f4088964b0" },
                                     { "id": 12, "username": "erik", "online":true, "name": "Ombretta", "surname": "Cognome", "ip": "192.168.4.1", "md5": "2f364c2e36b52bc80296cbf23da8b231" },
                                     { "id": 13, "username": "pippo", "online":true, "name": "Noemi", "surname": "Cognome", "ip": "192.168.1.8", "md5": "529ba429a58902bef56c2fcb672d5ccb" },
                                     { "id": 14, "username": "ambrosio", "online":false, "name": "Niceforo", "surname": "Cognome", "ip": "192.164.1.1", "md5": "a3a82559ae1ee7ec304fdbae095f063c" },
                                     { "id": 15, "username": "giulia", "online":false, "name": "Pantaleo", "surname": "Cognome", "ip": "192.16861.1", "md5": "5426db8fdf8dbbec1644b378175a2d9f" },
                                     { "id": 16, "username": "vanessa", "online":false, "name": "Pardo", "surname": "Cognome", "ip": "192.168.161", "md5": "d41a67854d304a5114718b3a5e123a91" },
                                     { "id": 17, "username": "valeria", "online":true, "name": "Flavio", "surname": "Cognome", "ip": "192.168.1.1", "md5": "c3deecdae51760d00e51b8b4fd2f09eb" },
                                     { "id": 18, "username": "martina", "online":false, "name": "Felicita", "surname": "Cognome", "ip": "192.166.1.1", "md5": "7b3297b0f913d67a251ec08401aca583" },
                                     { "id": 19, "username": "umbeto", "online":true, "name": "Fiore", "surname": "Cognome", "ip": "192.168.161", "md5": "e111d12722def577149766175fbe9ab4" },
                                     { "id": 20, "username": "osvaldo", "online":true, "name": "Fiorella", "surname": "Cognome", "ip": "192.166.1.1", "md5": "0f4cefeedec5163556751d61625eedd0" },
                                     { "id": 21, "username": "katia", "online":false, "name": "Erika", "surname": "Cognome", "ip": "192.168.1.1", "md5": "342bd0a61c7081db529c856d3bcd9545" },
                                     { "id": 22, "username": "claudia", "online":false, "name": "Marta", "surname": "Cognome", "ip": "192.148.1.1", "md5": "8bdb6217d08dcea7d67ff75f5f255081" },
                                     { "id": 23, "username": "lorenzo", "online":true, "name": "Stefano", "surname": "Cognome", "ip": "192.168.6.1", "md5": "2385676760fc16f76a43f3319faaa843" },
                                     { "id": 24, "username": "nicolo", "online":false, "name": "Enrico", "surname": "Cognome", "ip": "192.148.1.1", "md5": "6451ee8093c9cedc94f6c813b4dde2c5" },
                                     { "id": 25, "username": "armando", "online":true, "name": "Massimo", "surname": "Cognome", "ip": "192.168.1.1", "md5": "100a6c42a31a56e882475725d65537f8" },
                                     { "id": 26, "username": "ziro", "online":false, "name": "Giulio", "surname": "Cognome", "ip": "192.167.7.1", "md5": "8d1ddf41063e10757ba351d6d890a220" },
                                     { "id": 27, "username": "cinzia", "online":false, "name": "Andrea", "surname": "Cognome", "ip": "192.168.7.1", "md5": "3b2494b3af9f2d6c16caecd665fbf7a6" },
                                     { "id": 28, "username": "gilberto", "online":false, "name": "Benedetta", "surname": "Cognome", "ip": "192.648.1.1", "md5": "123" },
                                     { "id": 29, "username": "ombretta", "online":true, "name": "Luigi", "surname": "Cognome", "ip": "192.168.1.6", "md5": "123" },
                                     { "id": 30, "username": "massimo", "online":true, "name": "Anselmo", "surname": "Cognome", "ip": "195.168.151", "md5": "123" },
                             ]
                    };

        var store = DS.get("defaultStore");
        store.load(MyTalk.List, json);
        var adapter = store.adapterForType(MyTalk.List);
        adapter.didFindMany(store, MyTalk.List, json);

        json = {
                  "personal_datas": [{ id:1, "username": "mattia", "password": "123", "name":"Mattia", "surname":"Agostinetto", "email":"agomat@gmail.com", "md5":"699b1b3f9c23e21f13b2ac9267942b01"}] 
               };

        //store.load(MyTalk.PersonalData, json);
        //adapter = store.adapterForType(MyTalk.PersonalData);
        //adapter.didFindMany(store, MyTalk.PersonalData, json);        

        $("input[name=username]").val("aaaaaaa");
        $("input[name=password]").val("aaaaaaa");
        //$("form").first().submit();
      }, 1000);


      return;
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
    this.ws.all.send( JSON.stringify( ari ) ); // modifica
  }

});