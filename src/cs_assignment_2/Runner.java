package cs_assignment_2;
import java.util.HashMap;
import java.util.Scanner; 

public class Runner {
	Scanner scanner = new Scanner(System.in);
	ECA eca;
	
	public Runner() {
		
	}
	
	public void start() {
		boolean quit = false;
		System.out.println(
		"Author: Isaac Drury\r\n" +
		"Student ID: 110373289\r\n" +
		"Email ID: druij001\r\n" +
		"This is my own work as defined by the University's Academic Misconduct Policy.\n\n\n");
		
		try {
			while (quit == false)
				takeInput();
		} catch (Error e) {
			System.out.println("\n!!quitting!!\n");
			quit = true;
		}
	}
	
	
	/*******************************
	 * Handles the process of getting input from the user
	 */
	private void takeInput() {
		int ruleNumber = -1;
		int numGenerations = -1;
		int isArray = -1;
		String inputCells = "";
		
		// Get rule number from user
		while(ruleNumber == -1)
			try {
				System.out.println("Enter a rule number or presss Q to quit.");
				String input = this.scanner.nextLine().strip();
				
				// quit if q
				if (input.toLowerCase().equals("q")) throw new Error();
				
				ruleNumber = getNumberInput(input, 255);
			} catch(NumberFormatException e) {
				System.out.println("\nThe number must be between 1 and 255. Try again!\n");
			}
		
		// Get number of generations from user
		while (numGenerations == -1)
			try {
				System.out.println("How many generations?");
				numGenerations = getNumberInput(this.scanner.nextLine().strip(), 0, 50);
			} catch( NumberFormatException e) {
				System.out.println("\nThe number of generations must be between 1 and 50. Try again!\n");
			}
		
		
		// Prompt for array or single number
		while (isArray == -1 ) {
			try {
				System.out.println("Would you like to enter an array for input cells (Y/n)?");
				String input = this.scanner.nextLine().strip();
				System.out.println(input);
				
				if(input.toLowerCase().equals("y")) isArray = 1;
				else if(input.toLowerCase().equals("n")) isArray = 0;
				else throw new NumberFormatException("error");
			} catch( NumberFormatException e) {
				System.out.println("\nPlease enter y or n. Try again!\n");
			}
		}
		
		// Get input cells from user
		while (inputCells.length() == 0)
			try {
				if(isArray == 1) {
					System.out.println("Enter input cells array: ");
					inputCells = getInputCells(this.scanner.nextLine().strip(), true);
				} else {
					System.out.println("Enter input length: ");
					inputCells = getInputCells(this.scanner.nextLine().strip(), false);

				}
				System.out.println(inputCells);
			} catch( NumberFormatException e) {
				System.out.println("\n" + e.getMessage() + "\n");
			}
		
		// Initialise the ECA
		this.eca = new ECA(ruleNumber, numGenerations, inputCells);		
		System.out.println(eca.toString());
		
		// Do FST stuff
		performFST(numGenerations, inputCells);
					
	}
	
	/*********************************************
	 * Perform FST calculations based off rule 169
	 * @param numGenerations The number of generations to transition
	 * @param inputCells The starting input cells
	 */
	public void performFST(int numGenerations, String inputCells) {
		// Create list of transitions based off rule 169
		HashMap<String, String> transitions = new HashMap<String, String>();
		transitions.put("000", "1");
		transitions.put("011", "1");
		transitions.put("110", "0");
		transitions.put("001", "0");
		transitions.put("100", "0");
		transitions.put("111", "1");
		transitions.put("101", "1");
		transitions.put("010", "0");
		
		FST fst = new FST(transitions, numGenerations, inputCells);
		fst.printTransitions();		
		System.out.println(fst.toString());
	}
	
	/****************************************************
	 * Handles the process of getting a number from the user
	 * @param prompt The text to prompt the user input with
	 * @param upperLimit The upper limit which the number is allowed to be
	 */
	private int getNumberInput(String input, int lowerLimit, int upperLimit) throws NumberFormatException {		
		int number = Integer.parseInt(input);
	   
	   if(number <= lowerLimit || number > upperLimit) 
		   throw new NumberFormatException("The number must be between " + lowerLimit + " and "+upperLimit);
	   
	   return number;
	}
	
	/****************************************************
	 * Handles the process of getting a number from the user
	 * @param prompt The text to prompt the user input with
	 * @param upperLimit The upper limit which the number is allowed to be
	 */
	private int getNumberInput(String input, int upperLimit) throws NumberFormatException {		
		return getNumberInput(input, 0, upperLimit);
	}
	
	/****************************************************
	 * Handles the process of getting a binary number from the user
	 * @param prompt The text to prompt the user input with
	 */
	private String getInputCells(String input, boolean isArray) throws NumberFormatException {
		
		if(isArray) {
			// if an array is entered
			try {
				if(input.length()<3) throw new NumberFormatException();
				return toBinaryString(input);
			} catch (NumberFormatException e) {
				throw new NumberFormatException("Please enter a valid binary array");
			}
			
		}
		
		// If a single number is entered
		try {
			int length = getNumberInput(input, 2, 50);
			String returnList = "";
			for(int i=0; i<length; i++) {
				if(i == Math.floor(length/2))
					returnList = returnList.concat("1");
				else 
					returnList = returnList.concat("0");
			}
			return returnList;
			
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Please enter a number between 3 and 50");
		}
		
	}
	
	/*******************************************************
	 * Create a binary number from a string representation
	 * @param str The binary number in string format
	 */
	public String toBinaryString(String str) throws NumberFormatException {
		int length = str.length();
		String binaryArray = "";
		
		// Convert the string into a binary array
		for(int i=0; i<length; i++) {
			if(str.charAt(i) == '0') binaryArray = binaryArray.concat("0");
			else if(str.charAt(i) == '1') binaryArray = binaryArray.concat("1");
			else throw new NumberFormatException("Not a binary number!\n");
		}
		
		return binaryArray;
	}

}
