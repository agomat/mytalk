MyTalk.IpcallRoute = Ember.Route.extend({
  renderTemplate: function() {
    this.render('ipcall', { into: 'index', outlet: 'content' });
  },
  model: function() {
    return MyTalk.PersonalData.find(0);
  }
}); 
