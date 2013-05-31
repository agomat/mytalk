MyTalk.StateManager = Ember.StateManager.create({
  
  enableLogging: true,

  initialState: "isNotAuthenticated",

  isAuthenticated: Ember.State.create({
    enter: function () {
     
    },
    logout: function (manager, context) {
      manager.transitionTo('isNotAuthenticated');
    }
  }),

  isNotAuthenticated: Ember.State.create({
    enter: function () {
    
    },
    login: function (manager, context) {
      manager.transitionTo('isAuthenticated');
      MyTalk.Router.router.transitionTo('logged.index');
    }
  }),

  isBusy: Ember.State.create({

    campoDatoStrutturato: "ciao",
        /* 
            statistics -> {duration: int sentBytes: int ,receivedBytes: int}
            RTCinfo ->  {
                          myIP: ('string'),
                          myUserId: ('string'),
                          myRTCinfo: ('JSON di candidati e SDP'),
                          speakerIp: ('string'),
                          speakerUserId: ('string'),
                          speakerRTCinfo: ('JSON di candidati e SDP')
                        }
        */

    enter: function () {
     
    },
    free: function (manager, context) {
      manager.transitionTo('isNotBusy');
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