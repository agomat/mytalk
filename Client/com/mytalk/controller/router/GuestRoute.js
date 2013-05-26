MyTalk.GuestRoute = Ember.Route.extend({
  renderTemplate: function() {
    this.render('guest', { into: 'index', outlet: 'content' });
  },
  model: function() {
    var store = DS.get("defaultStore");
    var adapter = store.adapterForType(MyTalk.PersonalData);
    var json = {"personal_data": { id:0, "ip":"your IP here"}};
    store.load(MyTalk.PersonalData, json);
    adapter.didFindRecord(store, MyTalk.PersonalData, json,0);  
    return MyTalk.PersonalData.find(0);
  }
});