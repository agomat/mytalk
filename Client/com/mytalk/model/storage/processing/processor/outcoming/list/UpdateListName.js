MyTalk.processor.UpdateListName = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct,{
  name: 'UpdateListName',

  process: function (params) {
    var record = MyTalk.List.find(params.listId);
    var transaction = record.get('transaction');
    
    record.set('name', params.newName);
    transaction.reopen({
      processor: this,
      oldListName: params.oldName
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
     ARI.info.listName = record.get('transaction').get('oldListName');
     ARI.info.owner = MyTalk.PersonalData.find(0).get('username');
     ARI.info.newListName = record.get('name');
    
    ARI.info = JSON.stringify( ARI.info );

    var wasSent = socket.send( JSON.stringify(ARI) );
  	onSent( this.getProcessorName(), wasSent );
  },

  getProcessorName: function () {
  	return this.get('name');
  } 
});