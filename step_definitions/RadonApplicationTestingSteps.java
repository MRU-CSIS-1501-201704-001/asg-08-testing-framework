package step_definitions;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.io.File;
import java.util.Arrays;
import java.util.Queue;
import java.util.LinkedList;
import java.util.regex.Matcher;

import cucumber.api.java.en.*;
import cucumber.api.java.Before;
import cucumber.api.java.After;
import cucumber.api.Scenario;
import cucumber.api.PendingException;

import java.util.regex.Pattern;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;


public class RadonApplicationTestingSteps {
	
    private LinkedList<String> inputQueue = new LinkedList<>();
    private String[] outputLines;
    private int nextLine;

    private int inputInsertionIndex = 0;

    private static final String PRINT_BLOCK_DATA_MENU_OPTION = "1";
    private static final String PRINT_HOUSE_INFO_MENU_OPTION = "2";
    private static final String DEMOLISH_HOUSE_MENU_OPTION = "3";
    private static final String BUILD_INFILL_MENU_OPTION = "4";
    private static final String PRINT_REPORT_MENU_OPTION = "5";
    private static final String QUIT_MENU_OPTION = "6";
    private static final String BAD_MENU_OPTION = "8";

    private String fileToLoad = "data1.txt";
    private String fileToSave = "out.txt";

    @Before
    public void beforeCallingScenario() {
        // currently not used
    }

	
    @After
    public void afterRunningScenario(Scenario scenario) {
        this.inputQueue.clear();
        this.outputLines = null;
        this.nextLine = -1;
    }



    //
    // SHARED STEPDEFS
    //

    @Then("^the program will print the menu$")                              
    public void theProgramWillPrintTheMenu() throws Throwable {             
        checkForMenuText();                                      
    }     

    @Then("^the program will request my choice$")
    public void theProgramWillRequestMyChoice() throws Throwable {
        checkForPrompt();
    }

    @Given("^that I'm using \"([^\"]*)\" as my load file and \"([^\"]*)\" as my save file$")
    public void thatIMUsingAsMyLoadFileAndAsMySaveFile(String fileToLoad, String fileToSave) throws Throwable {
        
        this.fileToLoad = fileToLoad;
        this.fileToSave = fileToSave;

        setupCleanStart();
        setupCleanExit();
        this.inputInsertionIndex = 1;
    }

    @Then("^what should happen is the program should run$")                    
    public void whatShouldHappenIsTheProgramShouldRun() throws Throwable {     
        runTheApp();
        checkForMenuText();
        checkForPrompt();                                         
    }   

    @Then("^the program should print the menu again$")
    public void theProgramShouldPrintTheMenuAgain() throws Throwable {
        checkForMenuText();
    }

    @Then("^the program should request my choice again$")                     
    public void theProgramShouldRequestMyChoiceAgain() throws Throwable {     
        checkForPrompt();                                         
    } 

    //
    // APPLICATION MENU VALIDATION 
    //
                                                                
    @When("^I try to enter an invalid choice at the menu$")
    public void iTryToEnterAnInvalidChoiceAtTheMenu() throws Throwable {  
        addInput(BAD_MENU_OPTION);                                        
    }                                                                                                                                                      

    @Then("^the program should print an illegal choice message$")
    public void theProgramShouldPrintAnIllegalChoiceMessage() throws Throwable {
        this.nextLine--;
        assertRemainingOutputContainsFragment("illegal choice");
    }

    

    //
    // MENU OPTION 1: PRINT BLOCK DATA
    //

    @When("^I try to print the addresses of the houses on the block$")
    public void iTryToPrintTheAddressesOfTheHousesOnTheBlock() throws Throwable {
        addInput(PRINT_BLOCK_DATA_MENU_OPTION);
    }
    
    @Then("^the program should print the following addresses$")
    public void theProgramShouldPrintTheFollowingAddresses(String expectedAddresses) throws Throwable {
        assertBlockOfCloseEnoughTextIsInOuput(expectedAddresses);
    }



    //
    // MENU OPTION 2: PRINT HOUSE INFO
    //

    @When("^I try to enter the nonexistent house number \"([^\"]*)\"$")
    public void iTryToEnterTheNonExistentHouseNumber(String nonExistentHouseNum) throws Throwable {
        addInput(PRINT_HOUSE_INFO_MENU_OPTION);
        addInput(nonExistentHouseNum);
    }
    
