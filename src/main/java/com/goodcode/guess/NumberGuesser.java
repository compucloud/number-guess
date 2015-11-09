//======================================================================================
// Copyright Good Code Inc.
//
// Distributed under the Apache License Version 2.0.
// http://choosealicense.com/licenses/apache-2.0/
//======================================================================================
package com.goodcode.guess;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import com.goodcode.guess.utils.GuesserUtils;

/**
 * A number guessing game.  The user chooses a number in his mind and types “ready” to indicate
 * to the computer that he is ready to begin playing. The computer asks a series of questions to 
 * arrive at the number the user has in mind. The user can only respond with 'h','l','y' or 'e'. 
 * h = higher, l = lower, y = yes, e = end. The game ends when the user responds with “yes” or “end”.
 * 
 * Program uses a binary search approach, using the halfway point between the highest and lowest number
 * guessed so that it arrives to the answer with the shortest number of questions on average.
 * 
 * A guessing range minimum and maximum has been put in place to prevent the user from having the computer
 * try to guess numbers too large for memory. The guesser maximum can be increased to any number that can fit
 * into a primitive int data type through the the NumberGuesser(int max) constructor.
 * 
 * Error Code Definitions
 * 1000 - Unable to load header. System cannot find the header file in the classpath.
 * 
 * @author Jevon Gill
 */
public class NumberGuesser 
{	
	/* Constants for User Responses */	
	private static final String RESPONSE_LOWER = "l";
	private static final String RESPONSE_HIGHER = "h";
	private static final String RESPONSE_YES = "y";	
	private static final String RESPONSE_EXIT = "e";
	private static final String RESPONSE_READY = "ready";
	/* Constants for Instructions, validation, confirmation, error and notification messages  */
	protected static final String MSG_GOODBYE = "program ending. goodbye...";
	protected static final String MSG_BEGIN = "Ok, let's begin! ";
	protected static final String MSG_COME_BACK_SOON = "Ok, game stopping. Come back to play soon!";
	protected static final String MSG_GUESS = "Is the number %d ? ";	
	protected static final String MSG_INSTRUCTIONS_LINE_3 = "Type 'ready' to begin or 'e' to exit :";
	protected static final String MSG_INSTRUCTIONS_LINE_2 = "I (the computer) will try to guess it. \n";
	protected static final String MSG_INSTRUCTIONS_LINE_1 = "INSTRUCTIONS: Choose a number in your mind "
			+ "from 1 to %d and write it down.";
	protected static final String MSG_VALIDATION_READY = "Please enter valid input, enter 'ready' to begin"
			+ " or 'e' to exit.";
	protected static final String MSG_VALIDATION_INVALID_INPUT = "Please enter valid input, only characters "
			+ "h, l, y, or e. (h='higher', l='lower', y='yes', e='exit' ) are allowed.";
	protected static final String MSG_LINE_SEPARATOR = "---------------------------------------------------------"
			+ "-----------------------";
	protected static final String MSG_GUESSED_CORRECTLY = "Awesome! I guessed correctly. "
			+ "I had a good feeling about the number %d :) \n";
	protected static final String MSG_INSTRUCTIONS_INPUT = "Please respond with h, l, y, or e. "
			+ "(h='higher', l='lower', y='yes', e='exit' ) :";
	protected static final String MSG_UNXPECTED_ERROR = "An error has occurred. Please contact 1-800-111-1111."
			+ " Provide the customer service representative with the following diagnoses code: %d";
	/* Constants for default settings  */
	private static final int ERROR_CODE_HEADER = 1000;
	private static final int LOW_HIGH_OFFSET_ONE = 1;	
	private static final int GUESSER_MAX_PLUS_TWO_PADDING = 2;
	private static final String PATH_GUESSER_HEADER_TXT = "/guesser-header.txt";	
	
	/* Attributes for maximum number that user can guess and InputStream to collect user input.  */
	private int guesserMax = 1000;
	private InputStream inStream;
	
	/**
	 * Constructor, sets the maximum number that the user can select. To keep
	 * the game fun and prevent user from choosing a number too big for memory.
	 * 
	 * @param maxNumber defines the maximum of the guess range.
	 */
	public NumberGuesser(int maxNumber){
		this.guesserMax = maxNumber + GUESSER_MAX_PLUS_TWO_PADDING;
		this.inStream = System.in;
	}
	
