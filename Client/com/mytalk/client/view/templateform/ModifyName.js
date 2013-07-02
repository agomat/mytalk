/**
* Filename: ModifyName.js
* Package: com.mytalk.client.view.templateform
* Dependencies:
* Author: Griggio Massimo
* Date: 2013-07-02
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-07-02 | MG        | [+] Scrittura classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*
* Classe che rappresenta il $textfield$ per l'inserimento del nome per la modifica account.
*
*/

MyTalk.ModifyName = Ember.TextField.extend({
  
  /**
   * Proprietà necessaria per settare il nome dell'input area
   * @property +name           
   * @type {String}                   
   *
  */
  
  name: 'name',

  /**
   * Proprietà necessaria per settare l'id dell'input area
   * @property +id          
   * @type {String}                   
   *
  */
  
  id: 'name',
  
 /**
   * Proprietà necessaria per settare a $text$ il tipo di dato dell'input area
   * @property +type           
   * @type {String}                   
   *
  */

  type: 'text',

  /**
   * Proprietà necessaria per settare il placeholder dell'input area
   * @property +placeholder           
   * @type {String}                   
   *
  */
  
  placeholder: 'Nome'
});
