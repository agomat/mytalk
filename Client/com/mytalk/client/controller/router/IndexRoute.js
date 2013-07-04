/**
* Filename: IndexRoute.js
* Package: com.mytalk.client.controller.router
* Dependencies: com.mytalk.client.model.modelstruct.PersonalData
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
*  Gestisce la visualizzazione del template (Handlebars) e il collegamento del Model al Controller.
*
*/

MyTalk.IndexRoute = Ember.Route.extend({
  /**
   * Questo metodo è deputato alla renderizzazione del template $header$ all'interno della pagina HTML.
   *
   * @method -renderTemplate                                     
   * @return {Void} 
  */

  renderTemplate: function(controller, model) { 
    this.render('header', { into: 'index', outlet: 'head' });
  },
  
  /**
   * Questo metodo è deputato al collegamento tra Model $PersonalData$ e e il relativo Controller.
   *
   * @method -model                                     
   * @return {RecordArray} 
  */

  model: function() {
    return MyTalk.PersonalData.find();
  }
});
