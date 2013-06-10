/**
* Filename: Router.js
* Package: com.mytalk.client.controller.router
* Dependencies: 
* Author: Griggio Massimo
* Date: 2013-04-23
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-04-23 | MG        | [+] Scrittura classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*
* Description: Definisce il mapping tra gli hash della url e risorse (``Route'') dell'applicazione.
*
*/

MyTalk.Router.map(function() {
  this.resource('index', { path: '/' }, function() {
    this.resource('guest', { path: '/' });
    this.resource('ipcall', { path: '/ip' });
    this.resource('call', { path: '/calling' } , function() {
      this.resource('calling', { path: '/:call_id' });
    });
    this.resource('logged', { path: '/lists' } , function() {
      this.resource('list', { path: '/:list_id' });
    });
    
  });
});
