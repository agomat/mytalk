/**
* Filename: Authentication.js
* Package: com.mytalk.client.model.modelstruct
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

MyTalk.Authentication = DS.Model.extend({
  username: DS.attr('string'),
  password: DS.attr('string'),
  ip: DS.attr('string')
  
});