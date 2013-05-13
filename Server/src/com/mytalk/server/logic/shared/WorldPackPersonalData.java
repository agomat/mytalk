/**
* Filename: WorldPackPersonalData.java
* Package: com.mytalk.server.logic.shared
* Author: Michael Ferronato
* Date: 2013-05-02
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-05-02 |   MF      | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.logic.shared;

import com.mytalk.server.logic.shared.modelClient.PersonalData;


public class WorldPackPersonalData {
private PersonalData pd;
	
	public WorldPackPersonalData(PersonalData pdEntity){
		pd=pdEntity;
	}
	
	public PersonalData getPersonalData(){
		return pd;
	}
	
	public void setPersonalData(PersonalData personalData){
		pd=personalData;
	}
}
