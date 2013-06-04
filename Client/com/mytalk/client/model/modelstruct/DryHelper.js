/**
* Filename: DryHelper.js
* Package: com.mytalk.client.model.modelstruct
* Dependencies: 
* Author: Mattia Agostinetto
* Date: 2013-04-20
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-04-20 | MA        | [+] Scrittura mix DRY (Don't Repeat Yourself)
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

MyTalk.DryHelper = Ember.Mixin.create({

  fullName: function() {
    return this.get('name') + " " + this.get('surname');
  }.property('name','surname'),

  fullNameConc: function() {
    return this.get('name').toLowerCase() + this.get('surname').toLowerCase() + "_" + this.get('id');
  }.property('name','surname'),

  avatarTiny: function() {
    return 'http://www.gravatar.com/avatar/' + this.get('md5') + '?s=48&d=blank';
  }.property('md5'),

  avatarBig: function() {
    return 'http://www.gravatar.com/avatar/' + this.get('md5') + '?s=300&d=blank';
  }.property('md5')
  
});