	/**
     * Constructor, sets the maximum number that the user can select. To keep
	 * the game fun and prevent user from choosing a number too big for memory.
	 * 
	 * Also allows for the that collects user input from the console to be redirected.
	 * 
	 * @param maxNumber defines the maximum of the guess range.
	 * @param inputStream inputStream that collects the user input.
	 */
	public NumberGuesser(int maxNumber, InputStream inputStream){
		this.guesserMax = maxNumber + GUESSER_MAX_PLUS_TWO_PADDING;
		this.inStream = inputStream;
	}
	
	/**
	 * Consist of games entry and exit points. Game loops until user enters y for yes or e for exit.
	 */
	public void play(){
		displayHeader();		
		Scanner console = new Scanner(this.inStream);		
		while(true){
			System.out.println(String.format(MSG_INSTRUCTIONS_LINE_1,(guesserMax - GUESSER_MAX_PLUS_TWO_PADDING)));
	    	System.out.println(MSG_INSTRUCTIONS_LINE_2); 
			System.out.println(MSG_INSTRUCTIONS_LINE_3);
			String userResponse = console.next();
			while(!userResponse.equals(RESPONSE_READY)){
				if(userResponse.equals(RESPONSE_EXIT)){
					break;
				}else{
					System.out.println(MSG_VALIDATION_READY);
					userResponse = console.next();
				}			
			}
			if(userResponse.equals(RESPONSE_READY)){				
				System.out.println(MSG_BEGIN);
				boolean exit  = playRound(console);
				if(exit){
					System.out.println(MSG_GOODBYE);
					return;
				};
			}else if(userResponse.equals(RESPONSE_EXIT)){
				System.out.println(MSG_GOODBYE);
				console.close();
				return;
			}
		}		
	}
	
	/**
	 * Invokes on round of play. Performs a guess using average of the lowest and highest numbers
	 * that it has already guessed. It starts with a default low of 0 and high = guesserMax. 
	 * Stops when the user types y or e.
	 * @ returns exit - indicates whether the user has indicated if that they want to
	 *                  exit the game. true to exit, false to continue playing.
	 *                  The default exit value is exit=false
	 */
	private boolean playRound(Scanner console) {	
		
    	String userResponse = new String(); 
    	boolean exit = false;
    	int low = 0;
    	int high = guesserMax;    
    	
    	while (!userResponse.equals(RESPONSE_YES) || (!!userResponse.equals(RESPONSE_EXIT))) {
    		int guess = (high + low) / 2;
    		System.out.println(String.format(MSG_GUESS, guess));
      		System.out.println(MSG_INSTRUCTIONS_INPUT);
    		userResponse = console.next();
    		switch(userResponse){
    		 	case RESPONSE_HIGHER: low = guess - LOW_HIGH_OFFSET_ONE;
    		 		break;
    		    case RESPONSE_LOWER: high = guess + LOW_HIGH_OFFSET_ONE;
    		    	break;
    		    case RESPONSE_YES: System.out.println(String.format(MSG_GUESSED_CORRECTLY, guess));
    		    		  System.out.println(MSG_LINE_SEPARATOR);      		
    			   	      break;	
    		    case RESPONSE_EXIT: System.out.println(MSG_COME_BACK_SOON);
		    		return true;
    		    default : System.out.println(MSG_VALIDATION_INVALID_INPUT);
    		    	break;
    		}
    	}  
    	return exit;
	}

	/**
	 * Display the header and instructions for the game to the user.
	 */
	private void displayHeader() {
		String headerText = new String();
    	try {
			headerText = GuesserUtils.readTxtFileToString(PATH_GUESSER_HEADER_TXT);
		} catch (IOException e1) {
			System.out.println(String.format(MSG_UNXPECTED_ERROR, ERROR_CODE_HEADER));			
			System.exit(0);
		}
    	System.out.println(headerText);    	   	
	}	
	
	/**
	 * Main method, entry point to the game.
	 * @param args
	 */
	public static void main( String[] args )
    {    
		int guesserMax = 1000;
		NumberGuesser guesser = new NumberGuesser(guesserMax);
    	guesser.play();
    }

}