    @Then("^the program should print a no house message$")
    public void theProgramShouldPrintANoHouseMessage() throws Throwable {
        assertRemainingOutputContainsFragment("no house");
    }
    
    @When("^I try to enter the existent house number \"([^\"]*)\"$")
    public void iTryToEnterTheExistentHouseNumber(String existentHouseNum) throws Throwable {
        addInput(PRINT_HOUSE_INFO_MENU_OPTION);
        addInput(existentHouseNum);
    }
    
    @Then("^the program should print this information about the house$")
    public void theProgramShouldPrintThisInformationAboutTheHouse(String houseInfo) throws Throwable {
        assertBlockOfCloseEnoughTextIsInOuput(houseInfo);
    }



    //
    // MENU OPTION 3: DEMOLISH HOUSE
    //
    
    @When("^I try to demolish the nonexistent house number \"([^\"]*)\"$")
    public void iTryToDemolishTheNonexistentHouseNumber(String nonExistentHouseNum) throws Throwable {
        addInput(DEMOLISH_HOUSE_MENU_OPTION);
        addInput(nonExistentHouseNum);
    }

    @Then("^the program should print a demolition confirmation message$")
    public void theProgramShouldPrintADemolitionConfirmationMessage() throws Throwable {
        assertRemainingOutputContainsFragment("removed");;
    }
 
    @Then("^when the block is checked, the house should no longer be there$")
    public void whenTheBlockIsCheckedTheHouseShouldNoLongerBeThere(String blockWithHouseRemoved) throws Throwable {
        assertBlockOfCloseEnoughTextIsInOuput(blockWithHouseRemoved);
    }

    @Then("^the program should print a demolition error message$")
    public void theProgramShouldPrintADemolitionErrorMessage() throws Throwable {
        assertRemainingOutputContainsFragment("no house"); 
    }
    
    @When("^try to confirm that the house is gone by printing the addresses of the houses on the block$")
    public void tryToConfirmThatTheHouseIsGoneByPrintingTheAddressesOfTheHousesOnTheBlock() throws Throwable {
        addInput(PRINT_BLOCK_DATA_MENU_OPTION);
    }
    
    @When("^try to confirm that no other houses are gone by printing the addresses of the houses on the block$")
    public void tryToConfirmThatNoOtherHousesAreGoneByPrintingTheAddressesOfTheHousesOnTheBlock() throws Throwable {
        addInput(PRINT_BLOCK_DATA_MENU_OPTION);
    }
    
    @Then("^when the block is checked, the starting addresses should still be there$")
    public void whenTheBlockIsCheckedTheStartingAddressesShouldStillBeThere(String sameAddressesAsStart) throws Throwable {
        assertBlockOfCloseEnoughTextIsInOuput(sameAddressesAsStart);
    }

    //
    // MENU OPTION 4: INFILLS
    //

    @When("^I try to build an infill at invalid house number \"([^\"]*)\"$")
    public void iTryToBuildAnInfillAtInvalidHouseNumber(String invalidHouseNum) throws Throwable {
        addInput(BUILD_INFILL_MENU_OPTION);
        addInput(invalidHouseNum);
    }
    
    @When("^try to confirm that nothing has changed by printing the addresses of the houses on the block$")
    public void tryToConfirmThatNothingHasChangedByPrintingTheAddressesOfTheHousesOnTheBlock() throws Throwable {
        addInput(PRINT_BLOCK_DATA_MENU_OPTION);
    }

    @When("^try to confirm that the infill has gone in properly$")
    public void tryToConfirmThatTheInfillHasGoneInProperly() throws Throwable {
        addInput(PRINT_BLOCK_DATA_MENU_OPTION);
    }

    @Then("^the program should print an invalid house number message$")
    public void theProgramShouldPrintAnInvalidHouseNumberMessage() throws Throwable {
        assertRemainingOutputContainsFragment("not a valid house number for this block");
    }
    
    @Then("^the program should print the block resulting from this operation$")
    public void theProgramShouldPrintTheBlockResultingFromThisOperation(String resultingBlock) throws Throwable {
        assertBlockOfCloseEnoughTextIsInOuput(resultingBlock);
    }
    
