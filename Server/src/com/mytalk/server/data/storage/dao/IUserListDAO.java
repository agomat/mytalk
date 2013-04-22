/**
* Filename: IUserListDAO.java
* Package: com.mytalk.server.data.storage.dao
* Author: Nicolò Mazzucato
* Date: 2013-04-16
*
* Diary:
*
* Version |
Date
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-04-16 | NM        | [+] Creazione interfaccia e definizione metodi 
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/
package com.mytalk.server.data.storage.dao;

import com.mytalk.server.data.model.*;
import java.util.*;

public interface IUserListDAO {

	public void addUserToId(int id, String user);
	
	public void deleteUserToId(int id, String user);
	
	public List<UserList> getUserListsDetails(String user);
	
	public void checkAndRemoveBlacklistedUsers(String owner,String user);
}
