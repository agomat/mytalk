MyTalk.processor.Login = Ember.Object.extend({
  processRequest: function (record, socket_deprecated, onSuccess, onError) {
    var ARI = new Object();
    ARI.auth = null;
    ARI.req = "Login";
    var credenzials = new Object();
    credenzials.username = record.get('username');
    credenzials.password = record.get('password');
    ARI.info = JSON.stringify(credenzials);
    socket_deprecated.send(JSON.stringify(ARI));
  	if (true) onSuccess(); else onError();
  }
});