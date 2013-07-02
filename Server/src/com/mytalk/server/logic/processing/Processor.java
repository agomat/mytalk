/**
* Filename: Processor.java
* Package: com.mytalk.server.logic.shared
* Author: Nicolo' Toso
* Date: 2013-04-29
*
* Diary:
*
* Version |
Date
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.4     | 2013-07-02 |    MF     | [+] Aggiunta nuova richiesta nella hashmap UpdateAccount 
* 0.3     | 2013-06-22 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.2	  | 2013-05-13 |    NT	   | [#] Modifica dei nomi di alcune variabili in tutti i metodi al
* 										 fine di renderli più espressivi
* 0.1	  |	2013-04-29 |    NT     | [+] Creazione classe, costruttore e metodi   
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che a partire da una stringa JSON ottenuta da un oggetto Message ricevuto dalla
* componente CSCOM1, la interpreta utilizzando la classe Convert, ottenendo un oggetto ARI
* dal quale ricavare il valore del campo req corrispondente al tipo di richiesta sotto forma
* di stringa. Tale stringa assieme ad una mappa di stringhe propria della classe permette,
* attraverso l'uso di riflessione, di determinare la classe sulla quale invocare il metodo 
* manage(ARI) che processa i dati ricevuti. Una volta ottenuti i risultati del metodo manage,
* impacchetta tali risultati in un oggetto Message da restituire come risposta, alla componente
* CSCOM1
*/

package com.mytalk.server.logic.processing;

import com.google.gson.JsonSyntaxException;
import com.mytalk.server.communication.buffer.Message;
import com.mytalk.server.logic.processing.request_processor.state_update_information.StateUpdate;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.*;

public class Processor implements IProcessor{
	/**
	 * Identifica la mappa di stringhe necessaria per il mapping della richiesta
	 * 
	 * @property -hashmap
	 * @type {Map<String, String>}
	 */
	private Map<String, String> hashmap;
	
	/**
	 * Identifica un oggetto di tipo CSLOG1.Convert utilizzato per fare la traduzione 
	 * da stringa JSON a oggetto Java
	 * 
	 * @property -convert
	 * @type {Convert}
	 */
	private Convert convert=new Convert();
	
	/**
	 * Identifica un oggetto di tipo CSLOG1.StateUpdate utilizzato per creare il pacchetto 
	 * necessario per segnalare un cambiamento di stato di un user autenticato
	 * 
	 * @property -stateUpdate
	 * @type {StateUpdate}
	 */
	private StateUpdate stateUpdate=new StateUpdate();
	
	/**
	 * Costruttore della classe il quale inizializza la mappa mettendo come 
	 * indice il nome delle richiesta e come valore il nome della classe con il percorso intero,
	 * questo per ogni richiesta possibile
	 * 
	 * @method +Processor
	 */
	public Processor(){
		hashmap = new HashMap<String, String>();
		
		hashmap.put("AcceptCall","com.mytalk.server.logic.processing.request_processor.connection_information.AcceptCall");
		hashmap.put("RefuseCall","com.mytalk.server.logic.processing.request_processor.connection_information.RefuseCall");
		hashmap.put("UserCall","com.mytalk.server.logic.processing.request_processor.connection_information.UserCall");
		
		hashmap.put("CreateAccount","com.mytalk.server.logic.processing.request_processor.world_information.CreateAccount");
		hashmap.put("DeleteAccount","com.mytalk.server.logic.processing.request_processor.empty_information.DeleteAccount");
		hashmap.put("Login","com.mytalk.server.logic.processing.request_processor.world_information.Login");
		hashmap.put("UpdateAccount","com.mytalk.server.logic.processing.request_processor.world_information.UpdateAccount");
		hashmap.put("LoginAsAnonymous","com.mytalk.server.logic.processing.request_processor.world_information.LoginAsAnonymous");
		hashmap.put("Logout","com.mytalk.server.logic.processing.request_processor.empty_information.Logout");
		hashmap.put("LogoutToAnonymous","com.mytalk.server.logic.processing.request_processor.empty_information.LogoutToAnonymous");
		
		hashmap.put("BlackListAdd","com.mytalk.server.logic.processing.request_processor.list_information.BlackListAdd");
		hashmap.put("BlackListRemove","com.mytalk.server.logic.processing.request_processor.list_information.BlackListRemove");
		hashmap.put("ListCreate","com.mytalk.server.logic.processing.request_processor.update_list_information.ListCreate");
		hashmap.put("ListDelete","com.mytalk.server.logic.processing.request_processor.update_list_information.ListDelete");
		hashmap.put("ListUserAdd","com.mytalk.server.logic.processing.request_processor.list_information.ListUserAdd");
		hashmap.put("ListUserRemove","com.mytalk.server.logic.processing.request_processor.list_information.ListUserRemove");
		hashmap.put("UpdateListName","com.mytalk.server.logic.processing.request_processor.update_list_information.UpdateListName");
		
		hashmap.put("AddCall","com.mytalk.server.logic.processing.request_processor.give_call_information.AddCall");
		hashmap.put("GetCalls","com.mytalk.server.logic.processing.request_processor.give_call_information.GetCalls");

	}
		
