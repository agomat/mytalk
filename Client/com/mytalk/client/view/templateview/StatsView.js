/**
* Filename: StatsView.js
* Package: com.mytalk.client.view.templateview
* Dependencies:
* Author: Campese Stefano
* Date: 2013-07-02
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-07-02 | SC        | [+] Scrittura classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*
* Classe che rappresenta la vista associata al template $header$
*
*/

MyTalk.StatsView = Ember.View.extend({
  
  /**
   * Proprietà necessaria per impostare il nome del template associato alla vista
   * @property -templateName          
   * @type {String}                   
   *
  */

  templateName: "stats",
  
  /**
   * Proprietà necessaria per impostare il nome della vista
   * @property -name          
   * @type {String}                   
   *
  */

  name: "stats",

  /**
   * Questo metodo si occupa si occupa di invocare il metodo $getCalls$ del relativo controller.
   *
   * @method -didInsertElement            
   * @return {Void} 
   * @override \href{http://emberjs.com/api/modules/ember-views.html}{ember-view}$\href{https://github.com/emberjs/ember.js/blob/v1.0.0-rc.5/packages/ember-views/lib/views/view.js#L1603}{Ember.View}
  */

  didInsertElement: function() {
    this.get('controller').getCalls();
  }

});
