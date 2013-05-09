MyTalk.RegisterUsername = Ember.TextField.extend({
  attributeBindings: ["required"],
  required: true,
  name: 'username',
  id: 'username',
  type: 'text',
  placeholder: 'Username'
});