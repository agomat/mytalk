/**
* Filename: LoggedIndexRoute.js
* Package: com.mytalk.client.controller.router
* Dependencies: com.mytalk.client.model.modelstruct.List
* Author: Stefano Campese
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

MyTalk.LoggedIndexRoute = Ember.Route.extend({
  redirect: function() {
    this.replaceWith('list', MyTalk.List.find(0));
  }
});