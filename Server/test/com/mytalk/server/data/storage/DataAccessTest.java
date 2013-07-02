/**
 * Filename: DataAccessTest.java
 * Package: com.mytalk.server.data.storage
 * Author: Nicolo' Mazzucato
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

public class DataAccessTest{
	
	EnvironmentSetter envSetter=new EnvironmentSetter();
	IDataAccess dataAccess= new DataAccess();
	
	@Before
	public void setTestEnvironment(){
		envSetter.cleanDB();
		envSetter.initDB();
	}
	
	
	@Test
	public void checkAutentication() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		User firstTestUser=new User("user0","user0",null,null,null,null);
		User secondTestUser=new User("user1","password",null,null,null,null);
		User thirdTestUser=new User("random","password",null,null,null,null);
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
		assertTrue("non e' stato trovato l'utente con ip "+existingIp,result1);
		
		//test con ip di un utente non presente nella tabella OnlineUsers
		boolean result2=dataAccess.checkUserByIp(notExistingIp);
		assertTrue("e' stato trovato un utente con ip "+notExistingIp+" anche se non e' presente",!result2);
		
	}

	@Test
	public void checkCheckUserByName() throws AuthenticationFailException{
		String existingUsername="user2";
		String notExistingUsername="random";
		User toAuth=new User("user0","user0",null,null,null,null);
		
		//test con username presente nella tabella OnlineUsers
		boolean result1=dataAccess.checkUserByName(existingUsername, toAuth);
		assertTrue("non e' stato trovato l'utente con username "+existingUsername,result1);
		
		//test con username non presente nella tabella OnlineUsers
		boolean result2=dataAccess.checkUserByName(notExistingUsername, toAuth);
		assertTrue("e' stato trovato un utente con username "+notExistingUsername+" anche se non presente",!result2);
		
	}
	
	@Test
	public void checkGetUsernameByIp(){
		String ip="123.123.123.0";
		try {
			String username=dataAccess.getUsernameByIp(ip);
			assertEquals("L'username ritrovato non e` quello giusto","user0",username);
		} catch (LogoutException e) {
			fail("Non e` stato trovato l'ip passato");
		}
	}
	
	@Test(expected=LogoutException.class)
	public void checkGetUsernameByIpFail()throws LogoutException{
		String ip="123.123.123.123";
		String username=dataAccess.getUsernameByIp(ip);
	}
	
	@Test
	public void checkCreateAccount(){
		User testUser=new User("user10","user10","user10@mytalk.com","user10","user10","emailhash123123123");
		
		//test creazione di un nuovo utente
		try{
			dataAccess.createAccount(testUser);
			UserDAO ud=new UserDAO();
			String testUsername=testUser.getUsername();
			User createdUser=ud.get(testUsername);
			assertNotNull("L'utente non e' stato creato",createdUser);
			assertEquals("I campi dati degli utenti non sono stati creati correttamente",testUser.getUsername(),createdUser.getUsername());
		}catch(UsernameAlreadyExistingException exc){
			fail("e' stato creato un utente con un username gia' esistente");
		}
	}
	
	@Test(expected = UsernameAlreadyExistingException.class)
	public void checkCreateAccountFail() throws UsernameAlreadyExistingException{
		User testUser=new User("user0","user0","user0@mytalk.com","user0","user0","emailhash123123123");
		
		//test creazione di un utente avente un username gia' usato
		dataAccess.createAccount(testUser);
	}
	
	@Test
	public void checkLogin()throws AuthenticationFailException{
		User toAuth=new User("user4","user4",null,null,null,null);
		OnlineUser onUserTest=new OnlineUser("user4","111.111.111.1");
		
		//testo il metodo con un OnlineUser avente ip autenticato come anonimo e username non autenticato nel sistema
		try{
			dataAccess.login(onUserTest, toAuth);
			OnlineUserDAO od=new OnlineUserDAO();
			OnlineUser userToCheck=od.get(onUserTest.getIp());
			assertEquals("non e' stato autenticato l'utente valido",onUserTest.getUsername(),userToCheck.getUsername());
		}catch(UsernameNotCorrespondingException exc){
			fail("non e' stata trovata la corrispondenza tra username autenticato e username da autenticare (ecc:UsernameNotExisting)");
		}catch(IpNotLoggedException exc){
			fail("non e' stato trovato l'utente come autenticato anonimo (ecc:UserNotLoggedException)");
		}catch(UserAlreadyLoggedException exc){
			fail("e' stato trovato l'utente come gia' autenticato nel sistema (ecc:UserAlreadyLoggedException)");
		}catch(IpAlreadyLoggedException exc){
			fail("e' stato trovato l'ip come gia' autenticato nel sistema (ecc:IpAlreadyLoggedException)");
		}
	}
	
	@Test(expected=UserAlreadyLoggedException.class)
	public void checkLoginAuthenticatedUsername()throws AuthenticationFailException,UserAlreadyLoggedException, IpNotLoggedException, IpAlreadyLoggedException, UsernameNotCorrespondingException{
		User toAuth=new User("user0","user0",null,null,null,null);
		OnlineUser onUserTest=new OnlineUser("user0","111.111.111.2");
		
		//testo il metodo con un OnlineUser avente username gia' autenticato con un altro ip
		dataAccess.login(onUserTest, toAuth);
	}
	
	@Test(expected=IpAlreadyLoggedException.class)
	public void checkLoginAuthenticatedIp()throws AuthenticationFailException,IpAlreadyLoggedException, IpNotLoggedException, UserAlreadyLoggedException, UsernameNotCorrespondingException{
		User toAuth=new User("user5","user5",null,null,null,null);
		OnlineUser onUserTest=new OnlineUser("user5","123.123.123.2");
		
		//testo il metodo con un OnlineUser avente ip gia' autenticato con un username
		dataAccess.login(onUserTest, toAuth);
	}

	@Test(expected = IpNotLoggedException.class)
	public void checkLoginFailIp()throws AuthenticationFailException,IpNotLoggedException, UserAlreadyLoggedException, IpAlreadyLoggedException, UsernameNotCorrespondingException{
		User toAuth=new User("user0","user0",null,null,null,null);
		OnlineUser onUserTest=new OnlineUser("user0","111.111.111.111");
		
		//testo il metodo con un OnlineUser avente un ip non autenticato come anonimo
		dataAccess.login(onUserTest, toAuth);
	}
	
	@Test(expected = UsernameNotCorrespondingException.class)
	public void checkLoginFailNotCorresponding()throws AuthenticationFailException,UsernameNotCorrespondingException, IpNotLoggedException, UserAlreadyLoggedException, IpAlreadyLoggedException{
		User toAuth=new User("user0","user0",null,null,null,null);
		OnlineUser onUserTest=new OnlineUser("random","111.111.111.2");
		
		//testo il metodo con un OnlineUser avente username da autenticare non corrispondente al campo dell'oggetto da autenticare
		dataAccess.login(onUserTest, toAuth);
	}
	
	@Test
	public void checkUserLists()throws AuthenticationFailException{
		User testUser1=new User("user0","user0",null,null,null,null);
		User testUser2=new User("user9","user9",null,null,null,null);
		
		//test con un User che ha liste
		List<ListName> list1=dataAccess.userLists(testUser1);
		int count1=list1.size();
		assertEquals("e' stato trovato un numero di liste diverso da quelle possedute dall'utente",2,count1);
		
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
			OnlineUserDAO od=new OnlineUserDAO();
			OnlineUser checkUser=od.get(testUser.getIp());
			assertNull("l'utente non e' stato de-autenticato",checkUser);
		}catch(LogoutException exc){
			fail("non e' stato trovato l'utente da de-autenticare come autenticato");
		}
	}
	
	@Test(expected=LogoutException.class)
	public void checkLogoutFail()throws LogoutException{
		OnlineUser testUser=new OnlineUser(null,"123.123.123.123");
		
		//test con un utente non autenticato nel sistema
		dataAccess.logout(testUser);
	}
	
	 @Test
	 public void checkGetListUsers()throws AuthenticationFailException{
		 User toAuth=new User("user1","user1",null,null,null,null);
		 ListName list=new ListName();
		 list.setId(2);
		 
		 //test con una lista di proprieta' dell'utente autenticato
		 try{
			 List<User> listResult=dataAccess.getListUsers(list, toAuth);
			 assertEquals("la lista ritornata dal metodo e' sbagliata",4,listResult.size());
		 }catch(UsernameNotCorrespondingException exc){
			 fail("la lista non risulta corrispondente all'utente anche se lo e'");
		 }
	 }
	 
	 @Test(expected=UsernameNotCorrespondingException.class)
	 public void checkGetListUsersFail()throws AuthenticationFailException, UsernameNotCorrespondingException{
		 User toAuth=new User("user1","user1",null,null,null,null);
		 ListName list=new ListName();
		 list.setId(3);
		 
		 //test con una lista di proprieta' di un altro utente
		 List<User> listResult=dataAccess.getListUsers(list, toAuth);
	 }
	
	@Test
	public void checkGetOnlineUsers()throws AuthenticationFailException{
		User toAuth=new User("user1","user1",null,null,null,null);
		List<User> onlines=dataAccess.getOnlineUsers(toAuth);
		assertEquals("il numero di utenti autenticati non corrisponde quello effettivo",3,onlines.size());
	}
	
	@Test
	public void checkGetOfflineUsers()throws AuthenticationFailException{
		User toAuth=new User("user1","user1",null,null,null,null);
		List<User> offlines=dataAccess.getOfflineUsers(toAuth);
		assertEquals("il numero di utenti non autenticati non corrisponde quello effettivo",7,offlines.size());
	}
	
	@Test
	public void checkListCreate()throws AuthenticationFailException{
		User toAuth=new User("user1","user1",null,null,null,null);
		ListName newList=new ListName("office","user1");
		//testo la creazione della lista
		try{
			dataAccess.listCreate(newList, toAuth);
			ListNameDAO ld=new ListNameDAO();
			ListName checkList=ld.getByNameOwner(newList);
			assertNotNull("la lista non e' stata creata correttamente",checkList);
		}catch(ListAlreadyExistsException exc){
			fail("la lista risulta come gia' esistente");
		}catch(UsernameNotCorrespondingException exc){
			fail("la lista risulta non valida nella corrispondenza autenticato-proprietario");
		}
	}
	
	@Test(expected=ListAlreadyExistsException.class)
	public void checkListCreateFail()throws AuthenticationFailException, ListAlreadyExistsException, UsernameNotCorrespondingException{
		User toAuth=new User("user1","user1",null,null,null,null);
		ListName newList=new ListName("friends","user1");
		
		//test con una lista gia' presente per l'utente
		dataAccess.listCreate(newList, toAuth);
	}
	
	@Test(expected=UsernameNotCorrespondingException.class)
	public void checkListCreateFailCorresponding()throws AuthenticationFailException, ListAlreadyExistsException, UsernameNotCorrespondingException{
		User toAuth=new User("user1","user1",null,null,null,null);
		ListName newList=new ListName("office","user2");
		
		//test con una lista di un altro utente rispetto all'autenticato
		dataAccess.listCreate(newList, toAuth);
	}
	
	@Test
	public void checkListDelete()throws AuthenticationFailException{
		User toAuth=new User("user1","user1",null,null,null,null);
		ListName newList=new ListName("friends","user1");
		//testo la cancellazione di una lista
		try{
			dataAccess.listDelete(newList, toAuth);
			ListNameDAO ld=new ListNameDAO();
			ListName checkList=ld.getByNameOwner(newList);
			assertNull("la lista non e' stata cancellata dal db",checkList);
		}catch(ListNotExistingException exc){
			fail("la lista che stai cercando di cancellare non sembra essere esistente");
		}catch(UsernameNotCorrespondingException exc){
			fail("non e' stata trovata corrispondenza tra owner-username");
		}
		
	}
	
	@Test(expected=ListNotExistingException.class)
	public void checkListDeleteFail()throws AuthenticationFailException, ListNotExistingException, UsernameNotCorrespondingException{
		User toAuth=new User("user1","user1",null,null,null,null);
		ListName newList=new ListName("office","user1");
		
		//test di cancellazione di una lista che non esiste
		dataAccess.listDelete(newList, toAuth);
	}
	
	@Test(expected=UsernameNotCorrespondingException.class)
	public void checkListDeleteFailCorresponding()throws AuthenticationFailException, ListNotExistingException, UsernameNotCorrespondingException{
		User toAuth=new User("user1","user1",null,null,null,null);
		ListName newList=new ListName("friends","user2");
		
		//test di cancellazione di una lista di un altro utente
		dataAccess.listDelete(newList, toAuth);
	}
	
	@Test
	public void checkUserListAdd()throws AuthenticationFailException{
		User toAuth=new User("user1","user1",null,null,null,null);
		ListName newList=new ListName("friends","user1");
		String toAdd="user5";
		
		//test di aggiunta di un utente a una lista
		try{
			dataAccess.userListAdd(newList, toAdd, toAuth);
			UserListDAO ld=new UserListDAO();
			ListNameDAO lnd=new ListNameDAO();
			ListName list=lnd.getByNameOwner(newList);
			UserList checkUser=ld.get(list.getId(), toAdd);
			assertNotNull("l'utente non e' stato aggiunto alla lista",checkUser);
		}catch(UserAlreadyListedException exc){
			fail("l'utente risulta gia' nella lista");
		} catch (UserNotExistingException exc) {
			fail("l'utente da aggiungere non sembra essere registrato nel sistema");
		} catch (UsernameNotCorrespondingException e) {
			fail("non e' stata trovata la corrispondenza owner-username");
		} catch (ListNotExistingException e) {
			fail("la lista a cui aggiungere non risulta esistente");
		}
	}
	
	@Test(expected=UserAlreadyListedException.class)
	public void checkUserListAddFail()throws AuthenticationFailException,UserAlreadyListedException, UserNotExistingException, UsernameNotCorrespondingException, ListNotExistingException{
		User toAuth=new User("user1","user1",null,null,null,null);
		ListName newList=new ListName("friends","user1");
		String toAdd="user4";
		
		//test di aggiunta di un utente a una lista che lo ha gia'
		dataAccess.userListAdd(newList, toAdd, toAuth);
	}
	
	@Test(expected=UserNotExistingException.class)
	public void checkUserListAddFailNotExisting()throws AuthenticationFailException,UserAlreadyListedException, UserNotExistingException, UsernameNotCorrespondingException, ListNotExistingException{
		User toAuth=new User("user1","user1",null,null,null,null);
		ListName newList=new ListName("friends","user1");
		String toAdd="random";
		
		//test di aggiunta di un utente che non e' registrato nel sistema
		dataAccess.userListAdd(newList, toAdd, toAuth);
	}
	
	@Test(expected=UserNotExistingException.class)
	public void checkUserListAddFailSameUser()throws AuthenticationFailException,UserAlreadyListedException, UserNotExistingException, UsernameNotCorrespondingException, ListNotExistingException{
		User toAuth=new User("user1","user1",null,null,null,null);
		ListName newList=new ListName("friends","user1");
		String toAdd="user1";
		
		//test di aggiunta a una lista di se stesso
		dataAccess.userListAdd(newList, toAdd, toAuth);
	}
	
	@Test(expected=UsernameNotCorrespondingException.class)
	public void checkUserListAddFailNotCorresponding()throws AuthenticationFailException,UserAlreadyListedException, UserNotExistingException, UsernameNotCorrespondingException, ListNotExistingException{
		User toAuth=new User("user1","user1",null,null,null,null);
		ListName newList=new ListName("friends","user0");
		String toAdd="user4";
		
		//test di aggiunta a una lista appartenente a un altro utente
		dataAccess.userListAdd(newList, toAdd, toAuth);
	}
	
	@Test(expected=ListNotExistingException.class)
	public void checkUserListAddFailListNotExisting()throws AuthenticationFailException,UserAlreadyListedException, UserNotExistingException, UsernameNotCorrespondingException, ListNotExistingException{
		User toAuth=new User("user1","user1",null,null,null,null);
		ListName newList=new ListName("office","user1");
		String toAdd="user4";
		
		//test di aggiunta a una lista non esistente
		dataAccess.userListAdd(newList, toAdd, toAuth);
	}
	
	@Test
	public void checkUserListRemove()throws AuthenticationFailException{
		User toAuth=new User("user1","user1",null,null,null,null);
		ListName newList=new ListName("friends","user1");
		String toAdd="user3";
		
		//test di rimozione di un utente da una lista
		try{
			dataAccess.userListRemove(newList, toAdd, toAuth);
			UserListDAO ld=new UserListDAO();
			ListNameDAO lnd=new ListNameDAO();
			ListName list=lnd.getByNameOwner(newList);
			UserList checkUser=ld.get(list.getId(), toAdd);
			assertNull("l'utente non e' stato rimosso dalla lista",checkUser);
		}catch(UserNotListedException exc){
			fail("l'utente non risulta nella lista");
		} catch (UserNotExistingException exc) {
			fail("l'utente da aggiungere non sembra essere registrato nel sistema");
		} catch (UsernameNotCorrespondingException e) {
			fail("non e' stata trovata la corrispondenza owner-username");
		} catch (ListNotExistingException e) {
			fail("la lista da cui rimuovere non risulta esistente");
		}
	}
	
	@Test(expected=UserNotListedException.class)
	public void checkUserListRemoveFail()throws AuthenticationFailException,UserAlreadyListedException, UserNotExistingException, UsernameNotCorrespondingException, ListNotExistingException, UserNotListedException{
		User toAuth=new User("user1","user1",null,null,null,null);
		ListName newList=new ListName("friends","user1");
		String toAdd="user5";
		
		//test di rimozione di un utente da una lista che non lo ha
		dataAccess.userListRemove(newList, toAdd, toAuth);
	}
	
	@Test(expected=UserNotExistingException.class)
	public void checkUserListRemoveFailNotExisting()throws AuthenticationFailException,UserAlreadyListedException, UserNotExistingException, UsernameNotCorrespondingException, ListNotExistingException, UserNotListedException{
		User toAuth=new User("user1","user1",null,null,null,null);
		ListName newList=new ListName("friends","user1");
		String toAdd="random";
		
		//test di rimozione di un utente che non e' registrato nel sistema
		dataAccess.userListRemove(newList, toAdd, toAuth);
	}
	
	@Test(expected=UsernameNotCorrespondingException.class)
	public void checkUserListRemoveFailNotCorresponding()throws AuthenticationFailException,UserAlreadyListedException, UserNotExistingException, UsernameNotCorrespondingException, ListNotExistingException, UserNotListedException{
		User toAuth=new User("user1","user1",null,null,null,null);
		ListName newList=new ListName("friends","user0");
		String toAdd="user2";
		
		//test di rimozione da una lista appartenente a un altro utente
		dataAccess.userListRemove(newList, toAdd, toAuth);
	}
	
	@Test(expected=ListNotExistingException.class)
	public void checkUserListRemoveFailListNotExisting()throws AuthenticationFailException,UserAlreadyListedException, UserNotExistingException, UsernameNotCorrespondingException, ListNotExistingException, UserNotListedException{
		User toAuth=new User("user1","user1",null,null,null,null);
		ListName newList=new ListName("office","user1");
		String toAdd="user4";
		
		//test di rimozione da una lista non esistente
		dataAccess.userListRemove(newList, toAdd, toAuth);
	}
	
	@Test
	public void checkLoginAsAnonymous(){
		OnlineUser testUser=new OnlineUser(null,"123.123.123.123");
		//test con un ip non in uso
		try{
			dataAccess.loginAsAnonymous(testUser);
			OnlineUserDAO od=new OnlineUserDAO();
			OnlineUser checkOnline=od.get(testUser.getIp());
			assertNotNull("l'ip non e' stato registrato nel db",checkOnline);
		}catch(IpAlreadyLoggedException exc){
			fail("l'ip usato sembra essere gia' in uso");
		}
	}
	
	@Test(expected=IpAlreadyLoggedException.class)
	public void checkLoginAsAnonymousFail()throws IpAlreadyLoggedException{
		OnlineUser testUser=new OnlineUser(null,"123.123.123.0");
		
		//test con un ip gia' in uso
		dataAccess.loginAsAnonymous(testUser);
	}
	
	@Test
	public void checkGetUserBlacklist()throws AuthenticationFailException{
		User toAuth=new User("user0","user0",null,null,null,null);
		List<User> blacklisted=dataAccess.getUserBlacklist(toAuth);
		assertEquals("non sono stati trovati utenti nella blacklist di un utente che ne ha",1,blacklisted.size());
	}
	
	@Test
	public void checkBlacklistAdd()throws AuthenticationFailException{
		User toAuth=new User("user0","user0",null,null,null,null);
		Blacklist toBlack=new Blacklist("user0","user1");
		
		//test di aggiunta di un utente alla blacklist
		try{
			dataAccess.blacklistAdd(toBlack, toAuth);
			BlacklistDAO bd=new BlacklistDAO();
			Blacklist checkBlack=bd.get(toBlack.getOwner(), toBlack.getUsername());
			assertNotNull("l'utente non e' stato aggiunto alla blacklist",checkBlack);
		}catch(UserAlreadyBlacklistedException exc){
			fail("l'utente risulta gia' nella blacklist");
		} catch (UsernameNotCorrespondingException e) {
			fail("non e' stata trovata corrispondenza owner-username");
		} catch (UserNotExistingException e) {
			fail("non e' stato trovato nel db l'utente da aggiungere alla blacklist");
		}
	}
	
	@Test(expected=UsernameNotCorrespondingException.class)
	public void checkBlacklistAddFailNotCorresponding()throws AuthenticationFailException, UserAlreadyBlacklistedException, UsernameNotCorrespondingException, UserNotExistingException{
		User toAuth=new User("user0","user0",null,null,null,null);
		Blacklist toBlack=new Blacklist("user1","user2");
		
		//test di aggiunta di un utente alla blacklist di un altro utente
		dataAccess.blacklistAdd(toBlack, toAuth);
	}
	
	@Test(expected=UserAlreadyBlacklistedException.class)
	public void checkBlacklistAddFailAlreadyBlacklisted()throws AuthenticationFailException, UserAlreadyBlacklistedException, UsernameNotCorrespondingException, UserNotExistingException{
		User toAuth=new User("user0","user0",null,null,null,null);
		Blacklist toBlack=new Blacklist("user0","user9");
		
		//test di aggiunta di un utente gia' presente nella blacklist
		dataAccess.blacklistAdd(toBlack, toAuth);
	}
	
	@Test(expected=UserNotExistingException.class)
	public void checkBlacklistAddFailSame()throws AuthenticationFailException, UserAlreadyBlacklistedException, UsernameNotCorrespondingException, UserNotExistingException{
		User toAuth=new User("user0","user0",null,null,null,null);
		Blacklist toBlack=new Blacklist("user0","user0");
		
		//test di aggiunta di se stesso alla blacklist
		dataAccess.blacklistAdd(toBlack, toAuth);
	}
	
	@Test(expected=UserNotExistingException.class)
	public void checkBlacklistAddFailNotExisting()throws AuthenticationFailException, UserAlreadyBlacklistedException, UsernameNotCorrespondingException, UserNotExistingException{
		User toAuth=new User("user0","user0",null,null,null,null);
		Blacklist toBlack=new Blacklist("user0","random");
		
		//test di aggiunta di un utente inesistente alla blacklist
		dataAccess.blacklistAdd(toBlack, toAuth);
	}
	
	@Test
	public void checkBlacklistRemove()throws AuthenticationFailException{
		User toAuth=new User("user0","user0",null,null,null,null);
		Blacklist toBlack=new Blacklist("user0","user9");
		
		//test di rimozione di un utente dalla blacklist
		try{
			dataAccess.blacklistRemove(toBlack, toAuth);
			BlacklistDAO bd=new BlacklistDAO();
			Blacklist checkBlack=bd.get(toBlack.getOwner(), toBlack.getUsername());
			assertNull("l'utente non e' stato rimosso salla blacklist",checkBlack);
		} catch (UsernameNotCorrespondingException e) {
			fail("non e' stata trovata corrispondenza owner-username");
		} catch (UserNotBlacklistedException e) {
			fail("l'utente non e' stato trovato nella blacklist");
		}
	}
	
	@Test(expected=UsernameNotCorrespondingException.class)
	public void checkBlacklistRemoveFailNotCorresponding()throws AuthenticationFailException, UsernameNotCorrespondingException, UserNotBlacklistedException{
		User toAuth=new User("user0","user0",null,null,null,null);
		Blacklist toBlack=new Blacklist("user1","user8");
		
		//test di rimozione di un utente dalla blacklist di un altro utente
		dataAccess.blacklistRemove(toBlack, toAuth);
	}
	
	@Test(expected=UserNotBlacklistedException.class)
	public void checkBlacklistRemoveFailNotBlacklisted()throws AuthenticationFailException, UsernameNotCorrespondingException, UserNotBlacklistedException{
		User toAuth=new User("user0","user0",null,null,null,null);
		Blacklist toBlack=new Blacklist("user0","user1");
		
		//test di rimozione di un utente non presente nella blacklist
		dataAccess.blacklistRemove(toBlack, toAuth);
	}
	
	@Test
	public void checkGetCalls()throws AuthenticationFailException{
		User toAuth=new User("user0","user0",null,null,null,null);
		List<Call> calls=dataAccess.getCalls(toAuth);
		assertEquals("e` stato trovato un numero di chiamate errato",4,calls.size());
	}
	
	@Test
	public void checkAddCall()throws AuthenticationFailException{
		User toAuth=new User("user0","user0",null,null,null,null);
		Call call=new Call("user0","user1",34000,"2013-04-01",30495,56039);
		call.setId(12);
		dataAccess.addCall(call, toAuth);
		CallDAO cd=new CallDAO();
		Call checkCall=cd.get(12);
		assertNotNull("la chiamata non e` stata aggiunta",checkCall);
	}
	
	@Test
	public void checkDeleteAccount()throws AuthenticationFailException{
		User toAuth=new User("user0","user0",null,null,null,null);
		dataAccess.deleteAccount(toAuth);
		UserDAO ud=new UserDAO();
		User u=ud.get(toAuth.getUsername());
		assertNull("l'utente non e` stato cancellato con successo",u);
	}
	
	@Test
	public void checkUpdateAccount()throws AuthenticationFailException{
		User toChange=new User("user0","pwd","user0@prova.com","nome","cognome","emailhashNUOVO");
		User toAuth=new User("user0","user0",null,null,null,null);
		
		//test di cambio di tutti i campi permessi
		try{
			dataAccess.updateAccount(toChange, toAuth);
			UserDAO ud=new UserDAO();
			User u=ud.get(toAuth.getUsername());
			assertEquals("email non aggiornata",u.getEmail(),toChange.getEmail());
			assertEquals("hash della email non aggiornato",u.getEmailHash(),toChange.getEmailHash());
			assertEquals("password non aggiornata",u.getPassword(),toChange.getPassword());
			assertEquals("nome non aggiornato",u.getName(),toChange.getName());
			assertEquals("cognome non aggiornato",u.getSurname(),toChange.getSurname());
		}catch(UsernameNotCorrespondingException e){
			fail("non e` stata trovata la corrispondenza username da autenticare-username a cui cambiare dati");
		}
	}
	
	@Test(expected=UsernameNotCorrespondingException.class)
	public void checkUpdateAccountFail()throws AuthenticationFailException,UsernameNotCorrespondingException{
		User toAuth=new User("user1","user1",null,null,null,null);
		User toChange=new User("user11","user11",null,null,null,null);
		
		//test di cambio dati di un altro user
		dataAccess.updateAccount(toChange, toAuth);
	}
	
	@Test
	public void checkRenameList()throws AuthenticationFailException{
		User toAuth=new User("user0","user0",null,null,null,null);
		ListName newList=new ListName("friends","user0");
		String newName="enemies";
		
		//test di rinomina di una lista esistente
		try{
			dataAccess.renameList(newList, newName, toAuth);
			ListNameDAO ld=new ListNameDAO();
			ListName list=new ListName(newName,newList.getOwner());
			ListName checkList=ld.getByNameOwner(list);
			assertNotNull("la lista non e' stata rinominata", checkList);
		}catch(UsernameNotCorrespondingException exc){
			fail("non e' stata trovata la corrispondenza owner-username autenticato");
		} catch (ListNotExistingException e) {
			fail("la lista non risulta esistente");
		} catch (ListAlreadyExistsException e) {
			fail("esiste gia' una lista con questo nome");
		}
	}
	
	@Test(expected=UsernameNotCorrespondingException.class)
	public void checkRenameListFailNotCorresponding()throws AuthenticationFailException, ListNotExistingException, ListAlreadyExistsException, UsernameNotCorrespondingException{
		User toAuth=new User("user0","user0",null,null,null,null);
		ListName newList=new ListName("friends","user1");
		String newName="enemies";
		
		//test con username da autenticare e proprietario della lista diversi
		dataAccess.renameList(newList, newName, toAuth);
	}
	
	@Test(expected=ListNotExistingException.class)
	public void checkRenameListFailNotExisting()throws AuthenticationFailException, ListNotExistingException, ListAlreadyExistsException, UsernameNotCorrespondingException{
		User toAuth=new User("user0","user0",null,null,null,null);
		ListName newList=new ListName("office","user0");
		String newName="friends";
		
		//test di rinomina di una lista non esistente
		dataAccess.renameList(newList, newName, toAuth);
	}
	
	@Test(expected=ListAlreadyExistsException.class)
	public void checkRenameListFailAlreadyExisting()throws AuthenticationFailException, ListNotExistingException, ListAlreadyExistsException, UsernameNotCorrespondingException{
		User toAuth=new User("user0","user0",null,null,null,null);
		ListName newList=new ListName("friends","user0");
		String newName="friends";
		
		//test di rinomina di una lista con un nome gia' usato
		dataAccess.renameList(newList, newName, toAuth);
	}
	
	@Test
	public void checkGetUserById(){
		int id=2;
		
		//test di ricerca di un user con id valido
		try{
			User u=dataAccess.getUserById(id);
			assertNotNull("non e` stato ritornato nessun oggetto User",u);
			assertEquals("non e` stato ritornato l'oggetto User corretto",id,u.getId());
		}catch(IdNotFoundException exc){
			fail("l'id non e` stato trovato correttamente nel database");
		}
	}
	
	@Test(expected=IdNotFoundException.class)
	public void checkGetUserByIdFail()throws IdNotFoundException{
		int id=300;
		
		//test di ricerca di un User con id non presente
		User u=dataAccess.getUserById(id);
	}
	
	@Test
	public void checkGetUserIp(){
		String username="user0";
		
		//test di ricerca di un username autenticato
		try{
			String ip=dataAccess.getUserIp(username);
			String expected="123.123.123.0";
			assertEquals("l'ip dell'utente cercato e` sbagliato",expected,ip);
		}catch(UserNotLoggedException exc){
			fail("l'username non e` stato trovato correttamente nel database");
		}
	}
	
	@Test(expected=UserNotLoggedException.class)
	public void checkGetUserIpFailNotExisting()throws UserNotLoggedException{
		String username="";
		
		//test di ricerca di un username vuoto
		String ip=dataAccess.getUserIp(username);
	}
	
	@Test(expected=UserNotLoggedException.class)
	public void checkGetUserIpFail()throws UserNotLoggedException{
		String username="user6";
		
		//test di ricerca di un username di un utente non autenticato
		String ip=dataAccess.getUserIp(username);
	}
	
	@Test
	public void checkGetIdFromUsername(){
		String username="user0";
		
		//test di ricerca di un username esistente
		try{
			int id=dataAccess.getIdFromUsername(username);
			int expected=1;
			assertEquals("l'id dell'utente cercato e` sbagliato",expected,id);
		}catch(UserNotExistingException exc){
			fail("l'username non e` stato trovato correttamente nel database");
		}
	}
	
	@Test(expected=UserNotExistingException.class)
	public void checkGetIdFromUsernameFail()throws UserNotExistingException{
		String username="random";
		
		//test di ricerca di un username non esistente
		int id=dataAccess.getIdFromUsername(username);
	}
	
	@Test
	public void checkLogoutToAnonymous(){
		OnlineUser user=new OnlineUser("user0","123.123.123.0");
		
		//test di logout di un utente autenticato
		try{
			dataAccess.logoutToAnonymous(user);
			OnlineUserDAO od=new OnlineUserDAO();
			OnlineUser checkUser=od.get(user.getIp());
			assertNull("l'utente non e' stato deautenticato correttamente",checkUser.getUsername());
		}catch(LogoutException exc){
			fail("l'utente non e' stato trovato come autenticato");
		}
	}
	
	@Test(expected=LogoutException.class)
	public void checkLogoutToAnonymousFail()throws LogoutException{
		OnlineUser user=new OnlineUser("random","1.1.1.1");
		
		//test di logout di un utente non autenticato
		dataAccess.logoutToAnonymous(user);
	}
	
	@Test
	public void checkCheckBlacklist(){
		Blacklist toCheckTrue=new Blacklist("user0","user9");
		Blacklist toCheckFalse=new Blacklist("user0","random");
		
		//test di controllo di un user che e' nella blacklist dell'user0
		boolean b=dataAccess.checkBlacklist(toCheckTrue);
		assertTrue("l'utente non e' stato trovato nella blacklist",b);
	
		
		//test di controllo di un user che non e' nella blacklist dell'user0
		boolean t=dataAccess.checkBlacklist(toCheckFalse);
		assertTrue("l'utente e' stato trovato in blacklist pur non essendo presente",!t);

	}

}
