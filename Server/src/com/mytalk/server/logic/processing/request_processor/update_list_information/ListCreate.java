/**
* Filename: ListCreate.java
* Package: com.mytalk.server.logic.processing.request_processor.update_list_information
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

package com.mytalk.server.logic.processing.request_processor.update_list_information;

import com.mytalk.server.data.model.ListName;
import com.mytalk.server.exceptions.AuthenticationFail;
import com.mytalk.server.exceptions.ListAlreadyExists;
import com.mytalk.server.exceptions.UsernameNotCorresponding;
import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.UpdateListPack;


public class ListCreate extends GenericRequest {

	public ARI manage(ARI ari) {
		ARI response=null;
		Authentication auth=ari.getAuth();
		String infoRequest=ari.getInfo();
		UpdateListPack pack=(UpdateListPack)conv.convertJsonToJava(infoRequest,UpdateListPack.class);
		boolean checkAuth=checkAuthenticationWellFormed(auth);
		boolean checkPack=checkPartialUpdateListPackWellFormed(pack);
		if(!checkAuth || !checkPack){
			response=new ARI(null,"CorruptedPack",infoRequest);
		}
		else{
			com.mytalk.server.data.model.User userAuth=new com.mytalk.server.data.model.User(auth.getUser(),auth.getPwd(),null,null,null,null);
			String listName=pack.getListName();
			ListName newList=new ListName(listName,pack.getOwner());
			try{
				da.listCreate(newList,userAuth);
				response=new ARI(null,"SuccessfulListCreate",infoRequest);
			}catch(ListAlreadyExists lae){
				response=new ARI(null,"ListAlreadyExists",infoRequest);
			}catch(AuthenticationFail af){
				response=new ARI(null,"AuthenticationFailListCreate",infoRequest);
			} catch (UsernameNotCorresponding e) {
				response=new ARI(null,"UsernameNotCorrespondingListCreate",infoRequest);
			}
		}
		return response;
	}

}
