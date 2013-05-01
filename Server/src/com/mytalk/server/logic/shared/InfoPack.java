/**
* Filename: InfoPack.java
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
import com.mytalk.server.logic.shared.modelClient.*;

public class InfoPack extends Information{
	
	private AdditionalData ad;
	
	public InfoPack(String p, String em, String c, String sd, String u, String s){
		ad=new AdditionalData(p,em,c,sd,u,s);
	}
	public AdditionalData getAdditional(){
		return ad;
	}
}
