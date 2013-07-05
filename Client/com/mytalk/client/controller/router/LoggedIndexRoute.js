/**
* Filename: LoggedIndexRoute.js
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
*   Gestisce la visualizzazione del template (Handlebars) e il collegamento del Model al Controller.
*
*/

MyTalk.LoggedIndexRoute = Ember.Route.extend({
  
  /**
   *  Questo metodo permette di mostrare il template $list$ in modo tale che una volta loggati si mostri subito la lista $"Tutti i contatti"$ e il suo contenuto.
   *
   * @method -redirect                                     
   * @return {Void} 
  */
  
  redirect: function() {
    this.replaceWith('list', MyTalk.List.find(0));
  }
});
