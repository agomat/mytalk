/**
* Filename: BlackListAdd.java
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

import java.util.List;

import com.mytalk.server.exceptions.AuthenticationFail;
import com.mytalk.server.exceptions.IdNotFound;
import com.mytalk.server.exceptions.UserAlreadyBlacklisted;
import com.mytalk.server.exceptions.UserNotExisting;
import com.mytalk.server.exceptions.UsernameNotCorresponding;
import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.ListPack;
import com.mytalk.server.logic.shared.model_client.UserList;


public class BlackListAdd extends GenericRequest {
	public BlackListAdd(){}
	
	public ARI manage(ARI ari){
		ARI response=null;
		Authentication auth=ari.getAuth();
		String infoRequest=ari.getInfo();
		ListPack pack=(ListPack)conv.convertJsonToJava(infoRequest,ListPack.class);
		boolean checkAuth=checkAuthenticationWellFormed(auth);
		boolean checkPack=checkListPackWellFormed(pack);
		if(!checkAuth || !checkPack){
			response=new ARI(null,"CorruptedPack",null);
		}	
		else{
			com.mytalk.server.data.model.User userAuth=new com.mytalk.server.data.model.User(auth.getUser(),auth.getPwd(),null,null,null,null);
			List<UserList> listUserList=pack.getList(); // userlist contiene nome lista e lista di stringhe
			UserList userList=null;
			com.mytalk.server.data.model.Blacklist blacklist=null;
			List<Integer> listUser=null;
			String username=null;
			try{ 
				userList=listUserList.get(0); 
				listUser=userList.getList();
				username=(da.getUserById(listUser.get(0))).getUsername();
				blacklist=new com.mytalk.server.data.model.Blacklist(auth.getUser(),username);
				da.blacklistAdd(blacklist,userAuth);

				response=new ARI(null,"SuccessfulBlacklistAdd",infoRequest);
			}catch(UserAlreadyBlacklisted uab){
				response=new ARI(null,"UserAlreadyBlacklisted",infoRequest);
			}catch(AuthenticationFail af){
				response=new ARI(null,"AuthenticationFail",null);
			} catch (UserNotExisting une) {
				response=new ARI(null,"UserNotExisting",null);
			} catch (UsernameNotCorresponding unc) {
				response=new ARI(null,"UsernameNotCorresponding",null);
			} catch (IdNotFound inf) {
				response=new ARI(null,"IdNotFound",null);
			}
		}
		return response;
	}
}
