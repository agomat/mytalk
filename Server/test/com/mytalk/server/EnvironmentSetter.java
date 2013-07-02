/**
 * Filename: EnvironmentSetter.java
 * Package: com.mytalk.server
 * Author: Nicolo' Mazzucato
 * Date: 2013-05-06
 *
 * Diary:
 * Version | Date       | Developer | Changes
 * --------+------------+-----------+------------------
 * 0.1	   | 2013-05-06 |    NM     | [+] Inserimento classe e metodi   
 *
 * This software is distributed under GNU/GPL 2.0.
 *
 * Software licensed to:
 * - Zucchetti SRL
 */

package com.mytalk.server;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.mytalk.server.data.storage.HibernateUtil;


public class EnvironmentSetter {

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
		URL url = EnvironmentSetter.class.getResource(path);
		File file = new File(url.getPath());
		FileInputStream stream = new FileInputStream(file);
		try {
		    FileChannel fc = stream.getChannel();
		    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
		    return Charset.defaultCharset().decode(bb).toString();
		}
		finally{
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
			
			
		}catch(IOException exc){fail("File per la inizializzazione del DB non trovato");}
		
	}
}
