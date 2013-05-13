MyTalk.LoginPassword = Ember.TextField.extend({
  attributeBindings: ["required"],
  attributeBindings:["pattern"],
  required: true,
  name: 'password',
  id: 'password',
  type: 'password',
  placeholder: 'Password',
  attributeBindings: ['onchange'],
  onchange: "this.setCustomValidity(this.validity.patternMismatch ? 'La password deve contenere almeno sei caratteri' : '');",
  pattern:'\\w{6,}'
});