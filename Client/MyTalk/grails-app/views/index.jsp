<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Ember Starter Kit</title>

  <script src="js/libs/jquery-1.9.1.js"></script>
  <script src="js/libs/handlebars-1.0.0-rc.3.js"></script>
  <script src="js/libs/ember-1.0.0-rc.2.js"></script> 
  <script src="js/libs/ember-data.js"></script>
  <script src="js/libs/head.min.js"></script>
  <script src="js/load.js"></script>
  


  
   
</head>
<body>



<script type="text/x-handlebars" data-template-name="login">
    <h1>Login Page</h1>
  
        {{view.name}}


</script>
 <script type="text/x-handlebars" data-template-name="logged">
    <h1>Logged Page</h1>
  
        {{view.name}}

         
        {{#view MyTalk.LoginButton}}
          <button type="button">This is a clickable area!</button>
        {{/view}}
               

        <ul class="nav well">
        {{#each model}}
         <li>{{user.username}}, {{user.online}}</li>
        {{/each}}
        </ul>
</ul>
        
</script>

  

</body>
</html>
