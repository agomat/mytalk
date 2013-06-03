/**
* Filename: User.js
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

MyTalk.User = DS.Model.extend(MyTalk.DryHelper, {
  username: DS.attr('string'),
  lists: DS.hasMany('MyTalk.List'),
  name: DS.attr('string'),
  surname: DS.attr('string'),
  md5: DS.attr('string'),
  ip: DS.attr('string'),
  online: DS.attr('boolean'),
  unmatched: DS.attr('boolean'), // TODO: per mattia: tenere questa proprietà offline

});


