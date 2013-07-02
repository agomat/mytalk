/**
* Filename: ListUserRemove.java
* Package: com.mytalk.server.logic.processing.request_processor.list_information
* Author: Michael Ferronato
* Date: 2013-05-01 
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.3     | 2013-06-22 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.2	  |	2013-05-22 |    MF     | [#] Modifica dei nomi del metodo manage al fine di renderli
* 									     più espressivi e di facile comprensione
* 0.1	  |	2013-05-01 |    MF     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che si occupa di rimuovere un utente da una lista del client che ne fa richiesta
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
	/**
	 * Costruttore della classe con corpo vuoto poiche' non vi sono campi dati da inizializzare
	 * 
	 * @method +ListUserRemove
	 */
	public ListUserRemove(){}
	
	/**
	 * Una volta ricevuto l'ARI e fatti i controlli di pacchetto ben formato, usando i dati ricevuti
	 *  dal campo dati ari, viene creato un oggetto di tipo CSDAT1. ListName, utilizzato per
	 *  rimuovere un utente da una lista utilizzando l'oggetto DataAccess, classe appartenente 
	 *  al componente CSDAT2. Viene restituito un "SuccessfulListUserRemove". Se non va a buon
	 *  fine, vengono sollevate e catturate le seguenti eccezioni: "UserNotListed", "AuthenticationFail", "IdNotFound",
	 *  "UserNotExisting", "ListNotExisting" e "UsernameNotCorresponding", ritornando un pacchetto con
	 *  richiesta "UserNotListed", "AuthenticationFailListUserRemove", "IdNotFoundListUserRemove", 
	 *  "UserNotExistingListUserRemove", "ListNotExistingListUserRemove" o  
	 *  "UsernameNotCorrespondingListUserRemove". In caso di pacchetto mal formato,
	 * viene restituito un "CorruptedPack"
	 *  
	 *  @method +manage
	 *  @param {ARI} ari e' l'oggetto che contiene le informazioni necessarie alla classe per
	 *  poter processare la specifica richiesta e restituire il risultato dell'elaborazione
	 *  @return {ARI}
	 */
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
