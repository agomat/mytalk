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

import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.WorldPack;
import com.mytalk.server.logic.shared.modelClient.PersonalData;
import com.mytalk.server.data.model.*;
import com.mytalk.server.logic.processing.requestProcessor.*;
import com.mytalk.server.exceptions.*;

public class Login extends GenericRequest{
	
		public Login(){}
		
		public ARI manage(ARI ari){
			String i=ari.getInfo();
			ARI response=null;
			WorldPack x=(WorldPack)conv.convertJsonToJava(i, WorldPack.class);
			PersonalData p=x.getPersonalData();
			OnlineUser o=new OnlineUser(p.getUsername(), p.getIp());
			com.mytalk.server.data.model.User u=new com.mytalk.server.data.model.User(p.getUsername(), p.getPassword(), p.getName(), p.getSurname(), p.getEmail());
			try{
				da.login(o, u);
				response=new ARI(ari.getAuth(), "SuccessfulLogin", null);
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
			}
			
			return response;
			
		}
}
