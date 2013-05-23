/**
* Filename: StateUpdate.java
* Package: com.mytalk.server.logic.processing.request_processor.state_update_information
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


package com.mytalk.server.logic.processing.request_processor.state_update_information;

import com.mytalk.server.exceptions.IdNotFound;
import com.mytalk.server.exceptions.UserNotExisting;
import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.UserStatePack;
import com.mytalk.server.logic.shared.model_client.User;

public class StateUpdate extends GenericRequest{
	public StateUpdate(){}
	
	public ARI manage(ARI ari){
		ARI response=null;
		Integer id;
		UserStatePack pack;
		UserStatePack packIn;
		String infoResponse;
		User userClient=null;
		boolean status=false;
		if(ari.getReq().equals("Login")){
			status=true;
		}
		if(ari.getReq().equals("DeleteAccount")){
			packIn=(UserStatePack)conv.convertJsonToJava(ari.getInfo(), UserStatePack.class);
			userClient=new User(packIn.getUser().getId(),packIn.getUser().getUsername(),packIn.getUser().getName(),packIn.getUser().getSurname(),packIn.getUser().getMd5(),ari.getAuth().getIp(),status);
			pack= new UserStatePack(userClient);
			infoResponse=conv.convertJavaToJson(pack);
			return new ARI(null,"StateUpdate",infoResponse);
		}
		try {
			id = da.getIdFromUsername(ari.getAuth().getUser());
			com.mytalk.server.data.model.User user=da.getUserById(id);
			userClient=new User(id,user.getUsername(),user.getName(),user.getSurname(),user.getEmailHash(),ari.getAuth().getIp(),status);
			pack= new UserStatePack(userClient);
			infoResponse=conv.convertJavaToJson(pack);
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
