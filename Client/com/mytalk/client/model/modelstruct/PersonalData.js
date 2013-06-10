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
*
* Model per la rappresentazione dei dati personali dell'utente autenticato al sistema.
* Parte dei campi dati in esso definiti potranno essere modificato o aggiornato dall'utente.
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

/**
* Campo dati per memorizzare l'identificativo numerico dell'utente autenticato nel sistema
* 
* @property -userId
* @type {number}
*
*/

/**
* Campo dati per memorizzare l'username dell'utente autenticato nel sistema
* 
* @property -username
* @type {string}
*
*/

/**
* Campo dati per memorizzare la password dell'utente autenticato nel sistema
* 
* @property -password
* @type {string}
*
*/

/**
* Campo dati per memorizzare il nome dell'utente autenticato nel sistema
* 
* @property -name
* @type {string}
*
*/

/**
* Campo dati per memorizzare il cognome dell'utente autenticato nel sistema
* 
* @property -surname
* @type {string}
*
*/

/**
* Campo dati per memorizzare l'indirizzo email dell'utente autenticato nel sistema
* 
* @property -email
* @type {string}
*
*/

/**
* Campo dati per memorizzare l'indirizzo l'hash MD5 dell'email dell'utente autenticato nel sistema
* 
* @property -md5
* @type {string}
*
*/

/**
* Campo dati per memorizzare l'indirizzo l'indirizzo IP dell'utente autenticato nel sistema
* 
* @property -ip
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