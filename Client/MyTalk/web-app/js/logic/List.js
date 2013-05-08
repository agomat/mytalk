//DEPRECATO
MyTalk.List= Ember.Object.extend({

  users: [],

  getUsers: function() {  // funzione molto utile campastyle
    //modificata da max
    //ritorna un array di username invece che di users 
    //(che basta prendere facendo get('users'))
    var usernames=[];
    this.users.forEach(function(aux) {
      usernames.push(aux.get('username'));
    });
	return usernames;
  },

  getUser: function(username) {  // piu utile secondo me
    //cerca un utente nella lista dato l'username
    var i=0;
    this.users.forEach(function(aux) {
      if(aux.get('username')==username) {
        return i;
      }
      i++;
    });
    if(i<this.users.length) {
      return i;
    }
    return -1;
  },//e un'operazione usata sia da add che da remove 
    //secondo me conviene incapsularla in un metodo.

  addUser: function(user) {
    //aggiunge utente alla lista
    if(this.getUser(user.get('username')) == -1) {
      this.users.push(user);
      this.users.sort();
      return true;
    }
    else {
      console.log("User with name "+user.get('username')+" already exists.");
      return false;
    }
  }, 

  removeUser: function(user) {
    // rimuove l'utente
    var index=this.getUser(user.get('username'));
    if(index != -1) {  // if user exists
      this.getUsers().splice(index,1);
      this.get('users').sort();
      return true;
    }
    else {   // if user is not found
      console.log("User "+user.get('username')+" not found.");
      return false;
    }
  }, 

  printUsers: function() {
    //stampa l'array
    var c=this.users;//prendo l'array user
    console.log(c); // perchè non fare console.log(this.users)?
    //comunque se hai notato la funzione non stampa gli utenti ma
    //stampa users, cioè cosa contiene e il tipo e le proprieta
    //dell'oggetto
  }

});
