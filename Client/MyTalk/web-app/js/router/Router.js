MyTalk.Router.map(function(match) {
  this.route("index", {path:'/'});
  this.route("lists");
  
});

MyTalk.IndexRoute = Em.Route.extend({
    
  model: function() {
   return MyTalk.WUser.find();
  },

    setupController : function(controller,model) {
      //controller.set('content',['one','two','three']);
      //console.log('App.PostIndexRoute.setupController(): setting the App.postsIndexController constant to: ', controller.toString());
      //MyTalk.postsIndexController = controller;
   
    },
    renderTemplate: function(controller, model){ 
        //Render header into header outlet
        /*this.render('logo',{
            outlet:'logo'

        });

        this.render('login',{
          outlet:'login'
        });

         this.render('call',{
          outlet: 'call'
        });


        this.render('notlogged',{
          outlet: 'notlogged'
        });

        
        //Render index into main outlet. If you comment out 
        //this line, the code below fails
        */ 

        this.render('index'); 


        this.render('content',{
         
        }); 

       /* this.render('logo',{
            outlet:'logo',
            into:'index'
        });

       
        this.render('login',{
          outlet:'login',
          into:'index'
        });

/*
         this.render('call',{
          outlet: 'call',
           into:'index'
        });


        this.render('notlogged',{
          outlet: 'notlogged',
           into:'index'
        });*/

    }
});

MyTalk.ListsRoute = Ember.Route.extend({

    renderTemplate: function(controller, model){
        //Render header into header outlet

        this.render('lists');
        this.render('lists_t',{
         
        }); 
       /* this.render('header',{
            outlet:'header'
        });*/
        //Render index into main outlet. If you comment out 
        //this line, the code below fails
       // this.render('index');

        //by using into, we can render into the index template
        //Note: The controller is optional.if not specified,
        //ember picks up controller for the given template.
      /* this.render('dashboard',{
            outlet:'dashboard',
            into:'logged',
            
            //controller:this.controllerFor('somethingElse', MyTalk.WUser.find())
        });*/
        //controller is SpacesController
        this.render('spaces',{
            outlet:'spaces',
            into:'logged'
        });
    }
});