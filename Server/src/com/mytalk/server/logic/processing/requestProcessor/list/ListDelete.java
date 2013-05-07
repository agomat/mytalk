/**
* Filename: ListDelete.java
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

import com.mytalk.server.data.model.ListName;
import com.mytalk.server.exceptions.AuthenticationFail;
import com.mytalk.server.exceptions.ListNotExisting;
import com.mytalk.server.logic.processing.requestProcessor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.ListPack;
import com.mytalk.server.logic.shared.modelClient.UserList;
import com.mytalk.server.logic.shared.modelClient.WrapperUserList;

public class ListDelete extends GenericRequest {

	@Override
	public ARI manage(ARI pack) {
		ARI response=null;
		//creo oggetto necessario per l'autenticazione
		Authentication auth=pack.getAuth();
		com.mytalk.server.data.model.User userAuth=new com.mytalk.server.data.model.User(auth.getUser(),auth.getPwd(),null,null,null);
		//ottengo la stringa di info
		String infoRequest=pack.getInfo();
		ListPack list=(ListPack)conv.convertJsonToJava(infoRequest,ListPack.class);
		//elaborazione
		WrapperUserList wlist=list.getWrapperUserList();
		List<UserList> listUserList=wlist.getList();
		UserList listObj=listUserList.get(0);
		String listName=listObj.getName();
		ListName newList=new ListName(listName,auth.getUser());
		try{
			da.listDelete(newList,userAuth);
			response=new ARI(null,"SuccessfulListDelete",infoRequest);
		}catch(ListNotExisting lne){
			response=new ARI(null,"UnsuccessfulListDelete",infoRequest);
		}catch(AuthenticationFail af){
			response=new ARI(null,"AuthenticationFail",null);
		}
		return response;
	}

}
