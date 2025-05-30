package project.GradeCalculator;

import org.springframework.web.bind.annotation.*;

/*
    Following class is used to perform the SpringBoot
    HTTP requests to obtain data from them
 */
@RestController //Allows usage of HTTP protocols
@CrossOrigin(origins = "*") //Allows mapping to any URL
public class GradeCalculatorController
{
    GradeCalculatorService service; //Used to obtain String data

    //Initialize the service via constructor
    public GradeCalculatorController(GradeCalculatorService service)
    {
        this.service = service;
    }

    /*
        Following method obtains the String
        representing category-weight associations
        from the GradeCalculator frontend
     */
    @PostMapping("/get-weights")
    public String getWeights(@RequestBody String categoryWeights)
    {
        service.categoryWeights = categoryWeights;
        return categoryWeights;
    }

    /*
        Following method obtains the String
        representing assignment-category associations
        from the GradeCalculator frontend
     */
    @PostMapping("/get-assignment-categories")
    public String getAssignmentCategories(@RequestBody String assignmentCategories)
    {
        service.assignmentCategories = assignmentCategories;
        return assignmentCategories;
    }

    /*
        Following method obtains the String
        representing assignment-grade associations
        from the GradeCalculator frontend
     */
    @PostMapping("/get-assignment-grades")
    public String getAssignmentGrades(@RequestBody String assignmentGrades)
    {
        service.assignmentGrades = assignmentGrades;
        return assignmentGrades;
    }

    /*
        Following method sends data from the GradeCalculator
        back to JS in order to be used for the website
     */
    @GetMapping("/calculate-grades")
    public String sendGrade()
    {
        return service.calculateGrade(); //Use the method from the service
    }
}
