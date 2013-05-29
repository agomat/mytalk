MyTalk.processor.SuccessfulListUserAdd = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct, {
  name: 'SuccessfulListUserAdd',
  process: function (ari) {

  },
  
  getProcessorName: function () {
  	return this.get('name');
  } 
});