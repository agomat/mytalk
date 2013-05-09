MyTalk.RegisterPassword = Ember.TextField.extend({
  attributeBindings: ['pattern'],
  attributeBindings: ['onchange'],
  attributeBindings: ["required"],
  required: true,
  name: 'password',
  id: 'password',
  type: 'password',
  placeholder: 'Password',
  onchange: "this.setCustomValidity( this.validity.patternMismatch ? 'La password deve contenere almeno 6 caratteri': '')",
  pattern: '\\w{6,}'
});