	/**
	 * Metodo che riceve in input un oggetto Message contenente l'indirizzo IP del client 
	 * che ha fatto la richiesta e una stringa JSON, e restituisce in output una lista di oggetti 
	 * Message contenenti la risposta alla richiesta fatta. La stringa presente nel oggetto 
	 * Message in input viene convertita, utilizzando l'oggetto convert, in un oggetto Java di 
	 * tipo ARI. Ottenuto l'oggetto ARI il metodo procede verificando la presenza dei dati di 
	 * autenticazione in tale oggetto, ai quali imposta il valore relativo all'indirizzo ip uguale 
	 * all'indirizzo ricevuto nell'oggetto Message; nel caso in cui l'autenticazione sia assente 
	 * viene creato un oggetto Authentication con il solo indirizzo ip impostato. Successivamente 
	 * si procede con l'identificazione del tipo di richiesta utilizzando la stringa relativa alla 
	 * richiesta, presente nell'oggetto ARI, come indice nella mappa; ottenuta la classe 
	 * corrispondente si utilizza la tecnica della riflessione propria di Java per invocare su 
	 * tale classe il metodo manage, il quale processerà la richiesta e i relativi dati associati
	 *  alla parte informazione del pacchetto ARI e restituirà la risposta a tale richiesta sotto 
	 *  forma di oggetto ARI. Una volta ottenuto l'oggetto di risposta, su di esso viene fatta 
	 *  una verifica se il campo ip dei dati di autenticazione è impostato oppure no, in modo 
	 *  tale da determinare l'indirizzo ip di destinazione del pacchetto, nel caso in cui abbia 
	 *  valore nullo allora l'ip di destinazione sarà quello del client che ha fatto la richiesta. 
	 *  In seguito si esegue un primo controllo sul campo richiesta dell'oggetto di risposta serve 
	 *  a determinare se la risposta non necessita di essere inviata a nessun client, ossia se si 
	 *  tratta della risposta ad un logout o a un rifiuto di chiamata non riuscito poiché al client 
	 *  tale informazione non interessa; nel caso in cui non si tratti di queste risposte allora 
	 *  viene creato e aggiunto alla lista da restituire un oggetto Message contenente la 
	 *  conversione in stringa JSON del pacchetto ARI di risposta e l'indirizzo IP al quale mandare 
	 *  tale risposta. Il secondo controllo si esegue sul campo richiesta dell'oggetto ARI di 
	 *  risposta, al fine di determinare se il tipo di risposta indica un operazione di cambio 
	 *  stato completata con successo, in tale caso è necessario notificare il cambio di stato a 
	 *  tutti gli altri utenti utilizzando il campo dati stateUpdate, il quale restituisce 
	 *  un pacchetto da inviare contenente i dati dell'user che ha cambiato stato. Fatto ciò viene 
	 *  creato ed aggiunto un oggetto di tipo Message contenente la conversione JSON del pacchetto 
	 *  restituito da stateUpdate e come indirizzo ip la stringa broadcast la quale sarà 
	 *  interpretata a dovere nella componente CSCOM1
	 * 
	 * @method +processRequest
	 * @param {Message} message e' l'oggetto che viene processato, il quale contiene le informazioni 
	 * relative alla richiesta da processare
	 * @return {List<Message>}
	 * @exception {JsonSyntaxException} viene sollevata se il JSON contenuto nell'oggetto message
	 * non e' ben formato sintatticamente
	 */
	public List<Message> processRequest(Message message) throws JsonSyntaxException {
		ARI packInfo=convert.convertJsonToJava(message.getJson());			
		if(packInfo.getAuth()!=null){
			packInfo.getAuth().setIp(message.getIp());
		}
		else{
			packInfo.setAuth(new Authentication(null,null,message.getIp()));
		}
		String request=packInfo.getReq();
		ARI esito=null;
		String ipToSend=null;
		List<Message> responseList=new ArrayList<Message>();
		try{
			String r= hashmap.get(request);
			Class<?> cl=Class.forName(r);
			Object obj=cl.newInstance();
			Method m= cl.getDeclaredMethod("manage", ARI.class);
			esito=(ARI)m.invoke(obj, packInfo);	
		}catch(ClassNotFoundException cnfe){
			// Auto-generated catch block
			cnfe.printStackTrace();
		} catch (SecurityException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!packInfo.getReq().equals("Logout") && !packInfo.getReq().equals("LogoutToAnonymous") && !esito.getReq().equals("UnsuccessfulRefuseCall")){
			if(esito.getAuth()!=null){
				if(esito.getAuth().getUser()!=null){
					packInfo.getAuth().setUser(esito.getAuth().getUser());
				}
				ipToSend=esito.getAuth().getIp();
			}
			else{
				ipToSend=message.getIp();
			}
			esito.setAuth(null);
			String json=convert.convertJavaToJson(esito);
			responseList.add(new Message(ipToSend,json));
		}
		
		if(esito.getReq().equals("SuccessfulLogin") || esito.getReq().equals("SuccessfulLogoutToAnonymous") || esito.getReq().equals("SuccessfulLogout") || esito.getReq().equals("SuccessfulDeleteAccount")){
			if(packInfo.getAuth().getUser()!=null){
				ARI ari=stateUpdate.manage(packInfo);
				String jsonAri=convert.convertJavaToJson(ari);
				responseList.add(new Message("broadcast",jsonAri));
			}
		}
		return responseList;
	}
}
