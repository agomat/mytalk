/**
* Filename: UserListDAO.java
* Package: com.mytalk.server.data.storage.dao
* Author: Nicolò Toso
* Date: 2013-04-11
*
* Diary:
*
*
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

import org.hibernate.*;

import com.mytalk.server.data.persistence.HibernateUtil;
import java.util.List;


public class UserListDAO extends GenericDAO{
	
	public UserListDAO(){}
	
	public void save(UserList userlistObj){
		Transaction t=session.beginTransaction();
		session.save(userlistObj);
		t.commit();
	}
	
	public void delete(UserList userlistObj){
		Transaction t=session.beginTransaction();
		session.delete(userlistObj);
		t.commit();
	}
	
	public void update(UserList userlistObj){
		Transaction t=session.beginTransaction();
		session.update(userlistObj);
		t.commit();
	}

	public UserList get(int primaryKeyId,String primaryKeyUser){
		Transaction t=session.beginTransaction();
		UserList user=new UserList(primaryKeyId,primaryKeyUser);
		UserList dbUser=(UserList)session.get(UserList.class, user);
		t.commit();
		return dbUser;
	}
	
	//resituisce le associazioni utenti/lista
	public List<UserList> getUsersInList(int id){
		Transaction t=session.beginTransaction();
		SQLQuery query=session.createSQLQuery("SELECT * FROM UserLists WHERE idList='"+id+"'");
		query=query.addEntity(UserList.class);
		List<UserList> list=(List<UserList>)query.list();
		t.commit();
		return list;
	}
}
