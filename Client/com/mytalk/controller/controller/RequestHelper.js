MyTalk.RequestHelper = Ember.Mixin.create({
	computeRequestName: function(context){
		return context.callee.name.split('make')[1];
	}
});