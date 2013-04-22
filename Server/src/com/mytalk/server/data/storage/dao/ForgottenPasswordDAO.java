/**
* Filename: ForgottenPasswordDAO.java
* Package: com.mytalk.server.data.storage.dao
* Author: Nicolò Mazzucato
* Date: 2013-04-15
*
* Diary:
*
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-04-17 | NM        | [+] Creazione classe e definizione metodi  
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.data.storage.dao;

import com.mytalk.server.data.model.*;


public class ForgottenPasswordDAO extends GenericDAO {

	public ForgottenPasswordDAO(){}
	
	public void passwordRetriever(String u, String newpwd){
		ForgottenPassword m=new ForgottenPassword(u,newpwd);
		ForgottenPassword n=(ForgottenPassword)session.get(ForgottenPassword.class, u);
		if(n!=null){
			session.delete(n);
		}
		session.save(m);
		
		t.commit();
		session.close();
	}
	
	public String confirmChangePassword(String u){
		String s=null;
		ForgottenPassword m=(ForgottenPassword)session.get(ForgottenPassword.class, u);
		if(m!=null){
			s=m.getNewpwd();
			session.delete(m);
		}
		
		t.commit();
		session.close();
		
		return s;
	}
}
