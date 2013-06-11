/**
* Filename: CallingRoute.js
* Package: com.mytalk.client.controller.router
* Dependencies: 
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
*  Gestisce la visualizzazione del template (Handelbar) per questo tipo di risorsa.
*
*/

MyTalk.CallingRoute = Ember.Route.extend({
  /**
   * Questo metodo è deputato alla renderizzazione del template $calling$ all'interno della pagina HTML.
   *
   * @method -renderTemplate                                     
   * @return {Void} 
  */
  renderTemplate: function() {
    this.render('calling', { into: 'index', outlet: 'content' });
    Ember.run.later(this, function() {
    var controller = this.controllerFor('calling');
    console.log(controller.get('isIncomingCall'));
    if(controller.get('isIncomingCall') === false) {
      controller.call(controller.get('content'));
    }
    }, 500)
  }
});
