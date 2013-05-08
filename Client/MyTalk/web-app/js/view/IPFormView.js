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