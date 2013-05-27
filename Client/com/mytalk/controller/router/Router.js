MyTalk.Router.map(function() {
  this.resource('index', { path: '/' }, function() {
    this.resource('guest', { path: '/' });
    this.resource('call', { path:'/call' });
    this.resource('logged', { path: '/lists' } , function() {
      this.resource('list', { path: '/:list_id' });
    });
    
  });
});