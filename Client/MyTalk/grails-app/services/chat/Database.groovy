package chat

import org.atmosphere.cpr.*

class Database {
    private Set<User> online = new HashSet<User>()
    private Set<User> users = new HashSet<User>()

    public void addUser( User u )
    {
        users.add( u )
    }

    public  User getUserByIP( String ip ) {
        Iterator it = online.iterator();
        while (it.hasNext()) {
            if(it.next().IP.equals( ip )) {
                return it.next();
            }
        }
    }

    public  User getUser( String username ) {
        Iterator it = online.iterator();
        while (it.hasNext()) {
            if(it.next().username.equals( username )) {
                return it.next();
            }
        }
    }

    public void remove ( String ip ) {
        Iterator it = online.iterator();
        while (it.hasNext()) {
            User p = it.next();
            if(p.IP.equals( ip )) {
                it.remove()
            }
        }
    }

    public  void addOnline ( User u ) {
        online.add( u );
    }

    public String statoDatabase () {

        println "          * ONLINE USERS *";
        for (Iterator<User> it = online.iterator(); it.hasNext(); ) {
            User f = it.next();
            println "          > "+f
        }
        println "\n          * DATABASE USERS *";
        for (Iterator<User> it = users.iterator(); it.hasNext(); ) {
            User f = it.next();
            println "          > "+f
        }

    }

    public  User doLogin ( String username, String password, AtmosphereRequest req) {
        User u = null;
        for (Iterator<User> it = users.iterator(); it.hasNext(); ) {
            User f = it.next();
            if ( f.username.equals(username) && f.password.equals(password) )  {
                u = f.clonami();
                u.IP = req.getParameter("ip")
                break
            }
        }
        return u;
    }

}
