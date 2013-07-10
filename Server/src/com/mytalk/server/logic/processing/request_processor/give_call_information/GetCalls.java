/**
* Filename: GetCalls.java
* Package: com.mytalk.server.logic.processing.request_processor.give_call_information
* Author: Michael Ferronato
* Date: 2013-05-01 
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.4     | 2013-07-04 |    MF     | [#] Modifica alla creazione di un oggeto Call in seguito a modifica
* 									 della struttura della classe Call
* 0.3     | 2013-06-22 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.2	  |	2013-05-22 |    MF     | [+] Modifica dei nomi del metodo manage al fine di renderli
* 									     piu' espressivi e di facile comprensione
* 0.1	  |	2013-05-01 |    MF     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che si occupa di restituire la lista delle chiamate relative al client che ne fa richiesta
*/


package com.mytalk.server.logic.processing.request_processor.give_call_information;

import java.util.ArrayList;
import java.util.List;

import com.mytalk.server.exceptions.AuthenticationFailException;
import com.mytalk.server.exceptions.UserNotExistingException;
import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.GiveCallPack;
import com.mytalk.server.logic.shared.model_client.Call;
import com.mytalk.server.logic.shared.model_client.WrapperCall;

public class GetCalls extends GenericRequest {
	
	/**
	 * Costruttore della classe con corpo vuoto poiche' non vi sono campi dati da inizializzare
	 * 
	 * @method +GetCalls
	 */
	public GetCalls(){}

	/**
	 * Una volta ricevuto il pacchetto ARI e controllato che sia ben formato, viene creato un 
	 * oggetto di tipo CSDAT1.model.User, contenente le informazioni dell'Authentication del 
	 * campo dati ari. Tale oggetto viene utilizzato per ricavare la lista delle chiamate di 
	 * tale utente, rappresentandole in modo tale da distinguere le chiamate effettuate da quelle 
	 * ricevute. Inoltre vengono incrementati i contatori della durata e dei byte inviati e ricevuti 
	 * al fine di fornire le statistiche. Infine viene restituito un nuovo pacchetto ARI che 
	 * contiene il messaggio "GiveCalls" e l'informazione desiderata. Se i controlli non sono 
	 * stati superati, viene restituito un pacchetto con campo richiesta "AuthenticationFailGetCalls"
	 *  (autenticazione fallita) o "UsernameNotExistingGetCalls" (username non esistente). In caso di 
	 *  pacchetto non ben formato, viene restituito un "CorruptedPack"
	 *  
	 *  @method +manage
	 *  @param {ARI} ari e' l'oggetto che contiene le informazioni necessarie alla classe per
	 *  poter processare la specifica richiesta e restituire il risultato dell'elaborazione
	 *  @return {ARI}
	 */
	public ARI manage(ARI ari) {
		ARI response=null;
		Authentication auth=ari.getAuth();
		boolean checkAuth=checkAuthenticationWellFormed(auth);
		if(!checkAuth){
			response=new ARI(null,"CorruptedPack",null);
		}
		else{
			com.mytalk.server.data.model.User userAuth=new com.mytalk.server.data.model.User(auth.getUser(),auth.getPwd(),null,null,null,null);
			List<com.mytalk.server.data.model.Call> listOfCall=null;
			com.mytalk.server.data.model.Call callServer=null;
			List<Call> listCallClient=new ArrayList<Call>();
			boolean caller=false;
			Integer speaker=null;
			GiveCallPack giveCallPack=null;
			String infoResponse=null;
			WrapperCall wrapperCall=new WrapperCall(listCallClient);
			try{
				listOfCall=da.getCalls(userAuth);
				for(int i=0;i<listOfCall.size();i++){
					callServer=listOfCall.get(i); 
					if(callServer.getCaller()!= null && callServer.getCaller().equals(userAuth.getUsername())){ 
						caller=true;
						if(callServer.getReceiver()!=null){
							speaker=da.getIdFromUsername(callServer.getReceiver());
						}
						else{
							speaker=null;
						}
						listCallClient.add(new Call(callServer.getId(),speaker, caller, callServer.getStartdate(),callServer.getDuration() , callServer.getByteSent(),callServer.getByteReceived()));

						wrapperCall.increaseTotalByteSent(callServer.getByteSent());
						wrapperCall.increaseTotalByteReceived(callServer.getByteReceived());
						wrapperCall.increaseTotalDuration(callServer.getDuration());
					}
					else{
						caller=false;
						if(callServer.getCaller()!=null){
							speaker=da.getIdFromUsername(callServer.getCaller());
						}
						else{
							speaker=null;
						}
						
						listCallClient.add(new Call(callServer.getId(),speaker, caller, callServer.getStartdate(), callServer.getDuration(), callServer.getByteReceived(),callServer.getByteSent()));
						
						wrapperCall.increaseTotalByteSent(callServer.getByteReceived());
						wrapperCall.increaseTotalByteReceived(callServer.getByteSent());
						wrapperCall.increaseTotalDuration(callServer.getDuration());
					}			
				}
				wrapperCall.setList(listCallClient);
				giveCallPack=new GiveCallPack(wrapperCall);
				infoResponse=conv.convertJavaToJson(giveCallPack);
				response=new ARI(null,"GiveCalls",infoResponse);
			}catch(AuthenticationFailException af){
				response=new ARI(null,"AuthenticationFailGetCalls",null);
			} catch (UserNotExistingException e) {
				response=new ARI(null,"UsernameNotExistingGetCalls",null);
			}	
		}
		return response;
	}

}
