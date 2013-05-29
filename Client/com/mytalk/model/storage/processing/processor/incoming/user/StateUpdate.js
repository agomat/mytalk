MyTalk.processor.StateUpdate = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct, {
  name: 'StateUpdate',
  process: function(ari) {
    var info = JSON.parse(ari.info);
  	var username = info.user.username;
  	var status = info.user.online;
    var myUsername = MyTalk.PersonalData.find(0).get('username');
    if (myUsername && myUsername != username) {

//{"user":{"id":2,"username":"user1","name":"Marco","surname":"De Marchi","md5":"emailhash123123123","ip":"127.0.0.1:36014","online":true}}

  var store = DS.get("defaultStore");
  store.loadMany(MyTalk.User, info);
  var adapter = store.adapterForType(MyTalk.User);
  adapter.didFindMany(store, MyTalk.User, info);


    }

  },

  getProcessorName: function () {
    return this.get('name');
  } 

});