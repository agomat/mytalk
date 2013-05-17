MyTalk.User = DS.Model.extend({
  username: DS.attr('string'),
  lists: DS.hasMany('MyTalk.List'),
  name: DS.attr('string'),
  surname: DS.attr('string'),
  md5: DS.attr('string'),
  ip: DS.attr('string'),
  online: DS.attr('boolean'),
  
  getObject: function() { // di dubbia utilità
    return { username:this.get('username'), password:this.get('online') };
  },
 
  avatarTiny: function() {
    return 'http://www.gravatar.com/avatar/' + this.get('md5') + '?s=48&d=blank';
  }.property('md5'),

  avatarBig: function() {
    return 'background-image: url(http://www.gravatar.com/avatar/' + this.get('md5') + '?s=300&d=blank)';
  }.property('md5')

});


