package integrazione;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mytalk.server.communication.buffer.Message;
import com.mytalk.server.data.model.OnlineUser;
import com.mytalk.server.data.storage.dao.OnlineUserDAO;
import com.mytalk.server.logic.processing.Processor;

public class TI2 {

	@Test
	public void test() {
		String testIP="123.123.234.3";
		Message m=new Message(testIP,"{'auth':{'username':null,'password':null, ip:'"+testIP+"'},'req':'LoginAsAnonymous','info':null}");
		Processor p=new Processor();
		p.processRequest(m);
		OnlineUserDAO od=new OnlineUserDAO();
		OnlineUser o=od.get(testIP);
		assertNotNull("La richiesta non e` stata elaborata correttamente e il database non ha memorizzato le informazioni",o);
	}

}
