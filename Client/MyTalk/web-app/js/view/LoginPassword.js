MyTalk.LoginPassword = Ember.TextField.extend({
  attributeBindings: ["required"],
  required: true,
  name: 'password',
  id: 'password',
  type: 'password',
  placeholder: 'Password'
});