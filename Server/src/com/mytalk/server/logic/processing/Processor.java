/**
* Filename: Processor.java
* Package: com.mytalk.server.logic.shared
* Author: Nicolò Toso
* Date: 2013-04-29
*
* Diary:
*
* Version |
Date
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-04-29 | NT        | [+] Creazione classe, costruttore e metodi   
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/
package com.mytalk.server.logic.processing;

import com.mytalk.server.logic.shared.*;
import com.mytalk.server.logic.processing.requestProcessor.*;
import com.mytalk.server.logic.processing.requestProcessor.list.*;
import com.mytalk.server.logic.processing.requestProcessor.stats.*;

import java.util.*;

public class Processor {
	//classe che riceve e manda il JSON alla Comunication
	//comunica la richiesta alla GenericRequest
	Map<String, GenericRequest> hm;
	
	public Processor(){
		hm = new HashMap<String, GenericRequest>();
		hm.put("NotRegisteredCall",new NotRegisteredCall());
		hm.put("RegisteredCall",new RegisteredCall());
		hm.put("RefuseCall",new RefuseCall());
		hm.put("CreateAccount",new CreateAccount());
		hm.put("DeleteAccount",new DeleteAccount());
		hm.put("Login",new Login());
		hm.put("Logout",new Logout());
		
		hm.put("PasswordRetriever",new PasswordRetriever());
		hm.put("ConfirmChangePassword",new ConfirmChangePassword());
		hm.put("ChangePassword",new ChangePassword());
		hm.put("UpdateEmail",new UpdateEmail());
		hm.put("ConfirmUpdateEmail",new ConfirmUpdateEmail());
		hm.put("AccountConfirm",new AccountConfirm());
		hm.put("ListCreate",new ListCreate());
		hm.put("ListDelete",new ListDelete());
		
		hm.put("ListUserAdd",new ListUserAdd());
		hm.put("ListUserRemove",new ListUserRemove());
		hm.put("BlacklistAdd",new BlackListAdd());
		hm.put("BlackListRemove",new BlackListRemove());
		hm.put("GetCalls",new GetCalls());
		hm.put("GetGlobalStats",new GetGlobalStats());
		hm.put("AddCall",new AddCall());
	}
		
	//metodo che riceve JSON, ottiene la conversione in ARI e manda la Stringa "richiesta", controllando tipo
	public void processRequest(String json){
		Convert c=new Convert();
		ARI pack=c.convertJsonToJava(json);
		String request=pack.getReq();
		GenericRequest r= hm.get(request);
		r.manage(pack);
	}
	
	//metodo che riceve l'ARI, lo converte in JSON e manda la comunication
	public void up(){}
}
