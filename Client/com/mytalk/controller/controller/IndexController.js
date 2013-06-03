MyTalk.IndexController = Ember.ObjectController.extend({
  appStateBinding: Ember.Binding.oneWay('MyTalk.AppState.currentState.name'),
  appState: null,

  isAuthenticated: function () {
    return (this.get('appState') == 'isAuthenticated');
  }.property('appState'),

  login: function (username, password) {
    
    var ProcessorFactory = MyTalk.ProcessorFactory.create({});
    var processor = ProcessorFactory.createProcessorProduct( "Login" );
    processor.process({
      username: username,
      password: password
    });

  },

  register:function(name, surname, username, email, password1, password2){
    console.log('controller register: '+name +" "+ surname +" "+ username +" "+ email +" "+ password1 +" "+ password2);
    // TODO
  },

  ipCall:function(ip) {
    console.log('controller ipCAll: '+ip);
    // TODO
  }

});