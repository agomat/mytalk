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
			 * @exception {AuthenticationFail}
			 */
			public boolean checkUserByName(String name, User authenticate) throws AuthenticationFail;
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
			 * @exception {AuthenticationFail, UsernameNotCorresponding, IpNotLogged, UsernameAlreadyLogged, IpAlreadyLogged}
			 */
			public void login(OnlineUser user, User authenticate)throws AuthenticationFail,UsernameNotCorresponding, IpNotLogged, UserAlreadyLogged, IpAlreadyLogged;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +userLists
			 * @return {List<ListName>}
			 * @exception {AuthenticationFail}
			 */
			public List<ListName> userLists(User authenticate) throws AuthenticationFail;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +getListUsers
			 * @return {List<User>}
			 * @exception {AuthenticationFail, UsernameNotCorresponding}
			 */
			public List<User> getListUsers(ListName list, User authenticate) throws AuthenticationFail, UsernameNotCorresponding;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +getOnlineUsers
			 * @return {List<User>}
			 * @exception {AuthenticationFail}
			 */
			public List<User> getOnlineUsers(User authenticate) throws AuthenticationFail;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +getOfflineUsers
			 * @return {List<User>}
			 * @exception {AuthenticationFail}
			 */
			public List<User> getOfflineUsers(User authenticate) throws AuthenticationFail;
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
			 * @exception {AuthenticationFail, UsernameNotCorresponding,ListNotExisting,ListAlreadyExists}
			 */
			public void renameList(ListName list, String name, User authenticate) throws AuthenticationFail, UsernameNotCorresponding,ListNotExisting,ListAlreadyExists;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +listCreate
			 * @return {void}
			 * @exception {AuthenticationFail,ListAlreadyExists,UsernameNotCorresponding}
			 */
			public void listCreate(ListName list, User authenticate) throws AuthenticationFail,ListAlreadyExists,UsernameNotCorresponding;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +listDelete
			 * @return {void}
			 * @exception {AuthenticationFail,ListNotExisting,UsernameNotCorresponding}
			 */
			public void listDelete(ListName list, User authenticate) throws AuthenticationFail,ListNotExisting,UsernameNotCorresponding;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +userListAdd
			 * @return {void}
			 * @exception {AuthenticationFail,UserAlreadyListed,UserNotExisting,UsernameNotCorresponding,ListNotExisting}
			 */
			public void userListAdd(ListName list,String user, User authenticate) throws AuthenticationFail,UserAlreadyListed,UserNotExisting,UsernameNotCorresponding,ListNotExisting;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @emthod +userListRemove
			 * @return {void}
			 * @exception {AuthenticationFail,UserNotListed,UserNotExisting, UsernameNotCorresponding, ListNotExisting}
			 */		
			public void userListRemove(ListName list,String user, User authenticate) throws AuthenticationFail,UserNotListed,UserNotExisting, UsernameNotCorresponding, ListNotExisting;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +loginAsAnonymous
			 * @return {void}
			 * @exception {IpAlreadyLogged}
			 */
			public void loginAsAnonymous(OnlineUser user) throws IpAlreadyLogged;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +createAccount
			 * @return {void}
			 * @exception {UsernameAlreadyExisting}
			 */
			public void createAccount(User toCreate) throws UsernameAlreadyExisting;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +getUserBlacklist
			 * @return {List<User>}
			 * @exception {AuthenticationFail}
			 */
			public List<User> getUserBlacklist(User authenticate) throws AuthenticationFail;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +blacklistAdd
			 * @return {void}
			 * @exception {AuthenticationFail,UserAlreadyBlacklisted,UsernameNotCorresponding,UserNotExisting}
			 */
			public void blacklistAdd(Blacklist b, User authenticate) throws AuthenticationFail,UserAlreadyBlacklisted,UsernameNotCorresponding,UserNotExisting;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +blacklistRemove
			 * @return {void}
			 * @exception {AuthenticationFail,UserNotBlacklisted,UsernameNotCorresponding}
			 */
			public void blacklistRemove(Blacklist b, User authenticate) throws AuthenticationFail,UserNotBlacklisted,UsernameNotCorresponding;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +getCalls
			 * @return {List<Call>}
			 * @exception {AuthenticationFail}
			 */
			public List<Call> getCalls(User authenticate) throws AuthenticationFail;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +addCall
			 * @return {void}
			 * @exception {AuthenticationFail}
			 */
			public void addCall(Call call, User authenticate) throws AuthenticationFail;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +deleteAccount
			 * @return {void}
			 * @exception {AutneticationFail}
			 */
			public void deleteAccount(User userObj) throws AuthenticationFail;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +changePassword
			 * @return {void}
			 * @exception {AuthenticationFail,UsernameNotCorresponding}
			 */
			public void changePassword(User userObj, User authenticate) throws AuthenticationFail,UsernameNotCorresponding;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +getUserById
			 * @return {User}
			 * @excetion {IdNotFound}
			 */
			public User getUserById(int id) throws IdNotFound;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +getUserIp
			 * @return {String}
			 * @exception {UserNotLogged}
			 */
			public String getUserIp(String username)throws UserNotLogged;
			/**
			 * Metodo implementato dalla classe che estende tale interfaccia
			 * 
			 * @method +getIdFromUsername
			 * @return {int}
			 * @exception {UserNotExisting}
			 */
			public int getIdFromUsername(String username)throws UserNotExisting;
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
