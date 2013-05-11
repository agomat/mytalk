MyTalk.List = DS.Model.extend({
  name: DS.attr('string'),
  users: DS.hasMany('MyTalk.Users'),
  
  getObject: function() { // di dubbia utilità
    return this.get('users');
  },

  blackList: function() {
  return this.get('name') === 'blacklist';
  }.property('name'),

  generalList: function() {
  return this.get('name') === 'generale';
  }.property('name'),

  otherList: function() {
  if(this.get('name') != 'generale' && this.get('name') != 'blacklist'){
    return true;
  }
  else return false;
  
  }.property('name'),


  
});