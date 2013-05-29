MyTalk.processor.ListNotExistingListUserRemove = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct, {
  name: 'ListNotExistingListUserRemove',

  process: function (ari) {

  },
  
  getProcessorName: function () {
  	return this.get('name');
  } 
});