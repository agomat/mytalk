MyTalk.processor.Login = Ember.Object.extend(MyTalk.AbstractInProcessorProduct, {
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
    
    // TODO: usare seriamente la classe ARI
    var ARI = new Object();
    ARI.auth = null;
    ARI.req = this.get('name');
    var credenzials = new Object();
    credenzials.username = record.get('username');
    credenzials.password = record.get('password');
    ARI.info = JSON.stringify(credenzials);
    
    var wasSent = socket.send( JSON.stringify(ARI) );
  	onSent( this.getProcessorName(), wasSent );
  },

  getProcessorName: function () {
  	return this.get('name');
  } 
});