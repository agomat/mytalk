/**
* Filename: ResgisterUsername.js
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

MyTalk.RegisterUsername = Ember.TextField.extend({
  attributeBindings: ["required"],
  required: true,
  name: 'username',
  id: 'username',
  type: 'text',
  placeholder: 'Username'
});