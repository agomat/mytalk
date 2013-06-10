/**
* Filename: LoginPassword.js
* Package: com.mytalk.client.view.templateform
* Dependencies:
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

MyTalk.LoginPassword = Ember.TextField.extend({
  attributeBindings: ["required","pattern","onchange"],
  required: true,
  name: 'password',
  id: 'password',
  type: 'password',
  placeholder: 'Password',
  onchange: "this.setCustomValidity(this.validity.patternMismatch ? 'La password deve contenere almeno sei caratteri' : '');",
  pattern:'\\w{6,}'
});