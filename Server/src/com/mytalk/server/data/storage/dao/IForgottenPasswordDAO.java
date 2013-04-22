/**
* Filename: IForgottenPasswordDAO.java
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

public interface IForgottenPasswordDAO {

	public String confirmChangePassword(String u);
	
	public void passwordRetriever(String u, String newpwd);
}
