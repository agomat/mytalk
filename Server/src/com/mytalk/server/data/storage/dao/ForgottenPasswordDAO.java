/**
* Filename: ForgottenPasswordDAO.java
* Package: com.mytalk.server.data.storage.dao
* Author: Nicolò Mazzucato
* Date: 2013-04-15
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-04-17 | NM        | [+] Creazione classe e definizione metodi  
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.data.storage.dao;

import org.hibernate.Transaction;
import com.mytalk.server.data.model.*;

public class ForgottenPasswordDAO extends GenericDAO{

	public ForgottenPasswordDAO(){}
	
	//Aggiunge un oggetto ForgottenPassword ricevuto in input
	public void save(ForgottenPassword forgottenPasswordObj){
		Transaction t=session.beginTransaction();
		session.save(forgottenPasswordObj);
		t.commit();
		session.close();
	}
	
	//Aggiorna un oggetto Blacklist passato in input
	public void update(ForgottenPassword forgottenPasswordObj){
		Transaction t=session.beginTransaction();
		ForgottenPassword forgottenPasswordEntity=this.get(forgottenPasswordObj.getUsername());
		if(forgottenPasswordEntity.getNewpwd()==null){
			forgottenPasswordObj.setNewpwd(forgottenPasswordEntity.getNewpwd());
		}
		session.update(forgottenPasswordObj);
		t.commit();
	}
	
	//Ottenere un oggetto di tipo Blacklist
	public ForgottenPassword get(String primaryKey){
		Transaction t=session.beginTransaction();
		ForgottenPassword forgottenPasswordObj=(ForgottenPassword) session.get(ForgottenPassword.class,primaryKey);
		t.commit();
		return forgottenPasswordObj;
	}
	
	//Cancella un oggetto Blacklist passato in input
	public void delete(ForgottenPassword forgottenPasswordObj){
		Transaction t=session.beginTransaction();
		session.delete(forgottenPasswordObj);
		t.commit();
	}
}
