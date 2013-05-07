MyTalk.PersonalData = DS.Model.extend({
  username: DS.attr('string'),
  password: DS.attr('string'),
  nome: DS.attr('string'),
  cognome: DS.attr('string'),
  email: DS.attr('string'),
  
  getObject: function() { // di dubbia utilità
    return { username:this.get('username'), password:this.get('password') };
  },

  gravatar: function() {
    var email = this.get('email');
    return 'http://www.gravatar.com/avatar/' + MD5(email);
  }.property('email')

});
