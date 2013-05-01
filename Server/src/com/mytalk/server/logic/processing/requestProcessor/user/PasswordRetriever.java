package com.mytalk.server.logic.processing.requestProcessor.user;

import com.mytalk.server.logic.processing.requestProcessor.GenericRequest;
import com.mytalk.server.logic.shared.*;

public class PasswordRetriever extends GenericRequest {

	@Override
	public ARI manage(ARI pack) {
		Authentication aut=pack.getAuth();
	}

}
