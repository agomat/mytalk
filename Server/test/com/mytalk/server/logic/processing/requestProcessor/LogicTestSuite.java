/**
 * Filename: DataAccessTest.java
 * Package: com.mytalk.server.data.storage
 * Author: Nicolò Mazzucato
 * Date: 2013-05-22
 *
 * Diary:
 * Version | Date       | Developer | Changes
 * --------+------------+-----------+------------------
 * 0.1	   | 2013-05-22 |    NM     | [+] Inserimento classe e metodi   
 *
 * This software is distributed under GNU/GPL 2.0.
 *
 * Software licensed to:
 * - Zucchetti SRL
 */

package com.mytalk.server.logic.processing.requestProcessor;

import org.junit.runners.Suite;

import com.mytalk.server.logic.processing.requestProcessor.communication.AcceptCallTest;
import com.mytalk.server.logic.processing.requestProcessor.communication.RefuseCallTest;
import com.mytalk.server.logic.processing.requestProcessor.communication.UserCallTest;
import com.mytalk.server.logic.processing.requestProcessor.list.BlackListAddTest;
import com.mytalk.server.logic.processing.requestProcessor.list.BlackListRemoveTest;
import com.mytalk.server.logic.processing.requestProcessor.list.ListCreateTest;
import com.mytalk.server.logic.processing.requestProcessor.list.ListDeleteTest;
import com.mytalk.server.logic.processing.requestProcessor.list.ListUserAddTest;
import com.mytalk.server.logic.processing.requestProcessor.list.ListUserRemoveTest;
import com.mytalk.server.logic.processing.requestProcessor.list.UpdateListNameTest;
import com.mytalk.server.logic.processing.requestProcessor.stats.AddCallTest;
import com.mytalk.server.logic.processing.requestProcessor.stats.GetCallsTest;
import com.mytalk.server.logic.processing.requestProcessor.user.CreateAccountTest;
import com.mytalk.server.logic.processing.requestProcessor.user.DeleteAccountTest;
import com.mytalk.server.logic.processing.requestProcessor.user.LoginAsAnonymousTest;
import com.mytalk.server.logic.processing.requestProcessor.user.LoginTest;
import com.mytalk.server.logic.processing.requestProcessor.user.LogoutTest;
import com.mytalk.server.logic.processing.requestProcessor.user.LogoutToAnonymousTest;
import com.mytalk.server.logic.processing.requestProcessor.user.StateUpdateTest;

@Suite.SuiteClasses({
	   GenericRequestTest.class,
	   CreateAccountTest.class,
	   DeleteAccountTest.class,
	   LoginAsAnonymousTest.class,
	   LoginTest.class,
	   LogoutToAnonymousTest.class,
	   LogoutTest.class,
	   StateUpdateTest.class,
	   AddCallTest.class,
	   GetCallsTest.class,
	   BlackListAddTest.class,
	   BlackListRemoveTest.class,
	   ListCreateTest.class,
	   ListDeleteTest.class,
	   ListUserAddTest.class,
	   ListUserRemoveTest.class,
	   UpdateListNameTest.class,
	   AcceptCallTest.class,
	   RefuseCallTest.class,
	   UserCallTest.class
	})
public class LogicTestSuite {

}
