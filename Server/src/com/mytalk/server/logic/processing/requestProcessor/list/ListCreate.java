/**
* Filename: ListCreate.java
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
import com.mytalk.server.exceptions.ListAlreadyExists;
import com.mytalk.server.exceptions.UsernameNotCorresponding;
import com.mytalk.server.logic.processing.requestProcessor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.ListPack;
import com.mytalk.server.logic.shared.modelClient.UserList;
import com.mytalk.server.logic.shared.modelClient.WrapperUserList;


public class ListCreate extends GenericRequest {

	@Override
	public ARI manage(ARI ari) {
		ARI response=null;
		Authentication auth=ari.getAuth();
		String infoRequest=ari.getInfo();
		ListPack pack=(ListPack)conv.convertJsonToJava(infoRequest,ListPack.class);
		boolean checkAuth=this.checkAuthenticationWellFormed(auth);
		boolean checkPack=this.checkListPackWellFormed(pack);
		if(!checkAuth || !checkPack){
			response=new ARI(null,"CorruptedPack",null);
		}
		else{
			com.mytalk.server.data.model.User userAuth=new com.mytalk.server.data.model.User(auth.getUser(),auth.getPwd(),null,null,null);
			WrapperUserList wlist=pack.getWrapperUserList();
			List<UserList> listUserList=wlist.getList();
			UserList listObj=listUserList.get(0);
			String listName=listObj.getName();
			ListName newList=new ListName(listName,auth.getUser());
			try{
				da.listCreate(newList,userAuth);
				response=new ARI(null,"SuccessfulListCreate",infoRequest);
			}catch(ListAlreadyExists lae){
				response=new ARI(null,"UnsuccessfulListCreate",infoRequest);
			}catch(AuthenticationFail af){
				response=new ARI(null,"AuthenticationFail",null);
			} catch (UsernameNotCorresponding e) {
				response=new ARI(null,"UsernameNotCorresponding",null);
			}
		}
		return response;
	}

}
