/**
* Filename: ResgisterSurname.js
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

MyTalk.RegisterSurname = Ember.TextField.extend({
  attributeBindings: ["required"],
  required: true,
  name: 'surname',
  id: 'surname',
  type: 'text',
  placeholder: 'Cognome'
});