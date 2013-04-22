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

import java.sql.Time;
import java.util.List;
import java.util.Vector;

import com.mytalk.server.data.model.*;
import com.mytalk.server.data.storage.dao.*;


public class DataAccess implements IDataAccess{
	
	
	public DataAccess(){}
	
	//crea un UserDAO e fa una select sul DB per verificare presenza username e password e restituisce TRUE o FALSE
	public boolean authenticateClient(String u, String p){
		UserDAO ud=new UserDAO();
		return ud.checkAuthenticationData(u,p);
	}
	
	//RefuseCall viene fatto dalla logic
	 
	//verifica la presenza dell'ip nella tabella OnlineUser
	public boolean checkUserByIp(String s){	
		OnlineUserDAO od=new OnlineUserDAO();
		return od.checkIPIsOnline(s);
	}
	
	//verifica la presenza dell'username nella tabella OnlineUser
	public boolean checkUserByName(String s){		
		OnlineUserDAO od=new OnlineUserDAO();
		return od.nameIsOnline(s);
	}
	
	// inserisce i dati nella tabella ToConfirmAccount
	public void createAccount(String u,String p,String e){	
		ToConfirmAccountDAO tcad=new ToConfirmAccountDAO();
		tcad.addAccountToBeConfirmed(u,p,e);
	}
	
	// cancella un record dalla tabella User con il metodo delete di hibernate
	public void deleteAccount(String u){
		UserDAO ud=new UserDAO();
		ud.deleteAccount(u);
	}
	
	// aggiunge un record sulla tabella OnlineUser
	public void login(String u,String p, String ip){
		OnlineUserDAO od=new OnlineUserDAO();
		od.login(u,ip);
	}
	
	//interroga il db e restituisce le liste degli utenti
	public List<ListName> getUserLists(String u){
		ListNameDAO ld=new ListNameDAO();
		return ld.returnListUser(u); // restituisce vector di list
	}
	
	//interroga il db e restituisce le liste degli utenti
	public List<UserList> getUserListsDetails(String u){
		UserListDAO ld=new UserListDAO();
		return ld.getUserListsDetails(u); // restituisce vector di list
	}
	
	//restituisce un vettore di user che identifica la blacklist
	public List<User> getUserBlacklist(String u){
		BlacklistDAO bd=new BlacklistDAO();
		return bd.getUserBlacklist(u); // restituisce un vector di user
	}
	
	//restituisce un vettore di user che identifica tutti gli utenti
	public List<User> getAllUsers(){
		UserDAO ud=new UserDAO();
		return ud.giveListUser();
	}
	
	//restituisce un vettore di user che identifica tutti gli utenti OnlineUser
	public List<User> getOnlineUsers(){
		OnlineUserDAO od=new OnlineUserDAO();
		return od.getOnlineUsers();
	}
	
	//elimina dalla tabella OnlineUser il record corrispondente all'username u
	public void logout(String u){
		OnlineUserDAO od=new OnlineUserDAO();
		od.logout(u);
	}
	
	//inserisco un record nella tabella ForgottenPassword con username e newpwd
	public void passwordRetriever(String u, String newpwd){
		ForgottenPasswordDAO fpd=new ForgottenPasswordDAO();
		fpd.passwordRetriever(u, newpwd);
	}
	
	//
	public void confirmChangePassword(String u){ //ogni volta controllo se ci sono già Forgottenpassword nella tabella di quel username
		ForgottenPasswordDAO fpd=new ForgottenPasswordDAO();
		String pwd=fpd.confirmChangePassword(u); //verifico presenza record e restituisce la pwd
		if(pwd!=null){
			UserDAO ud=new UserDAO();
			ud.changeUserPassword(u,pwd); //cambia la pwd
		}
		
	}
	
	//cambia il valore della pwd sulla tabella User
	public void changePassword(String u, String newpwd){
		UserDAO ud=new UserDAO();
		ud.changeUserPassword(u,newpwd);
	}
	
	//inserisce un record nella tabella MailChange con username, email e codice
	public void updateEmail(String u,String e,String code){
		MailChangeDAO cmd=new MailChangeDAO();
		cmd.updateEmail(u,e,code);
	}
	
