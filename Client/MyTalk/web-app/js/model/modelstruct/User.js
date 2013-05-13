MyTalk.User = DS.Model.extend({
  username: DS.attr('string'),
  lists: DS.belongsTo('MyTalk.List'),
  name: DS.attr('string'),
  surname: DS.attr('string'),
  MD5email: DS.attr('string'),
  ip: DS.attr('string'),
  online: DS.attr('boolean'),
  
  getObject: function() { // di dubbia utilità
    return { username:this.get('username'), password:this.get('online') };
  } 
 /*
  gravatar: function() {
    var email = this.get('email');
    return 'http://www.gravatar.com/avatar/' + MD5email;
  }.property('MD5email') */

});


