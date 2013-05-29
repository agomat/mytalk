MyTalk.processor.StateUpdate = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct, {
  name: 'StateUpdate',
  process: function(ari) {
    var info = JSON.parse(ari.info);
    info.list = info.user;
    delete info.user;
  	var username = info.list.username;
  	var status = info.list.online;
    var myUsername = MyTalk.PersonalData.find(0).get('username');
    if (myUsername && myUsername != username) {
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