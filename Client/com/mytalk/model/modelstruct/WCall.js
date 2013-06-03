/**
* Filename: WCall.js
* Package: com.mytalk.model.modelstruct
* Dependencies: 
* Author: Mattia Agostinetto
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
*/

MyTalk.WCall = DS.Model.extend({
  list: DS.hasMany('MyTalk.Call'),
  totalByteSent: DS.attr('integer'),
  totalByteReceived: DS.attr('integer'),
  totalDuration: DS.attr('integer')
  
}); 
