MyTalk.WList = DS.Model.extend({
  list: DS.hasMany('MyTalk.List'),
  
  getObject: function() { // di dubbia utilità
    return this.get('list');
  }
  
});
