/**
* Filename: IUserDAO.java
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

public interface IUserDAO {

	public boolean checkAuthenticationData(String u,String p);
	
	public void deleteAccount(String u);
	
	public List<User> giveListUser();
	
	public void changeUserPassword(String user, String pwd);
	
	public void addUser(String user, String pwd, String email);
	
	public void changeUserMail(String user, String email);
}
