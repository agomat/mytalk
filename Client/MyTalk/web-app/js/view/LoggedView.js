MyTalk.LoggedView = Ember.View.extend({
  templateName: "logged",
  name: "logged",
  didInsertElement: function() { 
    Ember.run.later(this, function(){
      $.fn.dropDownMenu();
    }, 100);
  }
});