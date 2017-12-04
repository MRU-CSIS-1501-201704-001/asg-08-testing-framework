Feature: Assignment 08, Radon Level Analysis - Print Remediation Report
    
	Scenario: Printing a remediation report
		Given that I'm using "data1.txt" as my load file and "out.txt" as my save file
		When I try to print a remediation report
		Then what should happen is the program should run
		And the program should print the remediation report
			"""
			House Address               Level       Rating                                  
			1405   Rue Marie Curie        162.3505  Safe - no remediation required         
			1409   Rue Marie Curie        579.8343  Hazardous - remediation required       
			1413   Rue Marie Curie        711.3295  Hazardous - remediation required       
			1421   Rue Marie Curie        571.2571  Hazardous - remediation required       
			1425   Rue Marie Curie        347.5848  Hazardous - remediation required       
			1429   Rue Marie Curie        862.1592  Dangerous - immediate remediation required
			"""
		And the program should print the menu again
		And the program should request my choice again