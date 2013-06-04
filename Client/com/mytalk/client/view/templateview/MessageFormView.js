MyTalk.MessageFormView = Ember.View.extend({
  tagName: 'form',
  attributeBindings: ["method"],
  method: 'post',
  message: null,
  submit: function(event) {
    event.preventDefault();
    var message = this.get('message');
    this.get('controller').sendMessage(message);
    
  }
});