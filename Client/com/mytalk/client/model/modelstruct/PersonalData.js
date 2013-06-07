/**
* Filename: PersonalData.js
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
*/

MyTalk.PersonalData = DS.Model.extend({
  userId: DS.attr('number'),
  username: DS.attr('string'),
  password: DS.attr('string'),
  name: DS.attr('string'),
  surname: DS.attr('string'),
  email: DS.attr('string'),
  md5: DS.attr('string'),
  ip: DS.attr('string'),

  avatarTiny: function() {
    return 'http://www.gravatar.com/avatar/' + this.get('md5') + '?s=48&d=identicon';
  }.property('md5'),

});