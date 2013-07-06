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
* Model per la rappresentazione degli utenti generici registrati al sistema
*/

MyTalk.User = DS.Model.extend( {
  username: DS.attr('string'),
  lists: DS.hasMany('MyTalk.List'),
  name: DS.attr('string'),
  surname: DS.attr('string'),
  md5: DS.attr('string'),
  ip: DS.attr('string'),
  online: DS.attr('boolean'),
  unmatched: DS.attr('boolean'),

  fullName: function() {
    return this.get('name') + " " + this.get('surname');
  }.property('name','surname'),

  fullNameConc: function() {
    return this.get('name').toLowerCase() + ' ' + this.get('surname').toLowerCase() + ' ' + this.get('username').toLowerCase() + "_" + this.get('id');
  }.property('name','surname','username'),

  avatarSuperTiny: function() {
    return 'http://www.gravatar.com/avatar/' + this.get('md5') + '?s=35&d=blank';
  }.property('md5')

  avatarTiny: function() {
    return 'http://www.gravatar.com/avatar/' + this.get('md5') + '?s=48&d=blank';
  }.property('md5'),

  avatarBig: function() {
    return 'http://www.gravatar.com/avatar/' + this.get('md5') + '?s=300&d=blank';
  }.property('md5'),

});


/**
* Campo dati per memorizzare l'username del contatto
* 
* @property -username
* @type {string}
*
*/

/**
* Campo dati per memorizzare il nome del contatto
* 
* @property -name
* @type {string}
*
*/

/**
* Campo dati per memorizzare il cognome del contatto
* 
* @property -surname
* @type {string}
*
*/

/**
* Campo dati per memorizzare l'hash MD5 dell'email del contatto
* 
* @property -md5
* @type {string}
*
*/

/**
* Campo dati per memorizzare l'indirizzo IP del contatto
* 
* @property -ip
* @type {string}
*
*/

/**
* Valore booleano per identificare se il contatto è online oppure offline
* 
* @property -online
* @type {boolean}
*
*/

/**
* Proprietà che determina se il contatto appartiene al filtro della ricerca che l'utente sta eseguendo
* 
* @property -unmatched
* @type {boolean}
*
*/

/**
* Proprietà calcolata da {\color{blue}\href{http://emberjs.com/api/classes/Ember.ComputedProperty.html}{Ember.ComputedProperty}} come concatenazione di _name_, _surname_, _username_ la quale potrà essere usata come stringa per la ricerca dei contatti nella lista
* @property -fullName
* @type {String}
*
*/

/**
* Proprietà calcolata da {\color{blue}\href{http://emberjs.com/api/classes/Ember.ComputedProperty.html}{Ember.ComputedProperty}} come concatenazione di _name_, _surname_ per poter evitare la concatenazione a livello di template (Handlebars)
* 
* @property -fullNameConc
* @type {string}
*
*/

/**
* Proprietà calcolata da {\color{blue}\href{http://emberjs.com/api/classes/Ember.ComputedProperty.html}{Ember.ComputedProperty}} che combina la URI di Gravatar con l'hash MD5 del campo _md5_ al fine di ottenere la URL dell'avatar del contatto in formato molto piccolo
*
* @property -avatarSuperTiny
* @type {string}
*
*/

/**
* Proprietà calcolata da {\color{blue}\href{http://emberjs.com/api/classes/Ember.ComputedProperty.html}{Ember.ComputedProperty}} che combina la URI di Gravatar con l'hash MD5 del campo _md5_ al fine di ottenere la URL dell'avatar del contatto in formato piccolo
*
* @property -avatarTiny
* @type {string}
*
*/

/**
* Proprietà calcolata da {\color{blue}\href{http://emberjs.com/api/classes/Ember.ComputedProperty.html}{Ember.ComputedProperty}} che combina la URI di Gravatar con l'hash MD5 del campo _md5_ al fine di ottenere la URL dell'avatar del contatto in formato grande
* 
* @property -avatarBig
* @type {string}
*
*/

