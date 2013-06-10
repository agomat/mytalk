/**
* Filename: Store.js
* Package: com.mytalk.client.model.storage
* Dependencies:  com.mytalk.client.model.storage.SocketAdapter
* Author: Agostinetto Mattia
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
*
* Descrizione della _classe_
*/

MyTalk.Store = DS.Store.extend({
  /**
  * adapter ......... scrivere!
  * 
  * @property -adapter
  * @type {SocketAdapter}
  * @override \href{http://www.thomasboyt.com/ember-data-docs/}{ember-data}$\href{https://github.com/emberjs/data/blob/master/packages/ember-data/lib/system/store.js#L184}{Store}
  *
  */
  adapter: DS.SocketAdapter.create({})
});


