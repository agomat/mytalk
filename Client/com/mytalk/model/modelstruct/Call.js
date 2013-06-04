/**
* Filename: Call.js
* Package: com.mytalk.model.modelstruct
* Dependencies: com.mytalk.model.modelstruct.User
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

MyTalk.Call = DS.Model.extend({
  speaker: DS.belongsTo('MyTalk.User'),
  caller: DS.attr('boolean'),
  startDate: DS.attr('date'),
  duration: DS.attr('number'),
  byteSent: DS.attr('number'),
  byteReceived: DS.attr('number')
});