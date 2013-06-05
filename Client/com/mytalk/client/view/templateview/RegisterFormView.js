/**
* Filename: RegisterFormView.js
* Package: com.mytalk.client.view.templateview
* Dependencies: com.mytalk.client.controller.controller.IndexController
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

MyTalk.RegisterFormView = Ember.View.extend({
  tagName: 'form',
  attributeBindings: ["method"],
  method: 'post',
  name: null,
  surname: null,
  username: null,
  email: null,
  password: null,
  password_conf: null,
  
  submit: function(event) {
    event.preventDefault();
    var name = this.get('name');
    var password = this.get('password');
    var password_conf = this.get('password_conf');
    var email = this.get('email');
    var surname = this.get('surname');
    var username = this.get('username');
    this.get('controller').register(name, surname, username, email, password, password_conf);
  }
});