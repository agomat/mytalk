MyTalk.processor.SuccessfulRenameList = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct, {
  name: 'SuccessfulRenameList',

  process: function (ari) {

  },
  
  getProcessorName: function () {
  	return this.get('name');
  } 
});