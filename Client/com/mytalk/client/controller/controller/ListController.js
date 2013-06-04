MyTalk.ListController = Ember.ObjectController.extend({
  sortProperties: ['name'],

  deleteList: function (){
    var listName = this.get('content').get('name');
    var listId = this.get('content').get('id');
    var response = confirm('Sei sicuro di voler eliminare la lista '+listName+'?');
    if(response == true){
      var context = this.get('target.router');
      var ProcessorFactory = MyTalk.ProcessorFactory.create({});
      var processor = ProcessorFactory.createProcessorProduct( "ListDelete" );
      processor.process({
        listId: listId,
        name: listName
      });
      context.replaceWith('list', MyTalk.List.find(0) );
    }
  },

  renameList: function makeUpdateListName(){ 
    var listId = this.get('content').get('id');
    var listName = this.get('content').get('name');
    
    var newName = prompt("Digita il nuovo nome della lista:");
    var lists = MyTalk.List.find();
    var test = true;

    if(newName){
      lists.forEach( function(t){
        if(t.get('name') == newName){
          test=false;
        }
      });
      if(test==true){
        var ProcessorFactory = MyTalk.ProcessorFactory.create({});
        var processor = ProcessorFactory.createProcessorProduct( this.computeRequestName( arguments ) );
        processor.process({
          listId: listId,
          newName: newName,
          oldName: listName,
        });

      }
    else {
      alert("Esiste già una lista con quel nome");
      }
    }
  },

  filter:function(value){
    var context=this;
    var fcontent=[];
   
    if(value){
      context.get('content').get('users').forEach(function(entry){
        if((entry.get('fullNameConc')).indexOf(value) !== -1) {
          entry.set('unmatched',false);
        }
        else{
          entry.set('unmatched',true);
        }
      });

    }
    else{
      context.get('content').get('users').setEach('unmatched',false);
    }
  },

  umatchedReset:function(){
    this.get('content').get('users').setEach('unmatched',false);
  }.observes(this)
});