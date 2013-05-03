package com.mytalk.server.data.storage;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.List;

import com.mytalk.server.data.persistence.HibernateUtil;
import org.hibernate.*;

import com.mytalk.server.data.storage.dao.*;
import com.mytalk.server.data.model.*;
import com.mytalk.server.exceptions.*;

public class DataAccessTest {
	
	int numberUser=10;
	int numberList=5;
	IDataAccess dataAccess= new DataAccess();
		
	public void executeQuery(String q){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		Query query = session.createSQLQuery(q);
		query.executeUpdate();
		t.commit();
		session.close();
	}
	
	
	public void cleanDB(){		
		executeQuery("DELETE FROM OnlineUsers");
		executeQuery("DELETE FROM Calls");
		executeQuery("DELETE FROM UserLists");
		executeQuery("DELETE FROM ListNames");
		executeQuery("DELETE FROM Blacklists");
		executeQuery("DELETE FROM Users");		
	}
	
	private static String readFile(String path) throws IOException {
		URL url = DataAccessTest.class.getResource(path);
		File file = new File(url.getPath());
		FileInputStream stream = new FileInputStream(file);
		try {
		    FileChannel fc = stream.getChannel();
		    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
		    return Charset.defaultCharset().decode(bb).toString();
		  }
		  finally {
		    stream.close();
		  }
		}
	
	public void initDB(){
		try{
			//users
			String query=readFile("popolamentoTestUsers.sql");
			executeQuery(query);
			//blacklists
			query=readFile("popolamentoTestBlacklists.sql");
			executeQuery(query);
			//listnames
			query=readFile("popolamentoTestListNames.sql");
			executeQuery(query);
			//onlineusers
			query=readFile("popolamentoTestOnlineUsers.sql");
			executeQuery(query);
			//userlists
			query=readFile("popolamentoTestUserLists.sql");
			executeQuery(query);
			//calls
			query=readFile("popolamentoTestCalls.sql");
			executeQuery(query);
			
			
		}catch(IOException exc){fail("file non trovato");}
		
	}
	
	@Before
	public void setTestEnvironment(){
		cleanDB();
		initDB();
	}
	
	@Test
	public void checkAutentication() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		User firstTestUser=new User("user0","user0",null,null,null);
		User secondTestUser=new User("user1","password",null,null,null);
		User thirdTestUser=new User("random","password",null,null,null);
		Class[] args1 = new Class[1];
		args1[0] = User.class;
		Method method = DataAccess.class.getDeclaredMethod("authenticateClient",args1);
		method.setAccessible(true);
			
		//test autenticazione con password corretta
		boolean result1=(boolean)method.invoke(dataAccess, firstTestUser);
		assertTrue("autenticazione non riuscita con password uguali",result1);
		
		//test autenticazione con password sbagliata
		boolean result2=(boolean)method.invoke(dataAccess, secondTestUser);
		assertTrue("autenticazione riuscita, il metodo funziona anche con password errata",!result2);
		
		//test autenticazione di un utente non esistente
		boolean result3=(boolean)method.invoke(dataAccess, thirdTestUser);
		assertTrue("autenticazione di un utente inesistente riuscita",!result3);
			
	}
	
	@Test
	public void checkCheckUserByIp() {
		String existingIp="123.123.123.1";
		String notExistingIp="1.1.1.1";
		//test con ip di un utente presente nella tabella OnlineUsers
		boolean result1=dataAccess.checkUserByIp(existingIp);
		assertTrue("non è stato trovato l'utente con ip "+existingIp,result1);
		
		//test con ip di un utente non presente nella tabella OnlineUsers
		boolean result2=dataAccess.checkUserByIp(notExistingIp);
		assertTrue("è stato trovato un utente con ip "+notExistingIp+" anche se non è presente",!result2);
		
	}

	@Test
	public void checkCheckUserByName() throws AuthenticationFail{
		String existingUsername="user2";
		String notExistingUsername="random";
		User toAuth=new User("user0","user0",null,null,null);
		
		//test con username presente nella tabella OnlineUsers
		boolean result1=dataAccess.checkUserByName(existingUsername, toAuth);
		assertTrue("non è stato trovato l'utente con username "+existingUsername,result1);
		
		//test con username non presente nella tabella OnlineUsers
		boolean result2=dataAccess.checkUserByName(notExistingUsername, toAuth);
		assertTrue("è stato trovato un utente con username "+notExistingUsername+" anche se non presente",!result2);
		
	}
	
	@Test(expected = UsernameAlreadyExisting.class)
	public void checkCreateAccount() throws UsernameAlreadyExisting{
		User testUser1=new User("user10","user10","user10@mytalk.com","user10","user10");
		User testUser2=new User("user0","user0","user0@mytalk.com","user0","user0");
		
		//test creazione di un nuovo utente
		dataAccess.createAccount(testUser1);
//devo testare se l'ha creato?
		
		//test creazione di un utente avente un username già usato
		dataAccess.createAccount(testUser2);

	}
	
	@Test(expected = UsernameNotExisting.class)
	public void checkLogin()throws AuthenticationFail,UsernameNotExisting{
		User toAuth=new User("user0","user0",null,null,null);
//assume che il client era prima loggato come anonimo per poi fare l'update
		OnlineUser onUserTest1=new OnlineUser("111.111.111.1","user9");
		OnlineUser onUserTest2=new OnlineUser("111.111.111.0","user0");
		OnlineUser onUserTest3=new OnlineUser("111.111.111.2","random");
		
		//testo il metodo con un OnlineUser avente username esistente nel db e non loggato
		dataAccess.login(onUserTest1, toAuth);
//devo testare se l'ha aggiornato?
		//testo il metodo con un OnlineUser avente username esistente nel db e già loggato
		dataAccess.login(onUserTest2, toAuth);
//devo testare se l'ha aggiornato?
		//testo il metodo con un OnlineUser avente username non esistente nel db
		dataAccess.login(onUserTest3, toAuth);
		
	}
	
	@Test
	public void checkUserLists()throws AuthenticationFail{
		User testUser1=new User("user0","user0",null,null,null);
		User testUser2=new User("user9","user9",null,null,null);
		
		//test con un User che ha liste
		List<ListName> list1=dataAccess.userLists(testUser1);
		int count1=list1.size();
		assertEquals("è stato trovato un numero di liste diverso da quelle possedute dall'utente",1,count1);
		
		//test con un User che non ha liste
		List<ListName> list2=dataAccess.userLists(testUser2);
		int count2=list2.size();
		assertEquals("sono state trovate liste per un utente che non ne ha",0,count2);
	}
	
	@Test
	public void checkLogout(){
		
	}
}
