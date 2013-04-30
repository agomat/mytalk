/**
* Filename: ListNameDAO.java
* Package: com.mytalk.server.data.storage.dao
* Author: Nicolò Toso
* Date: 2013-04-11
*
* Diary:
* Version | Date       | Developer | Changes
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
import java.util.List;

public class ListNameDAO extends GenericDAO{
	
	public ListNameDAO() {}
	
	public void save(ListName listObj){
		Transaction t=session.beginTransaction();
		session.save(listObj);
		t.commit();
	}
	
	public void update(ListName listObj){
		Transaction t=session.beginTransaction();
		ListName listEntity=this.get(listObj.getId());
		if(listObj.getOwner()==null){
			listObj.setOwner(listEntity.getOwner());
		}
		if(listObj.getName()==null){
			listObj.setName(listEntity.getName());
		}
		session.update(listObj);
		t.commit();
	}
	
	public ListName get(int primaryKey){
		Transaction t=session.beginTransaction();
		ListName list=(ListName)session.get(ListName.class, primaryKey);
		t.commit();
		return list;
	}
	
	//interroga il db e restituisce le liste dell'utente
	public List<ListName> getUserLists(String username){
		Transaction t=session.beginTransaction();
		List<ListName> list=null;
		SQLQuery query=session.createSQLQuery("SELECT * FROM ListNames WHERE owner='"+username+"'");
		query=query.addEntity(ListName.class);
		list=(List<ListName>)query.list();
		t.commit();
		return list;
	}
	
	//get di una lista sapendo nome e proprietario
	public ListName getByNameOwner(ListName listObj){
		Transaction t=session.beginTransaction();
		String name=listObj.getName();
		String owner=listObj.getOwner();
		ListName list=null;
		SQLQuery query=session.createSQLQuery("SELECT * FROM ListNames WHERE owner='"+owner+"' && name='"+name+"'");
		query=query.addEntity(ListName.class);
		list=(ListName)query.uniqueResult();
		t.commit();
		return list;
	}
	
	public void delete(ListName listObj){
		Transaction t=session.beginTransaction();
		session.delete(listObj);
		t.commit();
	}
}