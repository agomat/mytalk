/**
* Filename: BlackListRemove.java
* Package: com.mytalk.server.logic.processing.requestProcessor.list
* Author: Michael Ferronato
* Date: 2013-05-01 
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-05-01 |  MF       | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/



package com.mytalk.server.logic.processing.requestProcessor.list;

import java.util.List;

import com.mytalk.server.exceptions.AuthenticationFail;
import com.mytalk.server.exceptions.UserNotBlacklisted;

import com.mytalk.server.logic.processing.requestProcessor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.ListPack;
import com.mytalk.server.logic.shared.modelClient.UserList;
import com.mytalk.server.logic.shared.modelClient.WrapperUserList;

public class BlackListRemove extends GenericRequest {
	public BlackListRemove(){}
	
	public ARI manage(ARI ari) {
		ARI response=null;
		//creo oggetto necessario per l'autenticazione
		Authentication auth=ari.getAuth();
		com.mytalk.server.data.model.User userAuth=new com.mytalk.server.data.model.User(auth.getUser(),auth.getPwd(),null,null,null);
		//ottengo la stringa di info
		String infoRequest=ari.getInfo();
		ListPack pack=(ListPack)conv.convertJsonToJava(infoRequest,ListPack.class);
		//elaboro l'oggetto ListPack per ricavare le informazioni necessarie a chiamare il metodo appropriato
		WrapperUserList wul=pack.getWrapperUserList(); //ricavo il wrapperUserList
		List<UserList> listUserList=wul.getList(); // userlist contiene nome lista e lista di stringhe
		UserList userList=null;
		com.mytalk.server.data.model.Blacklist blacklist=null;
		List<String> listString=null;
		String user=null;
		try{ // ho una sola blacklist perciò non scorro le liste
			userList=listUserList.get(0); //nome lista e la lista di user
			listString=userList.getList();// lista di user
			for(int j=0;j<listString.size();j++){ //scorre lista di utenti 
				user=listString.get(j);
				blacklist=new com.mytalk.server.data.model.Blacklist(auth.getUser(),user);
				da.blacklistRemove(blacklist,userAuth);
			}
			//se arrivo qua è andato a buon fine
			response=new ARI(null,"SuccessfulBlacklistRemove",infoRequest);
		}catch(UserNotBlacklisted unb){
			response=new ARI(null,"UnsuccessfulBlacklistRemove",infoRequest);
		}catch(AuthenticationFail af){
			response=new ARI(null,"AuthenticationFail",null);
		}
		return response;
	}

}
