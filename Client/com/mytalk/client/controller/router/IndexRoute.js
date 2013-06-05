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
*/

MyTalk.IndexRoute = Ember.Route.extend({
  renderTemplate: function(controller, model) { 
    this._super();
    this.render('header', { into: 'index', outlet: 'head' });
  },
  model: function() {
    return MyTalk.PersonalData.find();
  }
});
