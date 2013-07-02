/**
* Filename: ModifyConfirmPassword.js
* Package: com.mytalk.client.view.templateform
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
*  Classe che rappresenta il $textfield$ per l'inserimento della conferma password per la modifica account.
*
*/

MyTalk.ModifyConfirmPassword = Ember.TextField.extend({

  /**
   * Proprietà necessaria per la gestione del campo dati, aggiunge gli attributi $pattern$ e $onchange$
   * @property -attributeBindings           
   * @type {Binding}                   
   *
  */
  
  attributeBindings: ['pattern','onchange'],
 
  
  /**
   * Proprietà necessaria per settare il nome dell'input area
   * @property +name           
   * @type {String}                   
   *
  */
  
  name: 'password_conf',

  /**
   * Proprietà necessaria per settare l'id dell'input area
   * @property +id          
   * @type {String}                   
   *
  */
  
  id: 'password_conf',
  
  /**
   * Proprietà necessaria per settare a $password$ il tipo di dato dell'input area
   * @property +type           
   * @type {String}                   
   *
  */

  type: 'password',

  /**
   * Proprietà necessaria per settare il placeholder dell'input area
   * @property +placeholder           
   * @type {String}                   
   *
  */

  placeholder: 'Conferma Password',

  /**
   * Proprietà necessaria per settare l'attributo $onchange$ con la funzione relativa allo scatenamento dell'evento
   * @property +onchange           
   * @type {String}                   
   *
  */

  onchange: "this.setCustomValidity(this.validity.patternMismatch ? 'Le due password non combaciano' : checkPasswords(this.value, form.password.value)); function checkPasswords(a,b){ if(a!=b){return 'Le due password non combaciano';} else {return '';} };",
  
  /**
   * Proprietà necessaria per settare l'attributo $pattern$ con il pattern necessario alla verifica della password.
   * @property +pattern           
   * @type {String}                   
   *
  */

  pattern: '\\w{6,}'
});
