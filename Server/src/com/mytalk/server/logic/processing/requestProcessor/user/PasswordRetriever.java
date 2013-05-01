package com.mytalk.server.logic.processing.requestProcessor.user;

import com.mytalk.server.logic.processing.requestProcessor.GenericRequest;
import com.mytalk.server.logic.shared.*;
import com.mytalk.server.data.model.*;
import com.mytalk.server.logic.shared.modelClient.*;
import com.mytalk.server.exceptions.AuthenticationFail;

public class PasswordRetriever extends GenericRequest {

	@Override
	public ARI manage(ARI pack) {
		Authentication aut=pack.getAuth();
		com.mytalk.server.data.model.User toAuth=new com.mytalk.server.data.model.User(aut.getUser(),aut.getPwd(),null);
		InfoPack info=(InfoPack) conv.convertJsonToJava(pack.getInfo(), InfoPack.class);
		
		AdditionalData ad=info.getAdditional();
		ForgottenPassword newPass=new ForgottenPassword(ad.getUser(),ad.getPassword());
		ARI response=null;
		try{
			da.passwordRetriever(newPass, toAuth);
			response=new ARI();
		}catch(AuthenticationFail ecc){
			response=new ARI(null,"AuthenticationFail",null);
		}
		return response;
	}

}
