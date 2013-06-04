/**
* Filename: Store.js
* Package: com.mytalk.client.model.storage
* Dependencies:  com.mytalk.client.model.storage.SocketAdapter
* Author: Mattia Agostinetto
* Date: 2013-04-26
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-05-01 | MA        | [#] Utilizzo di SocketAdapter
* 0.1     | 2013-04-26 | MA        | [+] Creazione store
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

MyTalk.Store = DS.Store.extend({
  adapter: DS.SocketAdapter.create({})
});
