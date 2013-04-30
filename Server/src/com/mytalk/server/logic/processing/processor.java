/**
* Filename: processor.java
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
import java.util.*;

public class processor {
	//classe che riceve e manda il JSON alla Comunication
	//comunica la richiesta alla GenericRequest
	Map<String, Object> hm;
	
	public processor(){
		hm = new HashMap<String, Object>();
		hm.put("NotRegisteredCall",new NotRegisteredCall());
		hm.put("RegisteredCall",new RegisteredCall());
		hm.put("RefuseCallC",new RefuseCallC());
		hm.put("CreateAccount",new CreateAccount());
		hm.put("DeleteAccount",new DeleteAccount());
		hm.put("Login",new Login());
		hm.put("Logout",new Logout());
		hm.put("PasswordRetriever",new PasswordRetriver());
		hm.put("ConfirmChangePassword",new ConfirmChangePassword());
		hm.put("ChangePassword",new ChangePassword());
		hm.put("UpdateEmail",new UpdateEmail());
		hm.put("ConfirmUpdateEmail",new ConfirmUpdateEmail());
		hm.put("AccountConfirm",new AccountConfirm());
		hm.put("ListCreate",new ListCreate());
		hm.put("ListDelete",new ListDelete());
		hm.put("ListUserAdd",new ListUserAdd());
		hm.put("ListUserRemove",new ListUserRemove());
		hm.put("BlacklistAdd",new BlacklistAdd());
		hm.put("BlackListRemove",new BlackListRemove());
		hm.put("GetCalls",new GetCalls());
		hm.put("GetGlobalStats",new GetGlobalStats());
		hm.put("AddCall",new AddCall());
		hm.put("UserOffline",new UserOffline());
		hm.put("RequestCall",new RequestCall());
		hm.put("AcceptCall",new AcceptCall());
		hm.put("RefuseCallS",new RefuseCallS());
		hm.put("SuccessfulCreateAccount",new SuccessfulCreateAccount());
		hm.put("UnsuccessfulCreateAccount",new UnsuccessfulCreateAccount());
		hm.put("SuccessfulDeleteAccount",new SuccessfulDeleteAccount());
		hm.put("SuccessfulLogin",new SuccessfulLogin());
		hm.put("UnsuccessfulLogin",new UnsuccessfulLogin());
		hm.put("SuccessfulLogout",new SuccessfulLogout());
		hm.put("UnsuccessfulLogout",new UnsuccessfulLogout());
		hm.put("SuccessfulPasswordRetriever",new SuccessfulPasswordRetriever());
		hm.put("SuccessfulChangePassword",new SuccessfulChangePassword());
		hm.put("UnsuccessfulChangePassword",new UnsuccessfulChangePassword());
		hm.put("SuccessfulUpdateEmail",new SuccessfulUpdateEmail());
		hm.put("SuccessfulConfirmUpdateEmail",new SuccessfulConfirmUpdateEmail());
		hm.put("SuccessfulAccountConfirm",new SuccessfulAccountConfirm());
		hm.put("SuccessfulListCreate",new SuccessfulListCreate());
		hm.put("UnsuccessfulListCreate",new UnsuccessfulListCreate());
		hm.put("SuccessfulListDelete",new SuccessfulListDelete());
		hm.put("SuccessfulListUserAdd",new SuccessfulListUserAdd());
		hm.put("UnsuccessfulListUserAdd",new UnsuccessfulListUserAdd());
		hm.put("SuccessfulListUserRemove",new SuccessfulListUserRemove());
		hm.put("UnsuccessfulListUserRemove",new UnsuccessfulListUserRemove());
		hm.put("SuccessfulBlacklistAdd",new SuccessfulBlacklistAdd());
		hm.put("UnsuccessfulBlacklistAdd",new UnsuccessfulBlacklistAdd());
		hm.put("SuccessfulBlacklistRemove",new SuccessfulBlacklistRemove());
		hm.put("UnsuccessfulBlacklistRemove",new UnsuccessfulBlacklistRemove());
		hm.put("UpdateStatus",new UpdateStatus());
		hm.put("GiveGlobalStats",new GiveGlobalStats());
		hm.put("GiveCalls",new GiveCalls());
	}
		
	//metodo che riceve JSON, ottiene la conversione in ARI e manda la Stringa "richiesta", controllando tipo
	public void send(String json){
		convert c=new convert();
		ARI pack=c.JsonToJava(json);
		String request=pack.getReq();
		genericRequest r=(genericRequest) hm.get(request);
	}
	
	//metodo che riceve l'ARI, lo converte in JSON e manda la comunication
	public void up(){}
}
