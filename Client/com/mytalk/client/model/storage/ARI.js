/**
* Filename: ARI.js
* Package: com.mytalk.client.model.storage
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

MyTalk.ARI = Ember.Object.extend({
  auth: undefined,
  req: undefined,
  info: undefined,
  
  init: function(a, r, i) {
    this.set('auth', a);
    this.set('req', r);
    this.set('info', JSON.stringify(i));
    this._super();
  },

  getAuth: function() {
    return this.get('auth');
  },
  
  getReq: function() {
    return this.get('req');
  },
  
  getInfo: function() {
    return this.get('info');
  },

  setAuth: function(a) {
    this.set('auth', a);
  },
  
  setReq: function(r) {
    this.set('req', r);
  },
  
  setInfo: function(i) {
    this.set('info', JSON.stringify(i));
  }
  
});
