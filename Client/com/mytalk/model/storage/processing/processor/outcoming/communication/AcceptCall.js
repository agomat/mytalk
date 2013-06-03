/**
* Filename: AcceptCall.js
* Package: com.mytalk.storage.processing.processor.outcoming.communication
* Dependencies:  com.mytalk.storage.processing.processor.outcoming.AbstractOutProcessorProduct
*                com.mytalk.model.modelstruct.Authentication
*                com.mytalk.model.modelstruct.Call
*                com.mytalk.model.modelstruct.PersonalData
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

MyTalk.processor.AcceptCall = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct,{
  name: 'AcceptCall',

  process: function (params) {
    var myself = MyTalk.PersonalData.find(0);
    var speaker = params.speaker;
    var RTCinfo = params.RTCinfo;

    var nextId = MyTalk.Call.find().get('length'); 
    var record = MyTalk.Call.createRecord({id: nextId, speaker: speaker, caller: false, startDate: new Date() });
    var transaction = record.get('transaction');

    transaction.reopen({
      processor: this,
      myIp: myself.get('ip'),
      myUserId: myself.get('userId'),
      speakerIp: speaker.get('ip'),
      speakerUserId: speaker.get('id'),
      RTCinfo: RTCinfo
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
     ARI.info.myIp = record.get('transaction').get('myIp');
     ARI.info.myUserId = record.get('transaction').get('myUserId');
     ARI.info.speakerIp = record.get('transaction').get('speakerIp');
     ARI.info.speakerUserId = record.get('transaction').get('speakerUserId');
     ARI.info.RTCinfo = record.get('transaction').get('RTCinfo');
    
    ARI.info = JSON.stringify( ARI.info );

    var wasSent = socket.send( JSON.stringify(ARI) );
    onSent( this.getProcessorName(), wasSent );
  },

  getProcessorName: function () {
    return this.get('name');
  } 

});