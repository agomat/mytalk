/**
* Filename: WorldPersonalData.java
* Package: com.mytalk.server.logic.shared.modelClient
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


package com.mytalk.server.logic.shared.modelClient;



public class WorldPersonalData {
private PersonalData pd;
	
	public WorldPersonalData(PersonalData pdEntity){
		pd=pdEntity;
	}
	
	public PersonalData getPersonalData(){
		return pd;
	}
	
	public void setPersonalData(PersonalData personalData){
		pd=personalData;
	}
}
