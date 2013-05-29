MyTalk.processor.ListNotExistingListUserAdd = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct, {
  name: 'ListNotExistingListUserAdd',

  process: function (ari) {

  },
  
  getProcessorName: function () {
  	return this.get('name');
  } 
});