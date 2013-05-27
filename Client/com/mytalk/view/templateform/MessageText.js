MyTalk.MessageText = Ember.TextArea.extend({
  attributeBindings: ["required"],
  required: true,
  name: 'text_input',
  id: 'text_input',
  type: 'text',
  placeholder: 'Scrivi un messaggio'
});