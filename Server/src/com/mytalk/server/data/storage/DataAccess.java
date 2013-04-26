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

import java.sql.Timestamp;
import java.util.List;
import com.mytalk.server.data.model.*;
import com.mytalk.server.data.storage.dao.*;


public class DataAccess implements IDataAccess{
	
	
	public DataAccess(){}
	 
	//verifica la presenza dell'ip nella tabella OnlineUser
	public boolean checkUserByIp(String ip){	
		OnlineUserDAO od=new OnlineUserDAO();
		boolean online=false;
		OnlineUser user=od.get(ip);
		if(user!=null){
			online=true;
		}
		return online;
	}
	
	//verifica la presenza dell'username nella tabella OnlineUser
	public boolean checkUserByName(String name){		
		OnlineUserDAO od=new OnlineUserDAO();
		String ip=od.getUserIp(name);
		boolean online=false;
		if(ip!=null){
			online=true;
		}
		return online; 
	}
	
	// aggiunge un record sulla tabella OnlineUser
	public void login(OnlineUser user){
		OnlineUserDAO od=new OnlineUserDAO();
		od.update(user);//assume che il client era prima loggato come anonimo per poi fare l'update
	}
	
	//interroga il db e restituisce le liste dell'utente
	public List<ListName> userLists(String user){
		ListNameDAO ld=new ListNameDAO();
		return ld.getUserLists(user);
	}
	
	//interroga il db e restituisce gli utenti di una lista
	public List<User> getListUsers(ListName list){
		UserListDAO ld=new UserListDAO();
		int listId=list.getId();
		List<UserList> associations=ld.getUsersInList(listId);
		UserDAO ud=new UserDAO();
		List<User> users;
		for(int i=0; i<associations.size();i++){
			String username=associations.get(i).getUsername();
			User u=ud.get(username);
			users.add(u);
		}
		return users;
	}
	
	//restituisce una lista di user che identifica tutti gli utenti OnlineUser
	public List<User> getOnlineUsers(){
		OnlineUserDAO od=new OnlineUserDAO();
		List<OnlineUser> list=od.getOnlineUsers();
		List<User> users;
		UserDAO ud=new UserDAO();
		String username=null;
		for(int i=0;i<list.size();i++){
			username=list.get(i).getUsername();
			User u=ud.get(username);
			users.add(u);
		}
		return users;
	}
	
	//elimina dalla tabella OnlineUser il record corrispondente
	public void logout(OnlineUser user){
		OnlineUserDAO od=new OnlineUserDAO();
		od.delete(user);//assume che l'utente sia stato loggato per inviare questo pacchetto, ergo non fa controlli
	}
	
	//inserisce un record nella tabella MailChange con username, email e codice
	public void updateEmail(MailChange newMail){
		MailChangeDAO mcd=new MailChangeDAO();
		MailChange oldMail=mcd.get(newMail.getUsername());
		if(oldMail!=null){
			mcd.delete(newMail);
		}
		mcd.save(newMail);
	}
	
	//se il codice è giusto cancella il record da mailchange e aggiorna lo username con la nuova mail
	public void confirmUpdateEmail(MailChange newMail){
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
	}
	
	// verifica che non sia già presente la lista per quell'user e in caso negativo aggiunge un record
	public void listCreate(ListName list){
		ListNameDAO ld=new ListNameDAO();
		ListName listFound=ld.getByNameOwner(list);
		if (listFound==null){
			ld.save(list);
		}else{
			//throw //la lista esiste già con lo stesso nome
		}
	}
	
	// verifica che sia presente la lista per quell'user e in caso positivo rimuove il record
	public void listDelete(ListName list){
		ListNameDAO ld=new ListNameDAO();
		ListName listFound=ld.getByNameOwner(list);
		if (listFound!=null){
			ld.delete(list);
		}else{
			//throw //la lista non esiste
		}
	}
	
	//verifica che non sia già presente nella lista quell'user e in caso negativo lo inserisce nella lista
	public void userListAdd(ListName list,String user){
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
	}
	
	//verifica che sia presente la lista per quell'user e prende l'id della lista dalla tabella List e elimina un record dalla tabella UserList corrispondente all'id
	public void userListRemove(ListName list,String user){
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
	}
	
	//aggiorna la tabella degli utenti online con l'utente non loggato
	public void loginAsAnonymous(OnlineUser user){
		OnlineUserDAO ou=new OnlineUserDAO();
		ou.save(user);
	}
}
