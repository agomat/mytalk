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


public class DataAccess implements IDataAccess{
	
	
	public DataAccess(){}
	 
	//verifica dati autenticazione
	private boolean authenticateClient(User userObj){
		boolean esito=false;
		UserDAO ud=new UserDAO();
		User userEntity=ud.get(userObj.getUsername());
		String pwdUserEntity=userEntity.getPassword();
		String pwdUserObj=userObj.getPassword();
		if(userEntity!=null && pwdUserEntity.contains(pwdUserObj)){
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
	public boolean checkUserByName(String name, User authenticate){
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
			//throw
		}
	}
	
	// aggiunge un record sulla tabella OnlineUser
	public void login(OnlineUser user){
		OnlineUserDAO od=new OnlineUserDAO();
		od.update(user);//assume che il client era prima loggato come anonimo per poi fare l'update
		GenericDAO.closeSession();
	}
	
	//interroga il db e restituisce le liste dell'utente
	public List<ListName> userLists(String user, User authenticate){
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			ListNameDAO ld=new ListNameDAO();
			List<ListName> list=ld.getUserLists(user);
			GenericDAO.closeSession();
			return list;
		}else{
			GenericDAO.closeSession();
			//throw
		}
	}
	
	//interroga il db e restituisce gli utenti di una lista
	public List<User> getListUsers(ListName list, User authenticate){
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			UserListDAO ld=new UserListDAO();
			int listId=list.getId();
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
			//throw
		}
	}
	
	//restituisce una lista di user che identifica tutti gli utenti OnlineUser
	public List<User> getOnlineUsers(User authenticate){
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
			//throw
		}
	}
	
	//elimina dalla tabella OnlineUser il record corrispondente
	public void logout(OnlineUser user, User authenticate){
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			OnlineUserDAO od=new OnlineUserDAO();
			od.delete(user);//assume che l'utente sia stato loggato per inviare questo pacchetto, ergo non fa controlli
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			//throw
		}
	}
	
	//inserisce un record nella tabella MailChange con username, email e codice
	public void updateEmail(MailChange newMail, User authenticate){
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			MailChangeDAO mcd=new MailChangeDAO();
			MailChange oldMail=mcd.get(newMail.getUsername());
			if(oldMail!=null){
				mcd.delete(newMail);
			}
			mcd.save(newMail);
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			//throw
		}
	}
	
	//se il codice è giusto cancella il record da mailchange e aggiorna lo username con la nuova mail
	public void confirmUpdateEmail(MailChange newMail, User authenticate){
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			MailChangeDAO mcd=new MailChangeDAO();
			MailChange mailToChange=mcd.get(newMail.getUsername());//assume che esista già un record in MailChange con quel username
			String savedCode=mailToChange.getCode();
			String passedCode=newMail.getCode();
			if(savedCode==passedCode){
				mcd.delete(mailToChange);
				User updatedUser=new User(mailToChange.getUsername(),null,mailToChange.getNewmail());//non sono sicuro del campo null se me lo modifica facendo update o se lascia il campo originario
				UserDAO ud=new UserDAO();
				ud.update(updatedUser);
			}else{
				//throw //il codice passato è sbagliato, non faccio niente e lo notifico con una eccezione
			}
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			//throw
		}
	}
	
	// verifica che non sia già presente la lista per quell'user e in caso negativo aggiunge un record
	public void listCreate(ListName list, User authenticate){
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			ListNameDAO ld=new ListNameDAO();
			ListName listFound=ld.getByNameOwner(list);
			if (listFound==null){
				ld.save(list);
			}else{
				//throw //la lista esiste già con lo stesso nome
			}
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			//throw
		}
	}
	
	// verifica che sia presente la lista per quell'user e in caso positivo rimuove il record
	public void listDelete(ListName list, User authenticate){
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			ListNameDAO ld=new ListNameDAO();
			ListName listFound=ld.getByNameOwner(list);
			if (listFound!=null){
				ld.delete(list);
			}else{
				//throw //la lista non esiste
			}
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			//throw
		}
	}
	
	//verifica che non sia già presente nella lista quell'user e in caso negativo lo inserisce nella lista
	public void userListAdd(ListName list,String user, User authenticate){
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			ListNameDAO ld=new ListNameDAO();
			ListName dbList=ld.getByNameOwner(list);
			Integer Id=dbList.getId();
			UserListDAO uld=new UserListDAO();
			UserList u=uld.get(Id,user);
			if(u==null){
				UserList newUser=new UserList(Id,user);
				uld.save(newUser);
			}else{
				//throw //l'utente e` gia` nella lista
			}
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			//throw
		}
	}
	
	//verifica che sia presente la lista per quell'user e prende l'id della lista dalla tabella List e elimina un record dalla tabella UserList corrispondente all'id
	public void userListRemove(ListName list,String user, User authenticate){
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			ListNameDAO ld=new ListNameDAO();
			ListName dbList=ld.getByNameOwner(list);
			Integer Id=dbList.getId();
			UserListDAO uld=new UserListDAO();
			UserList u=uld.get(Id,user);
			if(u!=null){
				UserList newUser=new UserList(Id,user);
				uld.delete(newUser);
			}else{
				//throw //l'utente non e` presente nella lista
			}
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			//throw
		}
	}
	
	//aggiorna la tabella degli utenti online con l'utente non loggato
	public void loginAsAnonymous(OnlineUser user){
		OnlineUserDAO ou=new OnlineUserDAO();
		ou.save(user);
		GenericDAO.closeSession();
	}
	
	//restituisce un vettore di user che identifica la blacklist
	public List<User> getUserBlacklist(String u, User authenticate){
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			List<User> listOfUser=new ArrayList<User>();
			Blacklist b=null;
			BlacklistDAO bd=new BlacklistDAO();
			UserDAO ud=new UserDAO();
			List<Blacklist> blacklistList=bd.getUserBlacklist(u);
			for(int i=0;i<blacklistList.size();i++){
				b=blacklistList.get(i);
				listOfUser.add(ud.get(b.getUsername()));
			}
			GenericDAO.closeSession();
			return listOfUser; // restituisce una lista di user
		}else{
			GenericDAO.closeSession();
			//throw
		}
	}
	
	//inserisco un record nella tabella ForgottenPassword con username e newpwd
	public void passwordRetriever(ForgottenPassword forgottenPasswordObj, User authenticate){
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			ForgottenPasswordDAO fpd=new ForgottenPasswordDAO();
			ForgottenPassword forgottenPasswordEntity=(ForgottenPassword)fpd.get(forgottenPasswordObj.getUsername());
			if(forgottenPasswordEntity!=null){
				fpd.update(forgottenPasswordObj);
			}
			else{
				fpd.save(forgottenPasswordObj);
			}
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			//throw
		}
	}
	
	//ogni volta controllo se ci sono già Forgottenpassword nella tabella di quel username
	public void confirmChangePassword(ForgottenPassword forgottenPasswordObj, User authenticate){ 
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			ForgottenPasswordDAO fpd=new ForgottenPasswordDAO();
			UserDAO ud=new UserDAO();
			User userObj=new User(forgottenPasswordObj.getUsername(),forgottenPasswordObj.getNewpwd(),"null");
			ForgottenPassword forgottenPasswordEntity=(ForgottenPassword)fpd.get(forgottenPasswordObj.getUsername());
			if(forgottenPasswordEntity!=null){
				fpd.delete(forgottenPasswordObj);	
				ud.update(userObj);
			}
			else{
				System.out.println("Impossibile confermare password!!!");
			}	
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			//throw
		}
	}
	
	//aggiunge un record alla blacklist
	public void blacklistAdd(Blacklist b, User authenticate){
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			boolean check=false;
			Blacklist blacklistObj=null;
			String blacklistUsername=null;
			BlacklistDAO bd=new BlacklistDAO();
			List<Blacklist> blacklistList=bd.getUserBlacklist(b.getOwner());
			for(int i=0;i<blacklistList.size();i++){
				blacklistObj=blacklistList.get(i);
				blacklistUsername=blacklistObj.getUsername();
				if(blacklistUsername.contains(b.getUsername())){
					check=true;
				}
			}
			if(!check){
				bd.save(b);
			}
			else{
				System.out.println("Impossibile aggiungere poiché il campo è già presente");
			}
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			//throw
		}
	}
		
	//verifica che sia presente nella lista quell'user rimuove un record dalla tabella Blacklist
	public void blacklistRemove(Blacklist b, User authenticate){
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			boolean check=false;
			Blacklist blacklistObj=null;
			String blacklistUsername=null;
			BlacklistDAO bd=new BlacklistDAO();
			List<Blacklist> blacklistList=bd.getUserBlacklist(b.getOwner());
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
				System.out.println("Impossibile eliminare poiché il campo non è presente");
			}
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			//throw
		}
	}
		
	//restituisce un vector di oggetti call
	public List<Call> getCalls(String primaryKey, User authenticate){
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			CallDAO cd=new CallDAO();
			List<Call> calls=cd.getAllUserCalls(primaryKey);
			GenericDAO.closeSession();
			return calls;
		}else{
			GenericDAO.closeSession();
			//throw
		}
	}
	
	// aggiunge una chiamata alla tabella Call(nessun controllo poiché non ci sarà mai una chiamata uguale)
	public void addCall(Call callObj, User authenticate){
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			CallDAO cd=new CallDAO();
			cd.save(callObj);
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			//throw
		}
	}
	
	
	// cancella un record dalla tabella User con il metodo delete di hibernate
	public void deleteAccount(User userObj){
		boolean authenticated=authenticateClient(userObj);
		if(authenticated==true){
			UserDAO ud=new UserDAO();
			ud.delete(userObj);
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			//throw
		}
	}
	
	//restituisce un vettore di user che identifica tutti gli utenti
	public List<User> getAllUsers(User authenticate){
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			UserDAO ud=new UserDAO();
			List<User> users=ud.getAllUsers();
			GenericDAO.closeSession();
			return users;
		}else{
			GenericDAO.closeSession();
			//throw
		}
	}
	
	//cambia il valore della pwd sulla tabella User
	public void changePassword(User userObj, User authenticate){
		boolean authenticated=authenticateClient(authenticate);
		if(authenticated==true){
			UserDAO ud=new UserDAO();
			ud.update(userObj);
			GenericDAO.closeSession();
		}else{
			GenericDAO.closeSession();
			//throw
		}
	}
	
	//elimino il record corrispondente all'username dalla tabella ToConfirmAccount e 
	//aggiungo le info alla tabella user
	public void accountConfirm(ToConfirmAccount toConfirmAccountObj){
		ToConfirmAccountDAO tcad=new ToConfirmAccountDAO();
		UserDAO ud=new UserDAO();
		ToConfirmAccount toConfirmAccountEntity=tcad.get(toConfirmAccountObj.getUsername());
		if(toConfirmAccountEntity!=null){
			tcad.delete(toConfirmAccountObj);		
			ud.save(new User(toConfirmAccountEntity.getUsername(),toConfirmAccountEntity.getPassword(),toConfirmAccountEntity.getEmail()));
		}	
		else {
			System.out.println("Account da confermare non presente");
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
