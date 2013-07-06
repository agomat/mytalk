/**
* Filename: IndexView.js
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
*
* Classe che rappresenta la vista associata al template $index$
*
*/

MyTalk.IndexView = Ember.View.extend({
  
  /**
   * Proprietà necessaria per impostare il nome del template associato alla vista
   * @property -templateName          
   * @type {String}                   
   *
  */

  templateName: "index",


  /**
   * Proprietà necessaria per impostare il nome della vista
   * @property -name          
   * @type {String}                   
   *
  */

  name: "index",


  /**
   * Questo metodo si occupa di attivare alcune funzioni jQuery per la vista.
   *
   * @method -didInsertElement            
   * @return {Void} 
   * @override \href{http://emberjs.com/api/modules/ember-views.html}{ember-view}$\href{https://github.com/emberjs/ember.js/blob/v1.0.0-rc.5/packages/ember-views/lib/views/view.js#L1603}{Ember.View}
  */

  didInsertElement: function() {
    Ember.run.later(this, function(){
      $.fn.videoTutorial();
    }, 500);
  }
});
