package cs_assignment_2;

import java.util.HashMap;

public class FST {
	String cells;
	HashMap<String, String> transitions;
	int transitionLength = 3;
	int generations;
	
	public FST(HashMap<String, String> _transitions, int _generations, String _cells) {
		transitions = _transitions;
		cells = _cells;
		generations = _generations;
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

		// Create and calculate the substrings
		for(int i=0; i<cells.length(); i++) {
			if(i==0) 
				result = result.concat(getTransition(cells.substring(cells.length()-2).concat(cells.substring(i,1))));
			else if (i==1) 
				result = result.concat(getTransition(cells.substring(cells.length()-1).concat(cells.substring(i-1,2))));
			else 
				result = result.concat(getTransition(cells.substring(i-2, i+1)));
		}		
		
		return result;
	}
	
	/**********************************************
	 * Get the result of performing a transition
	 * @param transition
	 * @return
	 * @throws NumberFormatException
	 */
	private String getTransition(String transition) throws NumberFormatException {
		if(transition.length() != transitionLength) {
			throw new NumberFormatException("transition length of " + transition +" is " + transition.length() + ", but it should be " + transitionLength);
		}
		
		return transitions.get(transition);
	}
	
	/***************************************
	 * Print the map of transitions for the FST
	 */
	public void printTransitions() {
		transitions.forEach((name, result) -> System.out.println(name + " --> " + result) );
	}
	
	/****************************************************
	 * Perform the rule for the number of generations
	 * and return the result
	 * @return
	 */
	public String toString() {
		return performRuleForGenerations().replace('0', '.').replace("1", "X");
	}
	
}
