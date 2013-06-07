/**
* Filename: Login.js
* Package: com.mytalk.client.model.storage.processing.processor.outcoming.communication
* Dependencies:  com.mytalk.client.model.storage.processing.processor.outcoming.AbstractOutProcessorProduct
*                com.mytalk.model.modelstruct.Authentication
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
*/

MyTalk.processor.Login = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct, {
  name: 'Login',

  process: function (params) {
    var record = MyTalk.Authentication.find( 0 ).setProperties( params );
    var transaction = record.get('transaction');

    transaction.reopen({
      processor: this
    });

    transaction.commit();
  },

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

  getProcessorName: function () {
    return this.get('name');
  } 
});