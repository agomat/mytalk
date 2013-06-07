/**
* Filename: BlackListAdd.js
* Package: com.mytalk.client.model.storage.processing.processor.outcoming.communication
* Dependencies:  com.mytalk.client.model.storage.processing.processor.outcoming.AbstractOutProcessorProduct
*                com.mytalk.client.model.modelstruct.List
*                com.mytalk.client.model.modelstruct.Authentication
*                com.mytalk.client.model.modelstruct.User
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

MyTalk.processor.BlackListAdd = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct,{
  name: 'BlackListAdd',

  process: function (params) {
    var blacklist = MyTalk.List.find(1);
    blacklist.get('users').addObject(MyTalk.User.find(params.userId));
    var transaction = blacklist.get('transaction');

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
     inner.id = 0; 
     inner.name = "Blacklist";
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
