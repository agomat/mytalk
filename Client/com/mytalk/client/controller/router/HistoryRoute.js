/**
* Filename: HistoryRoute.js
* Package: com.mytalk.client.controller.router
*          com.mytalk.client.model.storage.processing.processor.outcoming.stats.GetCalls
* Dependencies: 
* Author: Griggio Massimo
* Date: 2013-04-28
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-06-12 | MG        | [+] Scrittura classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*
*  Gestisce la visualizzazione del template (Handlebars) per questo tipo di risorsa.
*
*/

MyTalk.HistoryRoute = Ember.Route.extend({
  model: function() {
    return MyTalk.Call.find(); 
  }
}); 
 
 
