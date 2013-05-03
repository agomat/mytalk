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
		
			public void login(OnlineUser user, User authenticate)throws AuthenticationFail,UsernameNotExisting;
			
			public List<ListName> userLists(User authenticate) throws AuthenticationFail;
		
			public List<User> getListUsers(ListName list, User authenticate) throws AuthenticationFail;
			
			public List<User> getOnlineUsers(User authenticate) throws AuthenticationFail;
			
			public List<User> getOfflineUsers(User authenticate) throws AuthenticationFail;
			
			public void logout(OnlineUser user);
			
			public void listCreate(ListName list, User authenticate) throws AuthenticationFail,ListAlreadyExists;
			
			public void listDelete(ListName list, User authenticate) throws AuthenticationFail,ListNotExisting;
		
			public void userListAdd(ListName list,String user, User authenticate) throws AuthenticationFail,UserAlreadyListed;
					
			public void userListRemove(ListName list,String user, User authenticate) throws AuthenticationFail,UserNotListed;
			
			public void loginAsAnonymous(OnlineUser user);
			
			public void createAccount(User toCreate) throws UsernameAlreadyExisting;
			
			public List<User> getUserBlacklist(String u, User authenticate) throws AuthenticationFail;
			
			public void blacklistAdd(Blacklist b, User authenticate) throws AuthenticationFail,UserAlreadyBlacklisted;
			
			public void blacklistRemove(Blacklist b, User authenticate) throws AuthenticationFail,UserNotBlacklisted;
			
			public List<Call> getCalls(String u, User authenticate) throws AuthenticationFail;
			
			public void addCall(Call call, User authenticate) throws AuthenticationFail;
			
			public void deleteAccount(User userObj) throws AuthenticationFail;
			
}
