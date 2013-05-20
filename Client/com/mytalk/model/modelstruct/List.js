MyTalk.List = DS.Model.extend({
  name: DS.attr('string'),
  users: DS.hasMany('MyTalk.User'),
  
  getObject: function() { // di dubbia utilità
    return this.get('users');
  },

  blackList: function() {
    if (this.get('name') === 'Blacklist') {
      return true;
    }
    else {
      return false;
    }
  }.property('name'),

  generalList: function() {
    if (this.get('name') === 'Tutti i contatti') {
      return true;
    }
    else {
      return false;
    }
  }.property('name'),

  customList: function() {
    if(this.get('name') != 'Tutti i contatti' && this.get('name') != 'Blacklist' && this.get('name') != null) {
      return true;
    }
    else {
      return false;
    }
  }.property('name'),

  online: function(){
    var users = this.get('users');
    var sum = 0;
    users.forEach(function(user){
      sum += user.get('online');
    });
    return sum;
  }.property('users.@each.online'),

  total: function(){
    var users = this.get('users');
    var sum = 0;
    users.forEach(function(user){
      sum += 1;
    });
    return sum;
  }.property('users.@each.name'),
  
});


