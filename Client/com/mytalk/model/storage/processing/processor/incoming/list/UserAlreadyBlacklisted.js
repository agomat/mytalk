MyTalk.processor.UserAlreadyBlacklisted = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct, {
  name: 'UserAlreadyBlacklisted',
  process: function (ari) {

  },
  
  getProcessorName: function () {
  	return this.get('name');
  } 
});