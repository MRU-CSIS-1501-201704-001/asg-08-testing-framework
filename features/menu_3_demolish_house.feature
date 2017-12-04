Feature: Assignment 08, Radon Level Analysis - Demolish House
    
	Scenario: Demolish a house - Nonexistent house number
		Given that I'm using "data1.txt" as my load file and "out.txt" as my save file
		When I try to demolish the nonexistent house number "1417"
		And try to confirm that no other houses are gone by printing the addresses of the houses on the block
		Then what should happen is the program should run
		And the program should print a demolition error message
		And when the block is checked, the starting addresses should still be there
			"""
			1405 Rue Marie Curie
			1409 Rue Marie Curie
			1413 Rue Marie Curie
			1421 Rue Marie Curie
			1429 Rue Marie Curie
			"""
		And the program should print the menu again
		And the program should request my choice again

    Scenario: Demolish a house - Existing house number
		Given that I'm using "data1.txt" as my load file and "out.txt" as my save file
		When I try to demolish the nonexistent house number "1425"
		And try to confirm that the house is gone by printing the addresses of the houses on the block
		Then what should happen is the program should run
		And the program should print a demolition confirmation message
		And when the block is checked, the house should no longer be there
			"""
			1405 Rue Marie Curie
			1409 Rue Marie Curie
			1413 Rue Marie Curie
			1421 Rue Marie Curie
			1429 Rue Marie Curie
			"""
		And the program should print the menu again
		And the program should request my choice again

