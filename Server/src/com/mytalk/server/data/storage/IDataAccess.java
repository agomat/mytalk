/**
* Filename: IDataAccess.java
* Package: com.mytalk.server.data.storage
* Author: Michael Ferronato
* Date: 2013-04-12
*
* Diary:
*
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.3     | 2013-06-18 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.2	  |	2013-04-15 |    MF     | [+] Aggiunta metodi 
* 0.1	  |	2013-04-12 |    MF     | [+] Creazione interfaccia    
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* L'interfaccia espone i metodi disponibili nelle classi che la implementano
*/

package com.mytalk.server.data.storage;

import java.util.List;

import com.mytalk.server.data.model.*;
import com.mytalk.server.exceptions.*;


public interface IDataAccess{
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +checkUserByIp
			 * @return {boolean}
			 */
			public boolean checkUserByIp(String ip);
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +checkUserByName
			 * @return {boolean}
			 * @exception {AuthenticationFailException}
			 */
			public boolean checkUserByName(String name, User authenticate) throws AuthenticationFailException;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +getUsernameByIp
			 * @return {String}
			 * @exception {LogoutException}
			 */
			public String getUsernameByIp(String ip) throws LogoutException;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +login
			 * @return {void}
			 * @exception {AuthenticationFailException, UsernameNotCorrespondingException, IpNotLoggedException, UsernameAlreadyLogged, IpAlreadyLoggedException}
			 */
			public void login(OnlineUser user, User authenticate)throws AuthenticationFailException,UsernameNotCorrespondingException, IpNotLoggedException, UserAlreadyLoggedException, IpAlreadyLoggedException;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +userLists
			 * @return {List<ListName>}
			 * @exception {AuthenticationFailException}
			 */
			public List<ListName> userLists(User authenticate) throws AuthenticationFailException;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +getListUsers
			 * @return {List<User>}
			 * @exception {AuthenticationFailException, UsernameNotCorrespondingException}
			 */
			public List<User> getListUsers(ListName list, User authenticate) throws AuthenticationFailException, UsernameNotCorrespondingException;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +getOnlineUsers
			 * @return {List<User>}
			 * @exception {AuthenticationFailException}
			 */
			public List<User> getOnlineUsers(User authenticate) throws AuthenticationFailException;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +getOfflineUsers
			 * @return {List<User>}
			 * @exception {AuthenticationFailException}
			 */
			public List<User> getOfflineUsers(User authenticate) throws AuthenticationFailException;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +logout
			 * @return {void}
			 * @exception {LogoutException}
			 */
			public void logout(OnlineUser user) throws LogoutException;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +renameList
			 * @return {void}
			 * @exception {AuthenticationFailException, UsernameNotCorrespondingException,ListNotExistingException,ListAlreadyExistsException}
			 */
			public void renameList(ListName list, String name, User authenticate) throws AuthenticationFailException, UsernameNotCorrespondingException,ListNotExistingException,ListAlreadyExistsException;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +listCreate
			 * @return {void}
			 * @exception {AuthenticationFailException,ListAlreadyExistsException,UsernameNotCorrespondingException}
			 */
			public void listCreate(ListName list, User authenticate) throws AuthenticationFailException,ListAlreadyExistsException,UsernameNotCorrespondingException;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +listDelete
			 * @return {void}
			 * @exception {AuthenticationFailException,ListNotExistingException,UsernameNotCorrespondingException}
			 */
			public void listDelete(ListName list, User authenticate) throws AuthenticationFailException,ListNotExistingException,UsernameNotCorrespondingException;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +userListAdd
			 * @return {void}
			 * @exception {AuthenticationFailException,UserAlreadyListedException,UserNotExistingException,UsernameNotCorrespondingException,ListNotExistingException}
			 */
			public void userListAdd(ListName list,String user, User authenticate) throws AuthenticationFailException,UserAlreadyListedException,UserNotExistingException,UsernameNotCorrespondingException,ListNotExistingException;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @emthod +userListRemove
			 * @return {void}
			 * @exception {AuthenticationFailException,UserNotListedException,UserNotExistingException, UsernameNotCorrespondingException, ListNotExistingException}
			 */		
			public void userListRemove(ListName list,String user, User authenticate) throws AuthenticationFailException,UserNotListedException,UserNotExistingException, UsernameNotCorrespondingException, ListNotExistingException;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +loginAsAnonymous
			 * @return {void}
			 * @exception {IpAlreadyLoggedException}
			 */
			public void loginAsAnonymous(OnlineUser user) throws IpAlreadyLoggedException;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +createAccount
			 * @return {void}
			 * @exception {UsernameAlreadyExistingException}
			 */
			public void createAccount(User toCreate) throws UsernameAlreadyExistingException;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +getUserBlacklist
			 * @return {List<User>}
			 * @exception {AuthenticationFailException}
			 */
			public List<User> getUserBlacklist(User authenticate) throws AuthenticationFailException;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +blacklistAdd
			 * @return {void}
			 * @exception {AuthenticationFailException,UserAlreadyBlacklistedException,UsernameNotCorrespondingException,UserNotExistingException}
			 */
			public void blacklistAdd(Blacklist b, User authenticate) throws AuthenticationFailException,UserAlreadyBlacklistedException,UsernameNotCorrespondingException,UserNotExistingException;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +blacklistRemove
			 * @return {void}
			 * @exception {AuthenticationFailException,UserNotBlacklistedException,UsernameNotCorrespondingException}
			 */
			public void blacklistRemove(Blacklist b, User authenticate) throws AuthenticationFailException,UserNotBlacklistedException,UsernameNotCorrespondingException;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +getCalls
			 * @return {List<Call>}
			 * @exception {AuthenticationFailException}
			 */
			public List<Call> getCalls(User authenticate) throws AuthenticationFailException;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +addCall
			 * @return {void}
			 * @exception {AuthenticationFailException}
			 */
			public void addCall(Call call, User authenticate) throws AuthenticationFailException;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +deleteAccount
			 * @return {void}
			 * @exception {AutneticationFail}
			 */
			public void deleteAccount(User userObj) throws AuthenticationFailException;
			
			public void updateAccount(User user, User authenticate)throws AuthenticationFailException,UsernameNotCorrespondingException;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +getUserById
			 * @return {User}
			 * @excetion {IdNotFoundException}
			 */
			public User getUserById(int id) throws IdNotFoundException;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +getUserIp
			 * @return {String}
			 * @exception {UserNotLoggedException}
			 */
			public String getUserIp(String username)throws UserNotLoggedException;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +getIdFromUsername
			 * @return {int}
			 * @exception {UserNotExistingException}
			 */
			public int getIdFromUsername(String username)throws UserNotExistingException;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +logoutToAnonymous
			 * @return {void}
			 * @exception {LogoutException}
			 */
			public void logoutToAnonymous(OnlineUser user)throws LogoutException;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +checkBlacklist
			 * @return {boolean}
			 */
			public boolean checkBlacklist(Blacklist blacklistObj);
}
