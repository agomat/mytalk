/**
* Filename: DataAccess.java
* Package: com.mytalk.server.data.storage
* Author: Michael Ferronato
* Date: 2013-04-12
*
* Diary:
*
* Version |
Date
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-04-12 | MF        | [+] Creazione classe    
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.data.storage;

import java.util.ArrayList;
import java.util.List;
import com.mytalk.server.data.model.*;
import com.mytalk.server.data.storage.dao.*;
import com.mytalk.server.exceptions.*;


public class DataAccess implements IDataAccess{
	
	
	public DataAccess(){}
	
	//verifica dati autenticazione
	private boolean authenticateClient(User userObj){
		boolean esito=false;
		UserDAO ud=new UserDAO();
		User userEntity=ud.get(userObj.getUsername());
		if(userEntity==null){
			return esito;
		}
		String pwdUserEntity=userEntity.getPassword();
		String pwdUserObj=userObj.getPassword();
		if(userEntity!=null && pwdUserEntity.equals(pwdUserObj)){
			esito=true;
		}
		return esito;
	}
		
	//verifica la presenza dell'ip nella tabella OnlineUser
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
	
	//verifica la presenza dell'username nella tabella OnlineUser
	public boolean checkUserByName(String name, User authenticate) throws AuthenticationFail{
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
			throw new AuthenticationFail();
		}
	}
	
	//crea un account nel database
	public void createAccount(User toCreate) throws UsernameAlreadyExisting{
		UserDAO ud=new UserDAO();
		User existant=ud.get(toCreate.getName());
		if(existant==null){
			ud.save(toCreate);
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			throw new UsernameAlreadyExisting();
		}
	}
	
	// aggiunge un record sulla tabella OnlineUser
	public void login(OnlineUser user, User authenticate) throws AuthenticationFail, UsernameNotCorresponding, IpNotLogged, UserAlreadyLogged, IpAlreadyLogged{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			String onlineUsername=user.getUsername();
			String authUsername=authenticate.getUsername();
			if(!onlineUsername.equals(authUsername)){ //username di authenticate e di user non corrispondenti
				GenericDAO.closeSession();
				throw new UsernameNotCorresponding();
			}
			OnlineUserDAO od=new OnlineUserDAO();
			String userIp=user.getIp();
			String userName=user.getUsername();
			boolean connected=od.checkIpConnected(userIp);
			boolean userConnected=od.checkUsernameConnected(userName);
			if(connected){		//ip già connesso
				if(!userConnected){	//username già connesso
					OnlineUser newOnline=od.get(userIp);
					if(newOnline.getUsername()!=null){		//ip già in uso da un user
						GenericDAO.closeSession();
						throw new IpAlreadyLogged();
					}else{
						newOnline.setUsername(userName);
						od.update(newOnline);
						GenericDAO.closeSession();
					}
				}else{
					GenericDAO.closeSession();
					throw new UserAlreadyLogged();
				}
			}else{
				GenericDAO.closeSession();
				throw new IpNotLogged();
			}
			
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	//interroga il db e restituisce le liste dell'utente
	public List<ListName> userLists(User authenticate) throws AuthenticationFail{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			String user=authenticate.getUsername();
			ListNameDAO ld=new ListNameDAO();
			List<ListName> list=ld.getUserLists(user);
			GenericDAO.closeSession();
			return list;
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	//interroga il db e restituisce gli utenti di una lista
	public List<User> getListUsers(ListName list, User authenticate) throws AuthenticationFail, UsernameNotCorresponding{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			UserListDAO ld=new UserListDAO();
			int listId=list.getId();
			ListNameDAO lnd=new ListNameDAO();
			ListName listCheck=lnd.get(listId);
			if(listCheck.getOwner().equals(authenticate.getUsername())){ //controllo che la lista sia effettivamente dell'utente
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
			}else{
				GenericDAO.closeSession();
				throw new UsernameNotCorresponding();
			}
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	//restituisce una lista di user che identifica tutti gli utenti OnlineUser
	public List<User> getOnlineUsers(User authenticate) throws AuthenticationFail{
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
			throw new AuthenticationFail();
		}
	}
	
	public List<User> getOfflineUsers(User authenticate) throws AuthenticationFail{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			UserDAO ud=new UserDAO();
			List<User> users=ud.getOfflineUsers();
			GenericDAO.closeSession();
			return users;
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	//elimina dalla tabella OnlineUser il record corrispondente
	public void logout(OnlineUser user)throws LogoutException{
		OnlineUserDAO od=new OnlineUserDAO();
		String ip=user.getIp();
		user=od.get(ip);
		if(user!=null){
			od.delete(user);
		}else{
			GenericDAO.closeSession();
			throw new LogoutException();
		}
		GenericDAO.closeSession();
	}
	
	// verifica che non sia già presente la lista per quell'user e in caso negativo aggiunge un record
	public void listCreate(ListName list, User authenticate) throws AuthenticationFail,ListAlreadyExists,UsernameNotCorresponding{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			String authUsername=authenticate.getUsername();
			String listUsername=list.getOwner();
			if(!listUsername.equals(authUsername)){
				GenericDAO.closeSession();
				throw new UsernameNotCorresponding();
			}
			ListNameDAO ld=new ListNameDAO();
			ListName listFound=ld.getByNameOwner(list);
			if (listFound==null){
				ld.save(list);
				GenericDAO.closeSession();
			}else{
				GenericDAO.closeSession();
				throw new ListAlreadyExists(); //la lista esiste già con lo stesso nome
			}
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	// verifica che sia presente la lista per quell'user e in caso positivo rimuove il record
	public void listDelete(ListName list, User authenticate) throws AuthenticationFail,ListNotExisting,UsernameNotCorresponding{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			String authUsername=authenticate.getUsername();
			String listUsername=list.getOwner();
			if(!listUsername.equals(authUsername)){
				GenericDAO.closeSession();
				throw new UsernameNotCorresponding();
			}
			ListNameDAO ld=new ListNameDAO();
			ListName listFound=ld.getByNameOwner(list);
			if (listFound!=null){
				ld.delete(listFound);
				GenericDAO.closeSession();
			}else{
				GenericDAO.closeSession();
				throw new ListNotExisting(); //la lista non esiste
			}
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	//verifica che non sia già presente nella lista quell'user e in caso negativo lo inserisce nella lista
	public void userListAdd(ListName list,String user, User authenticate) throws AuthenticationFail,UserAlreadyListed, UserNotExisting, UsernameNotCorresponding, ListNotExisting{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			UserDAO ud=new UserDAO();
			User toCheck=ud.get(user);
			String username=authenticate.getUsername();
			if(toCheck==null || user.equals(username)){
				GenericDAO.closeSession();
				throw new UserNotExisting();
			}
			String owner=list.getOwner();
			if(!owner.equals(username)){
				GenericDAO.closeSession();
				throw new UsernameNotCorresponding();
			}
			ListNameDAO ld=new ListNameDAO();
			ListName dbList=ld.getByNameOwner(list);
			if(dbList==null){
				GenericDAO.closeSession();
				throw new ListNotExisting();
			}
			Integer Id=dbList.getId();
			UserListDAO uld=new UserListDAO();
			UserList u=uld.get(Id,user);
			if(u==null){
				UserList newUser=new UserList(Id,user);
				uld.save(newUser);
				GenericDAO.closeSession();
			}else{
				GenericDAO.closeSession();
				throw new UserAlreadyListed(); //l'utente e` gia` nella lista
			}
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	//verifica che sia presente la lista per quell'user e prende l'id della lista dalla tabella List e elimina un record dalla tabella UserList corrispondente all'id
	public void userListRemove(ListName list,String user, User authenticate) throws AuthenticationFail,UserNotListed,UserNotExisting, UsernameNotCorresponding, ListNotExisting{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			UserDAO ud=new UserDAO();
			User toCheck=ud.get(user);
			String username=authenticate.getUsername();
			if(toCheck==null || user.equals(username)){
				GenericDAO.closeSession();
				throw new UserNotExisting();
			}
			String owner=list.getOwner();
			if(!owner.equals(username)){
				GenericDAO.closeSession();
				throw new UsernameNotCorresponding();
			}
			ListNameDAO ld=new ListNameDAO();
			ListName dbList=ld.getByNameOwner(list);
			if(dbList==null){
				GenericDAO.closeSession();
				throw new ListNotExisting();
			}
			Integer Id=dbList.getId();
			UserListDAO uld=new UserListDAO();
			UserList u=uld.get(Id,user);
			if(u!=null){
				uld.delete(u);
				GenericDAO.closeSession();
			}else{
				GenericDAO.closeSession();
				throw new UserNotListed(); //l'utente non e` presente nella lista
			}
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	//aggiorna la tabella degli utenti online con l'utente non loggato
	public void loginAsAnonymous(OnlineUser user)throws IpAlreadyLogged{
		OnlineUserDAO ou=new OnlineUserDAO();
		String ip=user.getIp();
		String username=user.getUsername();
		if(username!=null){
			user.setUsername(null);
		}
		boolean checkIp=ou.checkIpConnected(ip);
		if(!checkIp){
			ou.save(user);
		}else{
			GenericDAO.closeSession();
			throw new IpAlreadyLogged();
		}
		GenericDAO.closeSession();
	}
	
	//restituisce un vettore di user che identifica la blacklist
	public List<User> getUserBlacklist(User authenticate) throws AuthenticationFail{
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
			return listOfUser; // restituisce una lista di user
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	//aggiunge un record alla blacklist
	public void blacklistAdd(Blacklist b, User authenticate) throws AuthenticationFail,UserAlreadyBlacklisted, UsernameNotCorresponding, UserNotExisting{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			String owner=b.getOwner();
			String username=authenticate.getUsername();
			String toBlacklist=b.getUsername();
			UserDAO ud=new UserDAO();
			User u=ud.get(toBlacklist);
			if(u==null || username.equals(toBlacklist)){
				GenericDAO.closeSession();
				throw new UserNotExisting();
			}
			if(!owner.equals(username)){
				GenericDAO.closeSession();
				throw new UsernameNotCorresponding();
			}
			BlacklistDAO bd=new BlacklistDAO();
			Blacklist checkUser=bd.get(owner, toBlacklist);
			if(checkUser!=null){
				GenericDAO.closeSession();
				throw new UserAlreadyBlacklisted();
			}else{
				bd.save(b);
			}
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
		
	//verifica che sia presente nella lista quell'user rimuove un record dalla tabella Blacklist
	public void blacklistRemove(Blacklist b, User authenticate) throws AuthenticationFail,UserNotBlacklisted, UsernameNotCorresponding{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			String owner=b.getOwner();
			String username=authenticate.getUsername();
			String toBlacklist=b.getUsername();
			if(!owner.equals(username)){
				GenericDAO.closeSession();
				throw new UsernameNotCorresponding();
			}
			BlacklistDAO bd=new BlacklistDAO();
			Blacklist checkUser=bd.get(owner, toBlacklist);
			if(checkUser==null){
				GenericDAO.closeSession();
				throw new UserNotBlacklisted();
			}else{
				bd.delete(checkUser);
			}
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
		
	//restituisce un vector di oggetti call
	public List<Call> getCalls(User authenticate) throws AuthenticationFail{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			String username=authenticate.getUsername();
			CallDAO cd=new CallDAO();
			List<Call> calls=cd.getAllUserCalls(username);
			GenericDAO.closeSession();
			return calls;
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	// aggiunge una chiamata alla tabella Call(nessun controllo poiché non ci sarà mai una chiamata uguale)
	public void addCall(Call callObj, User authenticate) throws AuthenticationFail{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			CallDAO cd=new CallDAO();
			cd.save(callObj);
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	
	// cancella un record dalla tabella User con il metodo delete di hibernate
	public void deleteAccount(User userObj) throws AuthenticationFail{
		boolean authenticated=authenticateClient(userObj);
		if(authenticated==true){
			UserDAO ud=new UserDAO();
			User u=ud.get(userObj.getUsername());
			ud.delete(u);
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	//cambia il valore della pwd sulla tabella User
	public void changePassword(User userObj, User authenticate) throws AuthenticationFail,UsernameNotCorresponding{
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			String usernameAuth=authenticate.getUsername();
			String usernameNew=userObj.getUsername();
			if(!usernameAuth.equals(usernameNew)){
				GenericDAO.closeSession();
				throw new UsernameNotCorresponding();
			}
			UserDAO ud=new UserDAO();
			User u=ud.get(usernameNew);
			u.setPassword(userObj.getPassword());
			ud.update(u);
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
}
