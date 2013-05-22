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

package com.mytalk.server.logic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.mytalk.server.logic.processing.request_processor.GenericRequestTest;
import com.mytalk.server.logic.processing.request_processor.communication.AcceptCallTest;
import com.mytalk.server.logic.processing.request_processor.communication.RefuseCallTest;
import com.mytalk.server.logic.processing.request_processor.communication.UserCallTest;
import com.mytalk.server.logic.processing.request_processor.list.BlackListAddTest;
import com.mytalk.server.logic.processing.request_processor.list.BlackListRemoveTest;
import com.mytalk.server.logic.processing.request_processor.list.ListCreateTest;
import com.mytalk.server.logic.processing.request_processor.list.ListDeleteTest;
import com.mytalk.server.logic.processing.request_processor.list.ListUserAddTest;
import com.mytalk.server.logic.processing.request_processor.list.ListUserRemoveTest;
import com.mytalk.server.logic.processing.request_processor.list.UpdateListNameTest;
import com.mytalk.server.logic.processing.request_processor.stats.AddCallTest;
import com.mytalk.server.logic.processing.request_processor.stats.GetCallsTest;
import com.mytalk.server.logic.processing.request_processor.user.CreateAccountTest;
import com.mytalk.server.logic.processing.request_processor.user.DeleteAccountTest;
import com.mytalk.server.logic.processing.request_processor.user.LoginAsAnonymousTest;
import com.mytalk.server.logic.processing.request_processor.user.LoginTest;
import com.mytalk.server.logic.processing.request_processor.user.LogoutTest;
import com.mytalk.server.logic.processing.request_processor.user.LogoutToAnonymousTest;
import com.mytalk.server.logic.processing.request_processor.user.StateUpdateTest;

@RunWith(Suite.class)
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
