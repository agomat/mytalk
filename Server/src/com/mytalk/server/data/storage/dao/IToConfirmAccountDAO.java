/**
* Filename: ITConfirmAccountDAO.java
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

public interface IToConfirmAccountDAO {

	public void addAccountToBeConfirmed(String user, String pwd, String email);
	
	public void deleteConfirmedAccount(String user);
	
	public void deleteUnconfirmedAccount();
}
