package project.GradeCalculator;

import java.util.Set;
import java.util.HashMap;

/*
 * The following class calculates your grade percentage
 * based on input
 *
 * The program uses both the weights of the types of assignments
 * the user inputs + all the assignments given by the user
 * (and their type) in order to calculate the user's grade
 *
 * The program outputs the percentage of the user's grade,
 * but not necessarily the specific letter grade. This is something
 * the user can easily figure out, so it is not necessary
 * to include this in the implementation (especially since
 * it'll require a good amount of work to implement
 * functionality for alternate grading systems).
 */
public class GradeCalculator
{
    /*
     * The following HashMap stores the weights
     * of assignments types in a given class
     *
     * Key --> The assignment type
     * Value --> The weight given to the assignment (a decimal percentage)
     */
    private final HashMap<String, Double> weights;
    /*
     * The following HashMap stores assignments and their type
     *
     * Key --> The assignment
     * Value --> The assignment type
     */
    private final HashMap<String, String> assignments;
    /*
     * The following HashMap stores assignments and the grade given on them
     *
     * This HashMap is closely associated with the assignments
     * HashMap, as the String key should be the same between them
     *
     * Key --> The assignment
     * Value --> The grade on the assignment
     */
    private final HashMap<String, Double> grades;
    /*
     * The following HashMap stores the amount of assignments
     * associated with each weight category
     *
     * Key --> The weight category
     * Value --> The # of assignments associated with that weight category
     */
    private final HashMap<String, Integer> weightCounts;

    /*
     * Assigns to the GradeCalculator
     * HashMaps based on user input in the GUI program
     */
    public GradeCalculator(HashMap<String, Double> weights, HashMap<String, String> assignments
    , HashMap<String, Double> grades, HashMap<String, Integer> weightCounts)
    {
        this.weights = weights;
        this.assignments = assignments;
        this.grades = grades;
        this.weightCounts = weightCounts;
    }

    /*
     * Checks if the HashMaps passed in are valid for the GradeCalculator
     *
     * Validity is determined by checking if all the values in
     * the "assignments" HashMap can be found as keys in the
     * "weights" HashMap
     */
    private boolean isValid()
    {
        //Create a keySet for the assignments
        Set<String> assignmentSet = assignments.keySet();
        /*
         * Traverse through the set; check if the
         * value for each assignment can be found
         * as a weight in the weights HashMap
         */
        for(String assignment : assignmentSet)
        {
            if(!weights.containsKey(assignments.get(assignment)))
            {
                return false;
            }
        }
        return true;
    }

    /*
        This method checks that all the categories are
        considered when the user had inputted in
        all their assignments; otherwise, it
        doesn't consider that category
     */
    private double totalPercentage()
    {
        double percentage = 0.0; //Holder variable
        //Loop through each category
        for(String category : weightCounts.keySet())
        {
            percentage += weights.get(category); //Add each weight
        }
        return percentage;
    }


    /*
     * Performs calculation of grade based on the
     * instance fields and the values passed into them
     */
    public double calculateGrade()
    {
        /*
         * Check validity and throw an exception
         * if the passed in values are invalid
         */
        if(!isValid())
        {
            throw new IllegalArgumentException("Some of the values you passed "
            + "in were invalid. Some of the assignment types you gave do not "
            + " correspond to any weight category.");
        }
        /*
         * The following HashMap is used to calculate
         * each individual grade sum for each weight category
         */
        HashMap<String, Double> gradeSums = new HashMap<String, Double>();
        //Initialize the gradeSums Map with the weight categories
        for(String weightCategory : weights.keySet())
        {
            gradeSums.put(weightCategory, 0.0);
        }
        /*
         * Traverse the assignment set, adding to the corresponding
         * grade sum the assignment's grade value
         */
        for(String assignment : assignments.keySet())
        {
            /*
             * Key --> The weight category is the value of the assignments Map
             * Value --> The updated grade is value of the weight category
             * (found by getting the assignment type from the
             * assignments Map and then getting the value of the assignment
             * type from the gradeSums Map) and adding the
             * current assignments value to the value
             */
            gradeSums.put(assignments.get(assignment), gradeSums.get
                    (assignments.get(assignment)) + grades.get(assignment));
        }
        //Following variable stores the sum of all grades
        double grade = 0.0;
        System.out.println(gradeSums);
        //Calculate the grade based on the gradeSums Map
        for(String weightCategory : gradeSums.keySet())
        {
            //Don't consider any categories that don't have any associated assignments
            if(weightCounts.containsKey(weightCategory))
            {
                //Formula: Sum of [(Total Grade Sum / # of Items) * Weight]
                grade += (gradeSums.get(weightCategory) /
                ((double) weightCounts.get(weightCategory))
                * weights.get(weightCategory));
            }
        }
        return Math.round((grade / totalPercentage()) * 100.0) / 100.0; //Round to 2 decimal places
    }
}