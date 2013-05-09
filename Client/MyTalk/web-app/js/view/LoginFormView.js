MyTalk.LoginFormView = Ember.View.extend({
  needs: ['IndexView.controller'],
  indexBinding: "controller.index",
  tagName: 'form',
  attributeBindings:["method"],
  method: 'post',
  email: null,
  password: null,

  submit: function(event) {
    event.preventDefault();
    var email = this.get('email');
    var password = this.get('password');
    this.get('controller').login(email, password);
  }
});

Ember.TextField.reopen({
  attributeBindings: ['name'],
  attributeBindings: ['id'],
});

Ember.TextField.reopen({
  attributeBindings: ["required"],
  required: true
})