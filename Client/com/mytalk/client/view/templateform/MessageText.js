/**
* Filename: MessageText.js
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
*  Classe che rappresenta la $textarea$ per l'inserimento del testo del messaggio nella chat.
*
*/

MyTalk.MessageText = Ember.TextArea.extend({

  /**
   * Proprietà necessaria per la gestione del campo dati, aggiunge l'attributo $required$
   * @property -attributeBindings           
   * @type {Binding}                   
   *
  */

  attributeBindings: ["required"],
  
  /**
   * Proprietà necessaria per settare l'attributo $required$ a $true$
   * @property +required           
   * @type {Boolean}                   
   *
  */

  required: true,

  /**
   * Proprietà necessaria per settare il nome della text area
   * @property +name           
   * @type {String}                   
   *
  */
  
  name: 'text_input',
  
  /**
   * Proprietà necessaria per settare l'id della text area
   * @property +id          
   * @type {String}                   
   *
  */

  id: 'text_input',

  /**
   * Proprietà necessaria per settare a $text$ il tipo di dato della text area
   * @property +type           
   * @type {String}                   
   *
  */

  type: 'text',
  
  /**
   * Proprietà necessaria per settare il placeholder della text area
   * @property +palceholder           
   * @type {String}                   
   *
  */

  placeholder: 'Scrivi un messaggio'
});
