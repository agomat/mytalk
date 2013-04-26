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

import java.sql.Timestamp;
import java.util.List;

import com.mytalk.server.data.model.*;


public interface IDataAccess{
			public boolean checkUserByIp(String ip);
			
			public boolean checkUserByName(String name);
		
			public void login(OnlineUser user);
			
			public List<ListName> userLists(String user);
		
			public List<User> getListUsers(ListName list);
			
			public List<User> getOnlineUsers();
			
			public void logout(OnlineUser user);
			
			public void updateEmail(MailChange newMail);
			
			public void confirmUpdateEmail(MailChange newMail);
			
			public void listCreate(ListName list);
			
			public void listDelete(ListName list);
		
			public void userListAdd(ListName list,String user);
					
			public void userListRemove(ListName list,String user);
			
			public void loginAsAnonymous(OnlineUser user);
}