    @When("^I try to build an infill at existing house number \"([^\"]*)\"$")
    public void iTryToBuildAnInfillAtExistingHouseNumber(String existingHouseNum) throws Throwable {
        addInput(BUILD_INFILL_MENU_OPTION);
        addInput(existingHouseNum);
    }
    
    @Then("^the program should print an error message saying the lot is not vacant$")
    public void theProgramShouldPrintAnErrorMessageSayingTheLotIsNotVacant() throws Throwable {
        assertRemainingOutputContainsFragment("not vacant"); 
    }
    
    @When("^I try to build an infill at the first house in the block \"([^\"]*)\"$")
    public void iTryToBuildAnInfillAtTheFirstHouseInTheBlock(String firstHouseNum) throws Throwable {
        addInput(BUILD_INFILL_MENU_OPTION);
        addInput(firstHouseNum);
    }
    
    @When("^I try to enter valid volumes \"([^\"]*)\" and \"([^\"]*)\" respectively for the two basement volumes$")
    public void iTryToEnterValidVolumesAndRespectivelyForTheTwoBasementVolumes(String basementVolOne, String basementVolTwo) throws Throwable {
        addInput(basementVolOne);
        addInput(basementVolTwo);
    }
    
    @When("^I try to build an infill at the last house in the block \"([^\"]*)\"$")
    public void iTryToBuildAnInfillAtTheLastHouseInTheBlock(String lastHouseNum) throws Throwable {
        addInput(BUILD_INFILL_MENU_OPTION);
        addInput(lastHouseNum);
    }

    @Then("^after exiting and saving to \"([^\"]*)\"$")
    public void afterExitingAndSavingTo(String arg1) throws Throwable {
        // already taken care of
    }
    
    @Then("^I should find it contains data for the first infill$")
    public void iShouldFindItContainsDataForTheFirstInfill(String infillDetails) throws Throwable {
        assertOutputFileContains(infillDetails);
    }
    
    @Then("^I should find it contains data for the second infill$")
    public void iShouldFindItContainsDataForTheSecondInfill(String infillDetails) throws Throwable {
        assertOutputFileContains(infillDetails);
    }
    
    @When("^I try to build an infill at valid location \"([^\"]*)\"$")
    public void iTryToBuildAnInfillAtValidLocation(String validHouseNum) throws Throwable {
        addInput(BUILD_INFILL_MENU_OPTION);
        addInput(validHouseNum);
    }

    //
    // MENU OPTION 5: PRINT REMEDIATION REPORT
    //

    @When("^I try to print a remediation report$")
    public void iTryToPrintARemediationReport() throws Throwable {
        addInput(PRINT_REPORT_MENU_OPTION);
    }
    
    @Then("^the program should print the remediation report$")
    public void theProgramShouldPrintTheRemediationReport(String expectedReport) throws Throwable {
        assertBlockOfCloseEnoughTextIsInOuput(expectedReport);
    }
    


    //
    // OUTPUT FILE CREATION
    //

    @Given("^that I demolish existent house number \"([^\"]*)\"$")
    public void thatIDemolishExistentHouseNumber(String existentHouseNum) throws Throwable {
        addInput(DEMOLISH_HOUSE_MENU_OPTION);
        addInput(existentHouseNum);
    }
    
    @Given("^build an infill at valid location \"([^\"]*)\" with basement volumes \"([^\"]*)\" and \"([^\"]*)\"$")
    public void buildAnInfillAtValidLocationWithBasementVolumesAnd(String infillNumber, String infillBasementOne, String infillBasementTwo) throws Throwable {
        addInput(BUILD_INFILL_MENU_OPTION);
        addInput(infillNumber);
        addInput(infillBasementOne);
        addInput(infillBasementTwo);
    }


    @Then("^I should find the output file is named correctly and contains exactly these contents$")
    public void iShouldFindTheOutputFileIsNamedCorrectlyAndContainsExactlyTheseContents(String desiredFileContents) throws Throwable {
        assertFileHasDesiredName();
        assertOutputFileHasExactContents(desiredFileContents);
    }

    //
    // ALL THE HELPERS
    //



    public void provideKeyboardInput() {
        
        String queuedInput = "";
        for (String s : inputQueue) {
            queuedInput += String.format("%s%n",s);
        }
        // System.err.println("QueuedInput: " + inputQueue);
        System.setIn(new ByteArrayInputStream(queuedInput.getBytes()));
    }
    
