/**
* Filename: ResgisterPassword.js
* Package: com.mytalk.client.view.templateform
* Dependencies:
* Author: Griggio Massimo
* Date: 2013-04-26
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-04-26 | MG        | [+] Scrittura classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

MyTalk.RegisterPassword = Ember.TextField.extend({
  attributeBindings: ['pattern','required','onchange'],
  required: true,
  name: 'password',
  id: 'password',
  type: 'password',
  placeholder: 'Password',
  onchange: "this.setCustomValidity( this.validity.patternMismatch ? 'La password deve contenere almeno 6 caratteri': '')",
  pattern: '\\w{6,}'
});