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


package com.mytalk.server.logic.processing.requestProcessor.list;

import java.util.List;

import com.mytalk.server.exceptions.AuthenticationFail;
import com.mytalk.server.exceptions.IdNotFound;
import com.mytalk.server.exceptions.UserAlreadyBlacklisted;
import com.mytalk.server.exceptions.UserNotExisting;
import com.mytalk.server.exceptions.UsernameNotCorresponding;
import com.mytalk.server.logic.processing.requestProcessor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.ListPack;
import com.mytalk.server.logic.shared.modelClient.UserList;


public class BlackListAdd extends GenericRequest {
	public BlackListAdd(){}
	
	public ARI manage(ARI ari){
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
			com.mytalk.server.data.model.User userAuth=new com.mytalk.server.data.model.User(auth.getUser(),auth.getPwd(),null,null,null,null);
			List<UserList> listUserList=pack.getList(); // userlist contiene nome lista e lista di stringhe
			UserList userList=null;
			com.mytalk.server.data.model.Blacklist blacklist=null;
			List<Integer> listUser=null;
			String username=null;
			try{ // ho una sola blacklist perciò non scorro le liste
				userList=listUserList.get(0); //nome lista e la lista di user
				listUser=userList.getList();// lista di user
				for(int j=0;j<listUser.size();j++){ //scorre lista di id utente 
					username=(da.getUserById(listUser.get(j))).getUsername();
					blacklist=new com.mytalk.server.data.model.Blacklist(auth.getUser(),username);
					da.blacklistAdd(blacklist,userAuth);
				}
				response=new ARI(auth,"SuccessfulBlacklistAdd",infoRequest);
			}catch(UserAlreadyBlacklisted uab){
				response=new ARI(auth,"UserAlreadyBlacklisted",infoRequest);
			}catch(AuthenticationFail af){
				response=new ARI(auth,"AuthenticationFail",null);
			} catch (UserNotExisting une) {
				response=new ARI(auth,"UserNotExisting",null);
			} catch (UsernameNotCorresponding unc) {
				response=new ARI(auth,"UsernameNotCorresponding",null);
			} catch (IdNotFound inf) {
				response=new ARI(auth,"IdNotFound",null);
			}
		}
		return response;
	}
}
