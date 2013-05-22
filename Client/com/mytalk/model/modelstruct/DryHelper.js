MyTalk.DryHelper = Ember.Mixin.create({

  fullName: function() {
    return this.get('name') + " " + this.get('surname');
  }.property('name','surname'),

  avatarTiny: function() {
    return 'http://www.gravatar.com/avatar/' + this.get('md5') + '?s=48&d=blank';
  }.property('md5'),

  avatarBig: function() {
    return 'http://www.gravatar.com/avatar/' + this.get('md5') + '?s=300&d=blank';
  }.property('md5')
  
});