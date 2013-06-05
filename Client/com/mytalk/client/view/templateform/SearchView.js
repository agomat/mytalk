/**
* Filename: SearchView.js
* Package: com.mytalk.client.view.templateform
* Dependencies: com.mytalk.client.controller.controller.Users
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

MyTalk.SearchView = Ember.TextField.extend({
  name: 'search',
  type: 'text',
  placeholder: 'Filtra per nome o cognome',
  valueDidChange: function(element,property,value) {
    this.get('controller').filter(this.value);
  }.observes('value')
 });