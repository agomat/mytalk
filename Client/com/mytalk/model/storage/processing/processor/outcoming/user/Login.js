MyTalk.processor.Login = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct, {
  name: 'Login',

  process: function (params) {
  	var record = MyTalk.Authentication.createRecord( params );
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
    
    var wasSent = socket.send( JSON.stringify(ARI) );
  	onSent( this.getProcessorName(), wasSent );
  },

  getProcessorName: function () {
  	return this.get('name');
  } 
});