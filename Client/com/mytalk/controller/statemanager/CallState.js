MyTalk.CallState = Ember.StateManager.create({
  
  enableLogging: true,
  initialState: "isNotBusy",

  beingBusy: function (manager, context) { //TODO spostare in isNotBusy
    manager.isBusy.set('callInfo',context);
    manager.transitionTo( context.path );
  },

  isNotBusy: Ember.State.create({
    enter: function () {
     
    },
  }),

  isBusy: Ember.State.create({
    callInfo: null,

    enter: function () {
        MyTalk.Router.router.transitionTo('calling', MyTalk.User.find(this.get('callInfo').RTCinfo.speakerUserId) );
    },
    
    outcomingCall: Ember.State.create({
      enter: function () {
        
      },
      free: function (manager, context) {
        manager.transitionTo('isNotBusy');
      }
    }),
    
    incomingCall: Ember.State.create({
      enter: function () {

      },
      free: function (manager, context) {
        manager.transitionTo('isNotBusy');
      }
    }),

    isConnected: Ember.State.create({
      enter: function () {
        
      },
      free: function (manager, context) {
        manager.transitionTo('isNotBusy');
      }
    }),
  }),

  isNotBusy: Ember.State.create({
    enter: function () {
      
    },
    busy: function (manager, credentials) {
      manager.transitionTo('isBusy');
    }
  })

});