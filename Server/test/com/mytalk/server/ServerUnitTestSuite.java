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


package com.mytalk.server;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.mytalk.server.communication.CommunicationTestSuite;
import com.mytalk.server.data.DataTestSuite;
import com.mytalk.server.logic.LogicTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	   LogicTestSuite.class,
	   CommunicationTestSuite.class,
	   DataTestSuite.class
	})
public class ServerUnitTestSuite {

}
