/**
* Filename: User.js
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
*
* Descrizione della _classe_ 
*/

MyTalk.User = DS.Model.extend( {
  username: DS.attr('string'),
  lists: DS.hasMany('MyTalk.List'),
  name: DS.attr('string'),
  surname: DS.attr('string'),
  md5: DS.attr('string'),
  ip: DS.attr('string'),
  online: DS.attr('boolean'),
  unmatched: DS.attr('boolean'), // TODO: per mattia: tenere questa proprietà offline

  fullName: function() {
    return this.get('name') + " " + this.get('surname');
  }.property('name','surname'),

  fullNameConc: function() {
    return this.get('name').toLowerCase() + this.get('surname').toLowerCase() + this.get('username').toLowerCase() + "_" + this.get('id');
  }.property('name','surname','username'),

  avatarTiny: function() {
    return 'http://www.gravatar.com/avatar/' + this.get('md5') + '?s=48&d=blank';
  }.property('md5'),

  avatarBig: function() {
    return 'http://www.gravatar.com/avatar/' + this.get('md5') + '?s=300&d=blank';
  }.property('md5')

});


/**
* Descrizione di username
* 
* @property -username
* @type {string}
*
*/

/**
* Descrizione di lists
* 
* @property -lists
* @type {Array<List>}
*
*/

/**
* Descrizione di name
* 
* @property -name
* @type {string}
*
*/

/**
* Descrizione di surname
* 
* @property -surname
* @type {string}
*
*/

/**
* Descrizione di md5
* 
* @property -md5
* @type {string}
*
*/

/**
* Descrizione di ip
* 
* @property -ip
* @type {string}
*
*/

/**
* Descrizione di online
* 
* @property -online
* @type {boolean}
*
*/

/**
* Descrizione di unmatched
* 
* @property -unmatched
* @type {boolean}
*
*/

/**
* Descrizione di fullName
* 
* @property -fullName
* @return {string}
*
*/

/**
* Descrizione di fullNameConc
* 
* @property -fullNameConc
* @return {string}
*
*/

/**
* Descrizione di avatarTiny
* 
* @property -avatarTiny
* @return {string}
*
*/

/**
* Descrizione di avatarBig
* 
* @property -avatarBig
* @return {string}
*
*/

