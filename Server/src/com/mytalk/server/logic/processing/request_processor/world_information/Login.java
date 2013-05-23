/**
* Filename: Login.java
* Package: com.mytalk.server.logic.processing.request_processor.world_information
* Author: Nicolò Toso
* Date: 2013-05-02
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-05-02 |   NT      | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.logic.processing.request_processor.world_information;

import java.util.ArrayList;
import java.util.List;

import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.WorldPack;
import com.mytalk.server.logic.shared.model_client.*;

import com.mytalk.server.logic.processing.request_processor.*;
import com.mytalk.server.data.model.ListName;
import com.mytalk.server.data.model.OnlineUser;
import com.mytalk.server.exceptions.*;

public class Login extends GenericRequest{
	
		public Login(){}
		
		public ARI manage(ARI ari){
			ARI response=null;
			Authentication auth=ari.getAuth();
			WorldPack responsePack=null;
			boolean checkAuth=checkAuthenticationWellFormed(auth);
			if(!checkAuth){
				response=new ARI(null,"CorruptedPack",null);
			}else{
				com.mytalk.server.data.model.User user=new com.mytalk.server.data.model.User(auth.getUser(),auth.getPwd(),null,null,null,null);
				OnlineUser o=new OnlineUser(ari.getAuth().getUser(), ari.getAuth().getIp());
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
				List<com.mytalk.server.data.model.User> blackListUserServer=null;
				List<Integer> blackListInteger=new ArrayList<Integer>();
				try{
					da.login(o, user);
					
					listOnlineServer=da.getOnlineUsers(user);
					listOfflineServer=da.getOfflineUsers(user);
					for(int i=0;i<listOfflineServer.size();i++){
						userServer=listOfflineServer.get(i);
						userClient=new User(userServer.getId(),userServer.getUsername(),userServer.getName(),userServer.getSurname(),userServer.getEmailHash() ,null,false);
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
					
					//aggiunta liste utente
					listName=da.userLists(user);
					for(int k=0;k<listName.size();k++){
						list=da.getListUsers(listName.get(k), user);
						for(int z=0;z<list.size();z++){
							listInteger.add(list.get(z).getId());
						}
						userList=new UserList(listName.get(k).getId(),listName.get(k).getName(),listInteger);
						listUserlist.add(userList);
					}
					
					//aggiunta blacklist
					blackListUserServer=da.getUserBlacklist(user);
					for(int x=0;x<blackListUserServer.size();x++){
						blackListInteger.add(blackListUserServer.get(x).getId());
					}
					userList=new UserList(1,"BlackList",blackListInteger);
					listUserlist.add(userList);
					
					WorldList worldList=new WorldList(listUserlist,listAllUsersClient);
					String myIp=da.getUserIp(myData.getUsername());
					PersonalData pd=new PersonalData(myData.getId(),myData.getUsername(),myData.getPassword(),myData.getName(),myData.getSurname(),myData.getEmail(),myData.getEmailHash(),myIp);
					WorldPersonalData worldPersonalData=new WorldPersonalData(pd);
					
					responsePack=new WorldPack(worldList,worldPersonalData);
					String responsePackString=conv.convertJavaToJson(responsePack);
					
					response=new ARI(null, "SuccessfulLogin", responsePackString);
				} catch(AuthenticationFail af){
					response=new ARI(null, "AuthenticationFailLogin", null);
				} catch (UsernameNotCorresponding e) {
					response= new ARI(null, "UsernameNotCorrespondingLogin",null);
				}catch (IpNotLogged e) {
					response= new ARI(null, "IpNotLoggedLogin",null);
				} catch (UserAlreadyLogged e) {
					response= new ARI(null, "UserAlreadyLoggedLogin",null);
				} catch (IpAlreadyLogged e) {
					response= new ARI(null, "IpAlreadyLoggedLogin",null);
				} catch (UserNotLogged e) {
					response= new ARI(null,"UserNotLoggedLogin",null);
				}
			}
			return response;
			
		}
}
