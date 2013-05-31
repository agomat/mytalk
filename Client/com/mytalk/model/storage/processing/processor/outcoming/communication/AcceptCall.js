MyTalk.processor.AcceptCall = Ember.Object.extend(MyTalk.AbstractOutProcessorProduct,{
  name: 'AcceptCall',

  process: function (ari) {
    var store = DS.get("defaultStore");

  },
  
  getProcessorName: function () {
    return this.get('name');
  } 

});