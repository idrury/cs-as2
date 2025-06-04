package cs_assignment_2;

import java.util.ArrayList;
import java.util.HashMap;

public class ECA {
	private ArrayList<String> binaryRuleNumber;
	private int generations;
	private String cells;
	
	/***************************************
	 * Initate the ECA
	 * @param ruleNumber The rule number to initiate with
	 * @param generations The number of iterations to run
	 * @param The string of cells
	 */
	public ECA(int ruleNumber, int generations, String cells) {
		this.generations = generations;
		this.cells = cells;
		
		this.binaryRuleNumber = convertNumberToBinary(ruleNumber);	
	}
	

	/**********************************************
	 * Convert the rule number to it's binary array version
	 * @param number The rule number in base10 form
	 * @return An array list with the binary version of the number
	 */
	private ArrayList<String> convertNumberToBinary(int number) {
		String binary = Integer.toBinaryString(number);
		ArrayList<String> returnList = new ArrayList<String>();
		int offset = 8-binary.length();
		
		// Pad the binary string
		for(int i=7; i>=0; i--) {
			if(offset-i > 0)
				returnList.add("0");
			else
				returnList.add(String.valueOf(binary.charAt(i-offset)));
		}
		
		return returnList;
	}
	
	/**************************************************
	 * Perform the rule number for the given number of generations
	 * @return A string containing all the rule numbers delineated 
	 * by new lines
	 */
	private String performRuleForGenerations() {
		String currentCells = cells;
		String result = cells.concat("\n");
		
		for(int i=0; i<generations; i++) {
			currentCells = performRule(currentCells);
			result = result.concat(currentCells).concat("\n");
		}
		
		return result;
	}
	
	/*********************************************
	 * Loop through all cells and apply the rule for one generation
	 * @param cells The cells to apply the rule to
	 * @return A string with the result of applying the rule to each cell
	 */
	private String performRule(String cells) {
		String result = "";

		for(int i=0; i<cells.length(); i++) {
			if(i==0) 
				result = result.concat(getValueOfSubstring(cells.substring(cells.length()-1).concat(cells.substring(i,2))));
			else if (i==cells.length()-1) 
				result = result.concat(getValueOfSubstring(cells.substring(i-1).concat(cells.substring(0,1))));
			else 
				result = result.concat(getValueOfSubstring(cells.substring(i-1, i+2)));
		}		
		
		return result;
	}
	
	/*********************************************
	 * Get the value of a substring of cells
	 * @param subset A subset of the string relating to the rule
	 * @return The result of comparing the substring to the rule
	 */
	private String getValueOfSubstring(String subset) {
		int position = Integer.parseInt(subset, 2);
		return binaryRuleNumber.get(position);
	}
	
	/************************************
	 * Get the transitions of the ECA
	 * @return A HashMap containing the transitions
	 */
	public HashMap<String, String> getTransitions() {
		HashMap<String, String> returnMap = new HashMap<String, String>();
		
		for(int i=0; i<binaryRuleNumber.size(); i++) {
			String binary = Integer.toBinaryString(i);
			String value = binaryRuleNumber.get(i);			
			binary = "0".repeat(3-binary.length()).concat(binary);
			returnMap.put(binary, value);
			//System.out.println(binary + " --> " + value);
		}
		return returnMap;
	}
	
	
	/*************************************************
	 * Perform the rule for the number of generations
	 * and return the result
	 * @return
	 */
	public String toString() {
		return performRuleForGenerations().replace('0', '.').replace("1", "X");
	}
}
