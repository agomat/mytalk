/**
* Filename: MailChangeDAO.java
* Package: com.mytalk.server.data.storage.dao
* Author: Nicolò Mazzucato
* Date: 2013-04-15
*
* Diary:
*
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-04-16 | NM        | [+] Creazione classe e definizione metodi  
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.data.storage.dao;

import com.mytalk.server.data.model.*;

public class MailChangeDAO extends GenericDAO {
	
	public MailChangeDAO(){}

	//inserisce un record nella tabella MailChange con username, email e codice
	public void updateEmail(String u,String e,String code){
		MailChange m=new MailChange(u,e,code);
		MailChange n=(MailChange)session.get(MailChange.class, u);
		if(n!=null){
			session.delete(n);
		}
		session.save(m);
		t.commit();
		session.close();
	}
	
	public String confirmUpdateEmail(String u,String code){
		String mail=null;
		MailChange n=(MailChange)session.get(MailChange.class, u);
		if(n!=null){
			mail=n.getNewmail();
			session.delete(n);
		}
		t.commit();
		session.close();
		return mail;
	}
}
