MyTalk.HeaderView = Ember.View.extend({
  templateName: "header",
  name: "header",
  didInsertElement: function() {
    Ember.run.later(this, function(){
      $.fn.dropDownMenu();
    }, 100);
  }

});