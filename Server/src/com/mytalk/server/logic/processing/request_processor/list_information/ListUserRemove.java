/**
* Filename: ListUserRemove.java
* Package: com.mytalk.server.logic.processing.request_processor.list_information
* Author: Michael Ferronato
* Date: 2013-05-01 
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2	  |	2013-05-22 |   MF      | [#] Modifica dei nomi del metodo manage al fine di renderli
* 									     più espressivi e di facile comprensione
* 0.1	  |	2013-05-01 |   MF      | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.logic.processing.request_processor.list_information;

import java.util.List;

import com.mytalk.server.exceptions.AuthenticationFail;
import com.mytalk.server.exceptions.IdNotFound;
import com.mytalk.server.exceptions.ListNotExisting;
import com.mytalk.server.exceptions.UserNotExisting;
import com.mytalk.server.exceptions.UserNotListed;
import com.mytalk.server.exceptions.UsernameNotCorresponding;
import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.ListPack;
import com.mytalk.server.logic.shared.model_client.UserList;


public class ListUserRemove extends GenericRequest {
	public ListUserRemove(){}
	
	public ARI manage(ARI ari){
		ARI response=null;
		Authentication auth=ari.getAuth();
		com.mytalk.server.data.model.User userAuth=new com.mytalk.server.data.model.User(auth.getUser(),auth.getPwd(),null,null,null,null);
		String infoRequest=ari.getInfo();
		ListPack pack=(ListPack)conv.convertJsonToJava(infoRequest,ListPack.class);
		boolean checkAuth=checkAuthenticationWellFormed(auth);
		boolean checkPack=checkListPackWellFormed(pack);
		if(!checkAuth || !checkPack){
			response=new ARI(null,"CorruptedPack",infoRequest);
		}	
		else{		
			List<UserList> listUserList=pack.getUserIds(); // userlist contiene nome lista e lista interi
			UserList userList=null;
			com.mytalk.server.data.model.ListName listName=null;
			List<Integer> listString=null;
			String user=null;
			try{
				userList=listUserList.get(0); //nome lista e la lista di user
				listName=new com.mytalk.server.data.model.ListName(userList.getName(),auth.getUser()); //nome lista e owner
				listString=userList.getList();// lista di user
				user=(da.getUserById(listString.get(0))).getUsername();
				da.userListRemove(listName, user, userAuth);
				response=new ARI(null,"SuccessfulListUserRemove",infoRequest);
			}catch(UserNotListed unl){
				response=new ARI(null,"UserNotListed",infoRequest);
			}catch(AuthenticationFail af){
				response=new ARI(null,"AuthenticationFailListUserRemove",infoRequest);
			}catch (IdNotFound e) {
				response=new ARI(null,"IdNotFoundListUserRemove",infoRequest);
			}catch (UserNotExisting e) {
				response=new ARI(null,"UserNotExistingListUserRemove",infoRequest);
			} catch (ListNotExisting e) {
				response=new ARI(null,"ListNotExistingListUserRemove",infoRequest);
			} catch (UsernameNotCorresponding e) {
				response=new ARI(null,"UsernameNotCorrespondingListUserRemove",infoRequest);
			}
		}
		return response;
	}

}
