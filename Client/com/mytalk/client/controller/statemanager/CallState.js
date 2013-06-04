MyTalk.CallState = Ember.StateManager.create({

  enableLogging: true,
  initialState: 'isNotBusy',

  isNotBusy: Ember.State.create({
    enter: function () {
     
    },
    beingBusy: function (manager, context) {
      var lastContext = manager.isBusy.get('callData');
      context.reopen( lastContext ); 
      manager.isBusy.set('callData', context);
      manager.transitionTo( context.get('path') );
    },
  }),

  isBusy: Ember.State.create({
    callData: Ember.Object.create({}),
    
    beingConnected: function (manager, context) {
      var lastContext = manager.isBusy.get('callData');
      context.reopen( lastContext ); 
      manager.isBusy.set('callData', context);
      manager.transitionTo( 'isConnected' );
    },

    beingFree: function (manager) {
      manager.isBusy.set('callData', Ember.Object.create({}));
      manager.transitionTo( 'isNotBusy' );
    },

    outcomingCall: Ember.State.create({
      enter: function (manager) {
        MyTalk.Router.router.transitionTo('calling', manager.isBusy.get('callData').speaker );
      }

    }),
    
    incomingCall: Ember.State.create({
      enter: function (manager) {
        MyTalk.Router.router.transitionTo('calling', manager.isBusy.get('callData').speaker );
      },

    }),

    isConnected: Ember.State.create({
      enter: function (manager) {
        var callData = manager.isBusy.get('callData');
        
        if ( callData.isCaller ) {
          var RTCmanager = callData.RTCmanager;
          var RTCinfo = callData.RTCinfo;
        
          RTCmanager.setSDP( RTCinfo.sdp );
          for(var i=0; i<RTCinfo.ice.length; ++i) { //TODO possibilità di delegare il ciclo for
           RTCmanager.addICE( RTCinfo.ice[i] );
          }
        }
      },

    })

  })

});