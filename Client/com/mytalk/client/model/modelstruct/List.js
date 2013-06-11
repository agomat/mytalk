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
* Model per la rappresentazione di una lista personale dell'utente autenticato al sistema
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
* Contiene il nome della lista
* 
* @property -name
* @type {String}
*
*/

/**
* Lista di riferimenti di $User$ che rappresentano i contatti che l'utente autenticato ha aggiunto alla lista 
* 
* @property -users
* @type {Array<User>}
*
*/

/**
* Valore booleano generato da {\color{blue}\href{http://emberjs.com/api/classes/Ember.ComputedProperty.html}{Ember.ComputedProperty}}; $true$ la lista è la $BlackList$, $false$ altrimenti
* 
* @property -blackList
* @type {Boolean}
*
*/

/**
* Valore booleano generato da {\color{blue}\href{http://emberjs.com/api/classes/Ember.ComputedProperty.html}{Ember.ComputedProperty}}; $true$ la lista è la lista $Tutti i contatti$, $false$ altrimenti
* 
* @property -generalList
* @type {Boolean}
*
*/

/**
* Valore booleano generato da {\color{blue}\href{http://emberjs.com/api/classes/Ember.ComputedProperty.html}{Ember.ComputedProperty}}; $true$ la lista non è la $Blacklist$, $false$ altrimenti
* 
* @property -notBlacklist
* @type {Boolean}
*
*/

/**
* Valore booleano generato da {\color{blue}\href{http://emberjs.com/api/classes/Ember.ComputedProperty.html}{Ember.ComputedProperty}}; $true$ la lista non è la lista $Tutti i contatti$, $false$ altrimenti
* 
* @property -notGeneralList
* @type {Boolean}
*
*/

/**
* Valore booleano generato da {\color{blue}\href{http://emberjs.com/api/classes/Ember.ComputedProperty.html}{Ember.ComputedProperty}}; $true$ la lista è una lista personale, $false$ altrimenti
* 
* @property -customList
* @type {Boolean}
*
*/

/**
* Valore intero generato da {\color{blue}\href{http://emberjs.com/api/classes/Ember.ComputedProperty.html}{Ember.ComputedProperty}} per calcolare il numero degli utenti online della lista
* @property -online
* @type {number}
*
*/

/**
* Valore intero generato da {\color{blue}\href{http://emberjs.com/api/classes/Ember.ComputedProperty.html}{Ember.ComputedProperty}} per calcolare il totale degli utenti della lista
* 
* @property -total
* @type {number}
*
*/