package project.GradeCalculator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap; //Needed for GradeCalculator
import project.GradeCalculator.GradeCalculator; //What is being tested

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class GradeCalculatorApplicationTests
{
	/*
		Tests that this GradeCalculator can work even if not
		all the assignment categories that are given in
		the weights are considered
	 */
	@Test
	void testGradeCalculatorNotAllWeightsHaveAssociatedAssignments()
	{
		HashMap<String, Double> weights = new HashMap<String, Double>();
		weights.put("Review Projects", 0.06);
		weights.put("Projects", 0.24);

		HashMap<String, String> assignments = new HashMap<String, String>();
		assignments.put("P1", "Review Projects");
		assignments.put("P2", "Review Projects");

		HashMap<String, Double> grades = new HashMap<String, Double>();
		grades.put("P1", 100.0);
		grades.put("P2", 100.0);

		HashMap<String, Integer> counts = new HashMap<String, Integer>();
		counts.put("Review Projects", 2);

		GradeCalculator calculator = new GradeCalculator(weights, assignments, grades, counts);

		assertEquals(100.0, calculator.calculateGrade());
	}

}
