/**
* Filename: IDataAccess.java
* Package: com.mytalk.server.data.storage
* Author: Michael Ferronato
* Date: 2013-04-12
*
* Diary:
*
* Version |
Date
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2	  |	2013-04-15 | MF        | [+] Aggiunta metodi 
* 0.1	  |	2013-04-12 | MF        | [+] Creazione interfaccia    
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.data.storage;

import java.util.List;

import com.mytalk.server.data.model.*;
import com.mytalk.server.exceptions.*;


public interface IDataAccess{
			public boolean checkUserByIp(String ip);
			
			public boolean checkUserByName(String name, User authenticate) throws AuthenticationFail;
			
			public String getUsernameByIp(String ip) throws LogoutException;
		
			public void login(OnlineUser user, User authenticate)throws AuthenticationFail,UsernameNotCorresponding, IpNotLogged, UserAlreadyLogged, IpAlreadyLogged;
			
			public List<ListName> userLists(User authenticate) throws AuthenticationFail;
		
			public List<User> getListUsers(ListName list, User authenticate) throws AuthenticationFail, UsernameNotCorresponding;
			
			public List<User> getOnlineUsers(User authenticate) throws AuthenticationFail;
			
			public List<User> getOfflineUsers(User authenticate) throws AuthenticationFail;
			
			public void logout(OnlineUser user) throws LogoutException;
			
			public void renameList(ListName list, String name, User authenticate) throws AuthenticationFail, UsernameNotCorresponding,ListNotExisting,ListAlreadyExists;
			
			public void listCreate(ListName list, User authenticate) throws AuthenticationFail,ListAlreadyExists,UsernameNotCorresponding;
			
			public void listDelete(ListName list, User authenticate) throws AuthenticationFail,ListNotExisting,UsernameNotCorresponding;
		
			public void userListAdd(ListName list,String user, User authenticate) throws AuthenticationFail,UserAlreadyListed,UserNotExisting,UsernameNotCorresponding,ListNotExisting;
					
			public void userListRemove(ListName list,String user, User authenticate) throws AuthenticationFail,UserNotListed,UserNotExisting, UsernameNotCorresponding, ListNotExisting;
			
			public void loginAsAnonymous(OnlineUser user) throws IpAlreadyLogged;
			
			public void createAccount(User toCreate) throws UsernameAlreadyExisting;
			
			public List<User> getUserBlacklist(User authenticate) throws AuthenticationFail;
			
			public void blacklistAdd(Blacklist b, User authenticate) throws AuthenticationFail,UserAlreadyBlacklisted,UsernameNotCorresponding,UserNotExisting;
			
			public void blacklistRemove(Blacklist b, User authenticate) throws AuthenticationFail,UserNotBlacklisted,UsernameNotCorresponding;
			
			public List<Call> getCalls(User authenticate) throws AuthenticationFail;
			
			public void addCall(Call call, User authenticate) throws AuthenticationFail;
			
			public void deleteAccount(User userObj) throws AuthenticationFail;
			
			public void changePassword(User userObj, User authenticate) throws AuthenticationFail,UsernameNotCorresponding;
			
			public User getUserById(int id) throws IdNotFound;
			
			public String getUserIp(String username)throws UserNotLogged;
			
			public int getIdFromUsername(String username)throws UserNotExisting;
			
			public void logoutToAnonymous(OnlineUser user)throws LogoutException;
			
			public boolean checkBlacklist(Blacklist blacklistObj);
}
