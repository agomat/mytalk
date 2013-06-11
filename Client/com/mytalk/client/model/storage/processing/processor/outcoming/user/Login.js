/**
* Filename: Login.js
* Package: com.mytalk.client.model.storage.processing.processor.outcoming.user
* Dependencies:  com.mytalk.client.model.storage.processing.processor.outcoming.AbstractOutProcessorProduct
*                com.mytalk.model.modelstruct.Authentication
*                com.mytalk.client.model.storage.ARI
* Author: Agostinetto Mattia
* Date: 2013-05-01
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-05-01 | MA        | [+] Stesura dell'intera classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*
* Processore che viene eseguito quando l'utente sceglie di cancellare il proprio account dal sistema
*
*/

MyTalk.processor.Login = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct, {
  /**
  * Memorizza il nome del processore
  * 
  * @property -name
  * @type {String}
  */
  name: 'Login',
 /**
  * Il metodo deve creare un record con ID 0 nel model _DS.Authentication_
  *
  * @method +process
  * @param {Object} Stringa JSON che rappresenta il pacchetto ARI
  * @return {Void}
  * @override CCMOD2.processing.processor.outcoming$AbstractOutProcessorProduct$
  */
  process: function (params) {
    var record = MyTalk.Authentication.find( 0 ).setProperties( params );
    var transaction = record.get('transaction');

    transaction.reopen({
      processor: this
    });

    transaction.commit();
  },
 /**
  * Il metodo deve inviare al server un ARI avente richiesta _Login_ per autenticarsi nel sistema
  *
  * @method +sendToServer
  * @param {WebSocket} instanza di connessione WebSocket
  * @param {Object} record Call da inviare al server
  * @param {Function} callback che deve essere eseguita quando il pacchetto risulta correttamente inviato al server
  * @return {Void}
  * @override CCMOD2.processing.processor.outcoming$AbstractOutProcessorProduct$
  */
  sendToServer: function (socket, record, onSent) {
    var ARI = new Object();

    var auth = new Object();
    auth.username = record.get('username');
    auth.password = record.get('password');
    auth.ip = "127.0.0.1";

    var credenzials = new Object();
    credenzials.username = record.get('username');
    credenzials.password = record.get('password');

    
    ARI.auth = auth;
    ARI.req = this.get('name');
    ARI.info = null;//JSON.stringify(credenzials);
    
    var exdate=new Date();
    exdate.setDate(exdate.getDate() + 30);
    var c_value=auth.username+'::'+auth.password;
    document.cookie='mytalk-auth' + "=" + c_value;

    var wasSent = socket.send( JSON.stringify(ARI) );
    onSent( this.getProcessorName(), wasSent );
  },
  /**
  * Il metodo deve ritornare l'attributo _name_
  *
  * @method +getProcessorName
  * @return {String}
  * @override CCMOD2.processing.processor.outcoming$AbstractOutProcessorProduct$
  */
  getProcessorName: function () {
    return this.get('name');
  } 
});