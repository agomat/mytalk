/**
* Filename: Login.java
* Package: com.mytalk.server.logic.processing.requestProcessor.user
* Author: 
* Date:
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	           |           | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.logic.processing.requestProcessor.user;

import java.util.ArrayList;
import java.util.List;

import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.WorldPack;
import com.mytalk.server.logic.shared.modelClient.*;

import com.mytalk.server.logic.processing.requestProcessor.*;
import com.mytalk.server.data.model.ListName;
import com.mytalk.server.data.model.OnlineUser;
import com.mytalk.server.exceptions.*;

public class Login extends GenericRequest{
	
		public Login(){}
		
		public ARI manage(ARI ari){
			String infoRequest=ari.getInfo();
			ARI response=null;
			WorldPack responsePack=null;
			WorldPack pack=(WorldPack)conv.convertJsonToJava(infoRequest, WorldPack.class);
			boolean checkPack=this.checkWorldPackWellFormed(pack);
			if(!checkPack){
				response=new ARI(null,"CorruptedPack",null);
			}else{
				
				PersonalData p=pack.getWorldPersonalData().getPersonalData();
				com.mytalk.server.data.model.User user=new com.mytalk.server.data.model.User(p.getUsername(),p.getPassword(),p.getEmail(),p.getName(),p.getSurname(),p.getMd5());
				OnlineUser o=new OnlineUser(p.getUsername(), p.getIp());
				List<com.mytalk.server.data.model.User> listOnlineServer=null;
				List<com.mytalk.server.data.model.User> listOfflineServer=null;
				List<User> listAllUsersClient=new ArrayList<User>();
				User userClient=null;
				com.mytalk.server.data.model.User userServer=null;
				List<ListName> listName=null;
				List<UserList> listUserlist= new ArrayList<UserList>();
				UserList userList=null;
				List<com.mytalk.server.data.model.User> list=null;
				List<Integer> listInteger=new ArrayList<Integer>();
				String ip_user=null;
				com.mytalk.server.data.model.User myData=null;
				try{
					da.login(o, user);
					listOfflineServer=da.getOnlineUsers(user);
					listOnlineServer=da.getOfflineUsers(user);
					for(int i=0;i<listOfflineServer.size();i++){
						userServer=listOfflineServer.get(i);
						ip_user=da.getUserIp(userServer.getUsername());
						userClient=new User(userServer.getId(),userServer.getUsername(),userServer.getName(),userServer.getSurname(),userServer.getEmailHash() ,ip_user,false);
						listAllUsersClient.add(userClient);
					}
					for(int j=0;j<listOnlineServer.size();j++){
						userServer=listOnlineServer.get(j);
						if(userServer.getUsername().equals(user.getUsername())){
							myData=userServer;
						}
						ip_user=da.getUserIp(userServer.getUsername());
						userClient=new User(userServer.getId(),userServer.getUsername(),userServer.getName(),userServer.getSurname(),userServer.getEmailHash(),ip_user,true);
						listAllUsersClient.add(userClient);
					}
					listName=da.userLists(user);
					for(int k=0;k<listName.size();k++){
						list=da.getListUsers(listName.get(k), user);
						for(int z=0;z<list.size();z++){
							listInteger.add(list.get(z).getId());
						}
						userList=new UserList(listName.get(k).getId(),listName.get(k).getName(),listInteger);
						listUserlist.add(userList);
					}
					
					WorldList worldList=new WorldList(listUserlist,listAllUsersClient);
					String myIp=da.getUserIp(myData.getUsername());
					PersonalData pd=new PersonalData(myData.getUsername(),myData.getPassword(),myData.getName(),myData.getSurname(),myData.getEmail(),myData.getEmailHash(),myIp);
					WorldPersonalData worldPersonalData=new WorldPersonalData(pd);
					
					responsePack=new WorldPack(worldList,worldPersonalData);
					String responsePackString=conv.convertJavaToJson(responsePack);
					
					response=new ARI(ari.getAuth(), "SuccessfulLogin", responsePackString);
				} catch(AuthenticationFail af){
					response=new ARI(null, "AuthenticationFail", null);
				} catch (UsernameNotCorresponding e) {
					response= new ARI(null, "UsernameNotExisting",null);
				} catch (IpNotLogged e) {
					response= new ARI(null, "IpNotLogged",null);
				} catch (UserAlreadyLogged e) {
					response= new ARI(null, "UserAlreadyLogged",null);
				} catch (IpAlreadyLogged e) {
					response= new ARI(null, "IpAlreadyLogged",null);
				} catch (UserNotLogged e) {
					response= new ARI(null,"UserNotLogged",null);
				}
			}
			return response;
			
		}
}
