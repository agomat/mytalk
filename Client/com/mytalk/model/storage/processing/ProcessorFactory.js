MyTalk.ProcessorFactory = Ember.Object.extend({
	
	createProcessorProduct: function(requestName) { 
		var processor = eval("MyTalk.processor." + requestName);
		if( processor ) {
			return processor.create({});
		} else {
			return null;
		}
	}

});