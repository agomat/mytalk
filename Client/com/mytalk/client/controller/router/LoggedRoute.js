/**
* Filename: LoggedRoute.js
* Package: com.mytalk.client.controller.router
* Dependencies: com.mytalk.client.model.modelstruct.List
* Author: Campese Stefano
* Date: 2013-04-28
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-04-28 | SC        | [+] Scrittura classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*
* Description: Gestisce la visualizzazione del template (Handelbar) e il collegamento del Model al Controller.
*
*/

MyTalk.LoggedRoute = Ember.Route.extend({
   /**
   * Questo metodo è deputato alla renderizzazione del template $logged$ all'interno della pagina HTML.
   *
   * @method -renderTemplate                                     
   * @return {Void} 
  */
  renderTemplate: function() {
    this.render('logged', { into: 'index', outlet: 'content' });
  },

  /**
   * Questo metodo è deputato al collegamento tra Model $List$ e il relativo Controller.
   *
   * @method -model                                     
   * @return {RecordArray} 
  */

  model: function() {
    return MyTalk.List.find(); 
  }
});