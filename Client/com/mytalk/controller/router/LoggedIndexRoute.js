MyTalk.LoggedIndexRoute = Ember.Route.extend({
  redirect: function() {
    globalList = MyTalk.List.find(0);
    this.replaceWith('list', globalList);
  }
});