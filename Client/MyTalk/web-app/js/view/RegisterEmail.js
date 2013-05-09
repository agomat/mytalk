MyTalk.RegisterEmail = Ember.TextField.extend({
  attributeBindings: ["required"],
  required: true,
  name: 'email',
  id: 'email',
  type: 'email',
  placeholder: 'Email'
});