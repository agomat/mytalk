/**
* Filename: RegisterView.js
* Package: com.mytalk.client.view.templateview
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
* Classe che rappresenta la vista associata al template $register$
*
*/

MyTalk.RegisterView = Ember.View.extend({
  
  /**
   * Proprietà necessaria per impostare il nome del template associato alla vista
   * @property -templateName          
   * @type {String}                   
   *
  */
  
  templateName: "register",

  /**
   * Proprietà necessaria per impostare il nome della vista
   * @property -name          
   * @type {String}                   
   *
  */

  name: "register"
});
