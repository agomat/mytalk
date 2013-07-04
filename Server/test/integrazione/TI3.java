package integrazione;

import static org.junit.Assert.*;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.Test;

import com.mytalk.server.communication.Dispatcher;
import com.mytalk.server.communication.Receiver;
import com.mytalk.server.communication.Sender;
import com.mytalk.server.data.storage.HibernateUtil;

public class TI3 {

	private Boolean ricevuto=false;
	
	class TestClient extends WebSocketClient{
		public TestClient(URI serverURI){
			super(serverURI);
		}

		@Override
		public void onClose(int arg0, String arg1, boolean arg2) {
			//NON UTILE PER SCOPI DI TESTING
		}

		@Override
		public void onError(Exception arg0) {
			//NON UTILE PER SCOPI DI TESTING
		}

		@Override
		public void onMessage(String risp) {
			if(risp.equals(new String("{\"req\":\"CorruptedPack\"}")))
				ricevuto=true;
		}

		@Override
		public void onOpen(ServerHandshake arg0) {
			//NON UTILE PER SCOPI DI TESTING
		}
		
		public void sendMsg(){
			//invio di un messaggio non valido, il server tenta di processarlo e risponde comunicando il CorruptedPack
			this.send("{'auth':null,'req':'UserCall','info':null}");
		}
	}
	
	@Test
	public void test() {
		new HibernateUtil();
		Receiver receiver=new Receiver(new InetSocketAddress(8887));
		Thread receiverThread= new Thread(receiver);
		Dispatcher dispatcher=new Dispatcher();
		Thread dispatcherThread=new Thread(dispatcher);
		Sender sender=new Sender();
		Thread senderThread=new Thread(sender);
		sender.registerReceiver(receiver);
		senderThread.start();
		receiverThread.start();
		dispatcherThread.start();
		try {
			TestClient tc=new TestClient(new URI("ws://localhost:8887"));
			tc.connect();
			Thread t=new Thread(){
				public void run(){
					try {
						sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			t.start();
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			tc.sendMsg();
			Thread p=new Thread(){
				public void run(){
					try {
						sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			p.start();
			try {
				p.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			assertTrue("Messaggio di risposta non ricevuto entro 10 secondi, comunicazione con il server non funzionante",ricevuto);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

}
