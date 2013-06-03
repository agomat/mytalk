MyTalk.LoggedController = Ember.ObjectController.extend({
  sortProperties: ['name'],
  needs:['users'],
  createList:function (){

    var newName = prompt("Digita il nome della nuova lista: ");
    if(newName) {
      var list=this.get('content');
      var test=true;

      list.forEach(function(t){
        if(t.get('name')==newName){
          test=false;
        }
      });
      
      if(test==true){
        var ProcessorFactory = MyTalk.ProcessorFactory.create({});
        var processor = ProcessorFactory.createProcessorProduct( "ListCreate" );
        processor.process({
          id: MyTalk.List.find().get('length'),
          name: newName
        });
        this.get('controllers.users').set('newList',MyTalk.List.find().get('length'));
      }
      else {
        alert("Esiste già una lista con questo nome");
      }
    }  
  },
  
});