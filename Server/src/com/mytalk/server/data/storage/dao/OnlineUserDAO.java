/**
* Filename: OnlineUserDAO.java
* Package: com.mytalk.server.data.storage.dao
* Author: Nicolò Mazzucato
* Date: 2013-04-15
*
* Diary:
*
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-04-15 | NM        | [+] Creazione classe e definizione metodi  
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.data.storage.dao;

import com.mytalk.server.data.model.*;
import java.util.*;

public class OnlineUserDAO extends GenericDAO{

	public OnlineUserDAO(){}
	
	//verifica la presenza dell'ip nella tabella Online
	public boolean checkIPIsOnline(String s){
		boolean b=false;
		OnlineUser o=(OnlineUser) session.get(OnlineUser.class,s);
		t.commit();
		session.close();
		if(o!=null){
			b=true;
		}
		return b;
	}
	
	//verifica la presenza dell'username nella tabella OnlineUsers
	public boolean nameIsOnline(String s){
		boolean b=false;
		List<OnlineUser> l=session.createSQLQuery("SELECT * FROM OnlineUsers WHERE username=:s").addEntity(OnlineUser.class).setParameter("s",s).list();
		if(l.size()>0){
			b=true;
		}
		t.commit();
		session.close();
		return b;
	}
	
	public void login(String u, String ip){
		OnlineUser o=new OnlineUser(u,ip);
		session.update(o);
		t.commit();
		session.close();
	}
	
	public void logout(String s){
		List<OnlineUser> l=session.createSQLQuery("SELECT * FROM OnlineUsers WHERE username=:s").addEntity(OnlineUser.class).setParameter("s", s).list();
		if(l.size()>0){
			session.delete(l.get(0));
		}
		t.commit();
		session.close();
	}
	
	public List<User> getOnlineUsers(){
		List<User> l=session.createSQLQuery("SELECT * FROM Users WHERE username IS NOT NULL AND username IN (SELECT username FROM OnlineUsers)").addEntity(User.class).list();
		t.commit();
		session.close();
		return l;
	}
	
	public void loginAnonymous(String ip){
		OnlineUser oc=(OnlineUser) session.get(OnlineUser.class, ip);
		if(oc==null){
			OnlineUser o=new OnlineUser(null,ip);
			session.save(o);
		}	
		t.commit();
		session.close();
	}
	
	public void logoutAnonymous(String ip){
		List<OnlineUser> l=session.createSQLQuery("SELECT * FROM OnlineUsers WHERE ip=:ip").addEntity(OnlineUser.class).setParameter("ip", ip).list();
		if(l.size()>0){
			session.delete(l.get(0));
		}
		t.commit();
		session.close();
	}
}
