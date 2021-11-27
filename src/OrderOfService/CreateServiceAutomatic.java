/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OrderOfService;

import java.sql.SQLException;
import java.util.Random;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author natha
 */
public class CreateServiceAutomatic {
    //Used to set the parameters in constructor
    private final int numSongs;
    private final String tag;
    private final String speakerName;
    //Used in method selectSongs
    private String[][] eligibleSongs;
    private String[][] extraSongs;
    private String[][] orderSongs;
    //Used in method getSpeaker
    private boolean foundSpeaker;
    //Used in method getTag
    private boolean foundTag;
    private int tagID;
    
    /**
     * Creates an instantiation of CreateServiceAutomatic: a class that automatically
     * creates an order of service
     * @param NumSongs is the number of songs that the service will have
     * @param Tag is the main tag/theme of the service
     * @param SpeakerName is the speaker that will be used in the service
     */
    public CreateServiceAutomatic(int NumSongs, String Tag, String SpeakerName) {
        numSongs = NumSongs;
        tag = Tag;
        speakerName = SpeakerName;
        foundSpeaker = false;
        foundTag = false;
        
        //Main running of class is here
        
        //Only run method if there is something inputted in text box
        if(speakerName.isEmpty() == false) {
            //Run getSpeaker to evaluate whether service will have a speaker
            getSpeaker();
        }
        
        //Only run method if there is something inputted in text box
        if(tag.isEmpty() == false) {
            //Run getTag to determine whether the tag given can be used to influence 
            //selection of songs
            getTag();
        }
        
        //Run selectSongs to get a list of eligible songs
        selectSongs();
        
        //Now that songs are selected, run createOrder method
        createOrder();
    }
    
    
    /**
     * A method that will randomly select a list of songs for the service and add
     * them to a 2D array
     */
    public void selectSongs() {
        int thisError = 0;
        int numItems  = 0;
        orderSongs = new String[numSongs][2];
        ArrayList usedIndexes;
        
        //If there is a found tag add the relevant songs to array Songs
        if(foundTag == true) {
            //Get the songs required
            
            //Create the statement that counts the number of results
            String CountStatement = "select COUNT(*) from `Songs` where `SongID`"
                    + "in (select `SongID` from `SongTags` where `TagID` = " + tagID + ")";
            
            //Create statement that selects the title and key of these results
            String SelectStatement = "select `SongTitle`, `Key` from `Songs` where `SongID`"
                    + "in (select `SongID` from `SongTags` where `TagID` = " + tagID + ")";
            
            //Run the count
            OrderOfServiceMain.dbObject.sqlString = CountStatement;
            thisError = OrderOfServiceMain.dbObject.getCountBySelect();
            
            //Set numItems to the number of records generated from count statement
            numItems = OrderOfServiceMain.dbObject.NumberOfRecords;
            
            //Run the select
            OrderOfServiceMain.dbObject.sqlString = SelectStatement;
            thisError = OrderOfServiceMain.dbObject.getRecordSetBySelect();
            
            if(thisError == 0) {
                try {
                    //Used as a count/increment
                    int i = 0;
                    
                    //Instantiate array eligibleSongs
                    eligibleSongs = new String[numItems][2];
                    
                    //While there are more results
                    while(OrderOfServiceMain.dbObject.rs.next()) {
                        String SongTitle = OrderOfServiceMain.dbObject.rs.getString("SongTitle");
                        String Key = OrderOfServiceMain.dbObject.rs.getString ("Key");
                        
                        //Add SongTitle and Key to array eligibleSongs
                        eligibleSongs[i][0] = SongTitle;
                        eligibleSongs[i][1] = Key;
                        
                        //Increment i so that data is not overwritten
                        i++;
                    }
                }
                catch(SQLException ex) {
                    // Show errors in console
                    System.out.println("SQLException: " + ex.getMessage());
                    System.out.println("SQLState: " + ex.getSQLState());
                    System.out.println("VendorError: " + ex.getErrorCode());
                    thisError = ex.getErrorCode ();
                    
                    UserMessageBox MyMessage = new UserMessageBox ();
                    MyMessage.setMessage ("SQLException: " + ex.getMessage());
                    MyMessage.setVisible (true);
                }
            }
            
            //If any other songs are needed
            if(eligibleSongs.length < numSongs) {
                //Create count and select statements that get all of the yet unselected songs
                CountStatement = "select COUNT(*) from `Songs` where `SongID`"
                    + "not in (select `SongID` from `SongTags` where `TagID` = " + tagID + ")";
            
                SelectStatement = "select `SongTitle`, `Key` from `Songs` where `SongID`"
                    + "not in (select `SongID` from `SongTags` where `TagID` = " + tagID + ")";
                
                //Run the count
                OrderOfServiceMain.dbObject.sqlString = CountStatement;
                thisError = OrderOfServiceMain.dbObject.getCountBySelect();
                
                //Set numItems to the number of records
                numItems = OrderOfServiceMain.dbObject.NumberOfRecords;
                
                //Instantiate array extraSongs
                extraSongs = new String[numItems][2];
                
                //Run the select
                OrderOfServiceMain.dbObject.sqlString = SelectStatement;
                thisError = OrderOfServiceMain.dbObject.getRecordSetBySelect();
                
                if(thisError == 0) {
                    try {
                        //Used as a count/increment
                        int i = 0;
                        
                        //While there are more results 
                        while(OrderOfServiceMain.dbObject.rs.next()) {
                            String SongTitle = OrderOfServiceMain.dbObject.rs.getString("SongTitle");
                            String Key = OrderOfServiceMain.dbObject.rs.getString ("Key");
                            
                            //Add SongTitle and Key to array extraSongs
                            extraSongs[i][0] = SongTitle;
                            extraSongs[i][1] = Key;
                            
                            //Increment i so that data is not overwritten
                            i++;
                        }
                    }
                    catch(SQLException ex) {
                        // Show errors in console
                        System.out.println("SQLException: " + ex.getMessage());
                        System.out.println("SQLState: " + ex.getSQLState());
                        System.out.println("VendorError: " + ex.getErrorCode());
                        thisError = ex.getErrorCode ();
                        
                        UserMessageBox MyMessage = new UserMessageBox ();
                        MyMessage.setMessage ("SQLException: " + ex.getMessage());
                        MyMessage.setVisible (true);
                    }
                }
                
                
                //Create the song list
                
                
                //Add the eligible songs to the song list in a random order
                
                //Create an ArrayList that stores the indexes from array eligibleSongs
                //that have already been added to the song list
                usedIndexes = new ArrayList();
                
                //Loop that adds the eligible songs to the list
                for(int i = 0; i < eligibleSongs.length; i++) {
                    //Select a random number between 0 and the length of array
                    Random rand = new Random();
                    int chosenIndex = rand.nextInt(eligibleSongs.length);
                    
                    //While chosenIndex is in array usedIndexes
                    while(usedIndexes.contains(chosenIndex) == true) {
                        //Get a new random integer from 0 to the length of array
                        chosenIndex = rand.nextInt(eligibleSongs.length);
                    }
                    
                    //Add the song to array orderSongs
                    orderSongs[i][0] = eligibleSongs[chosenIndex][0];
                    orderSongs[i][1] = eligibleSongs[chosenIndex][1];
                }
                
                
                
                //Add songs into remaining space of orderSongs
                
                
                //Set a variable to the amount of songs that need to be added
                int remainingSpace = numSongs - eligibleSongs.length;
                
                
                //If there are less items in extraSongs than remainingSpace 
                //the database does not contain enough songs
                if(extraSongs.length < remainingSpace) {
                    //Create a UserMessageBox telling the user that they have
                    //asked for too many songs
                    UserMessageBox myMessage = new UserMessageBox();
                    myMessage.setTitle("Song Amount Error");
                    myMessage.setMessage("You have asked for too many songs: \n"
                            + "- The number of songs requested is greater than \n"
                            + "the number of songs in the database.");
                }
                
                
                //Recreate ArrayList usedIndexes so that it can be used with array extraSongs
                usedIndexes = new ArrayList();
                
                //Need to start from where we ended above so that data isn't overwritten
                for(int i = eligibleSongs.length; i < numSongs; i++) {
                    //Select a random number between 0 and the length of array extraSongs
                    Random rand = new Random();
                    int chosenIndex = rand.nextInt(extraSongs.length);
                    
                    //While chosenIndex is in array usedIndexes
                    while(usedIndexes.contains(chosenIndex) == true) {
                        //Select a random number between 0 and the length of array extraSongs
                        chosenIndex = rand.nextInt(extraSongs.length);
                    }
                    
                    //Add the song to array orderSongs
                    orderSongs[i][0] = extraSongs[chosenIndex][0];
                    orderSongs[i][1] = extraSongs[chosenIndex][1];
                }
            }
            //EligibleSongs length is greater than the num of songs required
            else {
                //Add the eligible songs to the song list in a random order

                //Create an ArrayList that stores the indexes from array eligibleSongs
                //that have already been added to the song list
                usedIndexes = new ArrayList();

                //Loop that adds the eligible songs to the list
                for(int i = 0; i < numSongs; i++) {
                    //Select a random number between 0 and the length of array
                    Random rand = new Random();
                    int chosenIndex = rand.nextInt(eligibleSongs.length);

                    //While chosenIndex is in array usedIndexes
                    while(usedIndexes.contains(chosenIndex) == true) {
                        //Get a new random integer from 0 to the length of array
                        chosenIndex = rand.nextInt(eligibleSongs.length);
                    }

                    //Add the song to array orderSongs
                    orderSongs[i][0] = eligibleSongs[chosenIndex][0];
                    orderSongs[i][1] = eligibleSongs[chosenIndex][1];
                }  
            }
        }
        //There is not a valid tag found
        else {
            //Search through database and get all songs
            
            //Create the statement that counts the number of results
            String CountStatement = "select COUNT(*) from `Songs`";
            
            //Create statement that selects the title and key of these results
            String SelectStatement = "select `SongTitle`, `Key` from `Songs`";
            
            //Run the count
            OrderOfServiceMain.dbObject.sqlString = CountStatement;
            thisError = OrderOfServiceMain.dbObject.getCountBySelect();
            
            //Set numItems to the number of records generated from count statement
            numItems = OrderOfServiceMain.dbObject.NumberOfRecords;
            
            //Run the select
            OrderOfServiceMain.dbObject.sqlString = SelectStatement;
            thisError = OrderOfServiceMain.dbObject.getRecordSetBySelect();
            
            //Instantiate array extraSongs
            extraSongs = new String[numItems][2];
            
            if(thisError == 0) {
                try {
                    //Used as a count/increment
                    int i = 0;
                    
                    //While there are more results
                    while(OrderOfServiceMain.dbObject.rs.next()) {
                        String SongTitle = OrderOfServiceMain.dbObject.rs.getString("SongTitle");
                        String Key = OrderOfServiceMain.dbObject.rs.getString ("Key");
                        
                        //Add SongTitle and Key to array extraSongs
                        extraSongs[i][0] = SongTitle;
                        extraSongs[i][1] = Key;
                        
                        //Increment i so that data is not overwritten
                        i++;
                    }
                }
                catch(SQLException ex) {
                    // Show errors in console
                    System.out.println("SQLException: " + ex.getMessage());
                    System.out.println("SQLState: " + ex.getSQLState());
                    System.out.println("VendorError: " + ex.getErrorCode());
                    thisError = ex.getErrorCode ();
                    
                    UserMessageBox MyMessage = new UserMessageBox ();
                    MyMessage.setMessage ("SQLException: " + ex.getMessage());
                    MyMessage.setVisible (true);
                }
            }
            
            //Add songs randomly selected into array orderSongs
            

            //Create an ArrayList that stores the indexes from array extraSongs
            //that have already been added to the song list
            usedIndexes = new ArrayList();

            //Loop that adds the songs to the list
            for(int i = 0; i < numSongs; i++) {
                //Select a random number between 0 and the length of array
                Random rand = new Random();
                int chosenIndex = rand.nextInt(extraSongs.length);

                //While chosenIndex is in array usedIndexes
                while(usedIndexes.contains(chosenIndex) == true) {
                    //Get a new random integer from 0 to the length of array
                    chosenIndex = rand.nextInt(extraSongs.length);
                }

                //Add the song to array orderSongs
                orderSongs[i][0] = extraSongs[chosenIndex][0];
                orderSongs[i][1] = extraSongs[chosenIndex][1];
            }  
        }       
    }
    
