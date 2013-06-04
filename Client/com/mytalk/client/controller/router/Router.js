MyTalk.Router.map(function() {
  this.resource('index', { path: '/' }, function() {
    this.resource('guest', { path: '/' });
    this.resource('ipcall', { path: '/ip' });
    this.resource('call', { path: '/calling' } , function() {
      this.resource('calling', { path: '/:call_id' });
    });
    this.resource('logged', { path: '/lists' } , function() {
      this.resource('list', { path: '/:list_id' });
    });
    
  });
});
