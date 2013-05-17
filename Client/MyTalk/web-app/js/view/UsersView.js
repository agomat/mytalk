MyTalk.UsersView= Ember.View.extend({
  templateName: "users",
  name: "users",
  actualList:function(){
  	this.get('controller').check();
  }.property('blacklist')
});