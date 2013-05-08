MyTalk = Ember.Application.create({
  LOG_TRANSITIONS: true
});

loadTemplates([
  'index.hbs',
  'content.hbs',
  'lists.hbs',
  'lists_t.hbs',
  '_header.hbs',
  '_dashboard.hbs',
  '_login.hbs',
  '_register.hbs',
  '_description.hbs',
  '_callip.hbs'
]);

//
//
// DA QUI IN POI CI SONO SOLO VISTE
// @Stefano Campese: spostare le viste (e le componeti in file di viste dento a /view)
//
//

Ember.TextField.reopen({ // e per cosa serve?
  attributeBindings: ['name'],
  attributeBindings: ['id'],
});

Ember.TextField.reopen({ // e per cosa serve?
  attributeBindings: ["required"],
  required: true
})

MyTalk.IndexView = Ember.View.extend({
  templateName: "index",
  name: "index",
});

MyTalk.LoginEmail = Ember.TextField.extend({
  name: 'email',
  id: 'email',
  type: 'email',
  placeholder: 'Email'
});

MyTalk.LoginPassword = Ember.TextField.extend({
  name: 'password',
  id: 'password',
  type: 'password',
  placeholder: 'Password'
});

MyTalk.RegisterName = Ember.TextField.extend({
  name: 'name',
  id: 'name',
  placeholder: 'Nome'
});

MyTalk.RegisterSurname = Ember.TextField.extend({
  name: 'surname',
  id: 'surname',
  placeholder: 'Cognome'
});

MyTalk.RegisterUsername = Ember.TextField.extend({
  name: 'username',
  id: 'username',
  placeholder: 'Username'
});

MyTalk.IPCall = Ember.TextField.extend({
  name: 'ip_call',
  id: 'ip_call_text',
  type: 'text',
  placeholder: 'Indirizzo IP'
});

MyTalk.RegisterEmail = Ember.TextField.extend({
  name: 'email',
  id: 'email',
  type: 'email',
  placeholder: 'Email'
});

MyTalk.RegisterPassword = Ember.TextField.extend({
  attributeBindings: ['pattern'],
  attributeBindings: ['onchange'],
  name: 'password',
  id: 'password',
  type: 'password',
  placeholder: 'Password',
  onchange: "this.setCustomValidity( this.validity.patternMismatch ? 'La password deve contenere almeno 6 caratteri': '')",
  pattern: "\\w{6,}",
});

MyTalk.RegisterConfirmPassword=Ember.TextField.extend({
  attributeBindings: ['pattern'],
  attributeBindings: ['onchange'],
  name: 'password_conf',
  id: 'password_conf',
  type: 'password',
  placeholder: 'Conferma Password',
  onchange: "this.setCustomValidity(this.validity.patternMismatch ? 'Le due password non combaciano' : checkPasswords(this.value,form.password.value)); function checkPasswords(a,b){if(a!=b){return 'Le due password non combaciano';}else{return '';}};",
  pattern: "\\w{6,}",
});

MyTalk.IndexView = Ember.View.extend({
  templateName: "index",
  name: "index",
});

MyTalk.Login = Ember.View.extend({
  templateName: "login",
  name: "login",
});

MyTalk.Header = Ember.View.extend({
  templateName: "header",
  name: "header",
});

MyTalk.Register = Ember.View.extend({
  templateName: "register",
  name: "register",
});

MyTalk.Description = Ember.View.extend({
  templateName: "description",
  name: "description",
});


MyTalk.IPFormView = Ember.View.extend({
  tagName: 'form',
  attributeBindings: ["method"],
  method: 'post',
  ip_call: null,
  submit: function(event) {
        event.preventDefault();
        var ip_call = this.get('ip_call');
        this.get('controller').ipCall(ip_call);
     }
});

MyTalk.RegisterFormView = Ember.View.extend({
  tagName: 'form',
  attributeBindings:["method"],
  method:'post',
 
  name:null,
  surname:null,
  username:null,
  email:null,
  password:null,
  password_conf:null,

  submit: function(event) {
        event.preventDefault();
        var name = this.get('name');
        var password = this.get('password');
        var password_conf=this.get('password_conf');
        var email=this.get('email');
        var surname=this.get('surname');
        var username=this.get('username');
        this.get('controller').register(name,surname,username,email,password,password_conf);
     }
});

MyTalk.LoginFormView = Ember.View.extend({
  needs: ['IndexView.controller'],
  indexBinding:"controller.index",
  tagName: 'form',
  attributeBindings:["method"],
  method:'post',
  email:null,
  password:null,

  submit: function(event) {
        event.preventDefault();
        var email = this.get('email');
        var password = this.get('password');
        this.get('controller').login(email,password);
     }
});

MyTalk.ContentView = Ember.View.extend({
  templateName: "content",
  name: "content",
});

MyTalk.LoginView = Ember.View.extend({
  templateName: "login",
  name: "login"
});

MyTalk.ListsView = Ember.View.extend({
    templateName: "lists",
    name: "lists"
});

MyTalk.ListView = Ember.View.extend({
    templateName: "list",
    name: "list"
});