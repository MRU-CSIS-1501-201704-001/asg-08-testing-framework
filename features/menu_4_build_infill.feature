Feature: Assignment 08, Radon Level Analysis - Build Infill
    
	Scenario: Build an Infill - Invalid house number
		Given that I'm using "data1.txt" as my load file and "out.txt" as my save file
		When I try to build an infill at invalid house number "1423"
		And try to confirm that nothing has changed by printing the addresses of the houses on the block
		Then what should happen is the program should run
		And the program should print an invalid house number message
		And the program should print the block resulting from this operation
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
    
    Scenario: Build an Infill - Lot is not vacant
		Given that I'm using "data1.txt" as my load file and "out.txt" as my save file
		When I try to build an infill at existing house number "1413"
		And try to confirm that nothing has changed by printing the addresses of the houses on the block
		Then what should happen is the program should run
		And the program should print an error message saying the lot is not vacant
		And the program should print the block resulting from this operation
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

    Scenario: Build an Infill - Lot is at the very beginning of the block
		Given that I'm using "data1.txt" as my load file and "out.txt" as my save file
		When I try to build an infill at the first house in the block "1401"
		And I try to enter valid volumes "216" and "188" respectively for the two basement volumes
		And try to confirm that the infill has gone in properly
		Then what should happen is the program should run
		And the program should print the block resulting from this operation
			"""
			1401 Rue Marie Curie
			1403 Rue Marie Curie
			1405 Rue Marie Curie
			1409 Rue Marie Curie
			1413 Rue Marie Curie
			1421 Rue Marie Curie
			1425 Rue Marie Curie
			1429 Rue Marie Curie
			"""
		And the program should print the menu again
		And the program should request my choice again
		And after exiting and saving to "out.txt" 
		Then I should find it contains data for the first infill
			"""
			1401
			216.0
			0.00092
			"""
		And I should find it contains data for the second infill
			"""
			1403
			188.0
			0.00092
			"""

Scenario: Build an Infill - Lot is before current first house
		Given that I'm using "data2.txt" as my load file and "out.txt" as my save file
		When I try to build an infill at the first house in the block "1405"
		And I try to enter valid volumes "216" and "188" respectively for the two basement volumes
		And try to confirm that the infill has gone in properly
		Then what should happen is the program should run
		And the program should print the block resulting from this operation
			"""
			1405 Rue Marie Curie
			1407 Rue Marie Curie
			1409 Rue Marie Curie
			1413 Rue Marie Curie
			1421 Rue Marie Curie
			1425 Rue Marie Curie
			1429 Rue Marie Curie
			"""
		And the program should print the menu again
		And the program should request my choice again
		And after exiting and saving to "out.txt" 
		Then I should find it contains data for the first infill
			"""
			1405
			216.0
			0.00329
			"""
		And I should find it contains data for the second infill
			"""
			1407
			188.0
			0.00329
			"""

    Scenario: Build an Infill - Lot is at the end of the block
		Given that I'm using "data1.txt" as my load file and "out.txt" as my save file
		When I try to build an infill at the last house in the block "1433"
		And I try to enter valid volumes "172" and "222" respectively for the two basement volumes
		And try to confirm that the infill has gone in properly
		Then what should happen is the program should run
		And the program should print the block resulting from this operation
			"""
			1405 Rue Marie Curie
			1409 Rue Marie Curie
			1413 Rue Marie Curie
			1421 Rue Marie Curie
			1425 Rue Marie Curie
			1429 Rue Marie Curie
			1433 Rue Marie Curie
			1435 Rue Marie Curie
			"""
		And the program should print the menu again
		And the program should request my choice again
		And after exiting and saving to "out.txt" 
		Then I should find it contains data for the first infill
			"""
			1433
			172.0
			0.00492
			"""
		And I should find it contains data for the second infill
			"""
			1435
			222.0
			0.00492
			"""

    Scenario: Build an Infill - Lot is at the middle of the block
		Given that I'm using "data1.txt" as my load file and "out.txt" as my save file
		When I try to build an infill at valid location "1417"
		And I try to enter valid volumes "202" and "194" respectively for the two basement volumes
		And try to confirm that the infill has gone in properly
		Then what should happen is the program should run
		And the program should print the block resulting from this operation
			"""
			1405 Rue Marie Curie
			1409 Rue Marie Curie
			1413 Rue Marie Curie
			1417 Rue Marie Curie
			1419 Rue Marie Curie
			1421 Rue Marie Curie
			1425 Rue Marie Curie
			1429 Rue Marie Curie
			"""
		And the program should print the menu again
		And the program should request my choice again
		And after exiting and saving to "out.txt" 
		Then I should find it contains data for the first infill
			"""
			1417
			202.0
			0.00745
			"""
		And I should find it contains data for the second infill
			"""
			1419
			194.0
			0.00745
			"""

