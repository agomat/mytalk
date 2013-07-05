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
   * @property -duration           
   * @type {Number}                   
   *
  */    

  duration: 0,
  
  /**
   * Proprietà contiene la quantità di byte ricevuti. 
   * @property -receivedBytes           
   * @type {Number}                   
   *
  */    
  
  receivedBytes: 0,
  
  /**
   * Proprietà contiene la quantità di byte inviati 
   * @property -sentBytes           
   * @type {Number}                   
   *
  */    
  
  sentBytes: 0,
 
  /**
   * Proprietà contiene la data della chiamata
   * @property -date           
   * @type {Date}                   
   *
  */   
 
  date:null,

  /**
   * Proprietà necessaria al fine di determinare se la chiamata è stata effettuata o ricevuta
   * @property -sender           
   * @type {Boolean}                   
   *
  */  
  sender:null,
 
  /**
   * Proprietà contiene l'id dell'utente interlocutore
   * @property -speaker           
   * @type {Number}                   
   *
  */ 
  speaker:null,
}); 
