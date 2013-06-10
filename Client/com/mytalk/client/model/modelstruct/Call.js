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
* Descrizione della _classe_
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
* Descrizione di speaker
* 
* @property -speaker
* @type {User}
* @private 
*
*/

/**
* Descrizione di caller
* 
* @property -caller
* @type {boolean}
* @private 
*
*/

/**
* Descrizione di startDate
* 
* @property -startDate
* @type {string}
* @private 
*
*/

/**
* Descrizione di duration
* 
* @property -duration
* @type {number}
* @private 
*
*/

/**
* Descrizione di byteSent
* 
* @property -byteSent
* @type {number}
* @private 
*
*/

/**
* Descrizione di byteReceived
* 
* @property -byteReceived
* @type {number}
* @private 
*
*/