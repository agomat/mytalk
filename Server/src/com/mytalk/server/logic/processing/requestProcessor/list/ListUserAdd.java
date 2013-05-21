/**
* Filename: ListUserAdd.java
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
import com.mytalk.server.exceptions.ListNotExisting;
import com.mytalk.server.exceptions.UserAlreadyListed;
import com.mytalk.server.exceptions.UserNotExisting;
import com.mytalk.server.exceptions.UsernameNotCorresponding;
import com.mytalk.server.logic.processing.requestProcessor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.ListPack;
import com.mytalk.server.logic.shared.modelClient.*;

public class ListUserAdd extends GenericRequest{
	public ListUserAdd(){}
	
	public ARI manage(ARI ari){
		ARI response=null;
		Authentication auth=ari.getAuth();
		com.mytalk.server.data.model.User userAuth=new com.mytalk.server.data.model.User(auth.getUser(),auth.getPwd(),null,null,null,null);
		String infoRequest=ari.getInfo();
		ListPack pack=(ListPack)conv.convertJsonToJava(infoRequest,ListPack.class);
		boolean checkAuth=checkAuthenticationWellFormed(auth);
		boolean checkPack=checkListPackWellFormed(pack);
		if(!checkAuth || !checkPack){
			response=new ARI(null,"CorruptedPack",null);
		}	
		else{	
			List<UserList> listUserList=pack.getList();
			UserList userList=null;
			com.mytalk.server.data.model.ListName listName=null;
			List<Integer> listString=null;
			String user=null;
			try{
				userList=listUserList.get(0); 
				listName=new com.mytalk.server.data.model.ListName(userList.getName(),auth.getUser()); 
				listString=userList.getList();
				user=(da.getUserById(listString.get(0))).getUsername();
				da.userListAdd(listName, user, userAuth);
				response=new ARI(null,"SuccessfulListUserAdd",infoRequest);
			}
			catch(UserAlreadyListed ual){
				response=new ARI(null,"UserAlreadyListed",infoRequest);
			}catch(AuthenticationFail af){
				response=new ARI(null,"AuthenticationFail",null);
			} catch (IdNotFound inf) {
				response=new ARI(null,"IdNotFound",null);
			} catch (UserNotExisting e) {
				response=new ARI(null,"UserNotExisting",null);
			} catch (ListNotExisting e) {
				response=new ARI(null,"ListNotExisting",null);
			} catch (UsernameNotCorresponding e) {
				response=new ARI(null,"UsernameNotCorresponding",null);
			}
		}
		return response;
	}
}
