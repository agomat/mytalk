/**
* Filename: GenericRequest.java
* Package: com.mytalk.server.logic.processing.request_processor
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

package com.mytalk.server.logic.processing.request_processor;

import java.util.List;
import com.mytalk.server.data.storage.DataAccess;
import com.mytalk.server.data.storage.IDataAccess;
import com.mytalk.server.logic.shared.*;
import com.mytalk.server.logic.shared.model_client.UserList;
import com.mytalk.server.logic.processing.Convert;

public abstract class GenericRequest {
	
	protected static IDataAccess da=new DataAccess();
	
	protected static Convert conv=new Convert();
	
	public GenericRequest(){};
	
	public abstract ARI manage(ARI pack);
	
	public  static boolean checkAuthenticationWellFormed(Authentication auth){
		boolean check=false;
		if(auth!=null && auth.getUser()!=null && auth.getPwd()!=null){
			check=true;
		}
		return check;
	}
	
	public static boolean checkConnectionPackWellFormed(ConnectionPack pack){
		boolean check=false;
		if(pack!=null && pack.getMyIp()!=null && pack.getSpeakerIp()!=null && pack.getSdpCall()!=null && pack.getMyUserId()!=null && pack.getSpeakerUserId()!=null){
			check=true;
		}
		return check;
	}
	
	public static boolean checkAnonymousConnectionPackWellFormed(ConnectionPack pack){
		boolean check=false;
		if(pack!=null && pack.getMyIp()!=null && pack.getSpeakerIp()!=null && pack.getSdpCall()!=null){
				check=true;
		}
		return check;
	}

	public static boolean checkListPackWellFormed(ListPack pack){
		boolean check=false;
		
		if(pack!=null && pack.getList()!=null){
			List<UserList> listUserList=pack.getList();
			if(listUserList.get(0)!=null && listUserList.get(0).getList()!=null){
				check=true;
			}	
		}
		return check;
	}
	
	public static boolean checkPartialListPackWellFormed(ListPack pack){
		boolean check=false;		
		if(pack!=null && pack.getList()!=null && pack.getList().size()!=0){
			check=true;
		}
		return check;
	}
	
	public static boolean checkGiveCallPackWellFormed(GiveCallPack pack){
		boolean check=false;
		if(pack!=null && pack.getWrapperCall()!=null && pack.getWrapperCall().getList()!=null && pack.getWrapperCall().getList().size()!=0){
			check=true;
		}
		return check;
	}
	
	public static boolean checkWorldPackWellFormed(WorldPack pack){
		boolean check=false;
		if(pack!=null && pack.getWorldPersonalData()!=null && pack.getWorldPersonalData().getPersonalData()!=null){
			check=true;
		}
		return check;
	}
	
	public static boolean checkUpdateListPackWellFormed(UpdateListPack pack){
		boolean check=false;
		if(pack!=null && pack.getListName()!=null && pack.getNewListName()!=null && pack.getOwner()!=null){
			check=true;
		}
		return check;
	}
	
	public static boolean checkPartialUpdateListPackWellFormed(UpdateListPack pack){
		boolean check=false;
		if(pack!=null && pack.getListName()!=null && pack.getOwner()!=null){
			check=true;
		}
		return check;
	}

}
