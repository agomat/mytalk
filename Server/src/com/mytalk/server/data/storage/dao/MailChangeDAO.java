/**
* Filename: MailChangeDAO.java
* Package: com.mytalk.server.data.storage.dao
* Author: Nicolò Mazzucato
* Date: 2013-04-15
*
* Diary:
* Version | Date       | Developer | Changes
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
import org.hibernate.*;

public class MailChangeDAO extends GenericDAO{
	
	public MailChangeDAO(){}
	
	public void save(MailChange mailObj){
		Transaction t=session.beginTransaction();
		session.save(mailObj);
		t.commit();
	}
	
	public void update(MailChange mailObj){
		Transaction t=session.beginTransaction();
		MailChange mailEntity=this.get(mailObj.getUsername());
		if(mailObj.getNewmail()==null){
			mailObj.setNewmail(mailEntity.getNewmail());
		}
		if(mailObj.getCode()==null){
			mailObj.setCode(mailEntity.getCode());
		}
		session.update(mailObj);
		t.commit();
	}
	
	public MailChange get(String primaryKey){
		Transaction t=session.beginTransaction();
		MailChange mail=(MailChange)session.get(MailChange.class, primaryKey);
		t.commit();
		return mail;
	}
	
	public void delete(MailChange mailObj){
		Transaction t=session.beginTransaction();
		session.delete(mailObj);
		t.commit();
	}
}
