MyTalk.processor.SuccessfulListUserRemove = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct, {
  name: 'SuccessfulListUserRemove',
  process: function (ari) {

  },
  
  getProcessorName: function () {
  	return this.get('name');
  } 
});