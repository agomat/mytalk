MyTalk= Ember.Application.create({
  ready: function() {},
  //LOG_TRANSITIONS: true
});

var indextmplate="<h1>Index Page</h1>{{view.name}}<button {{action click}}>Login</button>";

Ember.TEMPLATES['index'] = Ember.Handlebars.compile(indextmplate);

// fare package per le viste

MyTalk.IndexView = Ember.View.extend({
  templateName: "index",
  name: "index"
});

MyTalk.LoginView = Ember.View.extend({
    templateName: "login",
    name: "login-page"
});

MyTalk.LoggedView = Ember.View.extend({
    templateName: "logged",
    name: "logged-page"
});

MyTalk.LoginButton= Ember.View.extend({
  click: function(evt) {
    //console.log('controller: ', this.get('controller').toString());
    this.get('controller').send('turnItUp', 11); 
  }
});
