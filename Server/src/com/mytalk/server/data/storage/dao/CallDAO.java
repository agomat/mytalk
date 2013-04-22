/**
* Filename: CallDAO.java
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
import java.util.*;

import com.mytalk.server.data.model.*;

public class CallDAO extends GenericDAO {

	public CallDAO(){}
	
	//ritorna le chiamate effettuate e ricevute di un utente
	public List<Call> getCalls(String u){
		List<Call> l=session.createSQLQuery("SELECT * FROM Calls WHERE caller=:u OR receiver=:u").addEntity(Call.class).setParameter("u",u).list();
		
		t.commit();
		session.close();
		return l;
	}
	
	//aggiunge una chiamata al database
	public void addCall(String c,String r,Integer d,Integer bt,Integer br){
		Call s=new Call(c,r,d,bt,br);
		session.save(s);
		
		t.commit();
		session.close();
	}
}
