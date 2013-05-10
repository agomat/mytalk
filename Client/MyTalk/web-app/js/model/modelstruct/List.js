MyTalk.List = DS.Model.extend({
  name: DS.attr('string'),
  list: DS.hasMany('MyTalk.User'),
  
  getObject: function() { // di dubbia utilità
    return this.get('list');
  },

  blackList: function() { // valutarne l'utilità
  return this.get('name') === 'blacklist';
  }.property('name'),

  generalList: function() { // valutarne l'utilità
  return this.get('name') === 'generale';
  }.property('name'),

  otherList: function() { // valutarne l'utilità
  if(this.get('name') != 'generale' && this.get('name') != 'blacklist'){
    return true;
  }
  else return false;
  
  }.property('name'),


  
});

MyTalk.List.FIXTURES = [{
  id: 1,
  name: "generale"
}, {
  id: 2,
  name: "cacca"
}, {
  id: 3,
  name: "blacklist"
}, {
  id: 4,
  name: "amici"
}, {
  id: 5,
  name: "parenti"
}, {
  id: 6,
  name: "prostiute"
}];