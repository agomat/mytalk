MyTalk.RegisterConfirmPassword = Ember.TextField.extend({
  attributeBindings: ['pattern'],
  attributeBindings: ["required"],
  required: true,
  name: 'password_conf',
  id: 'password_conf',
  type: 'password',
  placeholder: 'Conferma Password',
  attributeBindings: ['onchange'],
  onchange: "this.setCustomValidity(this.validity.patternMismatch ? 'Le due password non combaciano' : checkPasswords(this.value, form.password.value)); function checkPasswords(a,b){ if(a!=b){return 'Le due password non combaciano';} else {return '';} };",
  pattern: '\\w{6,}'
});