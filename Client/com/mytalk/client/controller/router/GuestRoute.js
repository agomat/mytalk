/**
* Filename: GuestRoute.js
* Package: com.mytalk.client.controller.router
* Dependencies: 
* Author: Griggio Massimo
* Date: 2013-04-23
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-04-23 | MG        | [+] Scrittura classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

MyTalk.GuestRoute = Ember.Route.extend({
  renderTemplate: function() {
    this.render('guest', { into: 'index', outlet: 'content' });
  }
//   model: function() {
//     /*
//     var store = DS.get("defaultStore");
//     var adapter = store.adapterForType(MyTalk.PersonalData);
//     var json = {"personal_data": { id:0, 'ip':'0.0.0.0:0000'}};
//     store.load(MyTalk.PersonalData, json);
//     adapter.didFindRecord(store, MyTalk.PersonalData, json,0);
//     */ 
//     return MyTalk.PersonalData.find(0);
//   }
});