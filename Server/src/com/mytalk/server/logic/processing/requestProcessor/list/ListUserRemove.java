/**
* Filename: ListUserRemove.java
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
import com.mytalk.server.exceptions.UserNotExisting;
import com.mytalk.server.exceptions.UserNotListed;
import com.mytalk.server.exceptions.UsernameNotCorresponding;
import com.mytalk.server.logic.processing.requestProcessor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.ListPack;
import com.mytalk.server.logic.shared.modelClient.UserList;


public class ListUserRemove extends GenericRequest {
	public ListUserRemove(){}
	
	public ARI manage(ARI ari){
		ARI response=null;
		//creo oggetto necessario per l'autenticazione
		Authentication auth=ari.getAuth();
		com.mytalk.server.data.model.User userAuth=new com.mytalk.server.data.model.User(auth.getUser(),auth.getPwd(),null,null,null,null);
		//ottengo la stringa di info
		String infoRequest=ari.getInfo();
		ListPack pack=(ListPack)conv.convertJsonToJava(infoRequest,ListPack.class);
		//elaboro l'oggetto ListPack per ricavare le informazioni necessarie a chiamare il metodo appropriato
		boolean checkAuth=checkAuthenticationWellFormed(auth);
		boolean checkPack=checkListPackWellFormed(pack);
		if(!checkAuth || !checkPack){
			response=new ARI(null,"CorruptedPack",null);
		}	
		else{		
			List<UserList> listUserList=pack.getList(); // userlist contiene nome lista e lista di stringhe
			UserList userList=null;
			com.mytalk.server.data.model.ListName listName=null;
			List<Integer> listString=null;
			String user=null;
			try{
				for(int i=0;i<listUserList.size();i++){ //scorre tutte le liste presenti
					userList=listUserList.get(i); //nome lista e la lista di user
					listName=new com.mytalk.server.data.model.ListName(userList.getName(),auth.getUser()); //nome lista e owner
					listString=userList.getList();// lista di user
					for(int j=0;j<listString.size();j++){ //scorre lista di utenti 
						user=(da.getUserById(listString.get(j))).getUsername();
						da.userListRemove(listName, user, userAuth);
					}
					//se arrivo qua è andato a buon fine
					response=new ARI(null,"SuccessfulListUserRemove",infoRequest);
				}
			}catch(UserNotListed unl){
				response=new ARI(null,"UserNotListed",infoRequest);
			}catch(AuthenticationFail af){
				response=new ARI(null,"AuthenticationFail",null);
			} catch (IdNotFound e) {
				response=new ARI(null,"IdNotFound",null);
			}catch (UserNotExisting e) {
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
