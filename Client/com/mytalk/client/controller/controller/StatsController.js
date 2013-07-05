/**
* Filename: StatsController.js
* Package: com.mytalk.client.controller.controller
* Dependencies:
* Author: Campese Stefano
* Date: 2013-07-01
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-07-01 | SC        | [+] Scrittura classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*
*  Controller deputato alla visualizzazione, delle statische delle chiamate effettuate.
*
*/

MyTalk.StatsController = Ember.ObjectController.extend({
  /**
   * Questo metodo è necessario per collegare il controller al rispettivo model; tale
   * metodo viene invocato ogni qual volta viene creata un'istanza di questo controller.
   *
   * @method -init                                     
   * @return {Void} 
  */
  init:function(){
    this._super();
    this.set('content',MyTalk.WCall.find(0));
  },

});