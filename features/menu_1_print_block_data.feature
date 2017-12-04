Feature: Assignment 08, Radon Level Analysis - Print Block Data

    Scenario: Printing the addresses of the houses on the block
		Given that I'm using "data1.txt" as my load file and "out.txt" as my save file
		When I try to print the addresses of the houses on the block
		Then what should happen is the program should run
		And the program should print the following addresses
			"""
			1405 Rue Marie Curie
			1409 Rue Marie Curie
			1413 Rue Marie Curie
			1421 Rue Marie Curie
			1425 Rue Marie Curie
			1429 Rue Marie Curie
			"""
		And the program should print the menu again
		And the program should request my choice again