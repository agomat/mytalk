MyTalk.PersonalData = DS.Model.extend({
  username: DS.attr('string'),
  password: DS.attr('string'),
  
  getObject: function() {
    return { username:this.get('username'), password:this.get('password') };
  }

});