	//
	public boolean confirmUpdateEmail(String u,String code){
		MailChangeDAO cmd=new MailChangeDAO();
		String email=cmd.confirmUpdateEmail(u,code); //verifica la presenza nella tabella,elimino record e restituisce la mail
		if(email!=null){
			UserDAO ud=new UserDAO();
			ud.changeUserMail(u,email); //cambia la mail
			return true;
		}
		else
			return false;
	}
	
	//elimino il record corrispondente all'username dalla tabella ToConfirmAccount e aggiungo le info alla tabella user
	public void accountConfirm(String u){
		ToConfirmAccountDAO tcad=new ToConfirmAccountDAO();
		String[] pe=tcad.deleteConfirmedAccount(u);
		if(pe!=null){
			UserDAO ud=new UserDAO();
			ud.addUser(u,pe[0],pe[1]);
		}
		
	}
	
	// verifica che non sia già presente la lista per quell'user aggiunge un record alla tabella List con nome e owner
	public void listCreate(String nome,String owner){
		ListNameDAO ld=new ListNameDAO();
		ld.addRecordToListName(nome,owner);
	}
	
	// verifica che sia presente la lista per quell'user rimuove un record dalla tabella List che corrisponde a nome user e owner
	public void listDelete(String nome,String owner){
		ListNameDAO ld=new ListNameDAO();
		ld.deleteRecordFromListName(nome,owner);
	}
	
	// verifica che non sia già presente nella lista quell'user e prende l'id della lista dalla tabella List e aggiunge un record alla tabella UserList corrispondente all'id
	public boolean userListAdd(String nome,String owner,String user){
		ListNameDAO ld=new ListNameDAO();
		Integer id=ld.getIdList(nome,owner);//restituisce l'id della lista
		boolean esito=false;
		if(id!=null){
			UserListDAO lud=new UserListDAO();
			lud.addUserToId(id,user);
			esito=true;
		}
		return esito;
	}
	
	//verifica che sia presente la lista per quell'user e prende l'id della lista dalla tabella List e elimina un record dalla tabella UserList corrispondente all'id
	public boolean userListRemove(String nome,String owner,String user){
		ListNameDAO ld=new ListNameDAO();
		Integer id=ld.getIdList(nome,owner);//restituisce l'id della lista
		boolean esito=false;
		if(id!=null){
			UserListDAO lud=new UserListDAO();
			lud.deleteUserToId(id,user);//elimina record
			esito=true;
		}
		return esito;
	}
	
	//verifica che non sia già presente quell'user per quell'owner aggiunge un record alla tabella Blacklist
	public void blacklistAdd(String owner,String user){
		BlacklistDAO bd=new BlacklistDAO();
		bd.addUserToBlacklist(owner,user);
		UserListDAO uld=new UserListDAO();
		uld.checkAndRemoveBlacklistedUsers(owner, user);
	}
	
	//verifica che sia presente nella lista quell'user rimuove un record dalla tabella Blacklist
	public void blacklistRemove(String owner,String user){
		BlacklistDAO bd=new BlacklistDAO();
		bd.removeUserFromBlacklist(owner,user);
	}
	
	//restituisce un vector di oggetti call
	public List<Call> getCalls(String u){
		CallDAO cd=new CallDAO();
		return cd.getCalls(u);
	}
	
	//GetStats usa GetCalls e calcola le statistiche
	
	// aggiunge una chiamata alla tabella Call
	public void addCall(String c,String r,Time d,Integer bt,Integer br){
		CallDAO cd=new CallDAO();
		cd.addCall(c,r,d,bt,br);
	}
	
	// elimina tutti i record della tabella ToConfirmAccount
	public void deleteUnconfirmedAccount(){
		ToConfirmAccountDAO tcd= new ToConfirmAccountDAO();
		tcd.deleteUnconfirmedAccount();//elimina tutti i record della tabella a mezzanotte
	}
	
	//aggiorna la tabella degli utenti online con l'utente non loggato
	public void loginAsAnonymous(String ip){
		OnlineUserDAO ou=new OnlineUserDAO();
		ou.loginAnonymous(ip);
	}
	
	//aggiorna la tabella degli utenti online disautenticando l'utente non loggato
	public void logoutAsAnonymous(String ip){
		OnlineUserDAO ou=new OnlineUserDAO();
		ou.logoutAnonymous(ip);
	}
}
