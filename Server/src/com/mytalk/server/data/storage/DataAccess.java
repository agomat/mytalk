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
		OnlineUserDAO od=new OnlineUserDAO();
		String ip=null;
		boolean online=false;
		if(authenticated==true){
			ip=od.getUserIp(name);
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
	
	// aggiunge un record sulla tabella OnlineUser
	public void login(OnlineUser user){
		OnlineUserDAO od=new OnlineUserDAO();
		od.update(user);//assume che il client era prima loggato come anonimo per poi fare l'update
		GenericDAO.closeSession();
	}
	
	//interroga il db e restituisce le liste dell'utente
	public List<ListName> userLists(String user, User authenticate) throws AuthenticationFail{
		boolean authenticated=authenticateClient(authenticate);
		ListNameDAO ld=new ListNameDAO();
		List<ListName> list=null;
		if(authenticated==true){
			list=ld.getUserLists(user);
			GenericDAO.closeSession();
			return list;
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	//interroga il db e restituisce gli utenti di una lista
	public List<User> getListUsers(ListName list, User authenticate) throws AuthenticationFail{
		boolean authenticated=authenticateClient(authenticate);
		UserListDAO ld=new UserListDAO();
		int listId;
		List<UserList> associations=null;
		UserDAO ud=new UserDAO();
		List<User> users=null;
		String username=null;
		User u=null;
		if(authenticated==true){		
			listId=list.getId();
			associations=ld.getUsersInList(listId);
			users=new ArrayList<User>();
			for(int i=0; i<associations.size();i++){
				username=associations.get(i).getUsername();
				u=ud.get(username);
				users.add(u);
			}
			GenericDAO.closeSession();
			return users;
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	//restituisce una lista di user che identifica tutti gli utenti OnlineUser
	public List<User> getOnlineUsers(User authenticate) throws AuthenticationFail{
		boolean authenticated=authenticateClient(authenticate);
		OnlineUserDAO od=new OnlineUserDAO();
		UserDAO ud=new UserDAO();
		List<OnlineUser> list=null;
		List<User> users=null;
		String username=null;
		User u=null;
		if(authenticated==true){
			list=od.getOnlineUsers();
			users=new ArrayList<User>();
			ud=new UserDAO();
			for(int i=0;i<list.size();i++){
				username=list.get(i).getUsername();
				u=ud.get(username);
				users.add(u);
			}
			GenericDAO.closeSession();
			return users;
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	//elimina dalla tabella OnlineUser il record corrispondente
	public void logout(OnlineUser user, User authenticate) throws AuthenticationFail{
		boolean authenticated=authenticateClient(authenticate);
		OnlineUserDAO od=new OnlineUserDAO();
		OnlineUser onlineUserEntity=null;
		if(authenticated==true){
			onlineUserEntity=od.get(user.getIp());
			if(onlineUserEntity!=null){
				od.delete(user);
			}
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	//inserisce un record nella tabella MailChange con username, email e codice
	public void updateEmail(MailChange newMail, User authenticate) throws AuthenticationFail{
		boolean authenticated=authenticateClient(authenticate);
		MailChangeDAO mcd=new MailChangeDAO();
		MailChange oldMail=null;
		if(authenticated==true){
			oldMail=mcd.get(newMail.getUsername());
			if(oldMail!=null){
				mcd.delete(newMail);
			}
			mcd.save(newMail);
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	//se il codice è giusto cancella il record da mailchange e aggiorna lo username con la nuova mail
	public void confirmUpdateEmail(MailChange newMail, User authenticate) throws AuthenticationFail,WrongMailCode,ConfirmationMailFail{
		boolean authenticated=authenticateClient(authenticate);
		UserDAO ud=new UserDAO();
		MailChangeDAO mcd=new MailChangeDAO();
		MailChange mailToChange=null;
		String savedCode=null;
		String passedCode=null;
		User updatedUser=null;
		if(authenticated==true){
			mailToChange=mcd.get(newMail.getUsername());
			if(mailToChange!=null){
				savedCode=mailToChange.getCode();
				passedCode=newMail.getCode();
				if(savedCode.equals(passedCode)){
					mcd.delete(mailToChange);
					updatedUser=new User(mailToChange.getUsername(),null,mailToChange.getNewmail());
					ud.update(updatedUser);
				}else{
					throw new WrongMailCode(); //il codice passato è sbagliato, non faccio niente e lo notifico con una eccezione
				}
				GenericDAO.closeSession();
			}else{
				throw new ConfirmationMailFail();
			}
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	// verifica che non sia già presente la lista per quell'user e in caso negativo aggiunge un record
	public void listCreate(ListName list, User authenticate) throws AuthenticationFail,ListAlreadyExists{
		boolean authenticated=authenticateClient(authenticate);
		ListNameDAO ld=new ListNameDAO();
		ListName listFound=null;
		if(authenticated==true){
			listFound=ld.getByNameOwner(list);
			if (listFound==null){
				ld.save(list);
			}else{
				throw new ListAlreadyExists();
			}
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	// verifica che sia presente la lista per quell'user e in caso positivo rimuove il record
	public void listDelete(ListName list, User authenticate) throws AuthenticationFail,ListNotExisting{
		boolean authenticated=authenticateClient(authenticate);
		ListNameDAO ld=new ListNameDAO();
		ListName listFound=null;
		if(authenticated==true){
			listFound=ld.getByNameOwner(list);
			if (listFound!=null){
				ld.delete(list);
			}else{
				throw new ListNotExisting(); 
			}
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	//verifica che non sia già presente nella lista quell'user e in caso negativo lo inserisce nella lista
	public void userListAdd(ListName list,String user, User authenticate) throws AuthenticationFail,UserAlreadyListed{
		boolean authenticated=authenticateClient(authenticate);
		ListNameDAO ld=new ListNameDAO();
		UserListDAO uld=new UserListDAO();
		ListName dbList=null;
		Integer Id=null;	
		UserList u=null;
		UserList newUser=null;
		if(authenticated==true){
			dbList=ld.getByNameOwner(list);
			Id=dbList.getId();
			u=uld.get(Id,user);
			if(u==null){
				newUser=new UserList(Id,user);
				uld.save(newUser);
			}else{
				throw new UserAlreadyListed(); 
			}
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	//verifica che sia presente la lista per quell'user e prende l'id della lista dalla tabella List e elimina un record dalla tabella UserList corrispondente all'id
	public void userListRemove(ListName list,String user, User authenticate) throws AuthenticationFail,UserNotListed{
		boolean authenticated=authenticateClient(authenticate);
		ListNameDAO ld=new ListNameDAO();
		ListName dbList=null;
		Integer Id=null;
		UserListDAO uld=new UserListDAO();
		UserList u=null;
		UserList newUser=null;
		if(authenticated==true){
			dbList=ld.getByNameOwner(list);
			Id=dbList.getId();
			u=uld.get(Id,user);
			if(u!=null){
				newUser=new UserList(Id,user);
				uld.delete(newUser);
			}else{
				throw new UserNotListed(); 
			}
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	//aggiorna la tabella degli utenti online con l'utente non loggato
	public void loginAsAnonymous(OnlineUser user){
		OnlineUserDAO ou=new OnlineUserDAO();
		OnlineUser onlineUserEntity=ou.get(user.getIp());
		if(onlineUserEntity==null){
			ou.save(user);
		}
		GenericDAO.closeSession();
	}
	
	//restituisce un vettore di user che identifica la blacklist
	public List<User> getUserBlacklist(String u, User authenticate) throws AuthenticationFail{
		boolean authenticated=authenticateClient(authenticate);
		List<User> listOfUser=new ArrayList<User>();
		Blacklist b=null;
		BlacklistDAO bd=new BlacklistDAO();
		UserDAO ud=new UserDAO();
		List<Blacklist> blacklistList=null;
		if(authenticated==true){
			blacklistList=bd.getUserBlacklist(u);
			for(int i=0;i<blacklistList.size();i++){
				b=blacklistList.get(i);
				listOfUser.add(ud.get(b.getUsername()));
			}
			GenericDAO.closeSession();
			return listOfUser; 
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	//inserisco un record nella tabella ForgottenPassword con username e newpwd
	public void passwordRetriever(ForgottenPassword forgottenPasswordObj, User authenticate) throws AuthenticationFail{
		boolean authenticated=authenticateClient(authenticate);
		ForgottenPasswordDAO fpd=new ForgottenPasswordDAO();
		ForgottenPassword forgottenPasswordEntity=null;
		if(authenticated==true){
			forgottenPasswordEntity=(ForgottenPassword)fpd.get(forgottenPasswordObj.getUsername());
			if(forgottenPasswordEntity!=null){
				fpd.update(forgottenPasswordObj);
			}
			else{
				fpd.save(forgottenPasswordObj);
			}
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	//ogni volta controllo se ci sono già Forgottenpassword nella tabella di quel username
	public void confirmChangePassword(ForgottenPassword forgottenPasswordObj, User authenticate) throws AuthenticationFail,PasswordNotForgotten{ 
		boolean authenticated=authenticateClient(authenticate);
		ForgottenPasswordDAO fpd=new ForgottenPasswordDAO();
		UserDAO ud=new UserDAO();
		User userObj=null;
		ForgottenPassword forgottenPasswordEntity=null;
		if(authenticated==true){
			userObj=new User(forgottenPasswordObj.getUsername(),forgottenPasswordObj.getNewpwd(),"null");
			forgottenPasswordEntity=(ForgottenPassword)fpd.get(forgottenPasswordObj.getUsername());
			if(forgottenPasswordEntity!=null){
				fpd.delete(forgottenPasswordObj);	
				ud.update(userObj);
			}
			else{
				throw new PasswordNotForgotten();
			}	
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	//aggiunge un record alla blacklist
	public void blacklistAdd(Blacklist b, User authenticate) throws AuthenticationFail,UserAlreadyBlacklisted{
		boolean authenticated=authenticateClient(authenticate);
		boolean check=false;
		Blacklist blacklistObj=null;
		String blacklistUsername=null;
		BlacklistDAO bd=new BlacklistDAO();
		List<Blacklist> blacklistList=null;
		if(authenticated==true){
			blacklistList=bd.getUserBlacklist(b.getOwner());
			for(int i=0;i<blacklistList.size();i++){
				blacklistObj=blacklistList.get(i);
				blacklistUsername=blacklistObj.getUsername();
				if(blacklistUsername.equals(b.getUsername())){
					check=true;
				}
			}
			if(!check){
				bd.save(b);
			}
			else{
				throw new UserAlreadyBlacklisted();
			}
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
		
	//verifica che sia presente nella lista quell'user rimuove un record dalla tabella Blacklist
	public void blacklistRemove(Blacklist b, User authenticate) throws AuthenticationFail,UserNotBlacklisted{
		boolean authenticated=authenticateClient(authenticate);
		boolean check=false;
		Blacklist blacklistObj=null;
		String blacklistUsername=null;
		BlacklistDAO bd=new BlacklistDAO();
		List<Blacklist> blacklistList=null;
		if(authenticated==true){
			blacklistList=bd.getUserBlacklist(b.getOwner());
			for(int i=0;i<blacklistList.size();i++){
				blacklistObj=blacklistList.get(i);
				blacklistUsername=blacklistObj.getUsername();
				if(blacklistUsername.contains(b.getUsername())){
					check=true;
				}
			}
			if(check){
				bd.delete(b);
			}
			else{
				throw new UserNotBlacklisted();
			}
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
		
	//restituisce un vector di oggetti call
	public List<Call> getCalls(String primaryKey, User authenticate) throws AuthenticationFail{
		boolean authenticated=authenticateClient(authenticate);
		CallDAO cd=new CallDAO();
		List<Call> calls=null;
		if(authenticated==true){
			calls=cd.getAllUserCalls(primaryKey);
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
		CallDAO cd=new CallDAO();
		if(authenticated==true){		
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
		UserDAO ud=new UserDAO();
		if(authenticated==true){
			ud.delete(userObj);
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	//restituisce un vettore di user che identifica tutti gli utenti
	public List<User> getAllUsers(User authenticate) throws AuthenticationFail{
		boolean authenticated=authenticateClient(authenticate);
		UserDAO ud=new UserDAO();
		List<User> users=null;
		if(authenticated==true){
			users=ud.getAllUsers();
			GenericDAO.closeSession();
			return users;
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	//cambia il valore della pwd sulla tabella User
	public void changePassword(User userObj, User authenticate) throws AuthenticationFail{
		boolean authenticated=authenticateClient(authenticate);
		UserDAO ud=new UserDAO();
		if(authenticated==true){			
			ud.update(userObj);
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			throw new AuthenticationFail();
		}
	}
	
	//elimino il record corrispondente all'username dalla tabella ToConfirmAccount e 
	//aggiungo le info alla tabella user
	public void accountConfirm(ToConfirmAccount toConfirmAccountObj)throws AccountNotToConfirm{
		ToConfirmAccountDAO tcad=new ToConfirmAccountDAO();
		UserDAO ud=new UserDAO();
		ToConfirmAccount toConfirmAccountEntity=tcad.get(toConfirmAccountObj.getUsername());
		if(toConfirmAccountEntity!=null){
			tcad.delete(toConfirmAccountObj);		
			ud.save(new User(toConfirmAccountEntity.getUsername(),toConfirmAccountEntity.getPassword(),toConfirmAccountEntity.getEmail()));
		}	
		else {
			throw new AccountNotToConfirm();
		}
		GenericDAO.closeSession();
	}
	
	// elimina tutti i record della tabella ToConfirmAccount
	public void deleteUnconfirmedAccount(){
		ToConfirmAccountDAO tcd= new ToConfirmAccountDAO();
		tcd.deleteAll();//elimina tutti i record della tabella 
		GenericDAO.closeSession();
	}
}
