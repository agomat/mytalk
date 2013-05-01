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
import com.mytalk.server.exceptions.UserAlreadyListed;
import com.mytalk.server.logic.processing.Convert;
import com.mytalk.server.logic.processing.requestProcessor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.ListPack;
import com.mytalk.server.logic.shared.modelClient.*;

public class ListUserAdd extends GenericRequest{
	public ListUserAdd(){}
	
	public ARI manage(ARI ari){
		ARI response=null;
		//creo oggetto necessario per l'autenticazione
		Authentication auth=ari.getAuth();
		com.mytalk.server.data.model.User userAuth=new com.mytalk.server.data.model.User(auth.getUser(),auth.getPwd(),null);
		//ottengo la stringa di info
		String infoRequest=ari.getInfo();
		ListPack pack=(ListPack)conv.convertJsonToJava(infoRequest,ListPack.class);
		//elaboro l'oggetto ListPack per ricavare le informazioni necessarie a chiamare il metodo appropriato
		WrapperUserList wul=pack.getWrapperUserList(); //ricavo il wrapperUserList
		List<UserList> listUserList=wul.getList(); // userlist contiene nome lista e lista di stringhe
		UserList userList=null;
		com.mytalk.server.data.model.ListName listName=null;
		List<String> listString=null;
		String user=null;
		try{
			for(int i=0;i<listUserList.size();i++){ //scorre tutte le liste presenti
				userList=listUserList.get(i); //nome lista e la lista di user
				listName=new com.mytalk.server.data.model.ListName(userList.getName(),auth.getUser()); //nome lista e owner
				listString=userList.getList();// lista di user
				for(int j=0;j<listString.size();j++){ //scorre lista di utenti 
					user=listString.get(j);
					da.userListAdd(listName, user, userAuth);
				}
				//se arrivo qua è andato a buon fine
				response=new ARI(null,"SuccessfulListUserAdd",infoRequest);
			}
		}catch(UserAlreadyListed ual){
			response=new ARI(null,"UnsuccessfulListUserAdd",infoRequest);
		}catch(AuthenticationFail af){
			response=new ARI(null,"AuthenticationFail",null);
		}
		return response;
	}
}