    /**
     * A method that attempts to get the SpeakerID and SpeakerName that is 
     * referred to from field speakerName
     */
    public void getSpeaker() {
        boolean error = true; //Assume the speaker doesn't exist and there is an error
        int thisError = 0;
        String SQLString = "select COUNT(*) from `Speakers` where `SpeakerName` = '" + speakerName + "'";
        
        int numItems = 0;
        
        OrderOfServiceMain.dbObject.sqlString = SQLString;
        thisError = OrderOfServiceMain.dbObject.getCountBySelect ();
        
        numItems = OrderOfServiceMain.dbObject.NumberOfRecords;
        
        //If there is a speaker matching the description then there is no error
        if(numItems == 1) {
            error = false;
            foundSpeaker = true;
        }
        
        //Note: no need to get the speaker name from the  database since it is the
        //same as the given value from CreateServiceMain.java
        
        //If the speaker has not been found, output this UserMessageBox
        if(error == true) {
            UserMessageBox myMessage = new UserMessageBox();
            myMessage.setTitle("Error with speaker");
            myMessage.setMessage("There seems to be an error with the inputted speaker \n"
                    + "Either the speaker inputted doesn't exist \n"
                    + "OR \n"
                    + "The speaker exists twice \n"
                    + "As a result the order of service has omitted the speaker.");
            myMessage.setVisible(true);
        }
    }
    
