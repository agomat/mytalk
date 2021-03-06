/**
* Filename: SearchView.js
* Package: com.mytalk.client.view.templateform
* Dependencies: com.mytalk.client.controller.controller.Users
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
* Classe che rappresenta il $textfield$ per l'inserimento del nome dell'utente da cercare.
*
*/

MyTalk.SearchView = Ember.TextField.extend({
  
  /**
   * Proprietà necessaria per impostare il nome dell'input area
   * @property +name           
   * @type {String}                   
   *
  */

  name: 'search',

  /**
   * Proprietà necessaria per impostare a $text$ il tipo di dato dell'input area
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

  placeholder: 'Filtra per nome o cognome',

  /**
   * Questo metodo osserva il $value$ dell'input e lancia la ricerca ogni volta che questo cambia al fine di affinare la ricerca di un utente su una lista.
   *
   * @method +valueDidChange
   * @param {Object} è l'oggetto della view        
   * @param {Key} è la proprietà che viene cambiata    
   * @param {String} contiene il valore effettivo sul quale effettuare la ricerca                                 
   * @return {Void} 
  */

  valueDidChange: function(element,property,value) {
    this.get('controller').filter(this.value);
  }.observes('value'),

  resetValue:function(){
    this.set('value',"");
  }.observes('controller.list.id')
 });
