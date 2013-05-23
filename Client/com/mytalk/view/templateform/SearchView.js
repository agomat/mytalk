MyTalk.SearchView = Ember.TextField.extend({
  name: 'search',
  type: 'text',
  placeholder: 'Filtra per nome o cognome',
  valueDidChange: function(element,property,value) {
            this.get('controller').filter(this.value);
        }.observes('value')
 });