    /**
     * A method that gets the TagID referred to from field tag
     */
    public void getTag() {
        boolean error = true; //Assume the tag doesn't exist and there is an error
        int thisError = 0;
        String SQLString = "select COUNT(*) from `Tags` where `TagDescription` = '" + tag + "'";
        
        int numItems = 0;
        
        OrderOfServiceMain.dbObject.sqlString = SQLString;
        thisError = OrderOfServiceMain.dbObject.getCountBySelect ();
        
        numItems = OrderOfServiceMain.dbObject.NumberOfRecords;
        
        //If there is a tag matching the description then there is no error
        if(numItems == 1) {
            error = false;
            foundTag = true;
        }
        
        
        if(error == false) {
            SQLString = "select `TagID` from `Tags` where `TagDescription` "
                + "= '" + tag + "'";
            
            OrderOfServiceMain.dbObject.sqlString = SQLString;
            thisError = OrderOfServiceMain.dbObject.getRecordSetBySelect();
            
            if(thisError == 0) {
                try {
                    while(OrderOfServiceMain.dbObject.rs.next()) {
                        //Get the TagID from the result set and set tagID to it
                        String TagID = OrderOfServiceMain.dbObject.rs.getString("TagID");
                        
                        tagID = Integer.parseInt(TagID);
                    }
                }
                catch(SQLException ex) {
                    // Show errors in console
                    System.out.println("SQLException: " + ex.getMessage());
                    System.out.println("SQLState: " + ex.getSQLState());
                    System.out.println("VendorError: " + ex.getErrorCode());
                    thisError = ex.getErrorCode ();
                    
                    UserMessageBox MyMessage = new UserMessageBox ();
                    MyMessage.setMessage ("SQLException: " + ex.getMessage());
                    MyMessage.setVisible (true);
                }
            }
        }
        else {
            UserMessageBox myMessage = new UserMessageBox();
            myMessage.setTitle("Error with tag");
            myMessage.setMessage("There seems to be an error with the inputted tag \n"
                    + "Either the tag inputted doesn't exist \n"
                    + "OR \n"
                    + "The tag exists twice \n"
                    + "As a result the order of service has not taken the tag into account \n"
                    + "for song selection.");
            myMessage.setVisible(true);
        }
    }
    
