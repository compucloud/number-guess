//======================================================================================
// Copyright Good Code Inc.
//
// Distributed under the Apache License Version 2.0.
// http://choosealicense.com/licenses/apache-2.0/
//======================================================================================
package com.goodcode.guess;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for NumberGuesser. Test that it guesses numbers correctly and displays
 * error messages for invalid input.
 * 
 * @author Jevon Gill
 */
public class NumberGuesserTest 
    extends TestCase
{
	/*Messages*/
    private static final String MSG_UNIT_TEST_OUTPUT = "Unit Test Output";

	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public NumberGuesserTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( NumberGuesserTest.class );
    }

    /**
     * Test the play method. Test to see if the NumberGuesser guesses correctly for 
     * three numbers.
     */
    public void testPlay(){    	
    	
    	// 1) Choose numbers
    	int chosenNumber1 = 1;
    	int chosenNumber2 = 232;
    	int chosenNumber3 = 1000;
    	// 2) Redirect InputStream used to a test byteArrayInputStream where we have test data
    	String testUserInput = "ready l l l l l l l l l l y ready h h h h h h h h h y ready l l h h h l h h l y e";
    	InputStream stdin = new ByteArrayInputStream(testUserInput.getBytes());  
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);
        // 3) Invoke game play
    	NumberGuesser guesser = new NumberGuesser(1000,stdin);
    	guesser.play(); 
    	
    	// 4) Flush output stream
    	System.out.flush();    	
    	
    	// 5) Perform Test    	
    	assertTrue(baos.toString().contains(String.format(NumberGuesser.MSG_GUESSED_CORRECTLY, chosenNumber1)));
    	assertTrue(baos.toString().contains(String.format(NumberGuesser.MSG_GUESSED_CORRECTLY, chosenNumber2)));
    	assertTrue(baos.toString().contains(String.format(NumberGuesser.MSG_GUESSED_CORRECTLY, chosenNumber3)));
    	
    	// 6) Set input stream back and print out game output stream to the console.
    	System.setOut(old);
    	System.out.println(MSG_UNIT_TEST_OUTPUT + baos.toString());
   	
    }
    
    /**
     * Test the play method, Check if game displays messages appropriately for errors.
     */
    public void testPlay_IncorrectInput(){  
    	
    	// 1) set max guess 
    	int maxGuessNumber = 1000; 
      	// 2) Redirect InputStream used to a test byteArrayInputStream where we have test data
    	String testUserInput = "hey ready now e";
		InputStream stdin = new ByteArrayInputStream(testUserInput.getBytes());  
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);            	
		// 3) Invoke game play
        NumberGuesser guesser = new NumberGuesser(maxGuessNumber,stdin);
    	guesser.play(); 
    	System.out.flush(); 
    	// 4) Perform test
    	assertTrue(baos.toString().contains(NumberGuesser.MSG_VALIDATION_READY));
    	assertTrue(baos.toString().contains(NumberGuesser.MSG_VALIDATION_INVALID_INPUT));   	
    	// 5) Set input stream back and print out game output stream to the console.
    	System.setOut(old);
    	System.out.println(MSG_UNIT_TEST_OUTPUT + baos.toString()); 
    	
    }
}
