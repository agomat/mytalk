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
* Description: Classe che rappresenta la vista che rappresenta la form di invio messaggi nella chat.
*
*/

MyTalk.MessageFormView = Ember.View.extend({

  /**
   * Proprietà necessaria per settare il tipo di vista 
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
   * Proprietà necessaria per settare l'attributo $method$ a $post$ 
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
   * Questo metodo viene richiamato dal $submit$ della form e attiva il metodo, nel controller associato, per inviare il messaggio scritto nella chat.
   *
   * @method +submit              
   * @param {Object} event è l'evento di $submit$ effettuato sulla form               
   * @return {Void} 
  */

  submit: function(event) {
    event.preventDefault();
    var message = this.get('message');
    this.get('controller').sendMessage(message);
  }
});