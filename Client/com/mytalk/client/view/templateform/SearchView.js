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
* Description: Classe che rappresenta il $textfield$ per l'inserimento dell nome dell'utente da cercare.
*
*/

MyTalk.SearchView = Ember.TextField.extend({
  
  /**
   * Proprietà necessaria per settare il nome dell'input area
   * @property +name           
   * @type {String}                   
   *
  */

  name: 'search',

  /**
   * Proprietà necessaria per settare a $text$ il tipo di dato dell'input area
   * @property +type           
   * @type {String}                   
   *
  */

  type: 'text',

  /**
   * Proprietà necessaria per settare il placeholder dell'input area
   * @property +palceholder           
   * @type {String}                   
   *
  */

  placeholder: 'Filtra per nome o cognome',

  /**
   * Questo metodo osserva il $value$ dell'input e lancia la ricerca ogni volta che questo cambia al fine di affinare la ricerca di un utente su una lista.
   *
   * @method +valueDidChange
   * @param {Object} element è l'oggetto della view        
   * @param {Key} property è la proprietà che viene cambiata    
   * @param {String} value è contiene il valore effettivo sul quale effettuare la ricerca.                                 
   * @return {Void} 
  */

  valueDidChange: function(element,property,value) {
    this.get('controller').filter(this.value);
  }.observes('value')
 });