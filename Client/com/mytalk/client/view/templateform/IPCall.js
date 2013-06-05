/**
* Filename: IPCAll.js
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

MyTalk.IPCall = Ember.TextField.extend({
  attributeBindings: ["required"],
  required: true,
  name: 'ip_call',
  id: 'ip_call_text',
  type: 'text',
  placeholder: 'Indirizzo IP'
});