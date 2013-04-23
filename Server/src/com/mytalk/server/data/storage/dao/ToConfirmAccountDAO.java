/**
* Filename: ToConfirmAccountDAO.java
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

public class ToConfirmAccountDAO extends GenericDAO {
	public ToConfirmAccountDAO(){}
	
	// inserisce i dati nella tabella ToConfirmAccount dopo aver verificato che non sia già presente nella
	// tabella User e nella tabella ToConfirmAccount
	public void addAccountToBeConfirmed(String user, String pwd, String email){
		User u=(User) session.get(User.class, user);
		ToConfirmAccount tcac=(ToConfirmAccount) session.get(ToConfirmAccount.class, user);
		if(tcac==null && u==null){
			ToConfirmAccount tca= new ToConfirmAccount(user,pwd,email);
			session.save(tca);
		}		
		t.commit();
		session.close();
	}
	
	//elimino il record corrispondente all'username dalla tabella ToConfirmAccount
	public String[] deleteConfirmedAccount(String user){
		String[] pe=new String[2];
		ToConfirmAccount tca= (ToConfirmAccount) session.get(ToConfirmAccount.class, user);
		if(tca!=null){
			pe[0]=tca.getPassword();
			pe[1]=tca.getEmail();
			session.delete(tca);
		}
		t.commit();
		session.close();
		return pe;
	}
	
	// elimina tutti i record della tabella ToConfirmAccount
	public void deleteUnconfirmedAccount(){
		List<ToConfirmAccount> ltca= session.createSQLQuery("SELECT * FROM ToConfirmAccounts").addEntity(ToConfirmAccount.class).list();
		for(int i=0;i<ltca.size();i++){
			session.delete(ltca.get(i));
		}
		t.commit();
		session.close();
	}
	
}
