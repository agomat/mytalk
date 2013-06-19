/**
* Filename: StatsRoute.js
* Package: com.mytalk.client.controller.router
* Dependencies: 
* Author: Griggio Massimo
* Date: 2013-04-28
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-06-12 | MG        | [+] Scrittura classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*
*  Gestisce la visualizzazione del template (Handelbar) per questo tipo di risorsa.
*
*/

MyTalk.StatsRoute = Ember.Route.extend({
  /**
   * Questo metodo è deputato alla renderizzazione del template $stats$ all'interno della pagina HTML.
   *
   * @method -renderTemplate                                     
   * @return {Void} 
  */
  renderTemplate: function() {
    this.render('stats', { into: 'index', outlet: 'content' });
  }
}); 
 
 
