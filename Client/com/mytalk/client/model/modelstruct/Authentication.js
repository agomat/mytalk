/**
* Filename: Authentication.js
* Package: com.mytalk.client.model.modelstruct
* Dependencies: 
* Author: Agostinetto Mattia
* Date: 2013-04-20
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-04-20 | MA        | [+] Scrittura classe del Model
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*
* Model per la rappresentazione dei dati di autenticazione dell'utente collegato al sistema
*/

MyTalk.Authentication = DS.Model.extend({
  username: DS.attr('string'),
  password: DS.attr('string'),
  ip: DS.attr('string')
  
});

/**
* Campo dati per memorizzare l'username dell'utente
* 
* @property -username
* @type {string}
*
*/

/**
* Campo dati per memorizzare la password dell'utente
* 
* @property -password
* @type {string}
*
*/

/**
* Campo dati per memorizzare l'indirizzo IP dell'utente
* 
* @property -ip
* @type {string}
*
*/