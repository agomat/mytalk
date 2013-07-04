/**
* Filename: LoginFormView.js
* Package: com.mytalk.client.view.templateview
* Dependencies: com.mytalk.client.controller.controller.IndexController
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
* Classe che rappresenta la vista che rappresenta la form per effettuare il login.
*
*/

MyTalk.LoginFormView = Ember.View.extend({
  needs: ['IndexView.controller'],

  /**
   * Proprietà necessaria per impostare il Controller della vista a $IndexController$
   * @property -indexBinding          
   * @type {Binding}                   
   *
  */
  
  indexBinding: "controller.index",
 
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
   * Proprietà necessaria per ottenere il valore inserito nel campo dati $email$  della form
   * @property -email          
   * @type {String}                   
   *
  */
  
  email: null,
  
  /**
   * Proprietà necessaria per ottenere il valore inserito nel campo dati $password$  della form
   * @property -password          
   * @type {String}                   
   *
  */
  
  password: null,
 
  /**
   * Questo metodo viene richiamato dal $submit$ della form.
   * Il metodo si occupa di attivare, nel controller associato, il metodo _login()_ necessario per effetturare il login.
   *
   * @method +submit              
   * @param {Object} event è l'evento di $submit$ effettuato sulla form               
   * @return {Void} 
  */
  
  submit: function(event) {
    event.preventDefault();
    $('#loading_bar').css('visibility','visible');
    $("input[name=password]").change();
    $("input[name=username]").change();
    var username = $.trim( this.get('username') );
    var password = this.get('password');
    this.get('controller').login(username, password);
  }
});

Ember.TextField.reopen({
  attributeBindings: ['name'],
  attributeBindings: ['id'],
});

