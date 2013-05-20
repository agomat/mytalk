package info.mytalk.core;

import java.util.Collection;

import org.java_websocket.WebSocket;

import com.google.gson.*;

public class ReqHandler {
	
	Collection<WebSocket> connections;
	
	public ReqHandler( Collection<WebSocket> conn ) {
		connections = conn;
	}
	
	public ARI process(String raw, User user) {
		ARI payload = new Gson().fromJson(raw,ARI.class); 

		/* ************************ USER ************************ */
		
	    /*
	     *  Login -> SuccessfulLogin | UnsuccessfulLogin
	     */
		
		// todo: notificare a tutti che l'utente si Ã¨ loggato
		if (payload.req.equals("Login")) {
			ARI.Auth Apart = new Gson().fromJson(payload.info, ARI.Auth.class); // getting username and password from ARI's A part
			User queryResult = Database.login(Apart.username,Apart.password);
			if ( queryResult != null ) {
				ARI JPari = new ARI(null,"SuccessfulLogin", new Gson().toJson( Database.createJetPack(queryResult.getUsername()),Database.JetPack.class));
				sendToOne(new Gson().toJson(JPari),user.getIP());
			} else {		
				ARI _ = new ARI(null,"UnsuccessfulLogin",null);
				sendToOne(new Gson().toJson(_),user.getIP());
			}
		}
		
	    /*
	     *  Logout -> SuccessfulLogout
	     */
		
		if (payload.req.equals("Logout")) {
			Database.exitFromOnline(user);
			sendToOne(new Gson().toJson( new ARI(null,"SuccessfulLogout",null)),user.getIP());
		}
		
	    /*
	     *  CreateAccount -> SuccessfulCreateAccount | UnsuccessfulCreateAccount TODO
	     */
		
		/* ************************ COMUNICATION ************************ */
		
	    /*
	     *  UserCall -> UserOffline | lancia la bombazza!!!!!!!!!!
	     */
		
		if (payload.req.equals("UserCall")) {
			ARI.Connection info = new Gson().fromJson(payload.info, ARI.Connection.class); 
			// in info ci sono: sdp e IP del chiamante
			// costruisco il messaggio da inviare a  info.speakerIp
			System.out.println("GOT SDP    "+info.sdpCall);
			ARI toSpeaker = new ARI(null,"RequestCall",new Gson().toJson( new ARI.Connection(user.getIP(),info.speakerIp,info.sdpCall, "")) );
			if (! sendToOne(new Gson().toJson(toSpeaker),info.speakerIp) ) {
				// lo speaker si trova improvvisamente offline...
				sendToOne(new Gson().toJson(new ARI(null,"UserOffline",null)),user.getIP());
			}
		}
                if (payload.req.equals("UserCandidate")) {
                        ARI.Connection info = new Gson().fromJson(payload.info, ARI.Connection.class); 
                        // in info ci sono: sdp e IP del chiamante
                        // costruisco il messaggio da inviare a  info.speakerIp
			 System.out.println("GOT CANDIDATE    "+info.candidate);
                        ARI toSpeaker = new ARI(null,"RequestCall",new Gson().toJson( new ARI.Connection(user.getIP(),info.speakerIp, "", info.candidate)) );
                        if (! sendToOne(new Gson().toJson(toSpeaker),info.speakerIp) ) {
                                // lo speaker si trova improvvisamente offline...
                                sendToOne(new Gson().toJson(new ARI(null,"UserOffline",null)),user.getIP());
                        }
                }


	    /*
	     *  SPEAKER ----- RefuseCall ----->  SERVER ----- RefuseCallS -----> CALLER
	     */
		
		if (payload.req.equals("RefuseCall")) {
			ARI.Connection info = new Gson().fromJson(payload.info, ARI.Connection.class); 
			// IPcaller == info.myIp;
			sendToOne( new Gson().toJson( new ARI(null,"RefuseCallS",null) ) , info.myIp );
		}
		
	    /*
	     *  SPEAKER ----- AcceptCall ----->  SERVER ----- AcceptCallS -----> CALLER
	     */
		
		if (payload.req.equals("AcceptCall")) {
			ARI.Connection info = new Gson().fromJson(payload.info, ARI.Connection.class); 
			// IPcaller == info.myIp;
			sendToOne( new Gson().toJson( new ARI(null,"AcceptCallS",payload.info) ) , info.myIp );
		}		
		
		/* ************************ LISTS ************************ */
		// TODO
		
		
		
		return new ARI(null,null,null);
	}
	
	public boolean sendToOne( String payload , String IP ) {
		synchronized ( connections ) {
			WebSocket w = null;
			for( WebSocket c : connections ) {
				if( c.getUser().getIP().equals(IP) ) {
					w = c;
					break;
				}
			}
			if (w != null) {
				// sending this freaking message to other motherfucker user...
				w.send(payload);
				return true;
			} else {
				return false;
			}
		}
	}
	
	public void sendToAll( String payload ) {
		synchronized ( connections ) {
			for( WebSocket c : connections ) {
				c.send( payload );
			}
		}
	}
	
}
