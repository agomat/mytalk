/**
* Filename: IPFormView.js
* Package: com.mytalk.client.view.templateview
* Dependencies:com.mytalk.client.controller.controller.IndexController
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
* Classe che rappresenta la vista che rappresenta la form per l'inserimento di un indirizzo ip da contattare.
*
*/

MyTalk.IPFormView = Ember.View.extend({

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
   * Proprietà necessaria per ottenere il valore inserito nel campo dati $ip_call$  della form
   * @property -ip_call          
   * @type {String}                   
   *
  */

  ip_call: null,

  /**
   * Questo metodo viene richiamato dal $submit$ della form e attiva il metodo, nel controller associato, per contattare l'indirizzo ip scritto.
   *
   * @method +submit              
   * @param {Object} event è l'evento di $submit$ effettuato sulla form               
   * @return {Void} 
  */

  submit: function(event) {
    event.preventDefault();
    var ip_call = this.get('ip_call');
    this.get('controller').ipCall(ip_call);
  }
});
