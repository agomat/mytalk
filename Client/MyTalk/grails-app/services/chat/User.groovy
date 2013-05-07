package chat

class User {
    private String username = null
    private String password = null

    private String email = null
    private String nome = null
    private String cognome = null
    private String IP = null

    User () {}

    User(String _username, String _password, String _email, String _nome, String _cognome, String _IP) {
        username = _username
        password = _password
        email = _email
        nome = _nome
        cognome = _cognome
        IP = _IP
    }



    public void setIP(String ip) {
        IP = ip
    }

    public String getIP() {
        return IP
    }

    public String getUsername() {
        return username
    }

    public String toString() {
        return "[username="+username+"; password="+password+"; email="+email+"; nome="+nome+"; cognome="+cognome+"; IP="+IP+"]";
    }

    public Boolean isGuest() {
        if (username == null)
            return true;
        return false;
    }

    public User clonami () {
        User u = new User()
        u.username = username;
        u.password = password;
        u.nome = nome;
        u.cognome = cognome;
        u.IP = IP;
        return u;
    }

}
