/**
* Filename: CallInfo.js
* Package: com.mytalk.client.controller.controller
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
*  La classe rappresenta le statistiche della chiamata in corso.
*
*/

MyTalk.CallInfo = Ember.Object.extend({
  
  /**
   * Proprietà deputata al contenimento della durata della chiamata.
   * @property -text           
   * @type {String}                   
   *
  */    

  duration: null,
  
  /**
   * Proprietà contiene la quantità di byte ricevuti. 
   * @property -sent           
   * @type {Boolean}                   
   *
  */    
  
  receivedBytes: null,
  
  /**
   * Proprietà contiene la quantità di bite inviati 
   * @property -date           
   * @type {Date}                   
   *
  */    
  
  sentBytes: null
}); 
