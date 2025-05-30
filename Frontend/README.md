This project allows you to calculate your grade in a class.

The frontend website asks for two things: assignment categories and their weights, 
and assignments as well as their corresponding categories and grades.

Assignment -> The assignment name itself.
Category -> Where the assignment is grouped (ex. Test, Quiz, Homework, etc).
Weight -> How the categories are weighted.
Grade --> The individual grade the user got on an assignment.

Javascript is used to convert the data from the two forms into Maps, which are then converted to Strings.

These Strings are send to Java via API fetch functionality. Java uses SpringBoot to obtain these Strings.

After obtaining the Strings, the Java backend reconverts them to arrays and then uses them to create
Maps that are necessary for the GradeCalculator to perform its function.

Once the grade is calculated, it is sent back to JS where the program creates an alert
letting the user know their grade (or an error message if the user put in invalid input).

Notes: 
* Put in decimal values for weights and grades, not fractions
* For Weights put a value between 0 and 1.0
* For Grades put in a value between 0 and 100 (as a percentage)
* You'll need to run the SpringBoot application in order for the application to work as intended