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

// TODO RequestManager potrebbe essere un mixin (o oggetto) che si compone di tutte le richieste
MyTalk.RequestManager = Ember.Mixin.create({
    // implementare un metodo che data un nome di una richiesta
    // ritorna in mixin deputato a gestirla
    //// questo serve per onMessage; da server a me

    username: function() {
    	return MyTalk.Authentication.find(0).get('username');
    }.property()
});
