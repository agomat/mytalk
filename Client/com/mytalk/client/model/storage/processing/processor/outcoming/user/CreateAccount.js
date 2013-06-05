/**
* Filename: CreateAccount.js
* Package: com.mytalk.client.model.storage.processing.processor.outcoming.communication
* Dependencies:  com.mytalk.client.model.storage.processing.processor.outcoming.AbstractOutProcessorProduct
* Author: Mattia Agostinetto
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

MyTalk.processor.CreateAccount = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct,{
  name: 'CreateAccount',

  process: function (params) {
    var record = MyTalk.PersonalData.find(0).setProperties(params);
    var transaction = record.get('transaction');

    transaction.reopen({
      processor: this
    });

    transaction.commit();
  },

  sendToServer: function (socket, record, onSent) {
    var ARI = new Object();

    ARI.auth = null;
    ARI.req = this.get('name');
    
    var info = new Object();
    info.worldPersonalData = new Object();
    info.worldPersonalData.pd = new Object();
    info.worldPersonalData.pd.username = record.get('username');
    info.worldPersonalData.pd.password = record.get('password');
    info.worldPersonalData.pd.name = record.get('name');
    info.worldPersonalData.pd.surname = record.get('surname');
    info.worldPersonalData.pd.email = record.get('email');
    
    ARI.info = JSON.stringify(info);
    
    var wasSent = socket.send( JSON.stringify(ARI) );
    onSent( this.getProcessorName(), wasSent );
  },

  getProcessorName: function () {
    return this.get('name');
  } 
});