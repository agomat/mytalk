MyTalk.IndexView = Ember.View.extend({
  templateName: "index",
  name: "index",
  didInsertElement: function() {
    Ember.run.later(this, function(){
      $.fn.videoTutorial();
    }, 500);
  }
});