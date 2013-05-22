/**
* Filename: OnlineUserDAO.java
* Package: com.mytalk.server.data.storage.dao
* Author: Nicolò Mazzucato
* Date: 2013-04-15
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-04-15 | NM        | [+] Creazione classe e definizione metodi  
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.data.storage.dao;

import org.hibernate.*;

import com.mytalk.server.data.model.OnlineUser;

import java.util.List;

public class OnlineUserDAO extends GenericDAO{

	public OnlineUserDAO(){}
	
	public void save(OnlineUser onlineObj){
		Transaction t=session.beginTransaction();
		session.save(onlineObj);
		t.commit();
	}
	
	public void delete(OnlineUser onlineObj){
		Transaction t=session.beginTransaction();
		session.delete(onlineObj);
		t.commit();
	}
	
	public void update(OnlineUser onlineObj){
		Transaction t=session.beginTransaction();
		session.update(onlineObj);
		t.commit();
	}
	
	public OnlineUser get(String primaryKey){
		Transaction t=session.beginTransaction();
		OnlineUser online=(OnlineUser)session.get(OnlineUser.class, primaryKey);
		t.commit();
		return online;
	}
	
	//ritorna l'ip dell'user con username passato
	public String getUserIp(String name){
		Transaction t=session.beginTransaction();
		OnlineUser user=null;
		SQLQuery query=session.createSQLQuery("SELECT * FROM OnlineUsers WHERE username='"+name+"'");
		query=query.addEntity(OnlineUser.class);
		user=(OnlineUser)query.uniqueResult();
		String ip=null;
		if(user!=null){
			ip=user.getIp();
		}
		t.commit();
		return ip;
	}
	
	//ritorna gli user presenti anche nella tabella OnlineUsers
	public List<OnlineUser> getOnlineUsers(){
		Transaction t=session.beginTransaction();
		SQLQuery query=session.createSQLQuery("SELECT * FROM OnlineUsers WHERE username IS NOT NULL");
		query=query.addEntity(OnlineUser.class);
		List<OnlineUser> list=query.list();
		t.commit();
		return list;
	}
	
	//metodo di utilità per sapere se un ip è già connesso
	public boolean checkIpConnected(String ip){
		boolean result=false;
		OnlineUser newUser=get(ip);
		if(newUser!=null){
			result=true;
		}
		return result;
	}
	
	//metodo di utilità per sapere se un username è già connesso
	public boolean checkUsernameConnected(String username){
		Transaction t=session.beginTransaction();
		boolean result=false;
		OnlineUser user=null;
		SQLQuery query=session.createSQLQuery("SELECT * FROM OnlineUsers WHERE username='"+username+"'");
		query=query.addEntity(OnlineUser.class);
		user=(OnlineUser)query.uniqueResult();
		if(user!=null){
			result=true;
		}
		t.commit();
		return result;
	}
}
