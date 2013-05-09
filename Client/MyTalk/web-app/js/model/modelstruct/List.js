MyTalk.List = DS.Model.extend({
  name: DS.attr('string'),
  list: DS.hasMany('MyTalk.User'),
  
  getObject: function() { // di dubbia utilit�
    return this.get('list');
  },

  blackList: function() { // valutarne l'utilit�
    if (this.get('name').equals('blackList')) {
      return true;
    }
    return false;
  }.property('name')
  
});

