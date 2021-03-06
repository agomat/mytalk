/**
* Filename: DataAccess.java
* Package: com.mytalk.server.data.storage
* Author: Michael Ferronato
* Date: 2013-04-12
*
* Diary:
*
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.4     | 2013-07-02 |    NM     | [+] Aggiunto metodo updateAccount e relativi commenti
* 0.3     | 2013-06-18 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.2     | 2013-04-15 |	NM	   | [+] Inseriti metodi alla classe
* 0.1	  |	2013-04-12 |    MF     | [+] Creazione classe    
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Fornisce le operazioni disponibili allo strato superiore sotto forma di metodi pubblici, le quali
* usano classi del package com.mytalk.server.storage.dao per avere accesso al database
*/

package com.mytalk.server.data.storage;

import java.util.ArrayList;
import java.util.List;
import com.mytalk.server.data.model.*;
import com.mytalk.server.data.storage.dao.*;
import com.mytalk.server.exceptions.*;


public class DataAccess implements IDataAccess{
	
	/**
	 * Costruttore della classe con corpo vuoto poiche' non vi sono campi da inizializzare
	 * 
	 * @method +DataAccess
	 */
	public DataAccess(){}
	
	/**
	 * Metodo che controlla se l'oggetto userObj ha parametri username e password esistenti e 
	 * corrispondenti nel database. In caso positivo ritorna true altrimenti ritorna false
	 * 
	 * @method +authenticateClient
	 * @param {User} userObj e' l'oggetto sul quale il metodo agisce
	 * @return {boolean}
	 */
	private boolean authenticateClient(User userObj){
		boolean esito=false;
		UserDAO ud=new UserDAO();
		User userEntity=ud.get(userObj.getUsername());
		if(userEntity==null){
			GenericDAO.closeSession();
			return esito;
		}else{
			String pwdUserEntity=userEntity.getPassword();
			String pwdUserObj=userObj.getPassword();
			pwdUserObj=MD5Converter.getHashMD5(pwdUserObj);
			if(userEntity!=null && pwdUserEntity.equals(pwdUserObj)){
				esito=true;
			}
			GenericDAO.closeSession();
			return esito;
		}
	}
		
	/**
	 * Cerca nel database un utente autenticato con ip passato e in caso positivo ritorna true, 
	 * altrimenti ritorna false
	 * 
	 * @method +checkUserByIp
	 * @param {String} ip e' l'indirizzo ip utilizzato per la ricerca 
	 * @return {boolean}
	 */
	public boolean checkUserByIp(String ip){
		OnlineUserDAO od=new OnlineUserDAO();
		boolean online=false;
		OnlineUser user=od.get(ip);
		if(user!=null){
			online=true;
		}
		GenericDAO.closeSession();
		return online;
	}
	
