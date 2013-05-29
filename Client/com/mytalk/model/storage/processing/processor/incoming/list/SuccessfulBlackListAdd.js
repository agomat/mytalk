MyTalk.processor.SuccessfulBlackListAdd = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct, {
  name: 'SuccessfulBlackListAdd',

  process: function (ari) {

  },
  
  getProcessorName: function () {
  	return this.get('name');
  } 
});
