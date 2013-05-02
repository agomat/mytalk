package com.mytalk.server.data.storage;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import java.lang.reflect.*;

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
	
	public void initDB(){
		BlacklistDAO blacklistDAO = new BlacklistDAO();
		CallDAO callDAO = new CallDAO();
		ListNameDAO listNameDAO = new ListNameDAO();
		OnlineUserDAO onlineUserDAO = new OnlineUserDAO();		
		UserDAO userDAO = new UserDAO();
		UserListDAO userListDAO = new UserListDAO();
		
		for(int i=0; i<numberUser; i++){
			User u = new User("user"+i, "user"+i, "user"+i+"@mytalk.com", "user"+i, "user"+i);
			userDAO.save(u);
		}
		
		for(int i=0; i<numberUser; i++){
			Blacklist b = new Blacklist("user"+i, "user"+(numberUser-1-i));
			blacklistDAO.save(b);
		}
			
		
	}
	
	@Before
	public void setTestEnvironment(){
		cleanDB();
		initDB();
	}
	
	@Test
	public void checkAutentication() {
		UserDAO ud=new UserDAO();
		User u1=ud.get("user0");
		Class[] args1 = new Class[1];
		args1[0] = User.class;
		User u2=ud.get("user1");
		u2.setPassword("password");
		try{
			Method method = DataAccess.class.getDeclaredMethod("authenticateClient",args1);
			method.setAccessible(true);
			boolean result1=(boolean)method.invoke(dataAccess, u1);
			assertTrue("autenticazione non riuscita, le password non coincidono",result1);
			boolean result2=(boolean)method.invoke(dataAccess, u2);
			assertTrue("autenticazione non riuscita, le password non coincidono",result2);
		}catch(NoSuchMethodException exc){fail("NoSuchMethodException");}
		catch(InvocationTargetException exc){fail("InvocationTargetException");}
		catch(IllegalAccessException exc){fail("IllegalAccessException");}
	}

}
