MyTalk.LoggedIndexRoute = Ember.Route.extend({
  redirect: function() {
    globalList = this.modelFor('logged').toArray()[1];
    this.replaceWith('list', globalList);
  }
});