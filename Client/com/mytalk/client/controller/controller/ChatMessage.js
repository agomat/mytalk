/**
* Filename: ChatMessage.js
* Package: com.mytalk.client.controller.controller
* Dependencies: 
* Author: Campese Stefano
* Date: 2013-04-23
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-04-23 | SC        | [+] Scrittura classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*
* Description: La classe rappresenta un messaggio inviato nella chat durante una conversazione.
*
*/

MyTalk.ChatMessage = Ember.Object.extend({
  
  /**
   * Proprietà necessaria contenente il testo vero e proprio del messaggio inviato/ricevuto.
   * @property -text           
   * @type {String}                   
   *
  */    

  text: null,
  /**
   * Proprietà necessaria che determina se un messaggio e ricevuto o inviato. 
   * @property -sent           
   * @type {Boolean}                   
   *
  */    
  sent: null,
  /**
   * Proprietà contenente la data di invio o riezione del messaggio 
   * @property -date           
   * @type {Date}                   
   *
  */    
  date: null
}); 
