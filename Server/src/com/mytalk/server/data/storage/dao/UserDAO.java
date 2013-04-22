/**
* Filename: UserDao.java
* Package: com.mytalk.server.data.storage.dao
* Author: Michael Ferronato
* Date: 2013-04-12
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-04-15 | MF        | [+] Creazione classe e definizione metodi  
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.data.storage.dao;

import com.mytalk.server.data.model.*;
import java.util.*;

public class UserDAO extends GenericDAO {
	public UserDAO(){}
	
	//crea un UserDao e fa una select sul DB per verificare presenza username e password e restituisce TRUE o FALSE	
	public boolean checkAuthenticationData(String u,String p){		
		boolean esito=false;
		List<User> l=session.createSQLQuery("SELECT * FROM Users WHERE username=:u && password=:p").addEntity(User.class).setParameter("u", u).setParameter("p", p).list();	
		if(!l.isEmpty()){
			esito=true;
		}	
		t.commit();
		session.close();	
		return esito;
	}
	
	// cancella un record dalla tabella User con il metodo delete di hibernate
	public void deleteAccount(String u){
		User u1=(User)session.get(User.class, u);
		if(u1!=null){
			session.delete(u1);
		}
		
		t.commit();
		session.close();
	}
	
	//restituisce un vettore di user che identifica tutti gli utenti
	public List<User> giveListUser(){
		List<User> l=session.createSQLQuery("SELECT * FROM Users").addEntity(User.class).list();
		t.commit();
		session.close();
		return l;
	}
	
	//cambio il valore della password 
	public void changeUserPassword(String user, String pwd){
		User u1=(User) session.get(User.class,user);
		if(u1!=null){
			u1.setPassword(pwd);
		}			
		t.commit();
		session.close();
	}
	
	//aggiunge un User alla tabella Users
	public void addUser(String user, String pwd, String email){
		User u=(User)session.get(User.class,user);
		if(u==null){
			User u1=new User(user,pwd,email);
			session.save(u1);
		}					
		t.commit();
		session.close();
	}
	
	public void changeUserMail(String user, String email){
		User u1=(User) session.get(User.class, user);
		if(u1!=null){
			u1.setEmail(email);
			session.update(u1);		
		}
		t.commit();
		session.close();
	}
}
