/**
* Filename: UserListDAO.java
* Package: com.mytalk.server.data.storage.dao
* Author: Nicolò Toso
* Date: 2013-04-11
*
* Diary:
*
* Version |
Date
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-04-15 | NT        | [+] Creato classe e metodi  
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.data.storage.dao;

import com.mytalk.server.data.model.*;
import java.util.*;


public class UserListDAO extends GenericDAO{
	
	public UserListDAO(){}
	
	public void addUserToId(int id, String user){
		List<UserList> ul1=session.createSQLQuery("SELECT * FROM UserLists WHERE idList=:id && username=:user ")
				.addEntity(UserList.class)
				.setParameter("id",id)
				.setParameter("user", user).list();
		if(ul1.size()==0){
			UserList ul=new UserList(id, user);
			session.save(ul);
		}	
		t.commit();
		session.close();		
	}
	
	public void deleteUserToId(int id, String user){
		List<ListName> l=session.createSQLQuery("SELECT * FROM UserLists WHERE idList=:id && username=:user ")
				.addEntity(UserList.class)
				.setParameter("id",id)
				.setParameter("user", user).list();
		if(!(l.isEmpty())){
			session.delete(l.get(0));	
		}
		t.commit();
		session.close();		
	}
	
	//interroga il db e restituisce le liste degli utenti
	public List<UserList> getUserListsDetails(String user){
		List<UserList> lu=session.createSQLQuery("SELECT * FROM UserLists WHERE idList IN (SELECT id AS idList FROM ListNames WHERE owner=:user) ORDER BY idList").addEntity(UserList.class).setParameter("user", user).list();		
		t.commit();
		session.close();
		return lu;
	}
	
	//cerca ed elimina gli user che stanno andando in blacklist
	public void checkAndRemoveBlacklistedUsers(String owner,String user){
		List<UserList> ul=session.createSQLQuery("SELECT * FROM UserLists WHERE username=:user")
				.addEntity(UserList.class)
				.setParameter("user", user).list();
		for(int i=0;i<ul.size();i++){
			session.delete(ul.get(i));
		}
		t.commit();
		session.close();
	}

}
