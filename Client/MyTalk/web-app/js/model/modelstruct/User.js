// TODO: per tutti i file utilizzare Ember.Namespaces..

MyTalk.User = DS.Model.extend({
  //primaryKey: 'username',
  username: DS.attr('string'),
  online: DS.attr('boolean'),
  nome: DS.attr('string'),
  cognome: DS.attr('string'),
  ip: DS.attr('string'),
  user: DS.belongsTo('MyTalk.WUser', {
      inverse: 'list'
  }),
  
  getObject: function() { // di dubbia utilità
    return { username:this.get('username'), password:this.get('online') };
  },

  didLoad: function() {
    console.log("id: "+ this.username);
  }
  
});


MyTalk.User.reopenClass({
  primaryKey: "username",
  pk : "username"
});


DS.JSONSerializer.extend({
  primaryKey: function(type) {
      return '__username__';
    }
})