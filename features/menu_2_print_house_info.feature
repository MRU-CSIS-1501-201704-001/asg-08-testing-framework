Feature: Assignment 08, Radon Level Analysis - Print House Info    
	
	Scenario: Print the information about a house - Non existing house number
		Given that I'm using "data1.txt" as my load file and "out.txt" as my save file
		When I try to enter the nonexistent house number "1417"
		Then what should happen is the program should run
		And the program should print a no house message
		And the program should print the menu again
		And the program should request my choice again
		
	Scenario: Print the information about s house - Existing house number
		Given that I'm using "data1.txt" as my load file and "out.txt" as my save file
		When I try to enter the existent house number "1405"
		Then what should happen is the program should run
		And the program should print this information about the house
			"""
			House Number    :  1405
			Street          :  Rue Marie Curie
			Basement Volume :  484.0
			Daily Reading   :  0.0009
			Radon Level     :  162.35
			"""
		And the program should print the menu again
		And the program should request my choice again