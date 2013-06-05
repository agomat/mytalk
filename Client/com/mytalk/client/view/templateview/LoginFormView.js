/**
* Filename: LoginFormView.js
* Package: com.mytalk.client.view.templateview
* Dependencies: com.mytalk.client.controller.controller.IndexController
* Author: Stefano Campese
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

MyTalk.LoginFormView = Ember.View.extend({
  needs: ['IndexView.controller'],
  indexBinding: "controller.index",
  tagName: 'form',
  attributeBindings:["method"],
  method: 'post',
  email: null,
  password: null,

  submit: function(event) {
    event.preventDefault();
    var username = this.get('username');
    var password = this.get('password');
    this.get('controller').login(username, password);
  }
});

Ember.TextField.reopen({
  attributeBindings: ['name'],
  attributeBindings: ['id'],
});

