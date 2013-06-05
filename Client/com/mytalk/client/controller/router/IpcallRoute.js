/**
* Filename: IpcallRoute.js
* Package: com.mytalk.client.controller.router
* Dependencies: com.mytalk.client.model.modelstruct.PersonalData
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
*/

MyTalk.IpcallRoute = Ember.Route.extend({
  renderTemplate: function() {
    this.render('ipcall', { into: 'index', outlet: 'content' });
  },
  model: function() {
    return MyTalk.PersonalData.find(0);
  }
}); 
