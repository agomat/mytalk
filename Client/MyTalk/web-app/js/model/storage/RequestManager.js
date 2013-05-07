/**
* Filename: RequestManager.js
* Package: com.mytalk.client [...]
* Author: Mattia Agostinetto
* Editor: Sublime Text 2
* Date: 2013-04-26
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-05-01 | MA        | [+] Creazione del Mixin
*		  |			   |           | [+] <scrivere>
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

// TODO
MyTalk.RequestManager = Ember.Mixin.create({
    username: function() {
    	return MyTalk.Authentication.find(0).get('username');
    }.property()
});
