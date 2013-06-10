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
* Descrizione della _classe_
*/

MyTalk.WCall = DS.Model.extend({
  list: DS.hasMany('MyTalk.Call'),
  totalByteSent: DS.attr('integer'),
  totalByteReceived: DS.attr('integer'),
  totalDuration: DS.attr('integer')
  
});

/**
* Descrizione di list
* 
* @property -list
* @type {Array<Call>}
* @private 
*
*/

/**
* Descrizione di totalByteSent
* 
* @property -totalByteSent
* @type {number}
* @private 
*
*/

/**
* Descrizione di totalByteReceived
* 
* @property -totalByteReceived
* @type {number}
* @private 
*
*/

/**
* Descrizione di totalDuration
* 
* @property -totalDuration
* @type {number}
* @private 
*
*/