    private void runTheApp() throws Throwable {

        ByteArrayOutputStream outContent = null;
        PrintStream testSystemOut = null;
        try {
            outContent = new ByteArrayOutputStream();
            testSystemOut = new PrintStream(outContent, true, "UTF-8");

            PrintStream originalSystemOut = System.out;
            try {
                System.setOut(testSystemOut);

                provideKeyboardInput();
                Class.forName("AssignmentMain").getMethod("main",String[].class ).invoke(null,(Class<?>)null);
            } finally {
                System.setOut(originalSystemOut);
            }

            testSystemOut.flush();
            outputLines = outContent.toString("UTF-8").split("\\R+");
            nextLine = 0;
        } finally {
            testSystemOut.close();
            outContent.close();
        }

    }


    private void checkForPrompt() throws Throwable {
        assertRemainingOutputContainsFragment("your choice");
    }

    private void checkForMenuText() throws Throwable {
        assertRemainingOutputContainsFragment("1 - Print Block data");
        assertRemainingOutputContainsFragment("2 - Print the information about a house");
        assertRemainingOutputContainsFragment("3 - Demolish a house");
        assertRemainingOutputContainsFragment("4 - Build an infill");
        assertRemainingOutputContainsFragment("5 - Print remediation report");
        assertRemainingOutputContainsFragment("6 - Quit the application");
    }

    private void setupCleanExit() {
        inputQueue.offer(QUIT_MENU_OPTION);
        inputQueue.offer(fullFilePath(fileToSave));
    }

    private String fullFilePath(String fileName) {
        final String dir = System.getProperty("user.dir");
        String fileLoc = dir + "\\solution\\" + fileName;
        return fileLoc.replaceAll("\\\\","/");
    }

    private void setupCleanStart() {
        inputQueue.offer(fullFilePath(fileToLoad));
    }

    public void assertRemainingOutputIsEmpty() throws Throwable {
        if (this.nextLine != this.outputLines.length) {
            throw new AssertionError("Expected no further output, but there was.");
        }
    }
    

    public void assertRemainingOutputContainsFragment(String word) throws Throwable {
        this.assertRemainingOutputContains(word, "\"" + word + "\"");
    }

    public void assertRemainingOutputContainsWord(String word) throws Throwable {
        this.assertRemainingOutputContains("\\b" + word + "\\b", "word \"" + word + "\"");
    }

    public void assertRemainingOutputContains(String regex, String niceName) throws Throwable {

        int lineToCheck = this.nextLine;
        boolean found = false;
        
        
        

        regex = regex.replaceAll("[-.\\+*?\\[^\\]$(){}=!<>|:\\\\]", "\\\\$0");
        while (!found && lineToCheck < this.outputLines.length) {
            Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(this.outputLines[lineToCheck]);
            found = m.find();

            lineToCheck++;
        }

        if (found) {
            this.nextLine = lineToCheck;
        } else {
            throw new AssertionError("Could not find " + niceName + " in remaining lines:\n" +
                String.join("\n", Arrays.copyOfRange(this.outputLines, this.nextLine, this.outputLines.length)) +
                (this.nextLine > 0 ? "\nPrevious line was:\n" + this.outputLines[this.nextLine - 1] : ""));
        }
    }

    public void assertRemainingOutputMissingWord(String word) throws Throwable {
        this.assertRemainingOutputMissing("\\b" + word + "\\b", word);
    }

    public void assertRemainingOutputMissing(String regex, String niceName) throws Throwable {
        int lineToCheck = this.nextLine;
        boolean found = false;

        regex = regex.replaceAll("[-.\\+*?\\[^\\]$(){}=!<>|:\\\\]", "\\\\$0");
        while (!found && lineToCheck < this.outputLines.length) {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(this.outputLines[lineToCheck]);
            found = m.find();

            lineToCheck++;
        }

        if (found) {
            throw new AssertionError("Did not expect to find '" + regex + "', but found it in the line:\n" +
                String.join("\n", this.outputLines[lineToCheck - 1]));
        }
    }
    
