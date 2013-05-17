/**
* Filename: WorldPack.java
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

public class WorldPack extends Information{
	
	private WorldList worldList;
	private WorldPersonalData worldPersonalData;
	
	public WorldPack(WorldList wpl,WorldPersonalData wppd){
		worldList=wpl;
		worldPersonalData=wppd;
	}
	
	public void setWorldList(WorldList wpl){
		worldList=wpl;
	}
	
	public WorldList getWorldList(){
		return worldList;
	}
	
	public void setWorldPersonalData(WorldPersonalData wppd){
		worldPersonalData=wppd;
	}
	
	public WorldPersonalData getWorldPersonalData(){
		return worldPersonalData;
	}
}
