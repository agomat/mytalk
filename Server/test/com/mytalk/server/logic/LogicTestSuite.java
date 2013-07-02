/**
 * Filename: LogicTestSuite.java
 * Package: com.mytalk.server.logic
 * Author: Nicolo' Mazzucato
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
import com.mytalk.server.logic.processing.request_processor.connection_information.AcceptCallTest;
import com.mytalk.server.logic.processing.request_processor.connection_information.RefuseCallTest;
import com.mytalk.server.logic.processing.request_processor.connection_information.UserCallTest;
import com.mytalk.server.logic.processing.request_processor.empty_information.DeleteAccountTest;
import com.mytalk.server.logic.processing.request_processor.empty_information.LogoutTest;
import com.mytalk.server.logic.processing.request_processor.empty_information.LogoutToAnonymousTest;
import com.mytalk.server.logic.processing.request_processor.give_call_information.AddCallTest;
import com.mytalk.server.logic.processing.request_processor.give_call_information.GetCallsTest;
import com.mytalk.server.logic.processing.request_processor.list_information.BlackListAddTest;
import com.mytalk.server.logic.processing.request_processor.list_information.BlackListRemoveTest;
import com.mytalk.server.logic.processing.request_processor.list_information.ListUserAddTest;
import com.mytalk.server.logic.processing.request_processor.list_information.ListUserRemoveTest;
import com.mytalk.server.logic.processing.request_processor.state_update_information.StateUpdateTest;
import com.mytalk.server.logic.processing.request_processor.update_list_information.ListCreateTest;
import com.mytalk.server.logic.processing.request_processor.update_list_information.ListDeleteTest;
import com.mytalk.server.logic.processing.request_processor.update_list_information.UpdateListNameTest;
import com.mytalk.server.logic.processing.request_processor.world_information.CreateAccountTest;
import com.mytalk.server.logic.processing.request_processor.world_information.LoginAsAnonymousTest;
import com.mytalk.server.logic.processing.request_processor.world_information.LoginTest;

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
