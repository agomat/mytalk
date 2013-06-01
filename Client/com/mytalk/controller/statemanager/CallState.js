MyTalk.CallState = Ember.StateManager.create({
  
  enableLogging: true,
  initialState: "isNotBusy",

  beingBusy: function (manager, context) { //TODO spostare in isNotBusy
    manager.isBusy.set('RTCmanager',context.RTCmanager);
    manager.isBusy.set('RTCinfo',context.RTCinfo);
    manager.transitionTo( context.path );
  },

  isNotBusy: Ember.State.create({
    enter: function () {
     
    },
  }),

  isBusy: Ember.State.create({
    RTCmanager: null,
    RTCinfo: null,

    enter: function () {
        MyTalk.Router.router.transitionTo('calling', MyTalk.User.find(this.get('RTCinfo').speakerUserId) );
    },
    
    beingFree: function (manager, context) { //TODO vedere se spostare nei sottostati
      manager.isBusy.set('RTCmanager',null);
      manager.isBusy.set('RTCinfo',null);
      manager.transitionTo( 'isNotBusy' );
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
      enter: function (manager) {
        var RTCmanager = manager.isBusy.get('RTCmanager');
        if ( RTCmanager ) {
          RTCmanager.setSDP( RTCinfo.sdp );
          console.debug("ADD SDP "+ JSON.stringify(RTCinfo.sdp) ); 
          
          for(var i=0; i<RTCinfo.ice.length; ++i) { //TODO possibilità di delegare il ciclo for
           RTCmanager.addICE( RTCinfo.ice[i] );
           console.debug("ADD ICE "+ JSON.stringify(RTCinfo.ice[i]) );
          }
        }
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