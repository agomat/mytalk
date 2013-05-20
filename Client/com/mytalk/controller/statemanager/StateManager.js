MyTalk.StateManager = Ember.StateManager.create({
  initialState: "isNotAuthenticated",
  isAuthenticated: Ember.State.create({
    enter: function () {
      console.log("[StateManager] enter " + this.name);
    },
    logout: function (manager, context) {
      manager.transitionTo('isNotAuthenticated');
    }
  }),
  isNotAuthenticated: Ember.State.create({
    enter: function () {
      console.log("[StateManager] enter " + this.name);
    },
    login: function (manager, credentials) {
      manager.transitionTo('isAuthenticated');
      MyTalk.Router.router.transitionTo('logged.index');
    }
  }),
  isBusy: Ember.State.create({
    enter: function () {
      console.log("[StateManager] enter " + this.name);
    },
    free: function (manager, context) {
      manager.transitionTo('isNotBusy');
    }
  }),
  isNotBusy: Ember.State.create({
    enter: function () {
      console.log("[StateManager] enter " + this.name);
    },
    busy: function (manager, credentials) {
      manager.transitionTo('isBusy');
    }
  })
});