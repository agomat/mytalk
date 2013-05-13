MyTalk.List = DS.Model.extend({
  name: DS.attr('string'),
  users: DS.hasMany('MyTalk.User', {inverse: 'user'}),
  
  getObject: function() { // di dubbia utilità
    return this.get('users');
  },

  blackList: function() {
    if (this.get('name') === 'blacklist') {
      return true;
    }
    else {
      return false;
    }
  }.property('name'),

  generalList: function() {
    if (this.get('name') === 'generale') {
      return true;
    }
    else {
      return false;
    }
  }.property('name'),

  customList: function() {
    if(this.get('name') != 'generale' && this.get('name') != 'blacklist') {
      return true;
    }
    else {
      return false;
    }
  }.property('name'),

  online: function(){
    var users = this.get("users");
    var sum = 0;
    users.forEach(function(user){
      sum += user.get("online");
    });
    return sum;
  }.property("users.@each.online")

  
});