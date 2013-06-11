/**
* Filename: ListUserAdd.js
* Package: com.mytalk.client.model.storage.processing.processor.outcoming.list
* Dependencies:  com.mytalk.client.model.storage.processing.processor.outcoming.AbstractOutProcessorProduct
*                com.mytalk.client.model.modelstruct.User
*                com.mytalk.client.model.modelstruct.Authentication
*                com.mytalk.client.model.modelstruct.PersonalData
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
*/

MyTalk.processor.ListUserAdd = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct,{
  name: 'ListUserAdd',

  process: function (params) {
    var record = params.list;
    var transaction = record.get('transaction');
    record.get('users').addObject( MyTalk.User.find(params.userId) );

    transaction.reopen({
      processor: this,
      userId: params.userId
    });

    transaction.commit();
  },

  sendToServer: function (socket, record, onSent) {
    var ARI = new Object();

    var auth = new Object();
    auth.username = MyTalk.Authentication.find(0).get('username');
    auth.password = MyTalk.Authentication.find(0).get('password');
    auth.ip = MyTalk.PersonalData.find(0).get('ip');

    ARI.auth = auth;
    ARI.req = this.get('name');
    ARI.info = new Object();

     var inner = new Object();
     inner.id = record.get('id');
     inner.name = record.get('name');
     inner.list = [record.get('transaction').get('userId')];

     ARI.info.list = [];
     ARI.info.list.pushObject(inner);

    
    ARI.info = JSON.stringify( ARI.info );

    var wasSent = socket.send( JSON.stringify(ARI) );
    onSent( this.getProcessorName(), wasSent );
  },

  getProcessorName: function () {
    return this.get('name');
  } 
});