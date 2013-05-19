package info.mytalk.core;

import java.util.ArrayList;

import java.util.Collection;
import org.java_websocket.WebSocket;
import org.java_websocket.server.WebSocketServer;


public class Database {
	
	static class JetPack {
		ArrayList<List.ListSideload> lists;
		ArrayList<User.Contact> users;
		public JetPack(ArrayList<List.ListSideload> _lists, ArrayList<User.Contact> _users) {
			lists = _lists;
			users = _users;
		}
	}	
	
	private static ArrayList<User> users = new ArrayList<User>(); // persistent users
	private static ArrayList<User> online = new ArrayList<User>();
	
	
	public synchronized static User login(String username, String password) {
		for (User currentU : users) {
			if (currentU.getUsername().equals(username) && currentU.getPassword().equals(password) ) {
				return currentU;
			}	
		}
		return null;   
	}
	
	/* *************************** JETPACK ***************************** */
	
	private static ArrayList<List.ListSideload> createMultiListSideload( ArrayList<List> embeddedList) {
		// new list array of lists
		ArrayList<List.ListSideload> multiListSideload = new ArrayList<List.ListSideload>();
		
		for (List originList : embeddedList) {
			Integer id = originList.getId();
			String name = originList.getName();
			ArrayList<User> contacts = originList.getList();
			
			List.ListSideload listSideload = new List.ListSideload(id, name);
			for (User contact : contacts) {
				listSideload.pushContactId( contact.getId() );
			}
			// append della lista nel vettore liste
			multiListSideload.add(listSideload);
		}
		
		return multiListSideload;
	}
	
	public synchronized static JetPack createJetPack(String username) {
		User user = Database.getPersistentUserByUsername(username);
	    
		// step 1 - creazione lista di liste aventi chiavi esterne
		ArrayList<List.ListSideload> multiLSL = createMultiListSideload( user.getLists() );
	    
		// step 2 - creazione lista di TUTTI gli utenti persistenti
		ArrayList<User.Contact> multiCn = new ArrayList<User.Contact>();
		for (User u : users) {
			multiCn.add( new User.Contact(u.getId(), u.getName(), u.getSurname(), u.getMd5(), u.getIP(), u.getOnline()) );
		}
		
		return new JetPack(multiLSL,multiCn);
	}
	
	/* *********************** JETPACK **** END ************************ */
	
	public synchronized static User getPersistentUserByUsername(String user) {
		for (User u : users) {
			if (u.getUsername().equals(user)) {
				return u;
			}	
		}
		return null;   
	}
	
	public synchronized static User getOnlineUserByUsername(String username) {
		for (User u : online) {
	        if (u.getUsername().equals(username)) {
	        	return u;
	        }
		}
		return null;
	}
	
	public synchronized static void guestUserEnters(User user) {
		online.add(user);
	}
	
	public synchronized static void exitFromOnline(User user) {
		online.remove(user);
	}
	
	public synchronized static void broadCastToAllUsersAuth(WebSocketServer handler, String message) {
		Collection<WebSocket> con = handler.connections();
		synchronized ( con ) {
			for( WebSocket c : con ) {
				if (c.getUser().getEmail() != null)
					c.send( message );
			}
		}
	}
	
	public synchronized static void userBecomeAuth(User user) {
		// PRE: l'utente user e' un utente completo
		String ip = user.getIP();
		User removeMe = null;
		for (User u : online) {
	        if (u.getIP().equals(ip)) {
	        	removeMe = u;
	        	break;
	        }
		}
		online.remove(removeMe);
		users.add(user);
	}
	/* vedere se ha senso
	public synchronized static Boolean isOnline(User user) {
		String username = user.getUsername();
		Iterator it = online.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        String ip = (String) pairs.getKey();
	        User u = (User) pairs.getValue();
	        if (u.getUsername().equals(username)) {
	        	return true;
	        }
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    return false;
	    
		for (User u : online) {
	        if (u.getIP().equals(ip)) {
	        	removeMe = u;
	        	break;
	        }
		}
	    
	}
	
	
	public synchronized static Boolean isOnline(String username) {
		Iterator it = online.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        String ip = (String) pairs.getKey();
	        User u = (User) pairs.getValue();
	        if (u.getUsername().equals(username)) {
	        	return true;
	        }
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    return false;
	}
	*/
	public synchronized static String validIP( String _ip ) {
		_ip = _ip.substring(1);
		Integer max = 0;
		for (User u : online) {
	        String[] splitted = u.getIP().split(":");
	        String ip = splitted[0];
	        if (ip.equals(_ip)) {
	        	if (max < Integer.parseInt(splitted[1]))
	        		max = Integer.parseInt(splitted[1]);
	        }
		}
		return _ip+":"+(++max);	
	}
	
	public synchronized static void stato() {
		String output = "";
		output +="**** UTENTI ONLINE ****\n";
		// online
		for (User u : online) {
			output += u;
		}
	    output +="**** UTENTI PERSISTENTI ****\n";
		// persistent
		for (User u : users) {
			output += u;
		}
	    System.out.println(output);
	}
	
	// getter and seters
	
	public synchronized static User addUser(User u) {
		users.add(u);
		return u;
	}
	
	public synchronized static void popolamento() {
		User u01 = addUser(new User("mattia","Mattia","Agostinetto","mattia@gmail.com","mattia", null));
		User u02 = addUser(new User("marco","Marco","Filippino","marco@gmail.com", "mattia", null));
		User u03 = addUser(new User("luigi","Luigi","De Giovanni","luigi@gmail.com","mattia", null));
		User u04 = addUser(new User("andrea","Andrea","Dall'Osto","andrea@gmail.com","mattia",null));
		User u05 = addUser(new User("giovanni","Giovanni","Marchiori","giovanni@gmail.com","mattia",null));
		User u06 = addUser(new User("massimo","Massimo","Griggio","massimo@gmail.com","mattia",null));
		User u07 = addUser(new User("armando","Armando","Caprio","armando@gmail.com","mattia",null));
		User u08 = addUser(new User("stefano","Stefano","Campese","stefano@gmail.com","mattia",null));
		User u09 = addUser(new User("nicolo","Nicolo","Toso","nicolo@gmail.com","mattia",null));
		User u10 = addUser(new User("nicolo2","Nicolo","Mazzucato","nicolo2@gmail.com","mattia",null));
		User u11 = addUser(new User("michael","Michael","Ferronato","michael@gmail.com","mattia",null));
		
		u01.newList("Amici");
		u01.newList("Parenti");
		u01.addUserToList("Amici", u02);
		u01.addUserToList("Amici", u03);
		u01.addUserToList("Amici", u04);
		u01.addUserToList("Amici", u05);
		u01.addUserToList("Amici", u06);
		u01.addUserToList("Parenti", u07);
		u01.addUserToList("Parenti", u08);
		u01.addUserToList("Parenti", u09);
		u01.addUserToList("Parenti", u10);
		u01.addUserToList("Parenti", u11);
	}
}
