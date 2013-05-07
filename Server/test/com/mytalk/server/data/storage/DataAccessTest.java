/**
 * Filename: DataAccessTest.java
 * Package: com.mytalk.server.data.storage
 * Author: Nicolà Mazzucato
 * Date: 2013-04-29
 *
 * Diary:
 * Version | Date       | Developer | Changes
 * --------+------------+-----------+------------------
 * 0.1	   | 2013-04-29 |    NM     | [+] Inserimento classe e metodi   
 *
 * This software is distributed under GNU/GPL 2.0.
 *
 * Software licensed to:
 * - Zucchetti SRL
 */

package com.mytalk.server.data.storage;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import java.lang.reflect.*;
import java.util.List;

import com.mytalk.server.EnvironmentSetter;

import com.mytalk.server.data.model.*;
import com.mytalk.server.data.storage.dao.*;
import com.mytalk.server.exceptions.*;

public class DataAccessTest {
	
	EnvironmentSetter envSetter=new EnvironmentSetter();
	IDataAccess dataAccess= new DataAccess();
	
	@Before
	public void setTestEnvironment(){
		envSetter.cleanDB();
		envSetter.initDB();
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
	
	@Test
	public void checkCreateAccount(){
		User testUser=new User("user10","user10","user10@mytalk.com","user10","user10");
		
		//test creazione di un nuovo utente
		try{
			dataAccess.createAccount(testUser);
			UserDAO ud=new UserDAO();
			String testUsername=testUser.getUsername();
			User createdUser=ud.get(testUsername);
			if(createdUser==null){
				fail("L'utente non è stato creato");
			}
			if(!testUser.equals(createdUser)){
				fail("I campi dati degli utenti non sono stati creati correttamente");
			}
		}catch(UsernameAlreadyExisting exc){
			fail("è stato creato un utente con un username già esistente");
		}
	}
	
	@Test(expected = UsernameAlreadyExisting.class)
	public void checkCreateAccountFail() throws UsernameAlreadyExisting{
		User testUser=new User("user0","user0","user0@mytalk.com","user0","user0");
		//test creazione di un utente avente un username già usato
		dataAccess.createAccount(testUser);
	}
	
	@Test
	public void checkLoginNotAuthenticated()throws AuthenticationFail{
		User toAuth=new User("user4","user4",null,null,null);
		OnlineUser onUserTest=new OnlineUser("user4","111.111.111.1");
		
		//testo il metodo con un OnlineUser avente ip autenticato come anonimo e username non autenticato nel sistema
		try{
			dataAccess.login(onUserTest, toAuth);
		}catch(UsernameNotCorresponding exc){
			fail("non è stata trovata la corrispondenza tra username autenticato e username da autenticare (ecc:UsernameNotExisting)");
		}catch(IpNotLogged exc){
			fail("non è stato trovato l'utente come autenticato anonimo (ecc:UserNotLogged)");
		}catch(UserAlreadyLogged exc){
			fail("è stato trovato l'utente come già autenticato nel sistema (ecc:UserAlreadyLogged)");
		}catch(IpAlreadyLogged exc){
			fail("è stato trovato l'ip come già autenticato nel sistema (ecc:IpAlreadyLogged)");
		}
		OnlineUserDAO od=new OnlineUserDAO();
		OnlineUser userToCheck=od.get(onUserTest.getIp());
		if(onUserTest.equals(userToCheck)==false){
			fail("non è stato autenticato l'utente valido");
		}
	}
	
	@Test(expected=UserAlreadyLogged.class)
	public void checkLoginAuthenticatedUsername()throws AuthenticationFail,UserAlreadyLogged, IpNotLogged, IpAlreadyLogged, UsernameNotCorresponding{
		User toAuth=new User("user0","user0",null,null,null);
		OnlineUser onUserTest=new OnlineUser("user0","111.111.111.2");
		//testo il metodo con un OnlineUser avente username già autenticato con un altro ip
		dataAccess.login(onUserTest, toAuth);
	}
	
	@Test(expected=IpAlreadyLogged.class)
	public void checkLoginAuthenticatedIp()throws AuthenticationFail,IpAlreadyLogged, IpNotLogged, UserAlreadyLogged, UsernameNotCorresponding{
		User toAuth=new User("user5","user5",null,null,null);
		OnlineUser onUserTest=new OnlineUser("user5","123.123.123.1");
		//testo il metodo con un OnlineUser avente ip già autenticato con un username
		dataAccess.login(onUserTest, toAuth);
	}

	@Test(expected = IpNotLogged.class)
	public void checkLoginFailIp()throws AuthenticationFail,IpNotLogged, UserAlreadyLogged, IpAlreadyLogged, UsernameNotCorresponding{
		User toAuth=new User("user0","user0",null,null,null);
		OnlineUser onUserTest=new OnlineUser("user0","111.111.111.111");
		
		//testo il metodo con un OnlineUser avente un ip non autenticato come anonimo
		dataAccess.login(onUserTest, toAuth);
	}
	
	@Test(expected = UsernameNotCorresponding.class)
	public void checkLoginFailNotCorresponding()throws AuthenticationFail,UsernameNotCorresponding, IpNotLogged, UserAlreadyLogged, IpAlreadyLogged{
		User toAuth=new User("user0","user0",null,null,null);
		OnlineUser onUserTest=new OnlineUser("random","111.111.111.2");
		
		//testo il metodo con un OnlineUser avente username da autenticare non corrispondente al campo dell'oggetto da autenticare
		dataAccess.login(onUserTest, toAuth);
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
		OnlineUser testUser=new OnlineUser(null,"123.123.123.3");
		
		//test con un utente autenticato nel sistema
		try{
			dataAccess.logout(testUser);
		}catch(LogoutException exc){
			fail("non è stato trovato l'utente da de-autenticare come autenticato");
		}
	}
	
	@Test(expected=LogoutException.class)
	public void checkLogoutFail()throws LogoutException{
		OnlineUser testUser=new OnlineUser(null,"123.123.123.123");
		
		//test con un utente non autenticato nel sistema
		dataAccess.logout(testUser);
	}
	
	 @Test
	 public void checkGetListUsers()throws AuthenticationFail{
		 User toAuth=new User("user1","user1",null,null,null);
		 ListName list=new ListName();
		 list.setId(2);
		 
		 //test con una lista di proprietà dell'utente autenticato
		 try{
			 List<User> listResult=dataAccess.getListUsers(list, toAuth);
			 if(listResult.size()!=4){
				 fail("la lista ritornata dal metodo è sbagliata");
			 }
		 }catch(ListNotCorresponding exc){
			 fail("la lista non risulta corrispondente all'utente anche se lo è");
		 }
	 }
	 
	 @Test(expected=ListNotCorresponding.class)
	 public void checkGetListUsersFail()throws AuthenticationFail, ListNotCorresponding{
		 User toAuth=new User("user1","user1",null,null,null);
		 ListName list=new ListName();
		 list.setId(3);
		 
		 //test con una lista di proprietà di un altro utente
		 List<User> listResult=dataAccess.getListUsers(list, toAuth);
	 }
	
	@Test
	public void checkGetOnlineUsers()throws AuthenticationFail{
		User toAuth=new User("user1","user1",null,null,null);
		List<User> onlines=dataAccess.getOnlineUsers(toAuth);
		if(onlines.size()!=4){
			fail("il numero di utenti autenticati non corrisponde quello effettivo");
		}
	}
	
	@Test
	public void checkGetOfflineUsers()throws AuthenticationFail{
		User toAuth=new User("user1","user1",null,null,null);
		List<User> offlines=dataAccess.getOfflineUsers(toAuth);
		if(offlines.size()!=6){
			fail("il numero di utenti non autenticati non corrisponde quello effettivo");
		}
	}
	
	@Test
	public void checkListCreate()throws AuthenticationFail{
		User toAuth=new User("user1","user1",null,null,null);
		ListName newList=new ListName("office","user1");
		//testo la creazione della lista
		try{
			dataAccess.listCreate(newList, toAuth);
			ListNameDAO ld=new ListNameDAO();
			ListName checkList=ld.getByNameOwner(newList);
			if(checkList==null){
				fail("la lista non è stata creata correttamente");
			}
		}catch(ListAlreadyExists exc){
			fail("la lista risulta come già esistente");
		}catch(ListNotCorresponding exc){
			fail("la lista risulta non valida nella corrispondenza autenticato-proprietario");
		}
	}
	
	@Test(expected=ListAlreadyExists.class)
	public void checkListCreateFail()throws AuthenticationFail, ListAlreadyExists, ListNotCorresponding{
		User toAuth=new User("user1","user1",null,null,null);
		ListName newList=new ListName("friends","user1");
		//test con una lista già presente per l'utente
		dataAccess.listCreate(newList, toAuth);
	}
	
	@Test(expected=ListNotCorresponding.class)
	public void checkListCreateFailCorresponding()throws AuthenticationFail, ListAlreadyExists, ListNotCorresponding{
		User toAuth=new User("user1","user1",null,null,null);
		ListName newList=new ListName("office","user2");
		//test con una lista di un altro utente rispetto all'autenticato
		dataAccess.listCreate(newList, toAuth);
	}
}