    private void assertBlockOfCloseEnoughTextIsInOuput(String blockOfText) throws Throwable {
        if (this.nextLine >= this.outputLines.length) {
            String msg = String.format("Can't match %n%n%s%n%n because no more lines are left in the output!", blockOfText);
            throw new AssertionError(msg);
        }
        
        String[] splitBlock = blockOfText.split("\\R+");        

        int numLinesInBlock = splitBlock.length;
        int linesLeftInOutput = this.outputLines.length - this.nextLine;

        if (linesLeftInOutput < numLinesInBlock) {
            String msg = String.format("Can't match %n%n%s%n%n because remaining output doesn't have enough lines!", blockOfText);
            throw new AssertionError(msg);
        }

        int currentIndexInBlock = 0;
        int currentIndexInOutput = indexOfFirstPossibleMatch(splitBlock[0]);

        if (currentIndexInOutput == -1) {
            String remainingOutput = currentlyRemainingOutput();
            String msg = String.format("Couldn't match %n%n%s%n%n because the line %n%n%s%n%n doesn't really appear anywhere in the remaining output %n%n%s%n%n.", blockOfText, splitBlock[0], remainingOutput);
            throw new AssertionError(msg);
        }

        String currentLineInBlock = splitBlock[currentIndexInBlock];        
        String currentLineInOutput = this.outputLines[currentIndexInOutput];
        
        while (currentIndexInBlock < numLinesInBlock && lineIsCloseEnoughToTarget(currentLineInBlock, currentLineInOutput)) {
            currentLineInBlock = splitBlock[currentIndexInBlock];        
            currentLineInOutput = this.outputLines[currentIndexInOutput];

            currentIndexInBlock++;
            currentIndexInOutput++;
        }

        if (currentIndexInBlock != numLinesInBlock) {
            String msg = String.format("Couldn't match %n%n%s%n%n because the line %n%s%n doesn't really match expected line %n%s%n", blockOfText, currentLineInOutput, currentLineInBlock);
            throw new AssertionError(msg);
        } else {
            this.nextLine = currentIndexInOutput; 
        }
    }

    private String currentlyRemainingOutput() {
        String remaining = "";
        for (int i = this.nextLine; i < this.outputLines.length; i++) {
            remaining += String.format("%s%n", this.outputLines[i]);
        }
        return remaining;
    }


    private int indexOfFirstPossibleMatch(String line) {
        for (int i = this.nextLine; i < this.outputLines.length; i++) {
            if (lineIsCloseEnoughToTarget(line, this.outputLines[i])) {
                return i;
            }
        }
        return -1;
    }


    private boolean lineIsCloseEnoughToTarget(String line, String targetLine) {
        String[] splitLine = line.split("[^a-zA-Z0-9]+");
        String[] splitTargetLine = targetLine.split("[^a-zA-Z0-9]+");

        int splitLineSize = splitLine.length;
        int splitTargetLineSize = splitTargetLine.length;

        if (splitLineSize != splitTargetLineSize) {
            return false;
        }

        for (int i = 0; i < splitLineSize; i++) {
            String lineWord = splitLine[i];
            String targetLineWord = splitTargetLine[i];

            if (!lineWord.equalsIgnoreCase(targetLineWord)) {
                return false;
            }
        }
        return true;
    }


    private void assertOutputFileContains(String desiredContentBlock) throws Throwable {
        String path = fullFilePath(fileToSave);
       
        String fileContents = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
    
        if (!fileContents.contains(desiredContentBlock)) {
            String msg = String.format("Couldn't find %n%n%s%n%n in the output file contents %n%n%s%n%n", desiredContentBlock, fileContents);
            throw new AssertionError(msg);
        } 
    }

    private void assertOutputFileHasExactContents(String desiredFileContents) throws Throwable {
        String path = fullFilePath(fileToSave);
       
        String fileContents = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        fileContents = fileContents.trim();
        
        if (!fileContents.equals(desiredFileContents)) {
            String msg = String.format("Desired file content %n%n%s%n%n did not match the output file contents %n%n%s%n%n", desiredFileContents, fileContents);
            throw new AssertionError(msg);
        } 
    }

    private void assertFileHasDesiredName() throws Throwable {
        String path = fullFilePath(fileToSave);
        File f = new File(path);
        if (!f.exists() || f.isDirectory()) {
            String msg = String.format("Couldn't find a file named %s in the current directory.%n", fileToSave);
            throw new AssertionError(msg);
        }

    }

    private void addInput(String input) {
        inputQueue.add(this.inputInsertionIndex, input);
        this.inputInsertionIndex++;
    }
}
