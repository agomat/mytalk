/**
* Filename: GenericRequest.java
* Package: com.mytalk.server.logic.processing.request_processor
* Author: Nicolo' Toso
* Date: 2013-04-29
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-06-22 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.1	  |	2013-04-29 |    NT     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe astratta che rappresenta una richiesta generica proveniente dal client e contiene dei
* metodi di utilita' necessari per verificare il corretto contenuto dei pacchetti information e dei
* dati di autenticazione
*/

package com.mytalk.server.logic.processing.request_processor;

import java.util.List;
import com.mytalk.server.data.storage.DataAccess;
import com.mytalk.server.data.storage.IDataAccess;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.ConnectionPack;
import com.mytalk.server.logic.shared.GiveCallPack;
import com.mytalk.server.logic.shared.ListPack;
import com.mytalk.server.logic.shared.UpdateListPack;
import com.mytalk.server.logic.shared.WorldPack;
import com.mytalk.server.logic.shared.model_client.UserList;
import com.mytalk.server.logic.processing.Convert;

public abstract class GenericRequest {
	/**
	 * Identifica l'oggetto di tipo DataAccess utilizzato per comunicare con la componente CSDAT2
	 * 
	 * @property #da
	 * @type {IDataAccess}
	 */
	protected static IDataAccess da=new DataAccess();
	
	/**
	 * Identifica l'oggetto di tipo CSDAT1.Convert utilizzato per convertire le stringhe
	 *  da Java a JSON e viceversa
	 *  
	 *  @property #conv
	 *  @type {Convert}
	 */
	protected static Convert conv=new Convert();
	
	/**
	 * Costruttore della classe con corpo vuoto poiche' i due campi dati sono statici e vengono
	 * inizializzati staticamente
	 * 
	 * @method +GenericRequest
	 */
	public GenericRequest(){};
	
	/**
	 * Metodo astratto che si occupa di elaborare la richiesta e viene definito in modo specifico 
	 * dalle classi che implementano tale interfaccia 
	 * 
	 * @method +manage
	 * @param {ARI} pack e' l'oggetto che viene elaborato dal metodo al fine di processare la 
	 * specifica richiesta
	 * @return {ARI}
	 */
	public abstract ARI manage(ARI pack);
	
	/**
	 * Verifica che il pacchetto auth non abbia valore null e che i campi dati username e password
	 * siano diversi da null, in caso positivo restituisce true altrimenti false
	 * 
	 * @method +checkAuthenticationWellFormed
	 * @param {Authentication} auth e' l'oggetto sottoposto a verifica sulla sua buona formazione
	 * @return {boolean}
	 */
	public  static boolean checkAuthenticationWellFormed(Authentication auth){
		boolean check=false;
		if(auth!=null && auth.getUser()!=null && auth.getPwd()!=null){
			check=true;
		}
		return check;
	}
	
	/**
	 * Verifica che il pacchetto pack non abbia valore null e che tutti i suoi campi dati non
	 * abbiano un valore null, in caso positivo restituisce true altrimenti false
	 * 
	 * @method +checkConnectionPackWellFormed
	 * @param {ConnectionPack} pack e' l'oggetto sottoposto a verifica sulla sua buona formazione
	 * @return {boolean}
	 */
	public static boolean checkConnectionPackWellFormed(ConnectionPack pack){
		boolean check=false;
		if(pack!=null && pack.getMyIp()!=null && pack.getSpeakerIp()!=null && pack.getRTCinfo()!=null && pack.getMyUserId()!=null){
			check=true;
		}
		return check;
	}
	
	/**
	 * Verifica che il pacchetto pack non abbia valore null e che i suoi campi dati 
	 * myIp, speakerIp e RTCInfo non abbiano un valore null, in caso positivo restituisce true
	 * altrimenti false
	 * 
	 * @method +checkAnonymousConnectionPackWellFormed
	 * @param {ConnectionPack} pack e' l'oggetto sottoposto a verifica sulla sua buona formazione
	 * @return {boolean}
	 */
	public static boolean checkAnonymousConnectionPackWellFormed(ConnectionPack pack){
		boolean check=false;
		if(pack!=null && pack.getMyIp()!=null && pack.getSpeakerIp()!=null && pack.getRTCinfo()!=null){
				check=true;
		}
		return check;
	} 

