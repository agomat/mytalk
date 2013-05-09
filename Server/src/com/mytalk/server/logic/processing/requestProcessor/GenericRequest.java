/**
* Filename: GenericRequest.java
* Package: com.mytalk.server.logic.processing
* Author: Nicolò Toso
* Date: 2013-04-29
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-04-29 |    NT     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.logic.processing.requestProcessor;

import java.util.List;

import com.mytalk.server.logic.shared.*;
import com.mytalk.server.logic.shared.modelClient.UserList;
import com.mytalk.server.logic.shared.modelClient.WrapperUserList;

import com.mytalk.server.data.storage.*;
import com.mytalk.server.logic.processing.Convert;

public abstract class GenericRequest {
	
	protected static IDataAccess da=new DataAccess();
	
	protected static Convert conv=new Convert();
	
	public GenericRequest(){};
	
	public abstract ARI manage(ARI pack);
	
	public boolean checkAuthenticationWellFormed(Authentication auth){
		boolean check=false;
		if(auth!=null && auth.getUser()!=null && auth.getPwd()!=null){
			check=true;
		}
		return check;
	}
	
	public boolean checkConnectionPackWellFormed(ConnectionPack pack){
		boolean check=false;
		if(pack!=null && pack.getMyIp()!=null && pack.getSpeakerIp()!=null && pack.getSdpCall()!=null){
			check=true;
		}
		return check;
	}

	public boolean checkListPackWellFormed(ListPack pack){
		boolean check=false;
		WrapperUserList wl=pack.getWrapperUserList();
		List<UserList> listUserList=wl.getList();
		if(pack!=null && wl!=null && listUserList!=null){
			for(int i=0;i<listUserList.size();i++){
				if(listUserList.get(i).getList()!=null){
					check=true;
				}
				else{
					return false;
				}
			}
			
		}
		return check;
	}
}
