/**
* Filename: BlackListAdd.java
* Package: com.mytalk.server.logic.processing.request_processor.list_information
* Author: Michael Ferronato
* Date: 2013-05-01 
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.3     | 2013-06-22 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.2	  |	2013-05-22 |    MF     | [#] Modifica dei nomi del metodo manage al fine di renderli
* 									     piu' espressivi e di facile comprensione
* 0.1	  |	2013-05-01 |    MF     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che si occupa di aggiungere un utente nella blacklist del client che ne fa la richiesta
*/

package com.mytalk.server.logic.processing.request_processor.list_information;

import java.util.List;

import com.mytalk.server.exceptions.AuthenticationFailException;
import com.mytalk.server.exceptions.IdNotFoundException;
import com.mytalk.server.exceptions.UserAlreadyBlacklistedException;
import com.mytalk.server.exceptions.UserNotExistingException;
import com.mytalk.server.exceptions.UsernameNotCorrespondingException;
import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.ListPack;
import com.mytalk.server.logic.shared.model_client.UserList;


public class BlackListAdd extends GenericRequest {
	
	/**
	 * Costruttore della classe con corpo vuoto poiche' non vi sono campi dati da inizializzare
	 * 
	 * @method +BlackListAdd
	 */
	public BlackListAdd(){}
	
	/**
	 * Una volta ricevuto l'ARI e fatti i controlli di pacchetto ben formato, usando i dati ricevuti dal 
	 * campo dati ari, viene creato un oggetto di tipo CSDAT1. Blacklist, utilizzato per aggiungere 
	 * l'utente alla blacklist utilizzando l'oggetto DataAccess, classe appartenente al componente 
	 * CSDAT2. Viene restituito un "SuccessfulBlackListAdd". Se non va a buon fine, vengono 
	 * sollevate e catturate le seguenti eccezioni: "UserAlreadyBlacklistedException", 
	 * "AuthenticationFailException", "UserNotExistingException", "UsernameNotCorrespondingException", 
	 * "IdNotFoundException", ritornando un pacchetto con campo richiesta "UserAlreadyBlacklisted", 
	 * "AuthenticationFailBlackListAdd", "UserNotExistingBlackListAdd", "UserAlreadyBlacklisted", 
	 * "User"UsernameNotCorrespondingBlackListAdd" o "IdNotFoundBlackListAdd". In caso di pacchetto 
	 * mal formato, viene restituito un "CorruptedPack"
	 *  
	 *  @method +manage
	 *  @param {ARI} ari e' l'oggetto che contiene le informazioni necessarie alla classe per
	 *  poter processare la specifica richiesta e restituire il risultato dell'elaborazione
	 *  @return {ARI}
	 */
	public ARI manage(ARI ari){
		ARI response=null;
		Authentication auth=ari.getAuth();
		String infoRequest=ari.getInfo();
		ListPack pack=(ListPack)conv.convertJsonToJava(infoRequest,ListPack.class);
		boolean checkAuth=checkAuthenticationWellFormed(auth);
		boolean checkPack=checkListPackWellFormed(pack);
		if(!checkAuth || !checkPack){
			response=new ARI(null,"CorruptedPack",infoRequest);
		}	
		else{
			com.mytalk.server.data.model.User userAuth=new com.mytalk.server.data.model.User(auth.getUser(),auth.getPwd(),null,null,null,null);
			List<UserList> listUserList=pack.getList(); 
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

				response=new ARI(null,"SuccessfulBlackListAdd",infoRequest);
			}catch(UserAlreadyBlacklistedException uab){
				response=new ARI(null,"UserAlreadyBlacklisted",infoRequest);
			}catch(AuthenticationFailException af){
				response=new ARI(null,"AuthenticationFailBlackListAdd",infoRequest);
			} catch (UserNotExistingException une) {
				response=new ARI(null,"UserNotExistingBlackListAdd",infoRequest);
			} catch (UsernameNotCorrespondingException unc) {
				response=new ARI(null,"UsernameNotCorrespondingBlackListAdd",infoRequest);
			} catch (IdNotFoundException inf) {
				response=new ARI(null,"IdNotFoundBlackListAdd",infoRequest);
			}
		}
		return response;
	}
}
