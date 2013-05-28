MyTalk.processor.SuccessfulListCreate = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct, {
  name: 'SuccessfulListCreate',

  process: function (ari) {

  },
  
  getProcessorName: function () {
  	return this.get('name');
  } 
});
