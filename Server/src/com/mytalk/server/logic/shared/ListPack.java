/**
* Filename: ListPack.java
* Package: com.mytalk.server.logic.shared
* Author: Nicolò Toso
* Date: 2013-04-23
*
* Diary:
*
* Version |
Date
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-04-23 | NT        | [+] Creazione classe e costruttore
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.logic.shared;
import java.util.List;

import com.mytalk.server.logic.shared.modelclient.*;

public class ListPack {
	
	private WrapperUserList wul;
	
	public ListPack(List<UserList> c){
		wul=new WrapperUserList(c);
	}
}
