/**
* Filename: List.js
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

MyTalk.List = DS.Model.extend({
  name: DS.attr('string'),
  users: DS.hasMany('MyTalk.User'),

  blackList: Ember.computed.equal('name','BlackList'),
  generalList: Ember.computed.equal('name','Tutti i contatti'),
  notBlacklist: Ember.computed.not('blackList'),
  notGeneralList: Ember.computed.not('generalList'),
  customList: Ember.computed.and('notBlacklist','notGeneralList'),

  online: function(){
    var users = this.get('users');
    var sum = 0;
    users.forEach(function(user){
      sum = sum + user.get('online');
    });
    return sum;
  }.property('users.@each.online'),

  total: function(){
    var users = this.get('users');
    var sum = 0;
    users.forEach(function(user){
      sum = sum + 1;
    });
    return sum;
  }.property('users.@each.id')


});


/**
* Descrizione di name
* 
* @property -name
* @type {String}
*
*/

/**
* Descrizione di users
* 
* @property -users
* @type {Array<User>}
*
*/

/**
* Descrizione di blackList
* 
* @property -blackList
* @type {Boolean}
*
*/

/**
* Descrizione di generalList
* 
* @property -generalList
* @type {Boolean}
*
*/

/**
* Descrizione di notBlacklist
* 
* @property -notBlacklist
* @type {Boolean}
*
*/

/**
* Descrizione di notGeneralList
* 
* @property -notGeneralList
* @type {Boolean}
*
*/

/**
* Descrizione di customList
* 
* @property -customList
* @type {Boolean}
*
*/

/**
* Descrizione di online
* 
* @property -online
* @type {number}
* @return {String}
*
*/

/**
* Descrizione di total
* 
* @property -total
* @type {number}
* @return {Void}
*
*/