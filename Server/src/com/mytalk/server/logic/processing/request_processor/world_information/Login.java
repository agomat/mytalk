/**
* Filename: Login.java
* Package: com.mytalk.server.logic.processing.request_processor.world_information
* Author: Nicolo' Toso
* Date: 2013-05-02
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.4     | 2013-06-22 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.3	  |	2013-05-22 |    MF     | [#] Modifica dei nomi del metodo manage al fine di renderli
* 									     più espressivi e di facile comprensione
* 0.2	  |	2013-05-15 |    MF     | [#] Ottimizzazione del metodo manage al fine di ridurre la 
* 										 complessita'
* 0.1	  |	2013-05-02 |    NT     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che si occupa di effettuare il login di un utente del client che ne fa richiesta
*/


package com.mytalk.server.logic.processing.request_processor.world_information;

import java.util.ArrayList;
import java.util.List;

import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.WorldPack;
import com.mytalk.server.logic.shared.model_client.*;

import com.mytalk.server.logic.processing.request_processor.*;
import com.mytalk.server.data.model.ListName;
import com.mytalk.server.data.model.OnlineUser;
import com.mytalk.server.exceptions.*;

public class Login extends GenericRequest{
	/**
	 * Costruttore della classe con corpo vuoto poiche' non vi sono campi dati da inizializzare
	 * 
	 * @method +Login
	 */
	public Login(){}
	
	/**
	 * Una volta ricevuto il pacchetto ARI, si controlla se è ben formato. Se la risposta è negativa,
	 *  viene ritornato un "CorruptedPack". Viceversa, viene invocato il corrispondente metodo 
	 *  sull'oggetto DataAccess e, se l'operazione è andata a buon fine, vengono caricate tutte le
	 *  informazioni riguardanti l'utente e le sue liste. Nel dettaglio, viene caricata la lista 
	 *  degli utenti distinguendo quelli online da quelli offline, le liste personali dell'utente e 
	 *  la blacklist. Tutto questo viene inserito nel pacchetto WorldPack, che a sua volta farà parte
	 *  del pacchetto informazione del nuovo pacchetto ARI sotto forma di stringa JSON. Tale 
	 *  pacchetto conterrà anche la buona riuscita dell'operazione: "SuccessfulLogin". Altrimenti,
	 *  è possibile che vengano sollevate e catturate le seguenti eccezioni: "AuthenticationFailException", 
	 *  "UsernameNotCorrespondingException", "IpNotLoggedException", "UserAlreadyLoggedException", "IpAlreadyLoggedException", 
	 *  "UserNotLoggedException", ritornando un pacchetto con campo richiesta "AuthenticationFailLogin", 
	 *  "UsernameNotCorrespondingLogin", "IpNotLoggedLogin", "UserAlreadyLoggedLogin",
	 *  "IpAlreadyLoggedLogin" o "UserNotLoggedLogin".
	 *  
	 *  @method +manage
	 *  @param {ARI} ari e' l'oggetto che contiene le informazioni necessarie alla classe per
	 *  poter processare la specifica richiesta e restituire il risultato dell'elaborazione
	 *  @return {ARI}
	 */
	public ARI manage(ARI ari){
		ARI response=null;
		Authentication auth=ari.getAuth();
		WorldPack responsePack=null;
		boolean checkAuth=checkAuthenticationWellFormed(auth);
		if(!checkAuth){
			response=new ARI(null,"CorruptedPack",null);
		}else{
			com.mytalk.server.data.model.User user=new com.mytalk.server.data.model.User(auth.getUser(),auth.getPwd(),null,null,null,null);
			OnlineUser onlineUser=new OnlineUser(ari.getAuth().getUser(), ari.getAuth().getIp());
			List<com.mytalk.server.data.model.User> listOnlineServer=null;
			List<com.mytalk.server.data.model.User> listOfflineServer=null;
			List<User> listAllUsersClient=new ArrayList<User>();
			User userClient=null;
			com.mytalk.server.data.model.User userServer=null;
			List<ListName> listName=null;
			List<UserList> listUserlist= new ArrayList<UserList>();
			UserList userList=null;
			List<com.mytalk.server.data.model.User> list=null;
			String ip_user=null;
			com.mytalk.server.data.model.User myData=null;
			List<com.mytalk.server.data.model.User> blackListUserServer=null;
			List<Integer> blackListInteger=new ArrayList<Integer>();
			try{
				da.login(onlineUser, user);
				
				listOnlineServer=da.getOnlineUsers(user);
				listOfflineServer=da.getOfflineUsers(user);
				for(int i=0;i<listOfflineServer.size();i++){
					userServer=listOfflineServer.get(i);
					userClient=new User(userServer.getId(),userServer.getUsername(),userServer.getName(),userServer.getSurname(),userServer.getEmailHash() ,null,false);
					listAllUsersClient.add(userClient);
				}
				
				for(int j=0;j<listOnlineServer.size();j++){
					userServer=listOnlineServer.get(j);
					if(userServer.getUsername().equals(user.getUsername())){
						myData=userServer;
					}
					else{
						ip_user=da.getUserIp(userServer.getUsername());
						userClient=new User(userServer.getId(),userServer.getUsername(),userServer.getName(),userServer.getSurname(),userServer.getEmailHash(),ip_user,true);
						listAllUsersClient.add(userClient);
					}			
				}
				
				listName=da.userLists(user); 
				for(int k=0;k<listName.size();k++){
					List<Integer> listInteger=new ArrayList<Integer>();
					list=da.getListUsers(listName.get(k), user);
					for(int z=0;z<list.size();z++){
						listInteger.add(list.get(z).getId());
					}
					userList=new UserList(listName.get(k).getId(),listName.get(k).getName(),listInteger);
					listUserlist.add(userList);
				}
				
				blackListUserServer=da.getUserBlacklist(user);
				for(int x=0;x<blackListUserServer.size();x++){
					blackListInteger.add(blackListUserServer.get(x).getId());
				}
				userList=new UserList(1,"BlackList",blackListInteger);
				listUserlist.add(userList);
					
				WorldList worldList=new WorldList(listUserlist,listAllUsersClient);
				String myIp=da.getUserIp(myData.getUsername());
				PersonalData pd=new PersonalData(myData.getId(),myData.getUsername(),myData.getPassword(),myData.getName(),myData.getSurname(),myData.getEmail(),myData.getEmailHash(),myIp);
				WorldPersonalData worldPersonalData=new WorldPersonalData(pd);
				
				responsePack=new WorldPack(worldList,worldPersonalData);
				String responsePackString=conv.convertJavaToJson(responsePack);
				
				response=new ARI(null, "SuccessfulLogin", responsePackString);
			} catch(AuthenticationFailException af){
				response=new ARI(null, "AuthenticationFailLogin", null);
			} catch (UsernameNotCorrespondingException e) {
				response= new ARI(null, "UsernameNotCorrespondingLogin",null);
			}catch (IpNotLoggedException e) {
				response= new ARI(null, "IpNotLoggedLogin",null);
			} catch (UserAlreadyLoggedException e) {
				response= new ARI(null, "UserAlreadyLoggedLogin",null);
			} catch (IpAlreadyLoggedException e) {
				response= new ARI(null, "IpAlreadyLoggedLogin",null);
			} catch (UserNotLoggedException e) {
				response= new ARI(null,"UserNotLoggedLogin",null);
			}
		}
		return response;
	}
}
