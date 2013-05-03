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
		    /* Instead of using default, pass in a decoder. */
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
	public void checkAutentication() {
		User firstTestUser=new User("user0","user0",null,null,null);
		User secondTestUser=new User("user1","password",null,null,null);
		Class[] args1 = new Class[1];
		args1[0] = User.class;
		try{
			Method method = DataAccess.class.getDeclaredMethod("authenticateClient",args1);
			method.setAccessible(true);
			
			//testo autenticazione con password corretta
			boolean result1=(boolean)method.invoke(dataAccess, firstTestUser);
			assertTrue("autenticazione non riuscita, le password coincidono",result1);
			
			//testo autenticazione con password sbagliata
			boolean result2=(boolean)method.invoke(dataAccess, secondTestUser);
			assertTrue("autenticazione riuscita, il metodo funziona anche con password errata",!result2);
			
		}catch(NoSuchMethodException exc){fail("NoSuchMethodException");}
		catch(InvocationTargetException exc){fail("InvocationTargetException");}
		catch(IllegalAccessException exc){fail("IllegalAccessException");}
	}

}
