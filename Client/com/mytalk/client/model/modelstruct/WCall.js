/**
* Filename: WCall.js
* Package: com.mytalk.client.model.modelstruct
* Dependencies: com.mytalk.client.model.modelstruct.Call
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
* Model per la rappresentazione di uno storico di chiamate che l'utente autenticato ha eseguito
*/

MyTalk.WCall = DS.Model.extend({
  list: DS.hasMany('MyTalk.Call'),
  totalByteSent: DS.attr('number'),
  totalByteReceived: DS.attr('number'),
  totalDuration: DS.attr('number')
  
});

/**
* Lista di riferimenti di $Call$ che rappresentano le chiamate che l'utente autenticato ha eseguito
* 
* @property -list
* @type {Array<Call>}
*
*/

/**
* Memorizza il numero di $Byte$ inviati dall'utente autenticato da quando si è registrato al sistema
* 
* @property -totalByteSent
* @type {number} 
*
*/

/**
* Memorizza il numero di $Byte$ recevuti dall'utente autenticato da quando si è registrato al sistema
* 
* @property -totalByteReceived
* @type {number}
*
*/

/**
* Memorizza la durata delle chiamate effettuate e ricevute dell'utente autenticato da quando si è registrato al sistema
* 
* @property -totalDuration
* @type {number}
*
*/
