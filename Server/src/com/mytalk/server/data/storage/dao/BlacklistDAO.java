/**
* Filename: BlacklistDAO.java
* Package: com.mytalk.server.data.storage.dao
* Author: Michael Ferronato
* Date: 2013-04-16
*
* Diary:
*
* Version |
Date
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-04-16 | MF        | [+] Creazione classe e definizione metodi  
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.data.storage.dao;

import com.mytalk.server.data.model.*;
import java.util.*;
import java.io.Serializable;

public class BlacklistDAO extends GenericDAO {
	public BlacklistDAO(){}
	
	//restituisce un vettore di user che identifica la blacklist
	public List<User> getUserBlacklist(String user){
		List<User> l=session.createSQLQuery("SELECT * FROM Users WHERE username IN (SELECT username FROM Blacklists WHERE owner=:user)").addEntity(User.class).setParameter("user", user).list();
		
		t.commit();
		session.close();
		return l;
	}
	
	//verifica che non sia già presente quell'user per quell'owner aggiunge un record alla tabella Blacklist
	public void addUserToBlacklist(String owner,String user){
		List<Blacklist> b=session.createSQLQuery("SELECT * FROM Blacklists WHERE owner=:owner AND username=:user").addEntity(Blacklist.class).setParameter("owner", owner).setParameter("user", user).list();
		if(b.size()==0){	
			Blacklist nb=new Blacklist(owner,user);
			session.save(nb);
		}
		
		t.commit();
		session.close();
	}
	
	//verifica che sia presente nella lista quell'user rimuove un record dalla tabella Blacklist
	public void removeUserFromBlacklist(String o,String u){
		List<Blacklist> b=session.createSQLQuery("SELECT * FROM Blacklists WHERE owner=:o && username=:u").addEntity(Blacklist.class).setParameter("o", o).setParameter("u", u).list();
		if(b.size()>0){
			session.delete(b.get(0));
		}
		t.commit();
		session.close();
	}
}
