/**
* Filename: IPCAll.js
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
*  Classe che rappresenta il $textfield$ per l'inserimento dell'indirizzo IP da contattare.
*
*/

MyTalk.IPCall = Ember.TextField.extend({
  
  /**
   * Proprietà necessaria per la gestione del campo dati, aggiunge l'attributo $required$
   * @property -attributeBindings           
   * @type {Binding}                   
   *
  */
  
  attributeBindings: ['pattern','required','onchange'],
   
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
  
  name: 'ip_call',

  /**
   * Proprietà necessaria per impostare l'id dell'input area
   * @property +id          
   * @type {String}                   
   *
  */

  id: 'ip_call_text',
  
  /**
   * Proprietà necessaria per impostare il tipo di dato dell'input area
   * @property +type           
   * @type {String}                   
   *
  */

  type: 'text',
  
  /**
   * Proprietà necessaria per impostare il placeholder dell'input area
   * @property +placeholder           
   * @type {String}                   
   *
  */

  placeholder: 'Indirizzo IP',
  
  /**
   * Proprietà necessaria per impostare l'attributo $onchange$ con la funzione relativa allo scatenamento dell'evento
   * @property +onchange           
   * @type {String}                   
   *
  */

  onchange:"this.setCustomValidity( this.validity.patternMismatch ? 'Inserire un indirizzo IP corretto': '')",

  /**
   * Proprietà necessaria per impostare l'attributo $pattern$ con il pattern necessario alla verifica dell'
   * indirizzo IP.
   * @property +pattern           
   * @type {String}                   
   *
  */ 

  pattern:'([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})(:[0-9]{0,5})?'
});
