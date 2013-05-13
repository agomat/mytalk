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
import java.util.List;

import com.mytalk.server.logic.shared.modelClient.*;

public class WorldPack extends Information{
	
	private WorldPackList worldPackList;
	private WorldPackPersonalData worldPackPersonalData;
	
	public WorldPack(WorldPackList wpl,WorldPackPersonalData wppd){
		worldPackList=wpl;
		worldPackPersonalData=wppd;
	}
	
	public void setWorldPackList(WorldPackList wpl){
		worldPackList=wpl;
	}
	
	public WorldPackList getWorldPackList(){
		return worldPackList;
	}
	
	public void setWorldPackPersonalData(WorldPackPersonalData wppd){
		worldPackPersonalData=wppd;
	}
	
	public WorldPackPersonalData getWorldPackPersonalData(){
		return worldPackPersonalData;
	}
}
