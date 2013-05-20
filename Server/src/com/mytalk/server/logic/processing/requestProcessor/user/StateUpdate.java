/**
* Filename: StateUpdate.java
* Package: com.mytalk.server.logic.processing.requestProcessor.user
* Author: Michael Ferronato
* Date: 2013-05-20
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |2013-05-20  |   MF      | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.logic.processing.requestProcessor.user;

import java.util.ArrayList;
import java.util.List;

import com.mytalk.server.exceptions.IdNotFound;
import com.mytalk.server.exceptions.UserNotExisting;
import com.mytalk.server.logic.shared.modelClient.WorldList;
import com.mytalk.server.logic.processing.requestProcessor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.WorldPack;
import com.mytalk.server.logic.shared.modelClient.User;

public class StateUpdate extends GenericRequest{
	public StateUpdate(){}
	
	public ARI manage(ARI ari){
		ARI response=null;
		int id;
		boolean status=false;
		if(ari.getReq().equals("Login") || ari.getReq().equals("CreateAccount")){
			status=true;
		}
		try {
			id = da.getIdFromUsername(ari.getAuth().getUser());
			com.mytalk.server.data.model.User user=da.getUserById(id);
			User userClient=new User(id,user.getUsername(),user.getName(),user.getSurname(),user.getEmailHash(),ari.getAuth().getIp(),status);
			List<User> list= new ArrayList<User>();
			list.add(userClient);
			WorldList wl= new WorldList(null,list);
			WorldPack wp=new WorldPack(wl,null);
			String infoResponse=conv.convertJavaToJson(wp);
			
			response=new ARI(null,"StateUpdate",infoResponse);
		} catch (UserNotExisting e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IdNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}
}
