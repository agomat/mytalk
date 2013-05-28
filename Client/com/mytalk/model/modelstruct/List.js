MyTalk.List = DS.Model.extend({
  name: DS.attr('string'),
  users: DS.hasMany('MyTalk.User'),

  blackList: Ember.computed.equal('name','BlackList'),
  generalList: Ember.computed.equal('name','Tutti i contatti'),
  notBlacklist: Ember.computed.not('blackList'),
  notGeneralList: Ember.computed.not('generalList'),
  customList: Ember.computed.and('notBlacklist','notGeneralList'),

  online: function(){
    var users = this.get('users');
    var sum = 0;
    users.forEach(function(user){
      sum = sum + user.get('online');
    });
    return sum;
  }.property('users.@each.online'),

  total: function(){
    var users = this.get('users');
    var sum = 0;
    users.forEach(function(user){
      sum = sum + 1;
    });
    return sum;
  }.property('users.@each.id')


});


