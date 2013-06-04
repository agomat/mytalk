MyTalk.AppState = Ember.StateManager.create({
  
  enableLogging: true,
  initialState: "isNotAuthenticated",

  isAuthenticated: Ember.State.create({
    beingNotAuthenticated: function (manager, context) {
      manager.transitionTo('isNotAuthenticated');
    },
  }),

  isNotAuthenticated: Ember.State.create({
    beingAuthenticated: function (manager, context) {
      manager.transitionTo('isAuthenticated');
      MyTalk.Router.router.transitionTo('logged.index');
    }
  })

});