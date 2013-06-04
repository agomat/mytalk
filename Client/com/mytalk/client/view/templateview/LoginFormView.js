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
    var username = this.get('username');
    var password = this.get('password');
    this.get('controller').login(username, password);
  }
});

Ember.TextField.reopen({
  attributeBindings: ['name'],
  attributeBindings: ['id'],
});

