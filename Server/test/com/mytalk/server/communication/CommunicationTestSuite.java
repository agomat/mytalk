/**
 * Filename: CommunicationTestSuite.java
 * Package: com.mytalk.server.communication
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

package com.mytalk.server.communication;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.mytalk.server.communication.buffer.BufferTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	   BufferTest.class
	})
public class CommunicationTestSuite {

}
