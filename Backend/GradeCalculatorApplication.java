package project.GradeCalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*; //Used for Input-Output operations
import java.net.*; //Used for HTTP handling

/*
	Following class is where the actual GradeCalculator application is run,
	so that any SpringBoot required functionality can occur in the backend
 */
@SpringBootApplication //Required annotation
public class GradeCalculatorApplication
{
	//Run the application
	public static void main(String[] args)
	{
		SpringApplication.run(GradeCalculatorApplication.class, args);
	}
}
