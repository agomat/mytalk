/**
* Filename: LoginPassword.js
* Package: com.mytalk.client.view.templateform
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
*  Classe che rappresenta il $textfield$ per l'inserimento della password per l'autenticazione.
*
*/

MyTalk.LoginPassword = Ember.TextField.extend({
  
  /**
   * Proprietà necessaria per la gestione del campo dati, aggiunge l'attributo $required$, $onchange$, $pattern$
   * @property -attributeBindings           
   * @type {Binding}                   
   *
  */

  attributeBindings: ["required","pattern","onchange"],
  
  /**
   * Proprietà necessaria per impostare l'attributo $required$ a $true$
   * @property +required           
   * @type {Boolean}                   
   *
  */

  required: true,
  
  /**
   * Proprietà necessaria per impostare il nome dell'input area
   * @property +name           
   * @type {String}                   
   *
  */

  name: 'password',

  /**
   * Proprietà necessaria per impostare l'id dell'input area
   * @property +id          
   * @type {String}                   
   *
  */

  id: 'password',
  
  /**
   * Proprietà necessaria per impostare a $password$ il tipo di dato dell'input area
   * @property +type           
   * @type {String}                   
   *
  */

  type: 'password',
  
  /**
   * Proprietà necessaria per impostare il placeholder dell'input area
   * @property +placeholder           
   * @type {String}                   
   *
  */

  placeholder: 'Password',
  /**
   * Proprietà necessaria per impostare l'attributo $onchange$ con la funzione relativa allo scatenamento dell'evento
   * @property +onchange           
   * @type {String}                   
   *
  */

  onchange: "this.setCustomValidity(this.validity.patternMismatch ? 'La password deve contenere almeno sei caratteri' : '');",
  
  /**
   * Proprietà necessaria per impostare l'attributo $pattern$ con il pattern necessario alla verifica della password.
   * @property +pattern           
   * @type {String}                   
   *
  */

  pattern:'\\w{6,}'
});
