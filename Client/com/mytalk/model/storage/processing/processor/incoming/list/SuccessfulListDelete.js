MyTalk.processor.SuccessfulListDelete = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct, {
  name: 'SuccessfulListDelete',

  process: function (ari) {

  },
  
  getProcessorName: function () {
  	return this.get('name');
  } 
});