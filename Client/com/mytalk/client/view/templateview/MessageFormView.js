/**
* Filename: MessageFormView.js
* Package: com.mytalk.client.view.templateview
* Dependencies: com.mytalk.client.controller.controller.CollingController
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
* Classe che rappresenta la vista che rappresenta la form di invio messaggi nella chat.
*
*/

MyTalk.MessageFormView = Ember.View.extend({

  /**
   * Proprietà necessaria per impostare il tipo di vista 
   * @property -tagName          
   * @type {String}                   
   *
  */

  tagName: 'form',
  
  /**
   * Proprietà necessaria per la gestione della form, aggiunge l'attributo $method$
   * @property -attributeBindings           
   * @type {Binding}                   
   *
  */

  attributeBindings: ["method"],
  
  /**
   * Proprietà necessaria per impostare l'attributo $method$ a $post$ 
   * @property +method          
   * @type {String}                   
   *
  */

  method: 'post',

  /**
   * Proprietà necessaria per ottenere il valore inserito nel campo dati $message$  della form
   * @property -message          
   * @type {String}                   
   *
  */

  message: null,

  /**
   * Questo metodo viene richiamato dal $submit$ della form.
   * Il metodo si occupa di attivare, nel controller associato, il metodo _sendMessage()_ necessario 
   * per inviare il messaggio scritto nella chat.
   *
   * @method +submit              
   * @param {Object} event è l'evento di $submit$ effettuato sulla form               
   * @return {Void} 
  */

  submit: function(event) {
    event.preventDefault();
    var message = this.get('message');
    this.set('message','');
    this.get('controller').sendMessage(message);
  },

  /**
   * Questo metodo di occupa di attivare alcune funzioni JQuery che permettodo di catturare
   * la pressione del tasto $Invio$ esguendo l'invio del messaggio di testo all'altro peer
   *
   * @method -didInsertElement            
   * @return {Void} 
   * @override \href{http://emberjs.com/api/modules/ember-views.html}{ember-view}$\href{https://github.com/emberjs/ember.js/blob/v1.0.0-rc.5/packages/ember-views/lib/views/view.js#L1603}{Ember.View}
  */

  didInsertElement: function() {
    Ember.run.later(this, function(){
      $.fn.bindEnterKeyPressed();
    }, 500);
  }

});
