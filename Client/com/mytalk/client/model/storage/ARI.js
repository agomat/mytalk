/**
* Filename: ARI.js
* Package: com.mytalk.client.model.storage
* Dependencies: 
* Author: Griggio Massimo
* Date: 2013-04-23
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-04-23 | MG        | [+] Scrittura classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*
* Definisce la struttura del pacchetto di comunicazione scambiato con il server.
*
*/

MyTalk.ARI = Ember.Object.extend({
  
  /**
   * Proprietà che contiene username e password dell'utente
   * @property -auth           
   * @type {String}                   
   *
  */

  auth: undefined,

  /**
   * Proprietà che contiene la richiesta da eseguire.
   * @property -req           
   * @type {String}                   
   *
  */

  req: undefined,

  /**
   * Proprietà che contiene le informazioni da scambiare col server necessarie per soddisfare la richiesta.
   * @property -info           
   * @type {String}                   
   *
  */

  info: undefined,

  /**
   * Questo metodo si attiva ogni qual volta viene creata un'istanza della classe $ARI$, tale metodo si occupa di impostare i campi dati della classe.
   * I campi dati $auth$ e $req$ vengono impostati rispettivamente con i parametri $a$ e $r$ passati al metodo, mentre per impostare il campo dati $info$ viene applicata la funzione JavaScript 
   * _JSON.stringify()_  al parametro $i$ passato al metodo; tale funzione è necessaria al fine di convertire il JSON, ( contente le informazioni scambiate con il server ), in stringa.
   *
   * @method -init
   * @param {String} $a$ è la stringa da impostare nel campo dati $auth$       
   * @param {String} $r$ è la stringa da impostare nel campo dati $req$
   * @param {Object} $i$ è l'oggetto JSON contenente le informazioni.                                 
   * @return {Void} 
  */
  
  init: function(a, r, i) {
    this.set('auth', a);
    this.set('req', r);
    this.set('info', JSON.stringify(i));
    this._super();
  },
  
  /**
   * Questo metodo si occupa ritornare il valore del campo dati $auth$ dell'istanza della classe in questione
   *
   * @method +getAuth                                 
   * @return {String} 
  */

  getAuth: function() {
    return this.get('auth');
  },
  
  /**
   * Questo metodo si occupa ritornare il valore del campo dati $req$ dell'istanza della classe in questione
   *
   * @method +getReq                                 
   * @return {String} 
  */

  getReq: function() {
    return this.get('req');
  },
  
  /**
   * Questo metodo si occupa ritornare il valore del campo dati $info$ dell'istanza della classe in questione
   *
   * @method +getInfo                                 
   * @return {String} 
  */

  getInfo: function() {
    return this.get('info');
  },

  /**
   * Questo metodo si occupa di impostare il valore del campo dati $auth$ dell'istanza della classe in questione
   *
   * @method -setAuth
   * @param {String} $a$ è la stringa da impostare nel campo dati $auth$                                 
   * @return {Void} 
  */

  setAuth: function(a) {
    this.set('auth', a);
  },
  
  /**
   * Questo metodo si occupa di impostare il valore del campo dati $req$ dell'istanza della classe in questione
   *
   * @method -setReq
   * @param {String} $r$ è la stringa da impostare nel campo dati $req$                                 
   * @return {Void} 
  */

  setReq: function(r) {
    this.set('req', r);
  },
  
  /**
   * Questo metodo si occupa di impostare il valore del campo dati $info$ dell'istanza della classe in questione.
   * Per impostare tale campo dati il metodo applica al parametro $i$ la funzione JavaScript _JSON.stringify()_ al fine di convertire l'oggetto $JSON$ scambiato col server in una stringa.
   *
   * @method -setInfo
   * @param {Object} $i$ è l'oggetto JSON contenente le informazioni.                                  
   * @return {Void} 
  */

  setInfo: function(i) {
    this.set('info', JSON.stringify(i));
  }
  
});
