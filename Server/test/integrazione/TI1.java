package integrazione;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.mytalk.server.EnvironmentSetter;
import com.mytalk.server.data.model.Blacklist;
import com.mytalk.server.data.model.Call;
import com.mytalk.server.data.model.ListName;
import com.mytalk.server.data.model.OnlineUser;
import com.mytalk.server.data.model.User;
import com.mytalk.server.data.model.UserList;
import com.mytalk.server.data.storage.dao.BlacklistDAO;
import com.mytalk.server.data.storage.dao.CallDAO;
import com.mytalk.server.data.storage.dao.GenericDAO;
import com.mytalk.server.data.storage.dao.ListNameDAO;
import com.mytalk.server.data.storage.dao.OnlineUserDAO;
import com.mytalk.server.data.storage.dao.UserDAO;
import com.mytalk.server.data.storage.dao.UserListDAO;

public class TI1 {

	EnvironmentSetter envSetter=new EnvironmentSetter();
	@Before
	public void setTestEnvironment(){
		envSetter.cleanDB();
		envSetter.initDB();
	}
	
	@Test
	public void userTest() {
		User u=new User("nuovo","nuovo","nuovoutente@mytalk.com","nuovo","utente","emailhash");
		UserDAO ud=new UserDAO();
		ud.save(u);
		User u1=ud.get(u.getUsername());
		assertEquals("Oggetto User non salvato correttamente nel database",u,u1);
		u.setName("modificato");
		ud.update(u);
		User u2=ud.get(u.getUsername());
		assertEquals("Aggiornamento User non effettuato correttamente nel database",u2.getName(),"modificato");
		ud.delete(u2);
		User u3=ud.get(u.getUsername());
		assertNull("Record User non cancellato correttamente dal database",u3);
	}
	
	@Test
	public void blacklistTest(){
		Blacklist b=new Blacklist("user0","user3");
		BlacklistDAO bd=new BlacklistDAO();
		bd.save(b);
		Blacklist b1=bd.get(b.getOwner(),b.getUsername());
		assertEquals("Oggetto Blacklist non salvato correttamente nel database",b,b1);
		bd.delete(b1);
		Blacklist b3=bd.get(b.getOwner(),b.getUsername());
		assertNull("Record User non cancellato correttamente dal database",b3);
	}
	
	@Test
	public void userlistTest(){
		UserList l=new UserList(2,"user9");
		UserListDAO uld=new UserListDAO();
		uld.save(l);
		UserList ul=uld.get(l.getIdList(), l.getUsername());
		assertEquals("Oggetto UserList non salvato correttamente nel database",l,ul);
		uld.delete(l);
		UserList ul2=uld.get(l.getIdList(), l.getUsername());
		assertNull("Record UserList non cancellato correttamente dal database",ul2);
	}

	@Test
	public void listnameTest(){
		ListName l=new ListName("friends","user9");
		ListNameDAO lnd=new ListNameDAO();
		lnd.save(l);
		ListName l1=lnd.getByNameOwner(l);
		if(!l1.getName().equals(l.getName())||!l1.getOwner().equals(l.getOwner()))
			fail("Oggetto ListName non salvato correttamente nel database");
		lnd.delete(l1);
		ListName l2=lnd.getByNameOwner(l);
		assertNull("Record ListName non cancellato correttamente dal database",l2);
	}
	
	@Test
	public void onlineuserTest(){
		OnlineUser o=new OnlineUser("user5","111.111.111.11");
		OnlineUserDAO od=new OnlineUserDAO();
		od.save(o);
		OnlineUser o1=od.get(o.getIp());
		assertEquals("Oggetto OnlineUser non salvato correttamente nel database",o,o1);
		od.delete(o1);
		OnlineUser o2=od.get(o.getIp());
		assertNull("Record OnlineUser non cancellato correttamente dal database",o2);
	}
	
	@Test
	public void callTest(){
		Call c=new Call("user9","user4",25,"25-12-1990 14:14:14",22000,22000);
		c.setId(15);
		CallDAO cd=new CallDAO();
		cd.save(c);
		Call c1=cd.get(c.getId());
		assertEquals("Oggetto Call non salvato correttamente nel database",c,c1);
		c.setDuration(90);
		cd.update(c);
		Call c2=cd.get(c.getId());
		assertEquals("Aggiornamento Call non effettuato correttamente nel database",c2.getDuration(),90);
		cd.delete(c2);
		Call c3=cd.get(c.getId());
		assertNull("Record Call non cancellato correttamente dal database",c3);
	}
}
