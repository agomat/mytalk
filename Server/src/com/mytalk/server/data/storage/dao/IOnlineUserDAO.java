/**
* Filename: IOnLineUserDAO.java
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

public interface IOnlineUserDAO {

	public boolean checkIPIsOnline(String s);
	
	public boolean nameIsOnline(String s);
	
	public void login(String u, String ip);
	
	public void logout(String s);
	
	public List<User> getOnlineUsers();
	
	public void loginAnonymous(String ip);
	
	public void logoutAnonymous(String ip);
}
