package info.mytalk.core;

//import java.io.BufferedReader;
//import java.io.InputStreamReader;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.google.gson.Gson;
/**
 * A simple WebSocketServer implementation. Keeps track of a "chatroom".
 */
public class Server extends WebSocketServer {

	ReqHandler processor;
	
	public Server( int port ) throws UnknownHostException {
		super( new InetSocketAddress( port ) );
		processor = new ReqHandler( this.connections() );
	}

	public Server( InetSocketAddress address ) {
		super( address );
	}

	@Override
	public void onOpen( WebSocket conn, ClientHandshake handshake ) {
		/*
		 *  È stata aperta una nuova connessione
		 *  L'utente che accede è un utente guest
		 */
		
		// Creo un utente guest con l'ip calcolato
		String ip = Database.validIP( ""+conn.getRemoteSocketAddress().getAddress());
		User user = new User( ip );
		Database.guestUserEnters( user );
		
		// Collego l'user alla risorsa ws
		conn.setUser(user);
		
		//this.sendToAll( "new connection: " + handshake.getResourceDescriptor() );
		conn.send(new Gson().toJson(new ARI(null,"yourIp",ip)));
		System.out.println( "onOpen -> L'utente '" + user.getIP() + "' entra per la prima volta!" );
		Database.stato();
	}

	@Override
	public void onClose( WebSocket conn, int code, String reason, boolean remote ) {
		
		// l'utente esce dalla lista online
		User user = conn.getUser();
		Database.exitFromOnline(user);
		if (user.getUsername() == null)
			System.out.println( "onClose -> L'utente guest'" + user.getIP() + "' esce" );
		else
			System.out.println( "onClose -> L'utente '" + user.getUsername() + "' esce" );
		Database.stato();
		// se era un utente loggato, lancio la notifica a tutti gli altri utenti loggati
		
		if (user.getEmail() != null) {
			Database.broadCastToAllUsersAuth(this, "E' uscito l'utente "+user.getUsername());
		}
		
	}

	@Override
	public void onMessage( WebSocket conn, String message ) {
		/*
		if (conn.getUser().getUsername() != null)
			System.out.println( conn.getUser().getUsername() + " dice: " + message );
		else
			System.out.println( conn.getUser().getIP() + " dice: " + message );
		*/
		System.out.println("onMessage");
		//Boolean logged = false;
		//if (conn.getUser().getUsername() != null)
		//	logged = true;
		
		/* MESSAGGI AL SINGOLO UTENTE RICHIEDENTE */
		
		processor.process( message , conn.getUser() );
		// di richiesta WCall
		
		// di nuova lista
		
		// di aggiunta di un utente a una lista (blacklist inclusa)
		
		// di rimozione utente da una lista
		
		// di rinominazione di una lista
		
		/* MESSAGGI CHE COINVOLGONO 2 UTENTI */
		
		// di chiamata (SDP e company)
	}

	public static void main( String[] args ) throws InterruptedException , IOException {
		Database.popolamento();
		WebSocketImpl.DEBUG = false;
		int port = 8887; // 843 flash policy port
		try {
			port = Integer.parseInt( args[ 0 ] );
		} catch ( Exception ex ) {
		}
		Server s = new Server( port );
		s.start();
		System.out.println( "MyTalk server started on port: " + s.getPort() );
		
		/*
		BufferedReader sysin = new BufferedReader( new InputStreamReader( System.in ) );
		while ( true ) {
			String in = sysin.readLine();
			s.sendToAll( in );
		}
		*/
	}

	@Override
	public void onError( WebSocket conn, Exception ex ) {
		ex.printStackTrace();
		if( conn != null ) {
			// some errors like port binding failed may not be assignable to a specific websocket
		}
	}

}
