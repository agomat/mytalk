/**
* Filename: ProfileFormView.js
* Package: com.mytalk.client.view.templateview
* Dependencies: com.mytalk.client.controller.controller.ProfileController
* Author: Griggio Massimo
* Date: 2013-04-26
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-07-02 | SC        | [+] Aggiunta metodo di autoinserimento valori
* 0.1     | 2013-06-12 | MG        | [+] Scrittura classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*
* Classe che rappresenta la vista che rappresenta la form di modifica dei dati personali.
*
*/

MyTalk.ProfileFormView = Ember.View.extend({

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
   * Questo metodo si occupa di riempire i campi della form con i dati attuali dell'utente. 
   *
   * @method -didInsertElement
   * @return {Void}
   * @override \href{http://emberjs.com/api/modules/ember-views.html}{ember-view}$\href{https://github.com/emberjs/ember.js/blob/v1.0.0-rc.5/packages/ember-views/lib/views/view.js#L1603}{Ember.View}
  */
  
  didInsertElement: function() {
    var me=this.get('controller').returnAccount();
    this.set('name', me.get('name'));
    this.set('surname', me.get('surname'));
    this.set('username', me.get('username'));
    this.set('email', me.get('email'));
  },
  /**
   * Questo metodo viene richiamato dal $submit$ della form.
   * Il metodo si occupa di attivare, nel controller associato, il metodo _save()_ per salvare i dati immessi.
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
    this.get('controller').updateProfile(name, surname, username, email, password, password_conf);
  }
});
