package com.mytalk.server.data.storage;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import com.mytalk.server.data.persistence.HibernateUtil;
import java.sql.Timestamp;
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
		
	}

}
