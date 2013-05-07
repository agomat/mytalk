/**
* Filename: WebSocketConnection.js
* Package: com.mytalk.client [...]
* Author: Mattia Agostinetto
* Editor: Sublime Text 2
* Date: 2013-04-26
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-05-01 | MA        | [+] Creazione Mixin WebSocketConnection
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

MyTalk.WebSocketConnection = Ember.Mixin.create({
    socket: undefined,
    getSocket: function() {
        // Lazy creation
        if(!this.get('socket')) {
            this.set('socket', new MyTalk.WebSocketHandler());
        }
        return this.get('socket');
    }
});