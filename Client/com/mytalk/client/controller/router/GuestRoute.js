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
*
*  Gestisce la visualizzazione del template (Handelbar) per questo tipo di risorsa.
*
*/

MyTalk.GuestRoute = Ember.Route.extend({
	/**
   * Questo metodo è deputato alla renderizzazione del template $guest$ all'interno della pagina HTML.
   *
   * @method -renderTemplate                                     
   * @return {Void} 
  */
  renderTemplate: function() {
    this.render('guest', { into: 'index', outlet: 'content' });
  }
});
