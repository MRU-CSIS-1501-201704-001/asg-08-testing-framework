Feature: Assignment 08, Radon Level Analysis - Application Menu Validation
    
	Scenario: Entering an invalid choice
		Given that I'm using "data1.txt" as my load file and "out.txt" as my save file
		When I try to enter an invalid choice at the menu
		Then what should happen is the program should run
		And the program should print an illegal choice message
		And the program should print the menu again
		And the program should request my choice again