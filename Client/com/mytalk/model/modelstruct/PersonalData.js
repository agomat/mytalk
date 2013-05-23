MyTalk.PersonalData = DS.Model.extend({
  username: DS.attr('string'),
  password: DS.attr('string'),
  name: DS.attr('string'),
  surname: DS.attr('string'),
  email: DS.attr('string'),
  md5: DS.attr('string'),
  ip: DS.attr('string'),

  avatarTiny: function() {
    return 'http://www.gravatar.com/avatar/' + this.get('md5') + '?s=48&d=identicon';
  }.property('md5'),
  /*
  potrebbe essere utile quando la chiamata viene inizializzata
  avatarBig: function() {
    return 'http://www.gravatar.com/avatar/' + this.get('md5') + '?s=300&d=identicon';
  }.property('md5')
  */
});