	/**
	 * Verifica che il pacchetto pack non abbia valore null, che la lista di userlist non 
	 * abbia valore null e che l'oggetto userlist abbia i campi name e list con valore diverso 
	 * da null
	 * 
	 * @method +checkListPackWellFormed
	 * @param {ListPack} pack e' l'oggetto sottoposto a verifica sulla sua buona formazione
	 * @return {boolean}
	 */
	public static boolean checkListPackWellFormed(ListPack pack){
		boolean check=false;
		
		if(pack!=null && pack.getList()!=null){
			List<UserList> listUserList=pack.getList();
			if(listUserList.get(0)!=null && listUserList.get(0).getList()!=null && listUserList.get(0).getName()!=null){
				check=true;
			}	
		}
		return check;
	}
	
	/**
	 * Verifica che il pacchetto pack non abbia valore null, che la lista di userlist non abbia 
	 * valore null e che abbia dimensione maggiore di 0
	 * 
	 * @method +checkPartialListPackWellFormed
	 * @param {ListPack} pack e' l'oggetto sottoposto a verifica sulla sua buona formazione
	 * @return {boolean}
	 */
	public static boolean checkPartialListPackWellFormed(ListPack pack){
		boolean check=false;		
		if(pack!=null && pack.getList()!=null && pack.getList().size()!=0){
			check=true;
		}
		return check;
	}
	
	/**
	 * Verifica che il paccheto pack non abbia valore null, che il wrapperCall contenuto non abbia
	 * valore null, che il suo campo dati list non abbia valore null e che la corrispondente lista
	 * abbia dimensione maggiore di 0
	 * 
	 * @method +checkGiveCallPackWellFormed
	 * @param {GiveCallPack} pack e' l'oggetto sottoposto a verifica sulla sua buona formazione
	 * @return {boolean}
	 */
	public static boolean checkGiveCallPackWellFormed(GiveCallPack pack){
		boolean check=false;
		if(pack!=null && pack.getWrapperCall()!=null && pack.getWrapperCall().getList()!=null && pack.getWrapperCall().getList().size()!=0){
			check=true;
		}
		return check;
	}
	
	/**
	 * Verifica che il pacchetto pack non abbia valore null e che i due campi dati worldList e 
	 * worldPersonalData non abbiano valore uguale a null
	 * 
	 * @method +checkWorldPackWellFormed
	 * @param {WorldPack} pack e' l'oggetto sottoposto a verifica sulla sua buona formazione
	 * @return {boolean}
	 */
	public static boolean checkWorldPackWellFormed(WorldPack pack){
		boolean check=false;
		if(pack!=null && pack.getWorldPersonalData()!=null && pack.getWorldPersonalData().getPersonalData()!=null){
			check=true;
		}
		return check;
	}
	
	/**
	 * Verifica che il pacchetto pack non abbia valore null e che i suoi tre campi dati non
	 * abbiano valore uguale a null
	 * 
	 * @method +checkUpdateListPackWellFormed
	 * @param {UpdateListPack} pack e' l'oggetto sottoposto a verifica sulla sua buona formazione
	 * @return {boolean}
	 */
	public static boolean checkUpdateListPackWellFormed(UpdateListPack pack){
		boolean check=false;
		if(pack!=null && pack.getListName()!=null && pack.getNewListName()!=null && pack.getOwner()!=null){
			check=true;
		}
		return check;
	}
	
	/**
	 * verifica che il pacchetto pack non abbia valore null e che i suoi campi dati owner
	 * e listName non abbiamo valore null
	 * 
	 * @method +checkPartialUpdateListPackWellFormed
	 * @param {UpdateListPack} pack e' l'oggetto sottoposto a verifica sulla sua buona formazione
	 * @return {boolean}
	 */
	public static boolean checkPartialUpdateListPackWellFormed(UpdateListPack pack){
		boolean check=false;
		if(pack!=null && pack.getListName()!=null && pack.getOwner()!=null){
			check=true;
		}
		return check;
	}

}
