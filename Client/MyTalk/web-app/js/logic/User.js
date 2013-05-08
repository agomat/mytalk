MyTalk.User= Ember.Object.extend({

  username: null,
  online: false,

  getUsername: function() {
    return this.get('username');
  },
  
  getState: function() {
    return this.get('online');
  },

  setUsername: function(name) {
    this.set('username', name);
  },

  setOnline: function(bool) {
    this.set('online', bool);
  },

  setAll: function(name, mail, bool) {
    this.setUsername(name);
    this.setEmail(mail);
    this.setOnline(bool);
  },
  
  save: function() {
     MyTalk.WUser.createRecord({id:this.getUsername(),user:this}).get('transaction').commit();
     return this;
  }
  
});
