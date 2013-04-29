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


public interface IDataAccess{
			public boolean checkUserByIp(String ip);
			
			public boolean checkUserByName(String name, User authenticate);
		
			public void login(OnlineUser user);
			
			public List<ListName> userLists(String user, User authenticate);
		
			public List<User> getListUsers(ListName list, User authenticate);
			
			public List<User> getOnlineUsers(User authenticate);
			
			public void logout(OnlineUser user, User authenticate);
			
			public void updateEmail(MailChange newMail, User authenticate);
			
			public void confirmUpdateEmail(MailChange newMail, User authenticate);
			
			public void listCreate(ListName list, User authenticate);
			
			public void listDelete(ListName list, User authenticate);
		
			public void userListAdd(ListName list,String user, User authenticate);
					
			public void userListRemove(ListName list,String user, User authenticate);
			
			public void loginAsAnonymous(OnlineUser user);
			
			public List<User> getUserBlacklist(String u, User authenticate);
			
			public void passwordRetriever(ForgottenPassword forgottenPasswordObj, User authenticate);
			
			public void confirmChangePassword(ForgottenPassword forgottenPasswordObj, User authenticate);
			
			public void blacklistAdd(Blacklist b, User authenticate);
			
			public void blacklistRemove(Blacklist b, User authenticate);
			
			public List<Call> getCalls(String u, User authenticate);
			
			public void addCall(Call call, User authenticate);
			
			public void deleteAccount(User userObj);
			
			public List<User> getAllUsers(User authenticate);
			
			public void changePassword(User userObj, User authenticate);
			
			public void accountConfirm(ToConfirmAccount toConfirmAccountObj);
			
			public void deleteUnconfirmedAccount();
}
