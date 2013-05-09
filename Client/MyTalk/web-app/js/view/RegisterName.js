MyTalk.RegisterName = Ember.TextField.extend({
  attributeBindings: ["required"],
  required: true,
  name: 'name',
  id: 'name',
  type: 'text',
  placeholder: 'Nome'
});