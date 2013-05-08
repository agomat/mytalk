MyTalk = Ember.Application.create({
  LOG_TRANSITIONS: true,
  ready: function() {
  	$.getScript('js_graphics/animation-c2.js');
  }
});