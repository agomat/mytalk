/**
* Filename: IPFormView.js
* Package: com.mytalk.client.view.templateview
* Dependencies:com.mytalk.client.controller.controller.IndexController
* Author: Campese Stefano
* Date: 2013-04-26
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-04-26 | SC        | [+] Scrittura classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

MyTalk.IPFormView = Ember.View.extend({
  tagName: 'form',
  attributeBindings: ["method"],
  method: 'post',
  ip_call: null,
  submit: function(event) {
    event.preventDefault();
    var ip_call = this.get('ip_call');
    this.get('controller').ipCall(ip_call);
  }
});