	/**
	 * Cerca nel database un utente autenticato con username name, ritornando true in caso 
	 * positivo o false in caso negativo
	 * 
	 * @method +checkUserByName
	 * @param {String} name e' il nome utilizzato per la ricerca
	 * @param {User} authenticate e' l'oggetto che contiene i dati necessari per l'autenticazione
	 * @exception {AuthenticationFailException} viene sollevata se fallisce l'autenticazione
	 */
	public boolean checkUserByName(String name, User authenticate) throws AuthenticationFailException{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			OnlineUserDAO od=new OnlineUserDAO();
			String ip=od.getUserIp(name);
			boolean online=false;
			if(ip!=null){
				online=true;
			}
			GenericDAO.closeSession();
			return online;
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFailException();
		}
	}
	
	/**
	 * Cerca nel database un utente autenticato con ip passato e ritorna l'username associato
	 * a quell'ip; se non lo trova solleva un'eccezione di tipo LogoutException
	 * 
	 * @method +getUsernameByIp
	 * @param {String} ip e' l'indirizzo ip necessario per la ricerca
	 * @return {String}
	 * @exception {LogoutException} viene sollevata se l'username non risulta online
	 */
	public String getUsernameByIp(String ip) throws LogoutException{
		OnlineUserDAO od=new OnlineUserDAO();
		boolean check=od.checkIpConnected(ip);
		if(check){
			String username=od.getUsernameByIp(ip);
			GenericDAO.closeSession();
			return username;
		}
		else{
			GenericDAO.closeSession();
			throw new LogoutException();
		}
	}
	
	/**
	 * Aggiunge un record di un utente con l'oggetto toCreate passato; solleva
	 * UsernameAlreadyExistingException se l'username dell'utente da creare non e' unico nel database
	 * 
	 * @method +createAccount
	 * @param {User} toCreate e' l'oggetto che contiene i dati necessari per la creazione 
	 * dell'account
	 * @return {void}
	 * @exception {UsernameAlreadyExistingException} viene sollevata se l'username e' gia' presente
	 */
	public void createAccount(User toCreate) throws UsernameAlreadyExistingException{
		UserDAO ud=new UserDAO();
		User existant=ud.get(toCreate.getUsername());
		if(existant!=null){
			GenericDAO.closeSession();
			throw new UsernameAlreadyExistingException();
		}else{
			ud.save(toCreate);
			GenericDAO.closeSession();
		}
	}
	
	/**
	 * Aggiorna nel database un record di un utente autenticato nel sistema con solo indirizzo 
	 * ip corrispondente a user.ip, aggiungendo l'username in user.username; solleva un'eccezione
	 *  UsernameNotCorrespondingException se l'utente che si autentica non e' lo stesso che ha inviato la 
	 *  richiesta da autenticare; solleva IpNotLoggedException se l'utente non risulta gia' autenticato con 
	 *  solo indirizzo ip; solleva UserAlreadyLoggedException se un utente sta cercando di autenticarsi 
	 *  con un nome gia' autenticato con altro indirizzo ip; solleva IpAlreadyLoggedException se l'ip 
	 *  dell'utente che si autentica e' gia' in uso da un altro utente
	 *  
	 *  @method +login
	 *  @param {OnlineUser} user e' l'oggetto che contiene tutti i dati necessari per fare il login
	 *  @param {User} authenticate e' l'oggetto che contiene i dati necessari per l'autenticazione
	 *  @return {void}
	 *  @exception {AuthenticationFailException} viene sollevata se fallisce l'autenticazione
	 *  @exception {UsernameNotCorrespondingException} viene sollevata se i dati di autenticazione e di login
	 *  sono inconsistenti
	 *  @exception {IpNotLoggedException} viene sollevata se l'ip del utente che fa il login non e' online
	 *  @exception {UserAlreadyLoggedException} viene sollevata se l'utente e' gia' online
	 *  @exception {IpAlreadyLoggedException} viene sollevata se l'ip dell'utente e' gia' online
	 */
	public void login(OnlineUser user, User authenticate) throws AuthenticationFailException, UsernameNotCorrespondingException, IpNotLoggedException, UserAlreadyLoggedException, IpAlreadyLoggedException{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			String onlineUsername=user.getUsername();
			String authUsername=authenticate.getUsername();
			if(!onlineUsername.equals(authUsername)){ //username di authenticate e di user non corrispondenti
				GenericDAO.closeSession();
				throw new UsernameNotCorrespondingException();
			}else{
				OnlineUserDAO od=new OnlineUserDAO();
				String userIp=user.getIp();
				String userName=user.getUsername();
				boolean connected=od.checkIpConnected(userIp);
				boolean userConnected=od.checkUsernameConnected(userName);
				OnlineUser newOnline=od.get(userIp);
				if(!connected){		//ip gia' connesso
					GenericDAO.closeSession();
					throw new IpNotLoggedException();
				}
				else if(userConnected){	//username gia' connesso
					GenericDAO.closeSession();
					throw new UserAlreadyLoggedException();
				}
				else if(newOnline.getUsername()!=null){		//ip gia' in uso da un user
					GenericDAO.closeSession();
					throw new IpAlreadyLoggedException();
				}else{
					newOnline.setUsername(userName);
					od.update(newOnline);
					GenericDAO.closeSession();	
				}
			}
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFailException();
		}
	}
	
	/**
	 * Restituisce le liste dell'utente authenticate che autentica la richiesta
	 * 
	 * @method +userLists
	 * @param {User} authenticate e' l'oggetto che contiene i dati necessari per l'autenticazione
	 * @return {List<ListName>}
	 * @exception {AuthenticationFailException} viene sollevata se fallisce l'autenticazione
	 */
	public List<ListName> userLists(User authenticate) throws AuthenticationFailException{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			String user=authenticate.getUsername();
			ListNameDAO ld=new ListNameDAO();
			List<ListName> list=ld.getUserLists(user);
			GenericDAO.closeSession();
			return list;
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFailException();
		}
	}
	
	/**
	 * Restituisce gli utenti della lista list di proprieta' dell'utente che ha autenticato 
	 * la richiesta; solleva UsernameNotCorrespondingException se la lista passata non e' di proprieta' 
	 * dell'utente autenticato
	 * 
	 * @method +getListUsers
	 * @param {ListName} list e' l'oggetto che contiene i dati necessari della lista
	 * @param {User} authenticate e' l'oggetto che contiene i dati necessari per l'autenticazione
	 * @return {List<User>} 
	 * @exception {AuthenticationFailException} viene sollevata quando fallisce l'autenticazione
	 * @exception {UsernameNotCorrespondingException} viene sollevata se l'username di autenticazione e 
	 * l'username proprietario della lista non corrispondono 
	 */
	public List<User> getListUsers(ListName list, User authenticate) throws AuthenticationFailException, UsernameNotCorrespondingException{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			UserListDAO ld=new UserListDAO();
			int listId=list.getId();
			ListNameDAO lnd=new ListNameDAO();
			ListName listCheck=lnd.get(listId);
			if(!listCheck.getOwner().equals(authenticate.getUsername())){ //controllo che la lista sia effettivamente dell'utente
				GenericDAO.closeSession();
				throw new UsernameNotCorrespondingException();
			}else{
				List<UserList> associations=ld.getUsersInList(listId);
				UserDAO ud=new UserDAO();
				List<User> users=new ArrayList<User>();
				for(int i=0; i<associations.size();i++){
					String username=associations.get(i).getUsername();
					User u=ud.get(username);
					users.add(u);
				}
				GenericDAO.closeSession();
				return users;
			}
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFailException();
		}
	}
	
	/**
	 * Restituisce gli utenti registrati che risultano al momento autenticati con username
	 * 
	 * @method +getOnlineUsers
	 * @param {User} authenticate e' l'oggetto che contiene i dati necessari per l'autenticazione
	 * @return {List<User>}
	 * @exception {AuthenticationFailException} viene sollevata quando fallisce l'autenticazione
	 */
	public List<User> getOnlineUsers(User authenticate) throws AuthenticationFailException{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			OnlineUserDAO od=new OnlineUserDAO();
			List<OnlineUser> list=od.getOnlineUsers();
			List<User> users=new ArrayList<User>();
			UserDAO ud=new UserDAO();
			String username=null;
			for(int i=0;i<list.size();i++){
				username=list.get(i).getUsername();
				User u=ud.get(username);
				users.add(u);
			}
			GenericDAO.closeSession();
			return users;
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFailException();
		}
	}
	
	/**
	 * Restituisce gli utenti registrati che non risultano al momento autenticati con
	 * username
	 * 
	 * @method +getOfflineUsers
	 * @param {User} authenticate e' l'oggetto che contiene i dati necessari per l'autenticazione
	 * @return {List<User>}
	 * @exception {AuthenticationFailException} viene sollevata quando fallisce l'autenticazione
	 */
	public List<User> getOfflineUsers(User authenticate) throws AuthenticationFailException{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			UserDAO ud=new UserDAO();
			List<User> users=ud.getOfflineUsers();
			GenericDAO.closeSession();
			return users;
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFailException();
		}
	}
	
	/**
	 * Elimina un record di un utente autenticato avente lo stesso indirizzo ip dell'oggetto 
	 * user passato
	 * 
	 * @method +logout
	 * @param {OnlineUser} user e' l'oggetto che contiene i dati necessari al logout
	 * @return {void}
	 * @exception {LogoutException} viene sollevata se l'utente che richiede il logout non e' online
	 */
	public void logout(OnlineUser user)throws LogoutException{
		OnlineUserDAO od=new OnlineUserDAO();
		String ip=user.getIp();
		user=od.get(ip);
		if(user==null){
			GenericDAO.closeSession();
			throw new LogoutException();
		}else{
			od.delete(user);
			GenericDAO.closeSession();
		}
	}
	
	/**
	 * Crea un record di una lista utente corrispondente a list; solleva ListAlreadyExistsException 
	 * se la lista che si sta creando esiste gia'; solleva UsernameNotCorrespondingException se la lista 
	 * da creare non e' dell'utente che ha autenticato la richiesta
	 * 
	 * @method +listCreate
	 * @param {ListName} list e' l'oggetto che contiene i dati relativi alla lista da creare
	 * @param {User} authenticate e' l'oggetto che contiene i dati necessari per l'autenticazione
	 * @return {void}
	 * @exception {AuthenticationFailException} viene sollevata quando fallisce l'autenticazione
	 * @exception {ListAlreadyExistsException} viene sollevata se la lista esiste gia'
	 * @exception {UsernameNotCorrespondingException} viene sollevata se l'username di autenticazione e 
	 * l'username proprietario della lista non corrispondono
	 */
	public void listCreate(ListName list, User authenticate) throws AuthenticationFailException,ListAlreadyExistsException,UsernameNotCorrespondingException{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			String authUsername=authenticate.getUsername();
			String listUsername=list.getOwner();
			if(!listUsername.equals(authUsername)){
				GenericDAO.closeSession();
				throw new UsernameNotCorrespondingException();
			}else{
				ListNameDAO ld=new ListNameDAO();
				ListName listFound=ld.getByNameOwner(list);
				if (listFound!=null){
					GenericDAO.closeSession();
					throw new ListAlreadyExistsException(); 
				}else{
					ld.save(list);
					GenericDAO.closeSession();
				}
			}
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFailException();
		}
	}
	
	/**
	 * Elimina il record corrispondente alla lista list passata; solleva ListNotExistingException se 
	 * la lista che si sta eliminando non esiste; solleva UsernameNotCorrespondingException se la lista 
	 * da eliminare non e' dell'utente che ha autenticato la richiesta
	 * 
	 * @method +listDelete
	 * @param {ListName} list e' l'oggetto che contiene i dati relativi alla lista da eliminare
	 * @param {User} authenticate e' l'oggetto che contiene i dati necessari per l'autenticazione
	 * @return {void}
	 * @exception {AuthenticationFailException} viene sollevata quando fallisce l'autenticazione
	 * @exception {ListNotExistingException} viene sollevata se la lista da eliminare non esiste
	 * @exception {UsernameNotCorrespondingException} viene sollevata se l'username di autenticazione e 
	 * l'username proprietario della lista non corrispondono
	 */
	public void listDelete(ListName list, User authenticate) throws AuthenticationFailException,ListNotExistingException,UsernameNotCorrespondingException{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			String authUsername=authenticate.getUsername();
			String listUsername=list.getOwner();
			if(!listUsername.equals(authUsername)){
				GenericDAO.closeSession();
				throw new UsernameNotCorrespondingException();
			}else{
				ListNameDAO ld=new ListNameDAO();
				ListName listFound=ld.getByNameOwner(list);
				if (listFound==null){
					GenericDAO.closeSession();
					throw new ListNotExistingException(); //la lista non esiste
				}else{
					ld.delete(listFound);
					GenericDAO.closeSession();
				}
			}
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFailException();
		}
	}
	
	/**
	 * Aggiunge l'utente con username user alla lista list; solleva UserAlreadyListedException se l'utente 
	 * da aggiungere e' gia' presente nella lista; solleva UserNotExistingException se user non e' il nome 
	 * di un utente valido o se e' il nome dell'utente proprietario della lista; solleva 
	 * ListNotExistingException se la lista a cui aggiungere l'utente non esiste; solleva
	 *  UsernameNotCorrespondingException se la lista a cui aggiungere l'utente non e' di proprieta' 
	 *  dell'utente che ha autenticato la richiesta
	 *  
	 *  @method +userListAdd
	 *  @param {ListName} list e' l'oggetto che contiene i dati relativi alla lista
	 *  @param {String} user e' l'oggetto che identifica l'utente da aggiungere alla lista
	 *  @param {User} authenticate e' l'oggetto che contiene i dati necessari per l'autenticazione
	 *  @return {void}
	 *  @exception {AuthenticationFailException} viene sollevata quando fallisce l'autenticazione
	 *  @exception {UserAlreadyListedException} viene sollevata se l'utente e' gia' presente nella lista
	 *  @exception {UserNotExistingException} viene sollevata se l'utente da inserire non esiste
	 *  @exception {UsernameNotCorrespondingException} viene sollevata se l'username di autenticazione e 
	 *  l'username proprietario della lista non corrispondono
	 *  @exception {ListNotExistingException} viene sollevata se la lista non esiste
	 */
	public void userListAdd(ListName list,String user, User authenticate) throws AuthenticationFailException,UserAlreadyListedException, UserNotExistingException, UsernameNotCorrespondingException, ListNotExistingException{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			UserDAO ud=new UserDAO();
			User toCheck=ud.get(user);
			String username=authenticate.getUsername();
			if(toCheck==null || user.equals(username)){
				GenericDAO.closeSession();
				throw new UserNotExistingException();
			}else{
				String owner=list.getOwner();
				if(!owner.equals(username)){
					GenericDAO.closeSession();
					throw new UsernameNotCorrespondingException();
				}else{
					ListNameDAO ld=new ListNameDAO();
					ListName dbList=ld.getByNameOwner(list);
					if(dbList==null){
						GenericDAO.closeSession();
						throw new ListNotExistingException();
					}else{
						Integer Id=dbList.getId();
						UserListDAO uld=new UserListDAO();
						UserList u=uld.get(Id,user);
						if(u!=null){
							GenericDAO.closeSession();
							throw new UserAlreadyListedException(); //l'utente e` gia` nella lista
						}else{
							UserList newUser=new UserList(Id,user);
							uld.save(newUser);
							GenericDAO.closeSession();
						}
					}
				}
			}
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFailException();
		}
	}
	
	/**
	 * Rimuove l'utente con username user dalla lista list; solleva UserNotListedException se l'utente da 
	 * aggiungere non e' presente nella lista; solleva UserNotExistingException se user non e' il nome 
	 * di un utente valido; solleva ListNotExistingException se la lista a cui rimuovere l'utente non 
	 * esiste; solleva UsernameNotCorrespondingException se la lista a cui rimuovere l'utente non e' di 
	 * proprieta' dell'utente che ha autenticato la richiesta
	 * 
	 * @method +userListRemove
	 * @param {ListName} list e' l'oggetto che contiene i dati relativi alla lista
	 * @param {String} user e' l'oggetto che contiene l'username da rimuovere dalla lista
	 * @param {User} authenticate e' l'oggetto che contiene i dati necessari per l'autenticazione
	 * @return {void}
	 * @exception {AuthenticationFailException} viene sollevata quando fallisce l'autenticazione
	 * @exception {UserNotListedException} viene sollevata se l'utente non e' presente nella lista
	 * @exception {UserNotExistingException} viene sollevata se l'utente non esiste
	 * @exception {UsernameNotCorrespondingException} viene sollevata se l'username di autenticazione e
	 *  l'username proprietario della lista non corrispondono
	 *@exception  {ListNotExistingException} viene sollevata se la lista non esiste
	 */
	public void userListRemove(ListName list,String user, User authenticate) throws AuthenticationFailException,UserNotListedException,UserNotExistingException, UsernameNotCorrespondingException, ListNotExistingException{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			UserDAO ud=new UserDAO();
			User toCheck=ud.get(user);
			String username=authenticate.getUsername();
			if(toCheck==null || user.equals(username)){
				GenericDAO.closeSession();
				throw new UserNotExistingException();
			}else{
				String owner=list.getOwner();
				if(!owner.equals(username)){
					GenericDAO.closeSession();
					throw new UsernameNotCorrespondingException();
				}else{
					ListNameDAO ld=new ListNameDAO();
					ListName dbList=ld.getByNameOwner(list);
					if(dbList==null){
						GenericDAO.closeSession();
						throw new ListNotExistingException();
					}else{
						Integer Id=dbList.getId();
						UserListDAO uld=new UserListDAO();
						UserList u=uld.get(Id,user);
						if(u==null){
							GenericDAO.closeSession();
							throw new UserNotListedException(); //l'utente non e` presente nella lista
						}else{
							uld.delete(u);
							GenericDAO.closeSession();
						}
					}
				}
			}
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFailException();
		}
	}
	
	/**
	 * Aggiunge un record di utente autenticato con solo indirizzo ip corrispondente all'oggetto
	 * user passato; solleva IpAlreadyLoggedException se e' gia' presente un record con lo stesso indirizzo
	 * ip
	 * 
	 * @method +loginAsAnonymous
	 * @param {OnlineUser} user e' l'oggetto che contiene i dati necessari a fare il login anonimo
	 * @return {void}
	 * @exception {IpAlreadyLoggedException} viene sollevata l'ip e' gia' online
	 */
	public void loginAsAnonymous(OnlineUser user)throws IpAlreadyLoggedException{
		OnlineUserDAO ou=new OnlineUserDAO();
		String ip=user.getIp();
		String username=user.getUsername();
		if(username!=null){
			user.setUsername(null);
		}
		boolean checkIp=ou.checkIpConnected(ip);
		if(checkIp){
			GenericDAO.closeSession();
			throw new IpAlreadyLoggedException();
		}else{
			ou.save(user);
			GenericDAO.closeSession();
		}
	}
	
	/**
	 * Restituisce gli utenti nella blacklist dell'utente corrispondente a authenticate
	 * 
	 * @method +getUserBlacklist
	 * @param {User} authenticate e' l'oggetto che contiene i dati necessari all'autenticazione
	 * e a definire l'utente che richiede la blacklist
	 * @return {List<User>}
	 * @exception {AuthenticationFailException} viene sollevata quando fallisce l'autenticazione
	 */
	public List<User> getUserBlacklist(User authenticate) throws AuthenticationFailException{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			List<User> listOfUser=new ArrayList<User>();
			Blacklist b=null;
			BlacklistDAO bd=new BlacklistDAO();
			UserDAO ud=new UserDAO();
			String username=authenticate.getUsername();
			List<Blacklist> blacklistList=bd.getUserBlacklist(username);
			for(int i=0;i<blacklistList.size();i++){
				b=blacklistList.get(i);
				listOfUser.add(ud.get(b.getUsername()));
			}
			GenericDAO.closeSession();
			return listOfUser; 
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFailException();
		}
	}
	
	/**
	 * Aggiunge un record di un utente con username b.username alla blacklist dell'utente b.owner; 
	 * solleva UserAlreadyBlacklistedException se il record in questione esiste gia'; solleva 
	 * UsernameNotCorrespondingException se b.owner non corrisponde all'username dell'utente che ha 
	 * autenticato la richiesta; solleva UserNotExistingException se il parametro b.username non corrisponde 
	 * all'username di un utente esistente
	 * 
	 * @method +blacklistAdd
	 * @param {Blacklist} b e' l'oggetto che contiene i dati necessari a fare l'aggiunta alla 
	 * blacklist
	 * @param {User} authenticate e' l'oggetto che contiene i dati necessari per l'autenticazione
	 * @return {void}
	 * @exception {AuthenticationFailException} viene sollevata quando fallisce l'autenticazione
	 * @exception {UserAlreadyBlacklistedException} viene sollevata se l'utente e' gia' nella blacklist
	 * @exception {UsernameNotCorrespondingException} viene sollevata se l'username di autenticazione e
	 * l'username che ne fa richiesta non corrispondono
	 * @exception {UserNotExistingException} viene sollevata se l'utente da aggiungere alla blacklist
	 * non esiste
	 */
	public void blacklistAdd(Blacklist b, User authenticate) throws AuthenticationFailException,UserAlreadyBlacklistedException, UsernameNotCorrespondingException, UserNotExistingException{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			String owner=b.getOwner();
			String username=authenticate.getUsername();
			String toBlacklist=b.getUsername();
			UserDAO ud=new UserDAO();
			User u=ud.get(toBlacklist);
			if(u==null || username.equals(toBlacklist)){
				GenericDAO.closeSession();
				throw new UserNotExistingException();
			}else{
				if(!owner.equals(username)){
					GenericDAO.closeSession();
					throw new UsernameNotCorrespondingException();
				}else{
					BlacklistDAO bd=new BlacklistDAO();
					Blacklist checkUser=bd.get(owner, toBlacklist);
					if(checkUser!=null){
						GenericDAO.closeSession();
						throw new UserAlreadyBlacklistedException();
					}else{
						bd.save(b);
						GenericDAO.closeSession();
					}
				}
			}
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFailException();
		}
	}
		
	/**
	 * Rimuove un record di un utente con username b.username dalla blacklist dell'utente b.owner;
	 *  solleva UserNotBlacklistedException se il record in questione non esiste; solleva 
	 *  UsernameNotCorrespondingException se b.owner non corrisponde all'username dell'utente che ha 
	 *  autenticato la richiesta
	 *  
	 *  @method +blacklistRemove
	 *  @param {Blacklist} b e' l'oggetto che contiene i dati dell'utente da togliere dalla blacklist
	 *  @param {User} authenticate e' l'oggetto che contiene i dati necessari per l'autenticazione
	 *  @return {void}
	 *  @exception {AuthenticationFailException} viene sollevata quando fallisce l'autenticazione
	 *  @exception {UserNotBlacklistedException} viene sollevata se l'utente non e' presente nella blacklist
	 *  @exception {UsernameNotCorrespondingException} viene sollevata se l'username di autenticazione e
	 * l'username che ne fa richiesta non corrispondono
	 */
	public void blacklistRemove(Blacklist b, User authenticate) throws AuthenticationFailException,UserNotBlacklistedException, UsernameNotCorrespondingException{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			String owner=b.getOwner();
			String username=authenticate.getUsername();
			String toBlacklist=b.getUsername();
			if(!owner.equals(username)){
				GenericDAO.closeSession();
				throw new UsernameNotCorrespondingException();
			}else{
				BlacklistDAO bd=new BlacklistDAO();
				Blacklist checkUser=bd.get(owner, toBlacklist);
				if(checkUser==null){
					GenericDAO.closeSession();
					throw new UserNotBlacklistedException();
				}else{
					bd.delete(checkUser);
					GenericDAO.closeSession();
				}
			}
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFailException();
		}
	}
		
	/**
	 * restituisce una lista di oggetti Call che rappresentano le chiamate in cui l'utente 
	 * authenticate figura come caller o receiver
	 * 
	 * @method +getCalls
	 * @param {User} authenticate e' l'oggetto che contiene i dati necessari per l'autenticazione
	 * @return {List<Call>}
	 * @exception {AuthenticationFailException} viene sollevata quando fallisce l'autenticazione
	 */
	public List<Call> getCalls(User authenticate) throws AuthenticationFailException{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			String username=authenticate.getUsername();
			CallDAO cd=new CallDAO();
			List<Call> calls=cd.getAllUserCalls(username);
			GenericDAO.closeSession();
			return calls;
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFailException();
		}
	}
	
	/**
	 * Aggiunge un record di una chiamata corrispondente all'oggetto call passato
	 * 
	 * @method +addCall
	 * @param {Call} callObj e' l'oggetto che contiene i dati relativi alla chiamanta da aggiungere
	 * @param {User} authenticate e' l'oggetto che contiene i dati necessari per l'autenticazione
	 * @return {void}
	 * @exception {AuthenticationFailException} viene sollevata quando fallisce l'autenticazione
	 */
	public void addCall(Call callObj, User authenticate) throws AuthenticationFailException{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			CallDAO cd=new CallDAO();
			cd.save(callObj);
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFailException();
		}
	}
	
	
	/**
	 * Elimina il record di un utente corrispondente all'oggetto userObj passato
	 * 
	 * @method +deleteAccount
	 * @param {User} userObj e' l'oggetto che contiene i dati dell'account da eliminare
	 * @return {void}
	 * @exception {AuthenticationFailException} viene sollevata quando fallisce l'autenticazione
	 */
	public void deleteAccount(User userObj) throws AuthenticationFailException{
		boolean authenticated=authenticateClient(userObj);
		if(authenticated==true){
			UserDAO ud=new UserDAO();
			User u=ud.get(userObj.getUsername());
			ud.delete(u);
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFailException();
		}
	}
	
	/**
	 * Aggiorna i dati dell'utente usando quelli contenuti nell'oggetto user passato; solleva 
	 * UsernameNotCorrespondingException se user.username non corrisponde all'username dell'utente
	 *  che ha autenticato la richiesta
	 *  
	 * @method +updateAccount
	 * @param {User} user e' l'oggetto utilizzato per fare l'aggiornamento del record 
	 * corrispondente
	 * @param {User} authenticate e' l'oggetto utilizzato per fare l'autenticazione
	 * @return {void}
	 * @exception {AuthenticationFailException} viene sollevata quando fallisce l'autenticazione
	 * @exception {UsernameNotCorrespondingException} viene sollevata se l'username di autenticazione e
	 * l'username che ne fa richiesta non corrispondono
	 */
	public void updateAccount(User user, User authenticate) throws AuthenticationFailException, UsernameNotCorrespondingException{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			String usernameAuth=authenticate.getUsername();
			String usernameNew=user.getUsername();
			if(!usernameAuth.equals(usernameNew)){
				GenericDAO.closeSession();
				throw new UsernameNotCorrespondingException();
			}else{
				UserDAO ud=new UserDAO();
				User dbUser=ud.get(usernameNew);
				if(!dbUser.getEmail().equals(user.getEmail())){
					dbUser.setEmail(user.getEmail());
					dbUser.setEmailHash(user.getEmailHash());
				}
				if(!dbUser.getPassword().equals(user.getPassword())){
					dbUser.setPassword(user.getPassword());
				}
				if(!dbUser.getName().equals(user.getName())){
					dbUser.setName(user.getName());
				}
				if(!dbUser.getSurname().equals(user.getSurname())){
					dbUser.setSurname(user.getSurname());
				}
				ud.update(dbUser);
				GenericDAO.closeSession();
			}
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFailException();
		}
	}
	
	/**
	 * Rinomina la lista list con un nuovo nome name; solleva UsernameNotCorrespondingException se
	 * la lista non e' di proprieta' dell'utente che ha autenticato la richiesta; solleva 
	 * ListNotExistingException se la lista passata, a cui cambiare nome, non esiste; solleva 
	 * ListAlreadyExistsException se la lista per quell'utente con quel nome esiste gia'
	 * 
	 * @method +renameList
	 * @param {ListName} list e' l'oggetto che contiene i dati relativi alla lista da rinominare
	 * @param {String} name e' l'oggetto che contiene il nuovo nome della lista
	 * @param {User} authenticate e' l'oggetto che contiene i dati necessari per l'autenticazione
	 * @return {void}
	 * @exception {AuthenticationFailException} viene sollevata quando fallisce l'autenticazione
	 * @exception {UsernameNotCorrespondingException} viene sollevata se l'username di autenticazione e 
	 * l'username del proprietario della lista non corrispondono
	 * @exception {ListNotExistingException} viene sollevata se la lista da rinominare non esiste
	 * @exception {ListAlreadyExistsException} viene sollevata se il nuovo nome corrisponde ad un'altra
	 * lista di proprieta' dello stesso utente
	 */
	public void renameList(ListName list, String name, User authenticate) throws AuthenticationFailException,UsernameNotCorrespondingException,ListNotExistingException,ListAlreadyExistsException{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			String usernameAuth=authenticate.getUsername();
			String usernameList=list.getOwner();
			if(!usernameAuth.equals(usernameList)){
				GenericDAO.closeSession();
				throw new UsernameNotCorrespondingException();
			}else{
				ListNameDAO ld=new ListNameDAO();
				ListName checkList=ld.getByNameOwner(list);
				if(checkList==null){
					GenericDAO.closeSession();
					throw new ListNotExistingException();
				}else{
					ListName newList=new ListName(name,usernameList);
					ListName checkName=ld.getByNameOwner(newList);
					if(checkName!=null){
						GenericDAO.closeSession();
						throw new ListAlreadyExistsException();
					}else{
						checkList.setName(name);
						ld.update(checkList);
						GenericDAO.closeSession();
					}
				}
			}
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFailException();
		}
	}
	
	/**
	 * Ritorna l'oggetto user avente parametro id uguale a quello passato; solleva IdNotFoundException 
	 * se non esiste un record avente l'id cercato
	 * 
	 * @method +getUserById
	 * @param {int} id e' il valore dell'id dell'utente da cercare
	 * @return {User}
	 * @exception {IdNotFoundException} viene sollevata se l'id non e' associato a nessun utente registrato
	 */
	public User getUserById(int id) throws IdNotFoundException{
		UserDAO ud=new UserDAO();
		User requested=ud.getById(id);
		if(requested==null){
			GenericDAO.closeSession();
			throw new IdNotFoundException();
		}else{
			GenericDAO.closeSession();
			return requested;
		}
	}
	
	/**
	 * Ritorna l'indirizzo ip dell'utente autenticato con username username; solleva UserNotLoggedException 
	 * se l'utente username non e' autenticato
	 * 
	 * @method +getUserIp
	 * @param {String} username e' l'oggetto che contiene i dati relativi all'utente di cui cercare 
	 * l'indirizzo ip
	 * @return {String}
	 * @exception {UserNotLoggedException} viene sollevata se l'utente non e' autenticato
	 */
	public String getUserIp(String username)throws UserNotLoggedException{
		OnlineUserDAO od=new OnlineUserDAO();
		if(username.isEmpty()){
			GenericDAO.closeSession();
			throw new UserNotLoggedException();
		}else{
			String ip=od.getUserIp(username);
			if(ip==null){
				GenericDAO.closeSession();
				throw new UserNotLoggedException();
			}else{
				GenericDAO.closeSession();
				return ip;
			}
		}
	}
	
	/**
	 * Ritorna l'id dell'utente con username username; solleva UserNotExistingException se non esiste un
	 * utente con tale username
	 * 
	 * @method +getIdFromUsername
	 * @param {String} username e' l'oggetto che contiene i dati dell'utente di cui trovare l'id
	 * @return {int}
	 * @exception {UserNotExistingException} viene sollevata se l'utente non esiste
	 */
	public int getIdFromUsername(String username)throws UserNotExistingException{
		UserDAO ud=new UserDAO();
		User user=ud.get(username);
		if(user==null){
			GenericDAO.closeSession();
			throw new UserNotExistingException();
		}else{
			GenericDAO.closeSession();
			int id=user.getId();
			return id;
		}
	}
	
	/**
	 * Imposta a null il valore username di un record di un utente autenticato corrispondente a
	 * user; solleva LogoutException se non esiste un record corrispondente a user
	 * 
	 * @method +logoutToAnonymous
	 * @param {OnlineUser} user e' l'oggetto che contiene i dati necessarie per il logout ad anonimo
	 * @return {void}
	 * @exception {LogoutException} viene sollevata se l'utente non e' online
	 */
	public void logoutToAnonymous(OnlineUser user)throws LogoutException{
		OnlineUserDAO od=new OnlineUserDAO();
		String ip=user.getIp();
		OnlineUser unlogged=od.get(ip);
		if(unlogged==null){
			GenericDAO.closeSession();
			throw new LogoutException();
		}else{
			unlogged.setUsername(null);
			od.update(unlogged);
			GenericDAO.closeSession();
		}
	}
	
	/**
	 * 
	 * Verifica se esiste un record corrispondente all'oggetto passato blacklistObj e in caso 
	 * postivo restituisce true, altrimenti restituisce false
	 * 
	 * @method +checkBlacklist
	 * @param {Blacklist} blacklistObj e' l'oggetto che contiene i dati dell'utente della blacklist
	 * da verificare
	 * @return {boolean}
	 * 
	 */
	public boolean checkBlacklist(Blacklist blacklistObj){
		BlacklistDAO bd=new BlacklistDAO();
		String owner=blacklistObj.getOwner();
		String user=blacklistObj.getUsername();
		Blacklist checkUser=bd.get(owner, user);
		boolean result=false;
		if(checkUser!=null){
			result=true;
		}
		GenericDAO.closeSession();
		return result;
	}
}
