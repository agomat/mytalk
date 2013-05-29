MyTalk.processor.SuccessfulBlackListRemove = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct, {
  name: 'SuccessfulBlackListRemove',

  process: function (ari) {

  },
  
  getProcessorName: function () {
  	return this.get('name');
  } 
});
