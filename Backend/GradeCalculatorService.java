package project.GradeCalculator;

import java.util.HashMap; //Required for the GradeCalculator
import com.fasterxml.jackson.databind.ObjectMapper; //Required for converting String to 2-D array of Strings
import org.springframework.stereotype.Service;

/*
    Following class performs the actual
    functionality that the GradeCalculator
    is supposed to have.

    This class relies on the actual GradeCalculator
    class as well as the information obtained from
    the GradeCalculatorController class
 */
@Service //Necessary so SpringBoot makes an instance of this class
public class GradeCalculatorService
{
    //Given package access so the GradeCalculatorController class can access them
    String categoryWeights; //Variable used to store the category-weights String
    String assignmentCategories; //Variable used to store the assignment-categories String
    String assignmentGrades; //Variable used to store the assignment-grades String

    //Performs the calculation of the Grade via the controller and the GradeCalculator class
    public String calculateGrade()
    {
        //Declare each of the 2-D arrays so they can be used later
        String[][] categoryWeightsArray;
        String[][] assignmentCategoriesArray;
        String[][] assignmentGradesArray;

        try //Converting String to array can lead to errors
        {
            //Convert each String to a 2D array of Strings
            categoryWeightsArray = convertStringTo2DArray(categoryWeights);
            assignmentCategoriesArray = convertStringTo2DArray(assignmentCategories);
            assignmentGradesArray = convertStringTo2DArray(assignmentGrades);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return e.getMessage(); //Fail to convert String
        }

        //Create HashMap that maps categories to weights
        HashMap<String, Double> categoryWeightsMap = new HashMap<String, Double>();
        //Add elements to the categoryWeightsMap
        for(String[] category : categoryWeightsArray)
        {
            try //Possibly dangerous as the value of weight may not be a double
            {
                //The category is the key, the weight is the value
                categoryWeightsMap.put(category[0], Double.valueOf(category[1]));
            }
            catch(NumberFormatException e) //NumberFormatException could result from Double.valueOf
            {
                System.out.println(e.getMessage());
                return e.getMessage();
            }
        }

        //Create HashMap that maps assignments to categories
        HashMap<String, String> assignmentCategoriesMap = new HashMap<String, String>();
        //Add elements to the assignmentCategoriesMap
        for(String[] assignment : assignmentCategoriesArray)
        {
            //The assignment is the key, the category is the value
            assignmentCategoriesMap.put(assignment[0], assignment[1]);
        }

        //Create HashMap that maps assignments to grades
        HashMap<String, Double> assignmentGradesMap = new HashMap<String, Double>();
        //Add elements to the categoryWeightsMap
        for(String[] assignment : assignmentGradesArray)
        {
            try //Possibly dangerous as the value of grade may not be a double
            {
                //The category is the key, the weight is the value
                assignmentGradesMap.put(assignment[0], Double.valueOf(assignment[1]));
            }
            catch(NumberFormatException e) //NumberFormatException could result from Double.valueOf
            {
                System.out.println(e.getMessage());
                return e.getMessage();
            }
        }

        //Create HashMap that maps categories to category counts
        HashMap<String, Integer> weightCountsMap = new HashMap<String, Integer>();
        //Add elements to the weightCountsMap (based on values from the other maps)
        for(String assignment : assignmentCategoriesMap.keySet())
        {
            //Note, all categories in assignmentCategoriesMap should be in this map as well
            String category = assignmentCategoriesMap.get(assignment);
            //The category is the key, the # of assignments the category is associated with is the value
            weightCountsMap.put(category, weightCountsMap.getOrDefault(category, 0) + 1);
        }

        //Create a new GradeCalculator object to pass all the maps into
        GradeCalculator gradeCalculator = new GradeCalculator(categoryWeightsMap,
        assignmentCategoriesMap, assignmentGradesMap, weightCountsMap);

        try //Dangerous code due to possible invalid input by user
        {
            //Calculate the grade using the GradeCalculator
            return String.valueOf(gradeCalculator.calculateGrade());
        }
        catch(IllegalArgumentException e) //If any exception is caught during calculation
        {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    //Helper class, used to convert incoming Strings to 2D arrays for conversion to Map
    private String[][] convertStringTo2DArray(String arrayString) throws Exception
    {
        ObjectMapper mapStringToArray = new ObjectMapper(); //Used from Jackson to convert
        return mapStringToArray.readValue(arrayString, String[][].class); //Map the array
    }
}
