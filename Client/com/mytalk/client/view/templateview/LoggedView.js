/**
* Filename: LoggedView.js
* Package: com.mytalk.client.view.templateview
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

MyTalk.LoggedView = Ember.View.extend({
  templateName: "logged",
  name: "logged",
  didInsertElement: function() { 
    Ember.run.later(this, function(){
      $.fn.dropDownMenu();
    }, 100);
  }
});