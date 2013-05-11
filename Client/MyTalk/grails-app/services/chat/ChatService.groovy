package chat

import org.atmosphere.cpr.AtmosphereRequest
import org.atmosphere.cpr.AtmosphereResource
import org.atmosphere.cpr.AtmosphereResponse
import org.atmosphere.cpr.Broadcaster
import grails.converters.JSON
import org.atmosphere.cpr.MetaBroadcaster
import org.atmosphere.cpr.BroadcasterFactory
import org.atmosphere.cpr.BroadcasterLifeCyclePolicy


class ChatService {

    static transactional = false
    static atmosphere = [mapping: '/atmosphere/chat']
    private Database database = null;

    ChatService(){
        init()
    }
    def onRequest = { event ->

        try {
            AtmosphereRequest req = event.request
            if (req.method.equalsIgnoreCase("GET")) {

                println "Inside onRequest GET for path ${req.pathInfo}"
                if( req.pathInfo.indexOf("/all") > 0 ) {
                    // aggiungi utente guest //

                    // mi salvo il suo evento
                    req.session[ req.getParameter("ip") ] = event


                    User u = new User()
                    u.setIP ( req.getParameter("ip") )
                    //req.setAttribute("user",u)
                    database.addOnline( u );
                    println "Nuovo utente guest collegato: " + u;
                    database.statoDatabase()
                    event.broadcaster = BroadcasterFactory.default.lookup("/all", true)
                } else {
                    def uri = req.pathInfo.replaceAll("/chat","")
                    event.broadcaster = BroadcasterFactory.default.lookup(uri, true)
                }
                event.broadcaster.setBroadcasterLifeCyclePolicy(BroadcasterLifeCyclePolicy.EMPTY);
                event.suspend()

            } else if (req.method.equalsIgnoreCase("POST")) {
                println "Inside onRequest POST"
                def temp = req.reader.readLine().trim() //modifiche per max
                def ARI = JSON.parse( temp )
                 

                if (req.pathInfo == "/chat/all")
                {
                    // messaggi proveniente da utenti guest (non ancora autenticati)
                    /* MODIFICHE PER MAX */


                    //MetaBroadcaster.default.broadcastTo("/all", temp )
                    MetaBroadcaster.default.broadcastTo("/user", temp )



                }else if (req.pathInfo == "/chat/user") {
                    MetaBroadcaster.default.broadcastTo("/all", temp ) // max
                } else {
                    // messaggi proveniente da utenti autenticati

                    def broadcasterID = req.pathInfo.replaceAll("/chat","")

                    if (ARI.r == "autentificazione") {
                        User u = database.doLogin(ARI.a.username, ARI.a.password,req)
                        if (u != null) {
                            // utente autenticato
                            database.remove( u.IP )
                            database.addOnline( u )
                            database.statoDatabase()
                            // mi ricordo di me
                            req.setAttribute("user",u)

                            // RISPOSTA AL MITTENTE
                            MetaBroadcaster.default.broadcastTo( broadcasterID , "Bentornato "+ u.getUsername())
                            // NOTIFICA AGLI ALTRI UTENTI
                            MetaBroadcaster.default.broadcastTo("/all", u.getUsername()+" si è loggato")
                        } else {
                            // RISPOSTA AL MITTENTE
                            MetaBroadcaster.default.broadcastTo( broadcasterID , "Username o password non validi!")
                            // TODO RESUMING CONNECTION
                        }
                    } else if (ARI.r == "chiamata") {
                        User u = req.getAttribute("user")
                        if ( u != null ) {

                        }
                    }
                }

                /*
                if( data.indexOf("pie") > 0 ){         // manda la risposta a me
                    println "Broadcasting to pie! ${data}"
                    MetaBroadcaster.default.broadcastTo("/pie", data)
                } else if( data.indexOf("pie") > 0 ){

                    // manda la risposta a tutti dicendo che sono loggato
                }
                */
            }
        } catch (Exception e) {
            println "ERROR!!!!! $e "+ e.printStackTrace()
        }

    }

    def onStateChange = { event ->
        println "Inside onStateChange"
        AtmosphereResource r = event.resource
        AtmosphereResponse res = r.response

        try {
            if (event.isSuspended()) {

                //event.resource.get

                //def msg = JSON.parse(event.message)
                def RR =  event.message
                res.writer.write( RR )
                res.writer.flush()
                //r.resume()
            } else if (!event.isResuming()) {
                def broadcasterID = res.request().pathInfo.replaceAll("/chat/","")
                if ( broadcasterID == "all" )  // prima era diverso !=
                {
                    //MetaBroadcaster.default.broadcastTo("/all", broadcasterID +" esce") MAX
                    MetaBroadcaster.default.broadcastTo("/all", "idie")
                    println "Esce " + res.request().pathInfo
                }
            }
        } catch (Exception e) {
            println "ERROR in onStateChange. UUID: ${r.uuid()} ;  $e"
            println e.getStackTrace()
        }
    }

    private Broadcaster lookupBroadcaster(AtmosphereRequest req) {
        String broadcasterId = (String) req.getSession().getAttribute("key");
        return lookupBroadcaster(broadcasterId);
    }

    private Broadcaster lookupBroadcaster(String broadcasterId) {
        //LOG.info("Looking up for broadcaster: {}", broadcasterId);
        Broadcaster broadcaster = BroadcasterFactory.getDefault().lookup(
                broadcasterId);

        //LOG.info("Broadcaster found : {}", broadcaster.getID());
        return broadcaster;
    }

    private void init() {
        if (!database) {
            database = new Database();
            database.addUser( new User("user_1","pass","a@a.it","Matteo","Cognome",null) );
            database.addUser( new User("user_2","pass","b@b.it","Luigi","Cognome",null) );
            database.addUser( new User("user_3","pass","c@c.it","Marco","Cognome",null) );
            database.addUser( new User("user_4","pass","d@d.it","Luca","Cognome",null) );
            database.addUser( new User("user_5","pass","e@e.it","Giuseppe","Cognome",null) );
            database.addUser( new User("user_6","pass","f@f.it","Lucio","Cognome",null) );
            database.addUser( new User("user_7","pass","g@g.it","Alberto","Cognome",null) );
            database.addUser( new User("user_8","pass","h@h.it","Giulia","Cognome",null) );
            database.addUser( new User("user_9","pass","i@i.it","Giovanni","Cognome",null) );
        }
    }

}






