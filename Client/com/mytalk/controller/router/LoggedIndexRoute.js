MyTalk.LoggedIndexRoute = Ember.Route.extend({
  redirect: function() {
    this.replaceWith('list', MyTalk.List.find(0));
  }
});