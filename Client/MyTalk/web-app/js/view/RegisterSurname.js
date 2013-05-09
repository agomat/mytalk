MyTalk.RegisterSurname = Ember.TextField.extend({
  attributeBindings: ["required"],
  required: true,
  name: 'surname',
  id: 'surname',
  type: 'text',
  placeholder: 'Cognome'
});