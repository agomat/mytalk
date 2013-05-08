MyTalk.RegisterFormView = Ember.View.extend({
  tagName: 'form',
  attributeBindings: ["method"],
  method: 'post',
  name: null,
  surname: null,
  username: null,
  email: null,
  password: null,
  password_conf: null,
  
  submit: function(event) {
    event.preventDefault();
    var name = this.get('name');
    var password = this.get('password');
    var password_conf = this.get('password_conf');
    var email = this.get('email');
    var surname = this.get('surname');
    var username = this.get('username');
    this.get('controller').register(name, surname, username, email, password, password_conf);
  }
});