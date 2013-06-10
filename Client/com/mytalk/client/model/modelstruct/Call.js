/**
* Filename: Call.js
* Package: com.mytalk.client.model.modelstruct
* Dependencies: com.mytalk.client.model.modelstruct.User
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
* Model per la rappresentazione dei dati di una chiamata effettuata
*/

MyTalk.Call = DS.Model.extend({
  speaker: DS.belongsTo('MyTalk.User'),
  caller: DS.attr('boolean'),
  startDate: DS.attr('date'),
  duration: DS.attr('number'),
  byteSent: DS.attr('number'),
  byteReceived: DS.attr('number')
});

/**
* Contiene l'oggetto $User$ che rappresenta l'interlocutore della chiamata
* 
* @property -speaker
* @type {User}
*
*/

/**
* Valore booleano; $true$ se l'utente loggato è il chiamante, $false$ altrimenti
* 
* @property -caller
* @type {boolean}
*
*/

/**
* Memorizza la data nella quale la chiamata è cominciata
* 
* @property -startDate
* @type {Date}
*
*/

/**
* Memorizza la durata dell'intera conversazione
* 
* @property -duration
* @type {number}
*
*/

/**
* Memorizza il numero di $Byte$ inviati dall'utente loggato al suo interlocutore
* 
* @property -byteSent
* @type {number}
*
*/

/**
* Memorizza il numero di $Byte$ ricevuti dall'interlocutore
* 
* @property -byteReceived
* @type {number}
*
*/