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

import java.sql.Time;
import java.util.List;
import java.util.Vector;

import com.mytalk.server.data.model.*;


public interface IDataAccess{
			public boolean authenticateClient(String u, String p);
			
			public boolean checkUserByIp(String s);
			
			public boolean checkUserByName(String s);
			
			public void createAccount(String u,String p,String e);
			
			public void deleteAccount(String u);
			
			public void login(String u,String p,String ip);
			
			public List<ListName> getUserLists(String u);
		
			public List<UserList> getUserListsDetails(String u);
			
			public List<User> getUserBlacklist(String u);
			
			public List<User> getAllUsers();
			
			public List<User> getOnlineUsers();
			
			public void logout(String u);
			
			public void passwordRetriever(String u, String newpwd);
			
			public void confirmChangePassword(String u);

			public void changePassword(String u, String newpwd);
			
			public void updateEmail(String u,String e,String code);
			
			public boolean confirmUpdateEmail(String u,String code);
			
			public void accountConfirm(String u);
			
			public void listCreate(String nome,String owner);
			
			public void listDelete(String nome,String owner);
		
			public boolean userListAdd(String nome,String owner,String user);
					
			public boolean userListRemove(String nome,String owner,String user);
			
			public void blacklistAdd(String owner,String user);
			
			public void blacklistRemove(String owner,String user);
			
			public List<Call> getCalls(String u);
			
			public void addCall(String c,String r,Integer d,Integer bt,Integer br);
			
			public void deleteUnconfirmedAccount();
			
			public void loginAsAnonymous(String ip);
			
			public void logoutAsAnonymous(String ip);
}