    /**
     * The method that creates a table model with the order of service ready for 
     * SubmitService.java
     */
    public void createOrder() {
        //Set the order of service array to the orderSongs array
        String[][] orderOfService = orderSongs;
        
        //Test the result of previous methods
        for(int i = 0; i < numSongs; i++) {
            System.out.println("SongTitle: " + orderSongs[i][0] + ", Key: " + orderSongs[i][1]);
        }
        
        //If there is a speaker add to the order
        if(foundSpeaker == true) {
            //Create a new array for the order of service which is one index larger than orderSongs
            String[][] serviceOrder = new String[orderSongs.length + 1][2];
            
            //Generate a random index for the Speaker within the parameters of 0 and the serviceOrder length
            Random rand = new Random();
            int serviceIndex = rand.nextInt(serviceOrder.length);
            
            //Create a loop to put all items into new array
            for(int i = 0; i < serviceOrder.length; i++) {
                //If i is less than serviceIndex put items back in place
                if(i < serviceIndex) {
                    //Put the song in the same position as it was before
                    serviceOrder[i] = orderSongs[i];
                }
                //If i is equal to serviceIndex
                else if(i == serviceIndex) {
                    //Add the speaker to array
                    serviceOrder[i][0] = speakerName;
                    serviceOrder[i][1] = "";
                }
                //Otherwise i must be greater than serviceIndex
                else {
                    //Put the into the array the song from the position before i (in orderSongs)
                    serviceOrder[i] = orderSongs[i - 1];
                }
            }
            
            //Set array orderOfService to new array serviceOrder
            orderOfService = new String[serviceOrder.length][2];
            orderOfService = serviceOrder;
        }
        
        //Create an object CreateServiceManual and set the table model in there
        
        OrderOfService.CreateServiceManual manualCreator = new OrderOfService.CreateServiceManual();
        
        //Go through each item in the order array and add a row to the table model
        for(int i = 0; i < orderOfService.length; i++) {
            DefaultTableModel model = (DefaultTableModel) manualCreator.tblOrderOfService.getModel();
            int index = model.getRowCount();
            
            //Do this if it is not a song
            if(orderOfService[i][1].isEmpty()) {
                model.addRow(new Object[]{index, "Speaker", speakerName, null});
        
                manualCreator.tblOrderOfService.setModel(model); 
            }
            //Otherwise, it must be a song
            else {
                model.addRow(new Object[]{index, "Song", orderOfService[i][0], orderOfService[i][1]});
                
                manualCreator.tblOrderOfService.setModel(model); 
            }
            
        }
        //The Order of Service should be created now so create a SubmitSong object
        
        SubmitService submitService = new SubmitService(manualCreator);
        submitService.setVisible(true);
    }
}
