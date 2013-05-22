/**
* Filename: UpdateListName.java
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


package com.mytalk.server.logic.processing.request_processor.list;

import com.mytalk.server.data.model.ListName;
import com.mytalk.server.exceptions.AuthenticationFail;
import com.mytalk.server.exceptions.ListAlreadyExists;
import com.mytalk.server.exceptions.ListNotExisting;
import com.mytalk.server.exceptions.UsernameNotCorresponding;
import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.UpdateListPack;

public class UpdateListName extends GenericRequest {

	@Override
	public ARI manage(ARI ari) {
		ARI response=null;
		Authentication auth=ari.getAuth();
		com.mytalk.server.data.model.User userAuth=new com.mytalk.server.data.model.User(auth.getUser(),auth.getPwd(),null,null,null,null);
		String infoRequest=ari.getInfo();
		UpdateListPack pack=(UpdateListPack)conv.convertJsonToJava(infoRequest, UpdateListPack.class);
		boolean checkPack=checkUpdateListPackWellFormed(pack);
		if(!checkPack){
			response=new ARI(null,"CorruptedPack",null);
		}else{
			String listOwner=pack.getOwner();
			String listName=pack.getListName();
			String newListName=pack.getNewListName();
			ListName list=new ListName(listName,listOwner);
			try {
				da.renameList(list, newListName, userAuth);
				response=new ARI(null,"SuccessfulRenameList",null);
			} catch (AuthenticationFail e) {
				response=new ARI(null, "AuthenticationFail", null);
			} catch (ListNotExisting e) {
				response=new ARI(null, "ListNotExisting", null);
			} catch (ListAlreadyExists e) {
				response=new ARI(null, "ListAlreadyExists", null);
			} catch (UsernameNotCorresponding e) {
				response=new ARI(null, "UsernameNotCorresponding", null);
			}
		}
		return response;
	}

}
