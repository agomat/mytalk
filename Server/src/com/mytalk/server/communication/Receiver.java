/**
* Filename: Receiver.java
* Package: com.mytalk.server.communication
* Author: Nicolò Mazzucato
* Date: 2013-05-14
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.3     | 2013-06-18 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.2     | 2013-05-16 |	NT	   | [+] Riempimento classi
* 0.1	  |	2013-05-14 |    NM     | [+] Inserimento classe, oggetti e costruttore
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Thread che apre e chiude le connessioni WebSocket con i client e riceve i pacchetti delle
* richieste
*/


package com.mytalk.server.communication;

import java.net.InetSocketAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.mytalk.server.communication.buffer.BufferIncoming;
import com.mytalk.server.communication.buffer.Message;
import com.mytalk.server.exceptions.IpNotFound;

public class Receiver extends WebSocketServer{
	/**
	 * Costruttore di un oggetto di tipo Receiver che invoca il costruttore della superclasse
	 * passando come parametro un oggetto di tipo InetSocketAddress
	 * 
	 * @method +Receiver
	 * @param {InetSocketAddress} address e' un oggetto che identifica l'indirizzo del socket 
	 * necessario alla creazione dell'oggetto Receiver
	 */
	public Receiver(InetSocketAddress address) {
		super(address);
	}
	/**
	 * Riferimento al singleton di tipo BufferIncoming inerente alle richieste in entrata nel server;
	 * serve alla classe Receiver per eseguire operazioni di push dei messaggi ricevuti
	 * 
	 * @property -bufferIn
	 * @type {BufferIncoming}
	 */
	
	private BufferIncoming bufferIn=BufferIncoming.getInstance();
	
	/**
	 * Override del metodo onOpen della classe estesa, viene chiamato all'apertura di una connessione
	 *  WebSocket da parte di un client; estrae dall'oggetto di tipo WebSocket l'indirizzo ip del 
	 *  client e crea la richiesta di autenticazione con solo ip per poi aggiungerla nel buffer 
	 *  delle richieste in entrata
	 *  
	 *  @method +onOpen
	 *  @param {WebSocket} conn e' l'oggetto che identifica la connessione websocket
	 *  @param {ClientHandshake} hs e' l'oggetto 
	 *  @return {void}
	 */
	@Override
	public void onOpen(WebSocket conn, ClientHandshake hs) {
		String wsIp=conn.getRemoteSocketAddress().toString();
		wsIp=wsIp.substring(1);
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		System.out.println("["+dateFormat.format(cal.getTime())+"] Client connected with IP: "+wsIp);
		String loginAnonymous="{'auth':{'username':null,'password':null, ip:'"+wsIp+"'},'req':'LoginAsAnonymous','info':null}";
		Message openMsg=new Message(wsIp,loginAnonymous);
		bufferIn.push(openMsg);
	}
	
	/**
	 * Override del metodo onClose della classe estesa, viene chiamato alla chiusura di una
	 * connessione WebSocket da parte di un client; estrae dall'oggetto di tipo WebSocket
	 * l'indirizzo ip del client e crea la richiesta di chiusura della sessione utente (logout) per
	 * poi aggiungerla nel buffer delle richieste in entrata
	 * 
	 * @method +onClose
	 * @param {WebSocket} conn e' l'oggetto che identifica la connessione websocket
	 * @param {int} code 
	 * @param {String} reason
	 * @param {boolean} remote
	 * @return {void}
	 */
	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		String wsIp=conn.getRemoteSocketAddress().toString();
		wsIp=wsIp.substring(1);
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		System.out.println("["+dateFormat.format(cal.getTime())+"] Client with IP "+wsIp+" disconnected");
		String logout="{'auth':{'username':null,'password':null, ip:'"+wsIp+"'},'req':'Logout','info':null}";
		Message closeMsg=new Message(wsIp,logout);
		bufferIn.push(closeMsg);
	}
	
	/**
	 * Override del metodo onError della classe estesa, viene chiamato quando capita un errore 
	 * di connessione; stampa l'errore nella console del server
	 * 
	 * @method +OnError
	 * @param {WebSocket} conn e' l'oggetto che identifica la connessione websocket
	 * @param {Exception} ex e' l'oggetto che identifica l'eccezione sollevata
	 * @return {void}
	 */
	@Override
	public void onError(WebSocket conn, Exception ex) {
		ex.printStackTrace();
	}

	/**
	 * Override del metodo onMessage della classe estesa, viene chiamato quando il server riceve
	 * un messaggio da parte di un client; estrae l'indirizzo ip dall'oggetto di tipo WebSocket 
	 * e lo inserisce assieme al messaggio in un oggetto di tipo Message, per poi eseguire il
	 * metodo push(Message msg) nel buffer delle richieste in entrata
	 * 
	 * @method +onMessage
	 * @param {WebSocket} conn e' l'oggetto che identifica la connessione websocket
	 * @param {String} msg e' l'oggetto che identifica il messaggio ricevuto del client
	 * @return {void} 
	 */
	@Override
	public void onMessage(WebSocket conn, String msg) {
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		System.out.println("["+dateFormat.format(cal.getTime())+"] New message Received");
		String wsIp=conn.getRemoteSocketAddress().toString();
		wsIp=wsIp.substring(1);
		Message newMsg=new Message(wsIp,msg);
		bufferIn.push(newMsg);
	}

	/**
	 * Metodo di utilità che cerca nella collezione di oggetti WebSocket, che rappresentano 
	 * tutte le connessioni aperte, un oggetto WebSocket avente l'ip passato; se non trova 
	 * nessun oggetto con questo requisito solleva un'eccezione di tipo IpNotFound
	 * 
	 * @method +searchConnection
	 * @param {String} ip e' l'oggetto che identifica un indirizzo ip da cercare
	 * @return {WebSocket}
	 * @exception {IpNotFound} sollevata se la ricerca ha esito negativo
	 */
	public WebSocket searchConnection(String ip) throws IpNotFound{
		Iterator<WebSocket> iterator = connections().iterator();
		WebSocket wsFound=null;
		while (iterator.hasNext() && wsFound==null) {
			WebSocket ws=iterator.next();
			String wsIp=ws.getRemoteSocketAddress().toString();
			if(wsIp.equals(ip)){
				wsFound=ws;
			}
		}
		if(wsFound!=null){
			return wsFound;
		}else{
			throw new IpNotFound();
		}	
	}

}
