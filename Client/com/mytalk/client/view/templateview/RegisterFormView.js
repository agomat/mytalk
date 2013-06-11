/**
* Filename: RegisterFormView.js
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
* Classe che rappresenta la vista che rappresenta la form di registrazione nel portale.
*
*/

MyTalk.RegisterFormView = Ember.View.extend({

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
   * Proprietà necessaria per ottenere il valore inserito nel campo dati $name$  della form
   * @property -name          
   * @type {String}                   
   *
  */

  name: null,
  
  /**
   * Proprietà necessaria per ottenere il valore inserito nel campo dati $surname$  della form
   * @property -surname          
   * @type {String}                   
   *
  */

  surname: null,

  /**
   * Proprietà necessaria per ottenere il valore inserito nel campo dati $username$  della form
   * @property -username          
   * @type {String}                   
   *
  */
  
  username: null,

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
   * Proprietà necessaria per ottenere il valore inserito nel campo dati $password_conf$  della form
   * @property -password_conf         
   * @type {String}                   
   *
  */

  password_conf: null,
  
  /**
   * Questo metodo viene richiamato dal $submit$ della form.
   * Il metodo si occupa di attivare, nel controller associato, il metodo _register()_ necessatio per procedere
   * con la registrazione di un nuovo utente.
   *
   * @method +submit              
   * @param {Object} event è l'evento di $submit$ effettuato sulla form               
   * @return {Void} 
  */

  submit: function(event) {
    event.preventDefault();
    var name = this.get('name');
    var password = this.get('password');
    var password_conf = this.get('password_conf');
    var email = this.get('email');
    var surname = this.get('surname');
    var username = this.get('username');
    this.get('controller').register(name, surname, username, email, password, password_conf);
  }
});
